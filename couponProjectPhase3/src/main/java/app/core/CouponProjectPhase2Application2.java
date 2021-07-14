//package app.core;
//
//import java.time.LocalDate;
//import java.util.concurrent.TimeUnit;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.ConfigurableApplicationContext;
//
//import app.core.entities.Company;
//import app.core.entities.Coupon;
//import app.core.entities.Category;
//import app.core.entities.Customer;
//import app.core.exceptions.CouponSystemException;
//import app.core.services.AdminService;
//import app.core.services.CompanyService;
//import app.core.services.CustomerService;
//import app.core.services.LoginManager;
//import app.core.services.LoginManager.ClientType;
//
//@SpringBootApplication
//public class CouponProjectPhase2Application2 {
//
//	public static void main(String[] args) throws CouponSystemException {
//		try (ConfigurableApplicationContext ctx = SpringApplication.run(CouponProjectPhase2Application2.class, args);) {
//			System.out.println();
//			System.out.println("============================starting the application================================");
//			System.out.println();
//			initDB(ctx);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}
//	}
//	
//	public static void initDB(ConfigurableApplicationContext ctx) throws CouponSystemException, InterruptedException {
//
//		LoginManager loginManager = ctx.getBean(LoginManager.class);
//		AdminService admin = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMINISTRATOR);
//
//		
//
//		try {
//			// adding companies to database
//			Company company1 = admin
//					.addCompany(new Company("Coupon City", "jakobsons.htriemel@gmail.com", "shtriemel"));
//			Company company2 = admin.addCompany(new Company("Disco Palace", "disco.disco.gmail@com", "mozart"));
//			Company company3 = admin.addCompany(new Company("Evil Corp", "justin.bieber@gmail", "trump"));
//			Company company4 = admin.addCompany(new Company("Alphabet", "google.mail@hotmail.com", "steve jobs"));
//			System.out.println("======================================================================");
//			// adding customers to database
//			Customer customer1 = admin.addCustomer(new Customer("Yehonadav", "Melamed", "yoho.gmail.com", "yoho123"));
//			Customer customer2 = admin
//					.addCustomer(new Customer("Carmela", "Meluha", "salty.caramel.gmail.com", "chocolate"));
//			Customer customer3 = admin.addCustomer(new Customer("Kobi", "Cohen", "kowboy.gmail.com", "kowboywithAc"));
//			Customer customer4 = admin.addCustomer(new Customer("Michal", "Ganot", "michanot.gmail.com", "miga858"));
//			System.out.println("======================================================================");
//			// admin delete methods
//			admin.deleteCompany(company1.getId());
//			admin.deleteCustomer(customer2.getId());
//
//			// company services login with loginManager
//			
//			CompanyService disco = (CompanyService) loginManager.login(company2.getEmail(), company2.getPassword(),
//					ClientType.COMPANY);
//			CompanyService evil = (CompanyService) loginManager.login(company3.getEmail(), company3.getPassword(),
//					ClientType.COMPANY);
//			CompanyService alpha = (CompanyService) loginManager.login(company4.getEmail(), company4.getPassword(),
//					ClientType.COMPANY);
//
//			System.out.println(evil.getDetails());
//			System.out.println(disco.getDetails());
//
//			System.out.println("======================================================================");
//			// customer services login with loginManager
//			CustomerService yehonadav = (CustomerService) loginManager.login(customer1.getEmail(),
//					customer1.getPassword(), ClientType.CUSTOMER);
//			CustomerService kobi = (CustomerService) loginManager.login(customer3.getEmail(), customer3.getPassword(),
//					ClientType.CUSTOMER);
//			CustomerService michal = (CustomerService) loginManager.login(customer4.getEmail(), customer4.getPassword(),
//					ClientType.CUSTOMER);
//			System.out.println("======================================================================");
//
//			// admin update company and customer methods
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> admin update company");
//
//			admin.updateCompany(new Company(evil.getCompanyId(), "Evil Corp", "justin.timberlake@gmail", "trump"));
//			admin.updateCustomer(
//					new Customer(michal.getCustomerId(), "Michaelagelo", "Ganot", "michanot.gmail.com", "trueArtist"));
//			System.out.println("======================================================================");
//			// adding coupons
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> adding  coupons");
//			Coupon coupon1 = alpha
//					.addCoupon(new Coupon(Category.MAGIC, "search engine", "one free search without commercials",
//							LocalDate.now(), LocalDate.of(2021, 04, 9), 2, 1000, "image", alpha.getDetails()));
//			Coupon coupon2 = alpha.addCoupon(new Coupon(Category.ELECTRICITY, "alexa", "two dollar discount",
//					LocalDate.now(), LocalDate.now().minusDays(5), 6, 2, "image", alpha.getDetails()));
//			Coupon coupon3 = alpha.addCoupon(new Coupon(Category.FOOD, "invisible hot dog", "half off", LocalDate.now(),
//					LocalDate.now().plusDays(5), 30, 10, "image", alpha.getDetails()));
//			Coupon coupon4 = alpha
//					.addCoupon(new Coupon(Category.HYGENE_PRODUCTS, "body cleaning nano-bots", "one extra nano-bot",
//							LocalDate.now(), LocalDate.now().plusDays(5), 30, 10, "image", alpha.getDetails()));
//			Coupon coupon5 = evil.addCoupon(new Coupon(Category.FOOD, "peanut butter", "5% dicount", LocalDate.now(),
//					LocalDate.now().plusDays(2), 4, 17, "image", evil.getDetails()));
//			Coupon coupon6 = evil.addCoupon(new Coupon(Category.FOOD, "salted chocolate", "5% dicount", LocalDate.now(),
//					LocalDate.now().plusDays(2), 30, 34, "image", evil.getDetails()));
//			Coupon coupon7 = evil.addCoupon(new Coupon(Category.FOOD, "halva", "5% dicount", LocalDate.now(),
//					LocalDate.now().plusDays(2), 30, 10, "image", evil.getDetails()));
//			Coupon coupon8 = evil.addCoupon(new Coupon(Category.FOOD, "light beer", "5% dicount", LocalDate.now(),
//					LocalDate.now().plusDays(2), 32, 290, "image", evil.getDetails()));
//			Coupon coupon9 = evil.addCoupon(new Coupon(Category.FOOD, "small hamburgers", "5% dicount", LocalDate.now(),
//					LocalDate.now().minusDays(2), 13, 653.78, "image", evil.getDetails()));
//			Coupon coupon10 = disco.addCoupon(new Coupon(Category.VACATION, "disco resort", "free drinks at the bar",
//					LocalDate.now(), LocalDate.now().plusDays(2), 5, 10, "image", disco.getDetails()));
//			Coupon coupon11 = disco
//					.addCoupon(new Coupon(Category.ELECTRICITY, "disco watch", "free retuning once in ten years",
//							LocalDate.now(), LocalDate.now().plusDays(6), 21, 60, "image", disco.getDetails()));
//			Coupon coupon12 = disco.addCoupon(new Coupon(Category.ELECTRICITY, "disco ball", "5% dicount",
//					LocalDate.now(), LocalDate.now().minusDays(5), 3, 32.5, "image", disco.getDetails()));
//			Coupon coupon13 = disco.addCoupon(new Coupon(Category.ELECTRICITY, "roller skates", "free disco vibes",
//					LocalDate.now(), LocalDate.now().plusDays(6), 1, 564.68, "image", disco.getDetails()));
//			System.out.println("======================================================================");
//			// company service methods
//			disco.deleteCoupon(coupon11.getId());
//			evil.deleteCoupon(coupon9.getId());
//
//			System.out.println(disco.getCompanyCoupons());
//			System.out.println(alpha.getCompanyCoupons(Category.MAGIC));
//			System.out.println(evil.getCompanyCoupons(35));
//
//			coupon13.setDescription("electricity free!");
//			coupon13.setCategory(Category.TRANSPORT);
//			disco.updateCoupon(coupon13);
//			System.out.println(disco.getOneCoupon(coupon13.getId()));
//
//			System.out.println("======================================================================");
//			// customer service methods:
//			yehonadav.purchaseCoupon(coupon1);
//			yehonadav.purchaseCoupon(coupon6);
//			yehonadav.purchaseCoupon(coupon8);
//			yehonadav.purchaseCoupon(coupon13);
//			kobi.purchaseCoupon(coupon2);
//			kobi.purchaseCoupon(coupon4);
//			kobi.purchaseCoupon(coupon7);
//			michal.purchaseCoupon(coupon3);
//			michal.purchaseCoupon(coupon5);
//			michal.purchaseCoupon(coupon10);
//			michal.purchaseCoupon(coupon12);
//			System.out.println("======================================================================");
//			System.out.println(yehonadav.getDetails());
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> all of " + michal.getDetails().getFirstName() + "'s coupons");
//			System.out.println(michal.getAllCoupons());
//			System.out.println(
//					">>>>>>>>>>>>>>>>>>>>>>> all of " + michal.getDetails().getFirstName() + "'s electricity coupons");
//			System.out.println(michal.getAllCoupons(Category.ELECTRICITY));
//			System.out.println(
//					">>>>>>>>>>>>>>>>>>>>>>> all of " + michal.getDetails().getFirstName() + "'s food coupons");
//			System.out.println(michal.getAllCoupons(Category.FOOD));
//			System.out.println(">>>>>>>>>>>>>>>>>>>>>>> all of " + kobi.getDetails().getFirstName()
//					+ "'s coupons with price equal or less than 5");
//			System.out.println(kobi.getAllCoupons(5));
//			System.out.println("======================================================================");
//
//			Thread.sleep(TimeUnit.SECONDS.toMillis(70));
//		} catch (Exception e) {
//			throw new CouponSystemException(e);
//		}
//
//	}
//
//
//}