package app.core;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import app.core.exceptions.CouponSystemException;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CouponProjectPhase3ApplicationRun {

	public static void main(String[] args) throws CouponSystemException {
	ConfigurableApplicationContext ctx = SpringApplication.run(CouponProjectPhase3ApplicationRun.class, args);
	}
}
