package az.job.controller;


import az.job.dto.CompanyDto;
import az.job.dto.LoginDto;
import az.job.dto.LoginResponse;
import az.job.dto.UserDto;
import az.job.entity.Company;
import az.job.entity.User;
import az.job.service.CompanyService;
import az.job.service.UserService;

import lombok.RequiredArgsConstructor;
import org.example.JwtService;
import org.example.config.JwtAuthRequestFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequiredArgsConstructor
public class Controller {
    private final UserService userService;
    private final CompanyService companyService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



//    @PostMapping("/login")
//    public Authentication login (@RequestBody LoginDto loginDto) {
//        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password());
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return authentication;
//    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signIn(@Validated @RequestBody
                                                 LoginDto sign) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(sign.email(),
                sign.password());
        final Authentication authentication = authenticationManager.authenticate(authenticationToken);
        final String token = jwtService.issueToken(authentication);
        final LoginResponse signInResponse = new LoginResponse(JwtAuthRequestFilter.BEARER, token, "refresh");
        HttpHeaders httpHeaders = new HttpHeaders();
//        setCookies(httpHeaders, signInResponse);
        return new ResponseEntity<>(signInResponse, httpHeaders, HttpStatus.OK);
    }


    @PostMapping("/create-user")
    private User createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @PostMapping("/create-company")
    private Company createCompany(@RequestBody CompanyDto companyDto){
       return companyService.createCompany(companyDto);
    }






}
