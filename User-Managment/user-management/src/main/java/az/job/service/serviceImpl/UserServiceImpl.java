package az.job.service.serviceImpl;

import az.job.dto.UserDto;
import az.job.entity.Role;
import az.job.entity.User;
import az.job.repository.UserRepository;
import az.job.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @SneakyThrows
    @Override
    public User createUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.id());
        user.setName(userDto.name());
        user.setSurname(userDto.surname());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setPhoneNumber(userDto.phoneNumber());
        user.setRole(Role.USER);
        userRepository.save(user);
        return user;
    }
}
