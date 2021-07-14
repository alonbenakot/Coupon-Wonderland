package app.core.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer>{
	
	Coupon findByTitleAndCompanyId(String title, int companyId);
	
	Optional<Coupon> findByIdAndCompanyId(int couponId, int CompanyId);
	
	Optional<Coupon> findByTitleAndCompanyIdAndIdIsNot(String title, int companyId, int couponId);
	
	List<Coupon> findByCompanyId(int companyId);
	
	List<Coupon> findByCompanyIdAndCategory(int companyId, Category category);
	
	List<Coupon> findByCompanyIdAndPriceLessThanEqual(int companyId, double price);
	
	List<Coupon> findByEndDateIsBefore(LocalDate date);
	
	List<Coupon> findByCustomersIdAndCategory(int customerId,Category category);

	List<Coupon> findByAmountGreaterThanAndIdIsNot(int amount,int id);

	List<Coupon> findByCustomersIdAndPriceLessThanEqual(int customerId,double price);
	
	List<Coupon> findByCategory(Category category);
	
	List<Coupon> findByPriceLessThanEqual(double price);

	
	long deleteByEndDateBefore(LocalDate date); 
	
	
	
}
