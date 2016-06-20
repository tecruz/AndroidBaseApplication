package co.base.androidbaseapplication.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.List;

import co.base.androidbaseapplication.ApplicationTestCase;
import co.base.androidbaseapplication.model.entities.Country;
import co.base.androidbaseapplication.model.entities.rest.CountryItemResponse;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class CountryEntityJsonMapperTest extends ApplicationTestCase {

    private static final String JSON_RESPONSE_COUNTRY = "{\n" +
            "\t\"name\": \"Åland Islands\",\n" +
            "\t\"capital\": \"Mariehamn\",\n" +
            "\t\"altSpellings\": [\n" +
            "\t\t\"AX\",\n" +
            "\t\t\"Aaland\",\n" +
            "\t\t\"Aland\",\n" +
            "\t\t\"Ahvenanmaa\"\n" +
            "\t],\n" +
            "\t\"relevance\": \"0\",\n" +
            "\t\"region\": \"Europe\",\n" +
            "\t\"subregion\": \"Northern Europe\",\n" +
            "\t\"translations\": {\n" +
            "\t\t\"de\": \"Åland\",\n" +
            "\t\t\"es\": \"Alandia\",\n" +
            "\t\t\"fr\": \"Åland\",\n" +
            "\t\t\"ja\": \"オーランド諸島\",\n" +
            "\t\t\"it\": \"Isole Aland\"\n" +
            "\t},\n" +
            "\t\"population\": 28875,\n" +
            "\t\"latlng\": [\n" +
            "\t\t60.116667,\n" +
            "\t\t19.9\n" +
            "\t],\n" +
            "\t\"demonym\": \"Ålandish\",\n" +
            "\t\"area\": 1580,\n" +
            "\t\"gini\": null,\n" +
            "\t\"timezones\": null,\n" +
            "\t\"borders\": [],\n" +
            "\t\"nativeName\": \"Åland\",\n" +
            "\t\"callingCodes\": [\n" +
            "\t\t\"358\"\n" +
            "\t],\n" +
            "\t\"topLevelDomain\": [\n" +
            "\t\t\".ax\"\n" +
            "\t],\n" +
            "\t\"alpha2Code\": \"AX\",\n" +
            "\t\"alpha3Code\": \"ALA\",\n" +
            "\t\"currencies\": [\n" +
            "\t\t\"EUR\"\n" +
            "\t],\n" +
            "\t\"languages\": [\n" +
            "\t\t\"sv\"\n" +
            "\t]\n" +
            "}";

    private static final String JSON_RESPONSE_COUNTRIES_COLLECTION = "[{\n" +
            "\t\"name\": \"Åland Islands\",\n" +
            "\t\"capital\": \"Mariehamn\",\n" +
            "\t\"altSpellings\": [\n" +
            "\t\t\"AX\",\n" +
            "\t\t\"Aaland\",\n" +
            "\t\t\"Aland\",\n" +
            "\t\t\"Ahvenanmaa\"\n" +
            "\t],\n" +
            "\t\"relevance\": \"0\",\n" +
            "\t\"region\": \"Europe\",\n" +
            "\t\"subregion\": \"Northern Europe\",\n" +
            "\t\"translations\": {\n" +
            "\t\t\"de\": \"Åland\",\n" +
            "\t\t\"es\": \"Alandia\",\n" +
            "\t\t\"fr\": \"Åland\",\n" +
            "\t\t\"ja\": \"オーランド諸島\",\n" +
            "\t\t\"it\": \"Isole Aland\"\n" +
            "\t},\n" +
            "\t\"population\": 28875,\n" +
            "\t\"latlng\": [\n" +
            "\t\t60.116667,\n" +
            "\t\t19.9\n" +
            "\t],\n" +
            "\t\"demonym\": \"Ålandish\",\n" +
            "\t\"area\": 1580,\n" +
            "\t\"gini\": null,\n" +
            "\t\"timezones\": null,\n" +
            "\t\"borders\": [],\n" +
            "\t\"nativeName\": \"Åland\",\n" +
            "\t\"callingCodes\": [\n" +
            "\t\t\"358\"\n" +
            "\t],\n" +
            "\t\"topLevelDomain\": [\n" +
            "\t\t\".ax\"\n" +
            "\t],\n" +
            "\t\"alpha2Code\": \"AX\",\n" +
            "\t\"alpha3Code\": \"ALA\",\n" +
            "\t\"currencies\": [\n" +
            "\t\t\"EUR\"\n" +
            "\t],\n" +
            "\t\"languages\": [\n" +
            "\t\t\"sv\"\n" +
            "\t]\n" +
            "}, {\n" +
            "\t\"name\": \"Albania\",\n" +
            "\t\"capital\": \"Tirana\",\n" +
            "\t\"altSpellings\": [\n" +
            "\t\t\"AL\",\n" +
            "\t\t\"Shqipëri\",\n" +
            "\t\t\"Shqipëria\",\n" +
            "\t\t\"Shqipnia\"\n" +
            "\t],\n" +
            "\t\"relevance\": \"0\",\n" +
            "\t\"region\": \"Europe\",\n" +
            "\t\"subregion\": \"Southern Europe\",\n" +
            "\t\"translations\": {\n" +
            "\t\t\"de\": \"Albanien\",\n" +
            "\t\t\"es\": \"Albania\",\n" +
            "\t\t\"fr\": \"Albanie\",\n" +
            "\t\t\"ja\": \"アルバニア\",\n" +
            "\t\t\"it\": \"Albania\"\n" +
            "\t},\n" +
            "\t\"population\": 2893005,\n" +
            "\t\"latlng\": [\n" +
            "\t\t41,\n" +
            "\t\t20\n" +
            "\t],\n" +
            "\t\"demonym\": \"Albanian\",\n" +
            "\t\"area\": 28748,\n" +
            "\t\"gini\": 34.5,\n" +
            "\t\"timezones\": [\n" +
            "\t\t\"UTC+01:00\"\n" +
            "\t],\n" +
            "\t\"borders\": [\n" +
            "\t\t\"MNE\",\n" +
            "\t\t\"GRC\",\n" +
            "\t\t\"MKD\",\n" +
            "\t\t\"KOS\"\n" +
            "\t],\n" +
            "\t\"nativeName\": \"Shqipëria\",\n" +
            "\t\"callingCodes\": [\n" +
            "\t\t\"355\"\n" +
            "\t],\n" +
            "\t\"topLevelDomain\": [\n" +
            "\t\t\".al\"\n" +
            "\t],\n" +
            "\t\"alpha2Code\": \"AL\",\n" +
            "\t\"alpha3Code\": \"ALB\",\n" +
            "\t\"currencies\": [\n" +
            "\t\t\"ALL\"\n" +
            "\t],\n" +
            "\t\"languages\": [\n" +
            "\t\t\"sq\"\n" +
            "\t]\n" +
            "}]";

    private CountryItemResponse mCountryItemResponse;
    private List<CountryItemResponse> mCountryItemResponseList;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Gson gson = new Gson();
        mCountryItemResponse = gson.fromJson(JSON_RESPONSE_COUNTRY,
                new TypeToken<CountryItemResponse>() { }.getType());
        mCountryItemResponseList = gson.fromJson(JSON_RESPONSE_COUNTRIES_COLLECTION,
                new TypeToken<List<CountryItemResponse>>() { }.getType());
    }

    @Test
    public void testTransformCountryEntityHappyCase() {

        Country countryEntity = CountryItemMapper.transform(mCountryItemResponse);

        assertThat(countryEntity.getmCountryCode(), is("AX"));
        assertThat(countryEntity.getmCountryName(), is(equalTo("Åland Islands")));
    }

    @Test
    public void testTransformCountryEntityCollectionHappyCase() {
        Collection<Country> countryEntityCollection =
                CountryItemMapper.transform(
                        mCountryItemResponseList);

        assertThat(((Country) countryEntityCollection.toArray()[0]).getmCountryCode(), is("AX"));
        assertThat(((Country) countryEntityCollection.toArray()[1]).getmCountryCode(), is("AL"));
        assertThat(countryEntityCollection.size(), is(2));
    }
}
