package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.core.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
	Optional<Customer> findByEmailIgnoreCaseAndPassword(String email, String password);
	
	Customer findByEmailAndIdIsNot(String email, int id);
	
	Optional<Customer> findByEmail(String email);
	
}
