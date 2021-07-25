package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.exceptions.CouponSystemException;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.utilities.JwtUtil;

@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {

	private CompanyService service;
	private ConfigurableApplicationContext ctx;
	
	@Autowired
	public CompanyController(CompanyService service, ConfigurableApplicationContext ctx) {
		this.service = service;
		this.ctx = ctx;
	}

//	@GetMapping("/login")
//	public boolean login(@RequestParam String email, String password) {
//		try {
//			return service.login(email, password);
//		} catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//		}
//	}
	
	@PostMapping("/add-coupon")
	public Coupon addCoupon(@ModelAttribute Coupon coupon, BindingResult result , @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.addCoupon(coupon);
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@DeleteMapping("/delete-coupon/{id}")
	public void deleteCoupon(@PathVariable int id, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				service.deleteCoupon(id);
			} else {
				throw new Exception("token not validated!");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/details")
	public Company getDetails(@RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getDetails();
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@GetMapping("/get/one-coupon/{id}")
	public Coupon getOneCoupon(@PathVariable int id, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getOneCoupon(id);
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get/company-coupons")
	public List<Coupon> getCompanyCoupons(@RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getCompanyCoupons();
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get/company-coupons/category/{category}")
	public List<Coupon> getCompanyCoupons(@PathVariable Category category, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getCompanyCoupons(category);
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	@GetMapping("/get/company-coupons/max-price/{maxPrice}")
	public List<Coupon> getCompanyCoupons(@PathVariable double maxPrice, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getCompanyCoupons(maxPrice);
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping("/update/coupon")
	public Coupon updateCoupon(@ModelAttribute Coupon coupon, BindingResult result , @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.updateCoupon(coupon);
			}
			throw new Exception("token not validated!");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	public boolean recieveToken(String token) throws CouponSystemException {
		try {
			JwtUtil util = new JwtUtil();
			int id = util.extractUserId(token);
			service = ctx.getBean(CompanyService.class);
			service.setCompanyId(id);
			String userName = service.getDetails().getEmail();
			return util.validateToken(token, userName);
		} catch (Exception e) {
			throw new CouponSystemException("token not validated: " + e.getMessage());
		}
	}
	
}
