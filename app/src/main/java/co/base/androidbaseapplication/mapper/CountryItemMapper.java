package co.base.androidbaseapplication.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.ui.entity.Country;

public class CountryItemMapper
{

    public static List<Country> transform (Collection<CountryEntity> countries)
    {
        List<Country> countriesList = new ArrayList<>( );
        for ( CountryEntity country : countries )
        {
            Country countryItem = transform( country );
            countriesList.add( countryItem );
        }
        return countriesList;
    }

    public static Country transform (CountryEntity countryItem)
    {
        Country country = new Country( );
        country.setCountryCode( countryItem.getAlpha2Code( ) );
        country.setCountryName( countryItem.getName( ) );
        if ( countryItem.getLatlng( ).size( ) > 0 )
        {
            country.setLat( countryItem.getLatlng( ).get( 0 ).getVal( ) );
            country.setLng( countryItem.getLatlng( ).get( 1 ).getVal( ) );
        }
        country.setFlagCountryCode( countryItem.getAlpha3Code( ) );
        return country;
    }
}
