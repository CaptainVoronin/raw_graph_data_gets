package ru.nextbi.generation.atomic;

public enum GeneratorAlias {

    cyclic( "ru.nextbi.generation.atomic.CyclicSet" ),
    datetime( "ru.nextbi.generation.atomic.DateTimeGenerator" ),
    dictionary( "ru.nextbi.generation.atomic.DictionarySet" ),
    digit_string( "ru.nextbi.generation.atomic.DigitStringGenerator" ),
    email( "ru.nextbi.generation.atomic.EMailAddressGenerator" ),
    long_id( "ru.nextbi.generation.atomic.LongIDGenerator" ),
    human_name( "ru.nextbi.generation.atomic.NameGenerator" ),
    option_chooser( "ru.nextbi.generation.atomic.OptionChooser" ),
    phone_number( "ru.nextbi.generation.atomic.PhoneNumberGenerator" ),
    number( "ru.nextbi.generation.atomic.IntGenerator" ),
    string( "ru.nextbi.generation.atomic.StringGenerator" ),
    uuid( "ru.nextbi.generation.atomic.UUIDIDGenerator" );

    final String className;

    GeneratorAlias( String className )
    {
        this.className = className;
    }

    public String className()
    {
        return className;
    }
}
