package com.springsecurity.springjwt.Dto;

import lombok.extern.slf4j.*;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import com.springsecurity.springjwt.Entities.User;

@Service
@Slf4j
public class MappersImpl implements Mappers {

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
