package ru.nextbi;

public class PrinterFactory {
    static AInfoPrinter instance;

    public static AInfoPrinter getPrinter()
    {
        if( instance == null )
        {
            instance = new ScreenPrinter();
        }
        return instance;
    }
}
