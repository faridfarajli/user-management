package az.job.repository;

import az.job.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company,UUID> {
    Optional<Company> findByEmail(String username);
}
