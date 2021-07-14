package app.core.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
@Scope(value = "prototype")
public class CustomerService extends ClientService {

	private int customerId;

	@Autowired
	public CustomerService(CompanyRepository compRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		super(compRepo, custRepo, coupRepo);
	}

	@Override
	public boolean login(String email, String password) throws CouponSystemException {
		Optional<Customer> opt = custRepo.findByEmailIgnoreCaseAndPassword(email, password);
		return opt.isPresent();

	}

	public int getIdFromMailAndPassword(String email, String password) throws CouponSystemException {
		Optional<Customer> opt = custRepo.findByEmailIgnoreCaseAndPassword(email, password);
		if (opt.isPresent()) {
			return opt.get().getId();
		}
		throw new CouponSystemException("Login failed - incorrect email/password ");
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public Coupon purchaseCoupon(Coupon coupon) throws CouponSystemException {
		try {
			Customer customer = getDetails();
			Coupon couponFromDb = coupRepo.findByTitleAndCompanyId(coupon.getTitle(), coupon.getCompanyID());
			List<Coupon> coupons = customer.getCoupons();
			if (!coupons.contains(couponFromDb)) {
				if (couponFromDb.getAmount() > 0) {
				 if (!couponFromDb.getEndDate().isBefore(LocalDate.now())) {
					customer.addCoupon(couponFromDb);
					couponFromDb.setAmount(couponFromDb.getAmount() - 1);
					System.out.println(">>>>>>>>>>> customer " + customer.getFirstName() + " " + customer.getLastName()
							+ " purchased coupon " + couponFromDb.getTitle());
					return couponFromDb;
				 } else {
					 throw new CouponSystemException(
							 couponFromDb.getTitle() + " is expired. Sorry for the confusion");
				 }
				} else {
					throw new CouponSystemException(
							 "There are not any coupons of " + couponFromDb.getTitle() + " left.");
				}
			} else {
				throw new CouponSystemException("customer already purchased coupon " + couponFromDb.getTitle()
						+ ". Can only prchase the same coupon once.");
			}
		} catch (Exception e) {
			throw new CouponSystemException("purchaseCoupon failed - " + e.getMessage());
		}
	}

	public List<Coupon> getAllCoupons() throws CouponSystemException {
		try {
			Customer customer = getDetails();
			List<Coupon> coupons = customer.getCoupons();
			if (!coupons.isEmpty()) {
				return coupons;
			} else {
				return coupons;
			}
		} catch (Exception e) {
			throw new CouponSystemException("getAllCoupons failed - " + e.getMessage());
		}
	}

	public List<Coupon> getAllCoupons(Category category) throws CouponSystemException {
		try {
			return coupRepo.findByCustomersIdAndCategory(customerId, category);
		} catch (Exception e) {
			throw new CouponSystemException("getAllCoupons failed - " + e.getMessage());
		}
	}

	public List<Coupon> getAllCoupons(double maxPrice) throws CouponSystemException {
		try {
			return coupRepo.findByCustomersIdAndPriceLessThanEqual(customerId, maxPrice);
		} catch (Exception e) {
			throw new CouponSystemException("getAllCoupons failed - " + e.getMessage());
		}
	}

	public Customer getDetails() throws CouponSystemException {
		Optional<Customer> opt = custRepo.findById(customerId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CouponSystemException("getDetails failed - customer is null");
	}
}
