package ru.nextbi.generation.atomic;

public class StringGenerator implements IGenerator
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
    public void setParamString( String rawParams) throws Exception
    {};

    @Override
    public String getValue()
    {
        return randomAlphaNumeric( length );
    }

    static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    static final String NUMERIC_STRING = "0123456789";

    public static String randomAlphaNumeric(int count)
    {

        /*StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString(); */

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
