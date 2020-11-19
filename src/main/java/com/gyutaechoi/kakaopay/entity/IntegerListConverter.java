package com.gyutaechoi.kakaopay.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List<Integer>를 DB에 저장할 때, comma-separated string으로 저장함.
 */
@Converter
public class IntegerListConverter implements AttributeConverter<List<Integer>, String> {

    @Override
    public String convertToDatabaseColumn(List<Integer> attribute) {
        return attribute.stream().map(Object::toString).collect(Collectors.joining(","));
    }

    @Override
    public List<Integer> convertToEntityAttribute(String dbData) {
        return Arrays.asList(dbData.split("\\s*,\\s*")).stream().map(Integer::valueOf).collect(Collectors.toList());
    }
}

