package com.springsecurity.springjwt.Config;

import jakarta.persistence.AttributeConverter;

public class BooleanConvertor implements AttributeConverter<Boolean, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Boolean aBoolean) {
        return (aBoolean != null && aBoolean) ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer integer) {
        return integer != null && integer == 1;
    }
}
