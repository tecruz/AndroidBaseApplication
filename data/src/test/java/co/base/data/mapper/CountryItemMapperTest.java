package co.base.data.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.base.data.entity.CountryEntity;
import co.base.data.realm.RealmDouble;
import co.base.domain.Country;
import io.realm.RealmList;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class CountryItemMapperTest
{

    private static final String FAKE_COUNTRY_CODE = "CC";
    private static final String FAKE_COUNTRY_NAME = "Country";
    private static final String FAKE_FLAG_CODE = "FF";

    private CountryEntity countryEntity;

    @Before
    public void setUp ()
    {
        countryEntity = new CountryEntity( );
        countryEntity.setAlpha2Code( FAKE_COUNTRY_CODE );
        countryEntity.setName( FAKE_COUNTRY_NAME );
        countryEntity.setAlpha3Code( FAKE_FLAG_CODE );
        countryEntity.setLatlng( new RealmList<>(new RealmDouble(0.0), new RealmDouble(0.0)));
    }

    @Test
    public void testTransformCountryItemEntityHappyCase ()
    {
        Country countryEntity
                = CountryItemMapper.transform( this.countryEntity );

        assertThat( countryEntity.getCountryCode( ), is( FAKE_COUNTRY_CODE ) );
        assertThat( countryEntity.getCountryName( ), is( equalTo( FAKE_COUNTRY_NAME ) ) );
        assertThat( countryEntity.getLat( ), is( equalTo( 0.0 ) ) );
        assertThat( countryEntity.getLng( ), is( equalTo( 0.0 ) ) );
        assertThat( countryEntity.getFlagCountryCode( ), is( equalTo( FAKE_FLAG_CODE ) ) );
    }

    @Test
    public void testTransformCountryItemEntityCollectionHappyCase ()
    {

        CountryEntity mockUserEntityOne = mock( CountryEntity.class );
        CountryEntity mockUserEntityTwo = mock( CountryEntity.class );

        List<CountryEntity> countryEntityList = new ArrayList<>( 5 );
        countryEntityList.add( mockUserEntityOne );
        countryEntityList.add( mockUserEntityTwo );

        Collection<Country> countryCollection
                = CountryItemMapper.transform( countryEntityList );

        assertThat( countryCollection.toArray( )[0], is( instanceOf( Country.class ) ) );
        assertThat( countryCollection.toArray( )[1], is( instanceOf( Country.class ) ) );
        assertThat( countryCollection.size( ), is( 2 ) );
    }

}
