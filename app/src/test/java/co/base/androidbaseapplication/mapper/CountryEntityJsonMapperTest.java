package co.base.androidbaseapplication.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.base.domain.Country;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CountryEntityJsonMapperTest
{

    private static final String FAKE_COUNTRY_CODE = "CC";
    private static final String FAKE_COUNTRY_NAME = "Country";

    private Country countryEntity;

    @Before
    public void setUp ()
    {
        countryEntity = new Country( );
        countryEntity.setCountryCode( FAKE_COUNTRY_CODE );
        countryEntity.setCountryName( FAKE_COUNTRY_NAME );
    }

    @Test
    public void testTransformCountryEntityHappyCase ()
    {

        co.base.androidbaseapplication.ui.entity.Country countryEntity
                = CountryItemMapper.transform( this.countryEntity );

        assertThat( countryEntity.getCountryCode( ), is( FAKE_COUNTRY_CODE ) );
        assertThat( countryEntity.getCountryName( ), is( equalTo( FAKE_COUNTRY_NAME ) ) );
    }

    @Test
    public void testTransformCountryEntityCollectionHappyCase ()
    {

        Country mockUserEntityOne = mock( Country.class );
        Country mockUserEntityTwo = mock( Country.class );

        List<Country> countryEntityList = new ArrayList<>( 5 );
        countryEntityList.add( mockUserEntityOne );
        countryEntityList.add( mockUserEntityTwo );

        Collection<co.base.androidbaseapplication.ui.entity.Country> userCollection
                = CountryItemMapper.transform( countryEntityList );

        assertThat( userCollection.toArray( )[0], is( instanceOf( co.base.androidbaseapplication.ui.entity.Country.class ) ) );
        assertThat( userCollection.toArray( )[1], is( instanceOf( co.base.androidbaseapplication.ui.entity.Country.class ) ) );
        assertThat( userCollection.size( ), is( 2 ) );
    }

}
