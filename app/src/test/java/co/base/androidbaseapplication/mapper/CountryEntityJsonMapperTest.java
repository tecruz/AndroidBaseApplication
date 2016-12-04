package co.base.androidbaseapplication.mapper;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import co.base.androidbaseapplication.ApplicationTestCase;
import co.base.androidbaseapplication.data.entity.CountryEntity;
import co.base.androidbaseapplication.data.realm.RealmDouble;
import co.base.androidbaseapplication.ui.entity.Country;
import io.realm.RealmList;
import io.realm.RealmObject;

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

    private CountryEntity mCountryItemResponse;
    private List<CountryEntity> mCountryItemResponseList;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        Type token = new TypeToken<RealmList<RealmDouble>>() { }.getType();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .registerTypeAdapter(token, new TypeAdapter<RealmList<RealmDouble>>() {

                    @Override
                    public void write(JsonWriter out, RealmList<RealmDouble> value)
                            throws IOException {
                        // Ignore
                    }

                    @Override
                    public RealmList<RealmDouble> read(JsonReader in) throws IOException {
                        RealmList<RealmDouble> list = new RealmList<>();
                        in.beginArray();
                        while (in.hasNext()) {
                            list.add(new RealmDouble(in.nextDouble()));
                        }
                        in.endArray();
                        return list;
                    }
                })
                .create();
        mCountryItemResponse = gson.fromJson(JSON_RESPONSE_COUNTRY,
                new TypeToken<CountryEntity>() { }.getType());
        mCountryItemResponseList = gson.fromJson(JSON_RESPONSE_COUNTRIES_COLLECTION,
                new TypeToken<List<CountryEntity>>() { }.getType());
    }

    @Test
    public void testTransformCountryEntityHappyCase() {

        Country countryEntity = CountryItemMapper.transform(mCountryItemResponse);

        assertThat(countryEntity.getCountryCode(), is("AX"));
        assertThat(countryEntity.getCountryName(), is(equalTo("Åland Islands")));
    }

    @Test
    public void testTransformCountryEntityCollectionHappyCase() {
        Collection<Country> countryEntityCollection =
                CountryItemMapper.transform(mCountryItemResponseList);

        assertThat(((Country) countryEntityCollection.toArray()[0]).getCountryCode(), is("AX"));
        assertThat(((Country) countryEntityCollection.toArray()[1]).getCountryCode(), is("AL"));
        assertThat(countryEntityCollection.size(), is(2));
    }

}
