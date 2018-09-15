package ru.nextbi;

public class Version {
    public static int BUILD_NUMBER = 1;
    public static int MAJOR_VERSION = 0;
    public static int MINOR_VERSION = 1;

    public static final String getVersion()
    {
        return new StringBuilder().append( MAJOR_VERSION )
                .append( '.' ).append( MINOR_VERSION ).
                        append( '.' ).append( BUILD_NUMBER).toString();
    }
}
