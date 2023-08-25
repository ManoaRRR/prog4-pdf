package com.example.prog4.controller.mapper;

import com.example.prog4.repository.employee.entity.enums.Sex;
import org.springframework.stereotype.Component;

@Component
public class SexMapper {
    public Sex toDomain(String sex){
        try {
            return Sex.valueOf(sex);
        } catch (IllegalArgumentException e){
            return null;
        }
    }
}
