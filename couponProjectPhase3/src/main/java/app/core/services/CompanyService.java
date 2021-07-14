package app.core.services;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.CouponImage;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
@Scope(value = "prototype")
public class CompanyService extends ClientService {

	private int companyId;
	@Value("${file.upload-dir}")
	private String storageDir;
	private Path fileStoragePath;

	@Autowired
	public CompanyService(CompanyRepository compRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		super(compRepo, custRepo, coupRepo);
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	@PostConstruct
	public void init() {
		this.fileStoragePath = Paths.get(this.storageDir).toAbsolutePath();
		System.out.println(this.fileStoragePath);
		try {
			// create the directory
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Could not create directory", e);
		}

	}

	@Override
	public boolean login(String email, String password) {
		Company company = compRepo.findByEmailIgnoreCaseAndPassword(email, password);
		return company != null;

	}

	public int getCompanyFromMailAndPassword(String email, String password) {
		Company company = compRepo.findByEmailIgnoreCaseAndPassword(email, password);
		return company.getId();
	}

	public Coupon addCoupon(CouponImage couponImage) throws CouponSystemException {
		try {
			validateCoupon(couponImage);
			Coupon coupon = convertCouponImage(couponImage);
			if (coupRepo.findByTitleAndCompanyId(coupon.getTitle(), companyId) == null) {
				Optional<Company> opt = compRepo.findById(companyId);
				if (opt.isPresent()) {
					System.out.println(coupon.getTitle() + " coupon has been added to company " + opt.get().getName());
					opt.get().addCoupon(coupon);
					System.out.println(">>>>>>>>>>>> coupon " + coupon.getTitle() + " added");
					Coupon persistCoupon = coupRepo.findByTitleAndCompanyId(coupon.getTitle(), companyId);
					return persistCoupon;
				}
				throw new CouponSystemException("Company does not exist somehow.");
			} else {
				throw new CouponSystemException("Coupon with same title already exists for this company.");
			}
		} catch (Exception e) {
			throw new CouponSystemException("Adding coupon failed - " + e.getMessage());
		}
	}

	public Coupon updateCoupon(CouponImage couponImage) throws CouponSystemException {
		try {
			validateCoupon(couponImage);
			Coupon coupon = convertCouponImage(couponImage);
			coupon.setCompany(getDetails());
			Optional<Coupon> opt1 = coupRepo.findByIdAndCompanyId(coupon.getId(), this.companyId);
			if (opt1.isPresent()) {
				Coupon couponFromDb = opt1.get();
				if (couponFromDb.getCompanyID() == coupon.getCompanyID()) {
					// make sure there isn't a different coupon with in the company that has the
					// same title
					Optional<Coupon> opt2 = coupRepo.findByTitleAndCompanyIdAndIdIsNot(coupon.getTitle(),
							this.companyId, coupon.getId());
					if (opt2.isEmpty()) {
						couponFromDb = updateWithSetters(couponFromDb, coupon);
						System.out.println(">>>>>>>>>>>>>>>> coupon " + coupon.getId() + " updated");
						return couponFromDb;
					}
					throw new CouponSystemException("a different coupon with the same title already exists in this company.");
				}
				throw new CouponSystemException("cannot change companyId");
			}
			throw new CouponSystemException("coupon not found");
		} catch (Exception e) {
			throw new CouponSystemException("Failed updating coupon - " + e.getMessage());
		}
	}

	public void deleteCoupon(int couponId) throws CouponSystemException {
		Optional<Coupon> opt = coupRepo.findById(couponId);
		if (opt.isPresent()) {
			coupRepo.deleteById(couponId);
			System.out.println(">>>>>>>>>>> coupon " + opt.get().getTitle() + " deleted.");
		} else {
			throw new CouponSystemException("deleteCoupon failed - coupon not found ");
		}
	}

	public Coupon getOneCoupon(int couponId) throws CouponSystemException {
		try {
			Optional<Coupon> opt = coupRepo.findByIdAndCompanyId(couponId, this.companyId);
			if (opt.isPresent()) {
				return opt.get();
			}
			throw new CouponSystemException("Coupon with id: " + couponId + " not found");
		} catch (Exception e) {
			throw new CouponSystemException("getOneCoupon failed -", e);
		}
	}

	public List<Coupon> getCompanyCoupons() {
		return coupRepo.findByCompanyId(companyId);
	}

	public List<Coupon> getCompanyCoupons(Category category) {
		return coupRepo.findByCompanyIdAndCategory(companyId, category);
	}

	public List<Coupon> getCompanyCoupons(double maxPrice) {
		return coupRepo.findByCompanyIdAndPriceLessThanEqual(companyId, maxPrice);
	}

	public Company getDetails() {
		Optional<Company> opt = compRepo.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	/**
	 * for use in updateCoupon method
	 * 
	 * @param currentCoupon
	 * @param toUpdate
	 * @return
	 */
	public Coupon updateWithSetters(Coupon currentCoupon, Coupon toUpdate) {
		currentCoupon.setTitle(toUpdate.getTitle());
		currentCoupon.setDescription(toUpdate.getDescription());
		currentCoupon.setCategory(toUpdate.getCategory());
		currentCoupon.setAmount(toUpdate.getAmount());
		currentCoupon.setStartDate(toUpdate.getStartDate());
		currentCoupon.setEndDate(toUpdate.getEndDate());
		currentCoupon.setPrice(toUpdate.getPrice());
		currentCoupon.setImageName(toUpdate.getImageName());
		currentCoupon.setCustomers(toUpdate.getCustomers());
		return currentCoupon;
	}

	public Coupon convertCouponImage(CouponImage couponImage) throws CouponSystemException {
		try {
			System.out.println(couponImage);
			LocalDate start = LocalDate.parse(couponImage.getStartDate().toString());
			LocalDate end = LocalDate.parse(couponImage.getEndDate().toString());
			Coupon coupon = new Coupon(couponImage.getId(), couponImage.getCategory(), couponImage.getTitle(),
					couponImage.getDescription(), start, end, couponImage.getAmount(), couponImage.getPrice(), null);
			if (couponImage.getImage() != null) {
				coupon.setImageName(storeFile(couponImage.getImage()));
				// check if coupon is added or updated: add id = 0 | updated id != 0

				// coupon is being added:
			} else if (coupon.getId() == 0) {
				coupon.setImageName("no_image");
				// coupon is being updated:
			} else {
				Optional<Coupon> opt = coupRepo.findById(coupon.getId());
				if (opt.isPresent()) {
					coupon.setImageName(opt.get().getImageName());
				}
			}
			System.out.println(coupon);
			return coupon;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponSystemException("convertCouponImage failed: " + e);
		}
	}

	public String storeFile(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (fileName.contains("..")) {
			throw new RuntimeException("file name contains ilegal caharacters");
		}
		// copy the file to the destination directory (if already exists replace)
		try {
			Path targetLocation = this.fileStoragePath.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			return "pics/" + fileName;
		} catch (IOException e) {
			throw new RuntimeException("storing file " + fileName + " failed", e);
		}
	}

	/**
	 * Checks if the coupons are legal: amount larger than zero, price isn't
	 * negative, end date hasn't passed and isn't before start date.
	 * @param coupon that is going to be added or updated.
	 * @throws CouponSystemException
	 */
	public void validateCoupon(CouponImage coupon) throws CouponSystemException {
		if (coupon.getPrice() < 0) {
			throw new CouponSystemException("Price cannot be less than zero");
		}
		if (coupon.getAmount() < 1) {
			throw new CouponSystemException("Amount cannot be less than one");
		}
		LocalDate end = LocalDate.parse(coupon.getEndDate());
		if (end.isBefore(LocalDate.now())) {
			throw new CouponSystemException("End Date cannot be in the past");
		}
		LocalDate start = LocalDate.parse(coupon.getStartDate());
		if (end.isBefore(start)) {
			throw new CouponSystemException("End Date cannot be before the Start Date");
		}

	}

}
