package ru.nextbi.generation.atomic;

import java.util.Map;

public class PhoneNumberGenerator extends AbstractGenerator{
    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{

    }

    @Override
    public String getValue()
    {
        return StringGenerator.randomNumeric(11);
    }

}
