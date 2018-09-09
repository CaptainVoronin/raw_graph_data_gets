package ru.nextbi.generation.atomic;

import java.util.Map;

public class EMailAddressGenerator implements IGenerator
{
    String domain;

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params)
    {
        domain = "ru";
    }

    @Override
    public String getValue()
    {
        return  StringGenerator.randomAlphaNumeric( 8 ) + "@" + StringGenerator.randomAlphaNumeric( 10 ) + "." + domain;
    }
}
