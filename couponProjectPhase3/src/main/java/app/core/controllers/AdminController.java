package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CustomerService;
import app.core.utilities.JwtUtil;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {
	
	private AdminService service;
	
	
	@Autowired
	public AdminController(AdminService service) {
		this.service = service;
	}

//	@GetMapping("/login")
//	public boolean login(@RequestParam String email, String password) {
//		try {
//			return service.login(email, password);
//		} catch (CouponSystemException e) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
//		}
//		
//	}
	
	@PostMapping("/add/company")
	public Company addCompany(@RequestBody Company company, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.addCompany(company);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/company/{id}")
	public void deleteCompany(@PathVariable int id, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				service.deleteCompany(id);
			}
			else {
				throw new Exception("token not validated! ");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
			
		}
	}
	
	@GetMapping("/get/companies")
	public List<Company> getAllCompanies(@RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getAllCompanies();
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get/company/{id}")
	public Company getOneCompany(@PathVariable int id, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getOneCompany(id);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping("/update/company")
	public Company updateCompany(@RequestBody Company company, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.updateCompany(company);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
	}
	@PostMapping("/add/customer")
	public Customer addCustomer(@RequestBody Customer customer, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.addCustomer(customer);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@DeleteMapping("/delete/customer/{id}")
	public void deleteCustomer(@PathVariable int id, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				service.deleteCustomer(id);
			}
			else {
				throw new Exception("token not validated! ");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get/customers")
	public List<Customer> getAllCustomers(@RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getAllCustomers();
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/get/customer/{id}")
	public Customer getOneCustomer(@PathVariable int id, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.getOneCustomer(id);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@PutMapping("/update/customer")
	public Customer updateCustomer(@RequestBody Customer customer, @RequestHeader String token) {
		try {
			if (recieveToken(token)) {
				return service.updateCustomer(customer);
			}
			throw new Exception("token not validated! ");
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	public boolean recieveToken(String token) throws CouponSystemException {
		try {
			JwtUtil util = new JwtUtil();
			String userName = service.getEmail();
			return util.validateToken(token, userName);
		} catch (Exception e) {
			throw new CouponSystemException("token not validated: " + e.getMessage());
		}
	}
}
