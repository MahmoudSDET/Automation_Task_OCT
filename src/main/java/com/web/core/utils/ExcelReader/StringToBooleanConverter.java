package com.web.core.utils.ExcelReader;

import com.creditdatamw.zerocell.converter.Converter;

public class StringToBooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(String value, String columnname, int rownumber) {
        return value.equalsIgnoreCase("true");
    }
}
