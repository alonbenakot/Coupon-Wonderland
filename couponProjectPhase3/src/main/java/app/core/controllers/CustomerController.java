package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.CustomerService;
import app.core.utilities.JwtUtil;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

	private ConfigurableApplicationContext ctx;
	private CustomerService service;

	@Autowired
	public CustomerController(CustomerService service, ConfigurableApplicationContext ctx) {
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

	@GetMapping("/coupons")
	public List<Coupon> getAllCoupons(@RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getAllCoupons();
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@GetMapping("/coupons/category")
	public List<Coupon> getAllCoupons(@RequestParam Category category, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getAllCoupons(category);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@GetMapping("/coupons/max-price")
	public List<Coupon> getAllCoupons(@RequestParam double maxPrice, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getAllCoupons(maxPrice);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());

		}
	}

	@GetMapping("/details")
	public Customer getDetails(@RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getDetails();
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PutMapping("/purchase")
	public Coupon purchaseCoupon(@RequestBody Coupon coupon, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.purchaseCoupon(coupon);
			}  
				throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	public boolean recieveToken(String token) throws CouponSystemException {
		try {
			JwtUtil util = new JwtUtil();
			int id = util.extractUserId(token);
			service = ctx.getBean(CustomerService.class);
			service.setCustomerId(id);
			String userName = service.getDetails().getEmail();
			return util.validateToken(token, userName);
		} catch (CouponSystemException e) {
			throw new CouponSystemException("token not validated: " + e.getMessage());
		}
	}
}
