package ru.nextbi;

import java.util.*;

public abstract class AInfoPrinter implements Runnable {

    Boolean termnated;

    Set<String> messages;

    public AInfoPrinter()
    {
        termnated = new Boolean( false );
        messages = Collections.synchronizedSet( new HashSet<>() );
    }

    protected abstract void  out( String buff );

    public final void print( String message )
    {
        messages.add( message );
    }

    public final void terminate()
    {
        synchronized ( termnated )
        {
            termnated = new Boolean( true );
        }
    }

    @Override
    public void run() {

        while( !termnated )
        {
            synchronized ( messages ) {
                Iterator<String> it = messages.iterator();
                while (it.hasNext()) {
                    out( it.next() );
                    it.remove();
                }
            }
        }
    }
}
