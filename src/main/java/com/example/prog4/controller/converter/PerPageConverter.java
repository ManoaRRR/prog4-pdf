package com.example.prog4.controller.converter;

import com.example.prog4.model.utilities.PerPage;
import org.springframework.core.convert.converter.Converter;

public class PerPageConverter implements Converter<String, PerPage> {
    @Override
    public PerPage convert(String source) {
        return new PerPage(Integer.valueOf(source));
    }
}
