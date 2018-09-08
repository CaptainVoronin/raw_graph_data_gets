package ru.nextbi.generation.atomic;

public class EMailAddressGenerator implements IGenerator
{
    String domain;

    @Override
    public void setParamString( String rawParams )
    {
        domain = "ru";
    }

    @Override
    public String getValue()
    {
        return  StringGenerator.randomAlphaNumeric( 8 ) + "@" + StringGenerator.randomAlphaNumeric( 10 ) + "." + domain;
    }
}
