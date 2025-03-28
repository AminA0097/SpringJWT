package com.springsecurity.springjwt.Dto;



import com.springsecurity.springjwt.Entities.User;
import java.util.List;

public interface Mappers {
    List<UserDto> userToDto(List<User> users);
}
