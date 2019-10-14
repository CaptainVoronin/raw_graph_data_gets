package ru.nextbi;

public class Version {
    public static final int BUILD_NUMBER = 125;
    public static final int MAJOR_VERSION = 1;
    public static final int MINOR_VERSION = 0;

    public static String getVersion()
    {
        return new StringBuilder().append( MAJOR_VERSION )
                .append( '.' ).append( MINOR_VERSION ).
                        append( '.' ).append( BUILD_NUMBER).toString();
    }
}
