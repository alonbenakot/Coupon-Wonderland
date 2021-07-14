package app.core.services;


import app.core.exceptions.CouponSystemException;
import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

public abstract class ClientService {

	protected CompanyRepository compRepo;
	protected CustomerRepository custRepo;
	protected CouponRepository coupRepo;

	public ClientService(CompanyRepository compRepo, CustomerRepository custRepo, CouponRepository coupRepo) {
		this.compRepo = compRepo;
		this.custRepo = custRepo;
		this.coupRepo = coupRepo;
	}

	abstract boolean login(String email, String password) throws CouponSystemException;
}
