package ru.nextbi.generation.atomic;

import ru.nextbi.GTDGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *  Генератор адресов
 *  формат вызова
 *  prop: address address ( cities=<словарь городов>, streets=<словарь улиц>, max-building=<максимальный номер дома на улице>, control-numbers )
 *  Если присутствует control-numbers, то на одной улице не может быть двух адресов в одном доме. Но это жрет память.
 *
 */
public class AddressGenerator extends BaseDictionaryGenerator{
    private static final String MAX_BUILDING_NUMBER_KEY = "max-building";
    static String CITIES_KEY = "cities";
    static String STREETS_KEY = "streets";
    static String CONTROL_NUMBERS_KEY = "control-numbers";
    boolean controlNumbers = false;

    int maxBuildingNumber;

    Map<String, List<Integer>> control;

    @Override
    public String getValue() throws Exception{
        return generateAddress();
    }

    @Override
    public void setParams(Map<String, String> config, Map<String, String> params) throws Exception{

        super.setParams( config, params );

        if( params.containsKey( CITIES_KEY ) )
            createCities( config.get(GTDGenerator.CURRENT_DIR_KEY ), params.get( CITIES_KEY ) );
        else
            throw new IllegalArgumentException( "Dictionary for cities is not set" );

        if( params.containsKey( STREETS_KEY ) )
            createStreets( config.get(GTDGenerator.CURRENT_DIR_KEY ), params.get( STREETS_KEY ) );
        else
            throw new IllegalArgumentException( "Dictionary for streets is not set" );

        if( params.containsKey( CONTROL_NUMBERS_KEY ) ) {
            controlNumbers = true;
            control = new HashMap<>();
        }

        String buff = params.getOrDefault( MAX_BUILDING_NUMBER_KEY, "57" );
        maxBuildingNumber = Integer.parseInt( buff );
    }

    private void createStreets(String dir, String file) throws Exception{
        addDictionary( GeneratorUtils.makeAbsolutePath( dir, file ) );
    }

    private void createCities(String dir,String file) throws Exception{
        addDictionary( GeneratorUtils.makeAbsolutePath( dir, file ) );
    }

    private String generateAddress() throws IOException, ProviderNotInitiaqlizedException{
        String city = getDictionary( CITIES_KEY ).getRndValue();
        String street = getDictionary( STREETS_KEY ).getRndValue();
        int buildingNumber = getBuildingNumber( city, street );
        return city + ", ул. " + street + " д." + buildingNumber;
    }

    int getBuildingNumber( String city, String street )
    {
        int num = IntGenerator.getInt( 1, maxBuildingNumber );
        if( !controlNumbers )
            return num;
        else
        {
            List<Integer> nums = control.get( city + street );
            if( nums == null )
            {
                List<Integer> list = new ArrayList<>();
                list.add(num );
                control.put( city + street, list );
            }
            else
            {
                if( nums.size() == maxBuildingNumber )
                    throw new IndexOutOfBoundsException( "The number of buildings on street " + street + " exceded limitation " + maxBuildingNumber );

                while( nums.contains( num ) )
                    num = IntGenerator.getInt( 1, maxBuildingNumber );
                nums.add( num );
            }
        }

        return num;
    }
}
