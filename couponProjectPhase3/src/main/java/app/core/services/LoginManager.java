package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import app.core.exceptions.CouponSystemException;
import app.core.utilities.JwtUtil;
import app.core.utilities.JwtUtil.UserDetails;

@Component
@Scope("singleton")
public class LoginManager {

	@Autowired
	private ConfigurableApplicationContext ctx;

	public LoginManager() {
	}

	public UserDetails login(String email, String password, ClientType clientType) throws CouponSystemException {
		switch (clientType) {
		case ADMINISTRATOR:
			try {
				AdminService adminS = ctx.getBean(AdminService.class);
				if (adminS.login(email, password)) {
					System.out.println("Logged in successfully as admin");
					return returnUserDetailsWithToken(password, email, 0, clientType, "Admin");
				}
				throw new CouponSystemException("wrong email/password");
			} catch (Exception e) {
				throw new CouponSystemException("Login failed - " + e.getMessage());
			}
		case COMPANY:
			CompanyService compS = ctx.getBean(CompanyService.class);
			try {
				if (compS.login(email, password)) {
					int id = compS.getCompanyFromMailAndPassword(email, password);
					compS.setCompanyId(id);
					System.out.println("Logged in successfully as company " + id);
					return returnUserDetailsWithToken(password, email, id, clientType, compS.getDetails().getName());
				}
				throw new CouponSystemException("wrong email/password");
			} catch (Exception e) {
				throw new CouponSystemException("Login failed - " + e.getMessage());
			}
		case CUSTOMER:
			CustomerService custS = ctx.getBean(CustomerService.class);
			try {
				if (custS.login(email, password)) {
					int id = custS.getIdFromMailAndPassword(email, password);
					custS.setCustomerId(id);
					System.out.println("Logged in successfully as customer " + id);
					System.out.println(custS.getDetails());
					return returnUserDetailsWithToken(password, email, id, clientType,
							custS.getDetails().getFirstName());
				}
				throw new CouponSystemException("wrong email/password");
			} catch (Exception e) {
				throw new CouponSystemException("Login failed - " + e.getMessage());
			}
		}
		return null;
	}

	// for use in administrator login
//	public UserDetails returnUserDetailsWithToken(String password, String email, ClientType clientType) {
//		UserDetails details = new UserDetails(password, email, 0, clientType);
//		JwtUtil util = new JwtUtil();
//		details.setToken(util.generateToken(details));
//		return details;
//	}
	// for use in customer and company login
	public UserDetails returnUserDetailsWithToken(String password, String email, int id, ClientType clientType,
			String name) {
		UserDetails details = new UserDetails(password, email, id, clientType, name);
		System.out.println(details);
		JwtUtil util = new JwtUtil();
		details.setToken(util.generateToken(details));
		return details;
	}

	public enum ClientType {
		ADMINISTRATOR, COMPANY, CUSTOMER;
	}

}
