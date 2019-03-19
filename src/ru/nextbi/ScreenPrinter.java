package ru.nextbi;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

public class ScreenPrinter extends AInfoPrinter {
    @Override
    protected void out(String buff) {
        System.out.println( buff );
    }
}
