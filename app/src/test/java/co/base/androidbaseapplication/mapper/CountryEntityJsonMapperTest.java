package co.base.androidbaseapplication.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.ui.entity.Country;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

@RunWith(PowerMockRunner.class)
public class CountryEntityJsonMapperTest
{

    private static final String FAKE_COUNTRY_CODE = "CC";
    private static final String FAKE_COUNTRY_NAME = "Country";

    private CountryEntity countryEntity;

    @Before
    public void setUp ()
    {
        countryEntity = new CountryEntity( );
        countryEntity.setAlpha2Code( FAKE_COUNTRY_CODE );
        countryEntity.setName( FAKE_COUNTRY_NAME );
    }

    @Test
    public void testTransformCountryEntityHappyCase ()
    {

        Country countryEntity = CountryItemMapper.transform( this.countryEntity );

        assertThat( countryEntity.getCountryCode( ), is( FAKE_COUNTRY_CODE ) );
        assertThat( countryEntity.getCountryName( ), is( equalTo( FAKE_COUNTRY_NAME ) ) );
    }

    @Test
    public void testTransformCountryEntityCollectionHappyCase ()
    {

        CountryEntity mockUserEntityOne = mock( CountryEntity.class );
        CountryEntity mockUserEntityTwo = mock( CountryEntity.class );

        List<CountryEntity> countryEntityList = new ArrayList<>( 5 );
        countryEntityList.add( mockUserEntityOne );
        countryEntityList.add( mockUserEntityTwo );

        Collection<Country> userCollection = CountryItemMapper.transform( countryEntityList );

        assertThat( userCollection.toArray( )[0], is( instanceOf( Country.class ) ) );
        assertThat( userCollection.toArray( )[1], is( instanceOf( Country.class ) ) );
        assertThat( userCollection.size( ), is( 2 ) );
    }

}
