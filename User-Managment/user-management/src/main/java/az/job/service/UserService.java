package az.job.service;

import az.job.dto.UserDto;
import az.job.entity.User;

public interface UserService {


    User createUser(UserDto userDto);


}
