package app.core.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Category;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.services.GuestService;

@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = "http://localhost:3000")
public class GuestController {

	private GuestService service;

	@Autowired
	public GuestController(GuestService service) {
		this.service = service;
	}
	
	@GetMapping("/get/coupons/all")
	public List<Coupon> getAllSiteCoupons() {
		try {
			return service.getAllSiteCoupons();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	
	@GetMapping("/get/coupons/category/{category}")
	public List<Coupon> getAllSiteCategory(@PathVariable Category category) {
		try {
			return service.getAllSiteCategoryCoupons(category);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	@GetMapping("/get/coupons/max-price/{maxPrice}")
	public List<Coupon> getgetAllSiteMaxPriceCoupons(@PathVariable double maxPrice) {
		try {
			return service.getAllSiteMaxPriceCoupons(maxPrice);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PostMapping("/add/customer")
	public Customer addCustomer(@RequestBody Customer customer) {
		try {
				return service.addCustomer(customer);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
}
