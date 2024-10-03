package com.web.core.utils.ExcelReader;

import com.creditdatamw.zerocell.converter.Converter;

public class StringToDoubleConverter implements Converter<Double> {

    @Override
    public Double convert(String value, String s1, int i) {
        return Double.parseDouble(value);
    }
}
