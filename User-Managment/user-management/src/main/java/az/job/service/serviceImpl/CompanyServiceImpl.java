package az.job.service.serviceImpl;

import az.job.dto.CompanyDto;
import az.job.entity.Company;
import az.job.entity.Role;
import az.job.repository.CompanyRepository;
import az.job.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public Company createCompany(CompanyDto companyDto) {
        Company company = new Company();
        company.setId(companyDto.id());
        company.setCompanyName(companyDto.companyName());
        company.setEmail(companyDto.email());
        company.setPassword(passwordEncoder.encode(companyDto.password()));
        company.setPhoneNumber(companyDto.phoneNumber());
        company.setRole(Role.COMPANY);
        companyRepository.save(company);
        return company;
    }
}
