package ru.nextbi;

import com.sun.istack.internal.NotNull;

public class Counter {
    String name;
    int interval;
    AInfoPrinter printer;
    int count;
    int roundCount;

    public Counter(@NotNull AInfoPrinter printer, String name, int interval) {
        this.printer = printer;
        this.name = name;
        this.interval = interval;
        count = 0;
        roundCount = 0;
    }

    public void inc() {
        count++;
        roundCount++;
        if (roundCount >= interval) {
            roundCount = 0;
            printer.print(name + " " + count);
        }
    }

    public void printTotal()
    {
        printer.print( "Total for " + name + " " + count);
    }
}
