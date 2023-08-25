package com.example.prog4.controller.converter;

import com.example.prog4.model.utilities.Page;
import org.springframework.core.convert.converter.Converter;

public class PageConverter implements Converter<String, Page> {
    @Override
    public Page convert(String source) {
        return new Page(Integer.valueOf(source));
    }
}
