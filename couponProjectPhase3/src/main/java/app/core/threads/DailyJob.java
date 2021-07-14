package app.core.threads;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import app.core.services.JobService;

@Component
public class DailyJob implements Runnable {

	private Thread thread;
	private boolean quit = false;
	@Value("${daily.job.sleep.duration.in.minutes:1}")
	private long sleepDurationInMinutes;
	@Autowired
	private JobService service;

	public DailyJob() {}

	@PostConstruct
	public void init() {
		this.thread = new Thread(this);
		sleepDurationInMinutes = TimeUnit.MINUTES.toMillis(sleepDurationInMinutes);
		this.thread.start();
		System.out.println("DailyJob is has started.");

	}

	@Override
	public void run() {
			try {
				while (!quit) {
				System.out.println("DailyJob is active. Will now attempt to delete bad coupons.");
				long expiredCouponsCount = service.deleteExpiredCoupons();
				System.out.println(
						expiredCouponsCount + " expired coupons have been deleted by the DailyJob. The world is safe.");
				Thread.sleep(sleepDurationInMinutes);
				}
			}
			 catch (InterruptedException e) {
				quit = true;
				
			} 
			catch (Exception e) {
//				ensures the thread stops running even if interrupted 
//				not through stop() method
				quit = true;
				System.out.println("DailyJob failed to delete coupons " + e.getMessage());

			}
			finally {
				System.out.println("DailyJob has finished shutting down.");
			}
	}

	@PreDestroy
	public void stop() {
		System.out.println("DailyJob is starting to shut down.");
		quit = true;
		thread.interrupt();
	}

	public boolean isQuit() {
		return quit;
	}

	public long getSleepDurationInMinutes() {
		return sleepDurationInMinutes;
	}
}
