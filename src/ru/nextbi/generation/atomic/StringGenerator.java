package ru.nextbi.generation.atomic;

import java.util.Map;

public class StringGenerator extends AbstractGenerator
{
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    int length;

    public StringGenerator( )
    {
        length = 10;
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception
    {
        String buff = params.get( "length" );
        if( buff != null )
            length = Integer.parseInt( buff );
    }

    @Override
    public String getValue()
    {
        return randomAlphaNumeric( length );
    }

    @Override
    public void initialize() throws Exception{

    }

    static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static final String NUMERIC_STRING = "0123456789";

    public static String randomAlphaNumeric(int count)
    {
        return randomString( ALPHA_NUMERIC_STRING, count );
    }

    public static String randomNumeric(int count)
    {
        return randomString( NUMERIC_STRING, count );
    }

    static String randomString(String aplhabet, int count)
    {

        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*aplhabet.length());

            builder.append(aplhabet.charAt(character));
        }

        return builder.toString();
    }
}
