package co.base.androidbaseapplication.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.base.androidbaseapplication.ui.entity.Country;

public class CountryItemMapper
{

    public static List<Country> transform (Collection<co.base.domain.Country> countries)
    {
        List<Country> countriesList = new ArrayList<>( );
        for ( co.base.domain.Country country : countries )
        {
            Country countryItem = transform( country );
            countriesList.add( countryItem );
        }
        return countriesList;
    }

    public static Country transform (co.base.domain.Country countryItem)
    {
        Country country = new Country( );
        country.setCountryCode( countryItem.getCountryCode( ) );
        country.setCountryName( countryItem.getCountryName( ) );
        if ( countryItem.getLat( ) != null && countryItem.getLng( ) != null )
        {
            country.setLat( countryItem.getLat( ) );
            country.setLng( countryItem.getLng( ) );
        }
        country.setFlagCountryCode( countryItem.getFlagCountryCode( ) );
        return country;
    }
}
