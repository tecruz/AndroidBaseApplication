package co.base.domain.domain;

import org.junit.Before;
import org.junit.Test;

import co.base.domain.Country;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CountryTest
{
    private static final String FAKE_COUNTRY_CODE = "NA";
    private static final String FAKE_COUNTRY_NAME = "NA";
    private static final String FAKE_COUNTRY_FLAG_COUNTRY_CODE = "NA";
    private static final Double FAKE_COUNTRY_LAT = 0.0;
    private static final Double FAKE_COUNTRY_LNG = 0.0;

    private Country country;

    @Before
    public void setUp ()
    {
        country = new Country( );
        country.setCountryCode( FAKE_COUNTRY_CODE );
        country.setCountryName( FAKE_COUNTRY_NAME );
        country.setFlagCountryCode( FAKE_COUNTRY_FLAG_COUNTRY_CODE );
        country.setLat( FAKE_COUNTRY_LAT );
        country.setLng( FAKE_COUNTRY_LNG );
    }

    @Test
    public void testCountryConstructorHappyCase ()
    {
        final String countryCode = country.getCountryCode( );
        final String countryName = country.getCountryName( );
        final String flagCountryCode = country.getFlagCountryCode( );
        final Double countryLat = country.getLat( );
        final Double countryLng = country.getLng( );

        assertThat( countryCode, is( FAKE_COUNTRY_CODE ) );
        assertThat( countryName, is( FAKE_COUNTRY_NAME ) );
        assertThat( flagCountryCode, is( FAKE_COUNTRY_FLAG_COUNTRY_CODE ) );
        assertThat( countryLat, is( FAKE_COUNTRY_LAT ) );
        assertThat( countryLng, is( FAKE_COUNTRY_LNG ) );
    }
}
