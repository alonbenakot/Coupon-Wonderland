package app.core.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class GuestService {
	
	private CouponRepository coupRepo;
	private CustomerRepository custRepo;

	@Autowired
	public GuestService(CouponRepository coupRepo, CustomerRepository custRepo) {
		this.coupRepo = coupRepo;
		this.custRepo = custRepo;
	}
	
	public List<Coupon> getAllSiteCoupons() throws CouponSystemException {
		try {
			return coupRepo.findAll();
		} catch (Exception e) {
			throw new CouponSystemException("getAllSiteCoupons failed: " + e.getMessage());
		}
	}
	
	public List<Coupon> getAllSiteCategoryCoupons(Category category) throws CouponSystemException {
		try {
			return coupRepo.findByCategory(category);
		} catch (Exception e) {
			throw new CouponSystemException("getAllSiteCategoryCoupons failed: " + e.getMessage());
		}
	}
	
	public List<Coupon> getAllSiteMaxPriceCoupons(double maxPrice) throws CouponSystemException {
		try {
			return coupRepo.findByPriceLessThanEqual(maxPrice);
		} catch (Exception e) {
			throw new CouponSystemException("getAllSiteMaxPriceCoupons failed: " + e.getMessage());
		}
	}

	public Customer addCustomer(Customer customer) throws CouponSystemException {
		try {
			System.out.println(customer);
			System.out.println(custRepo  + " is here");
			custRepo.save(customer);
			System.out.println(">>>>>>>>>>>" + customer + "added");
			return customer;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CouponSystemException("addCustomer failed - customer with id/email already exists", e);
		}
	}
}
