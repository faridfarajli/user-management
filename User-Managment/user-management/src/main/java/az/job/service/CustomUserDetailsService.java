package az.job.service;

import az.job.entity.Company;
import az.job.entity.User;
import az.job.repository.CompanyRepository;
import az.job.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        Optional<Company> company = companyRepository.findByEmail(username);

        if (user.isPresent()) {
            var roleOfStudent = Stream.of(user.get().getRole())
                    .map(x -> new SimpleGrantedAuthority(x.name()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), roleOfStudent);
        } else if (company.isPresent()) {
            var roleOfTeacher = Stream.of(company.get().getRole())
                    .map(x -> new SimpleGrantedAuthority(x.name()))
                    .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(company.get().getEmail(), company.get().getPassword(), roleOfTeacher);
        } else {
            throw new UsernameNotFoundException("Email or password wrong");
        }
    }
}
