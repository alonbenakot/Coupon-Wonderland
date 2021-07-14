package app.core.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

@Service
@Transactional
public class AdminService extends ClientService {

	private String email = "admin@admin.com";
	private String password = "admin";

	@Autowired
	public AdminService(CompanyRepository compRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		super(compRepo, custRepo, coupRepo);
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public boolean login(String email, String password) throws CouponSystemException {
		return email.equals(this.email) && password.equals(this.password);
	}

	public Company addCompany(Company company) throws CouponSystemException {
		try {
			if (compRepo.findByEmail(company.getEmail()).isEmpty()) {
				if (compRepo.findByName(company.getName()).isEmpty()) {
					Company c = compRepo.save(company);
					System.out.println(">>>>>>>>> company " + c.getName() + "has been added");
					return c;
				}
				else {
					throw new CouponSystemException("Company with the same name already exists.");
				}
			} else {
				throw new CouponSystemException("Company with the same email already exists.");
			}
		} catch (Exception e) {
			throw new CouponSystemException("Adding company failed - " + e.getMessage());
		}
	}

	public Company updateCompany(Company company) throws CouponSystemException {
		try {
			Optional<Company> opt = compRepo.findById(company.getId());
			if (opt.isPresent()) {
				if (compRepo.findByEmailAndIdIsNot(company.getEmail(), company.getId()).isEmpty()) {
					Company updatedCompany = opt.get();
					if (updatedCompany.getName().equals(company.getName())) {
						updatedCompany.setEmail(company.getEmail());
						updatedCompany.setPassword(company.getPassword());
						updatedCompany.setCoupons(company.getCoupons());
						System.out.println(">>>>>>>> company updated");
						return updatedCompany;
					
				} else {
					throw new CouponSystemException("Company " + company.getName()
							+ " cannot be changed the new name: " + company.getName() + ".");
				}
				} else {
					throw new CouponSystemException("Another company with the same email already exists.");
				}
			}
			throw new CouponSystemException("Company not found");
		} catch (Exception e) {
			throw new CouponSystemException("Failed to update company - " + e.getMessage());
		}
	}

	public void deleteCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = compRepo.findById(companyId);
		if (opt.isPresent()) {
			compRepo.deleteById(companyId);
			System.out.println(">>>>>>>>>> company deleted");
		} else {
			throw new CouponSystemException("deleteCompany failed - company with id " + companyId + " not found.");
		}
	}

	public List<Company> getAllCompanies() {
		return compRepo.findAll();
	}

	public Company getOneCompany(int companyId) throws CouponSystemException {
		Optional<Company> opt = compRepo.findById(companyId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CouponSystemException("company with id " + companyId + " not found");

	}

	public Customer addCustomer(Customer customer) throws CouponSystemException {
		try {
			if (custRepo.findByEmail(customer.getEmail()).isEmpty()) {
				custRepo.save(customer);
				System.out.println(">>>>>>>>>>>" + customer + "added");
				return customer;
			} else {
				throw new CouponSystemException("Customer with the same email already exists.");
			}
		} catch (Exception e) {
			throw new CouponSystemException("Failed to add customer - " + e.getMessage());
		}
	}

	public Customer updateCustomer(Customer customer) throws CouponSystemException {
		try {
			Optional<Customer> opt = custRepo.findById(customer.getId());
			if (opt.isPresent()) {
				if (custRepo.findByEmailAndIdIsNot(customer.getEmail(), customer.getId()) == null) {
					Customer updatedCustomer = opt.get();
					updatedCustomer.setFirstName(customer.getFirstName());
					updatedCustomer.setLastName(customer.getLastName());
					updatedCustomer.setEmail(customer.getEmail());
					updatedCustomer.setPassword(customer.getPassword());
					updatedCustomer.setCoupons(customer.getCoupons());
					System.out.println(">>>>>>>>>>> customer updated");
					return updatedCustomer;
				}
				throw new CouponSystemException("Customer with the same email already exists.");
			}
			throw new CouponSystemException("Customer to update with same id: " + customer.getId() + " not found.");
		} catch (Exception e) {
			throw new CouponSystemException("Update Customer failed - " + e.getMessage());
		}
	}

	public void deleteCustomer(int customerId) throws CouponSystemException {
		Optional<Customer> opt = custRepo.findById(customerId);
		if (opt.isPresent()) {
			custRepo.deleteById(customerId);
			System.out.println("customer deleted");
		} else {
			throw new CouponSystemException("deleteCustomer failed - customer with id " + customerId + " not found.");
		}
	}

	public List<Customer> getAllCustomers() {
		return custRepo.findAll();
	}

	public Customer getOneCustomer(int customerId) throws CouponSystemException {
		Optional<Customer> opt = custRepo.findById(customerId);
		if (opt.isPresent()) {
			return opt.get();
		}
		throw new CouponSystemException("getOneCustomer failed - customer with id " + customerId + " not found");
	}
}
