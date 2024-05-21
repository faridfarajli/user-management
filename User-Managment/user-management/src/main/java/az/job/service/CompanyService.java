package az.job.service;

import az.job.dto.CompanyDto;
import az.job.entity.Company;

public interface CompanyService {

    Company createCompany(CompanyDto companyDto);
}
