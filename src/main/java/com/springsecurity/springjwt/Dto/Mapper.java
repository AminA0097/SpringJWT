package com.springsecurity.springjwt.Dto;

import com.springsecurity.springjwt.Entities.User;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
@Service
public class Mapper implements Mappers{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(Mapper.class);

    @Override
    public List<UserDto> userToDto(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = new UserDto();
            Class<?> userClass = user.getClass();
            while (userClass != null) {
                for (Field field : userClass.getDeclaredFields()) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(user);
                        try {
                            Field dtoField = UserDto.class.getDeclaredField(field.getName());
                            dtoField.setAccessible(true);
                            dtoField.set(userDto, value);
                        } catch (NoSuchFieldException e) {
                            log.warn("Field {} not found", field.getName());
                        }
                    } catch (IllegalAccessException e) {
                        log.warn("Field {} not accessible", field.getName());
                    }
                }
                userClass = userClass.getSuperclass();
            }

            userDtos.add(userDto);
        }

        return userDtos;
    }
}
