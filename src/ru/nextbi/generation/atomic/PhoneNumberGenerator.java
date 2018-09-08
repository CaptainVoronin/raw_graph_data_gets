package ru.nextbi.generation.atomic;

public class PhoneNumberGenerator implements IGenerator{
    @Override
    public void setParamString(String rawParams) throws Exception{

    }

    @Override
    public String getValue()
    {
        return StringGenerator.randomNumeric(11);
    }
}
