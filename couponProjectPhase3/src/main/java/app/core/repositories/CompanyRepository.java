package app.core.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.core.entities.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	Company findByEmailIgnoreCaseAndPassword(String email, String password);
	
	Optional<Company> findByEmailAndIdIsNot(String email, int id);
	
	Optional<Company> findByName(String name);
	
	Optional<Company> findByEmail(String email);
	

}
