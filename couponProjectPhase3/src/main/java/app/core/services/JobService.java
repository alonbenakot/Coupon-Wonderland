package app.core.services;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.core.repositories.CouponRepository;

@Service
@Transactional
public class JobService {

	private CouponRepository coupRepo;

	@Autowired
	public JobService(CouponRepository coupRepo) {
		this.coupRepo = coupRepo;
	}
	
	public long deleteExpiredCoupons() {
		return coupRepo.deleteByEndDateBefore(LocalDate.now());
	}
}
