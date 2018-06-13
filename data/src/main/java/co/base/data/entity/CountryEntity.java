package co.base.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.base.data.realm.RealmDouble;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Country Entity used in the data layer.
 */
public class CountryEntity extends RealmObject
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("relevance")
    @Expose
    private String relevance;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("subregion")
    @Expose
    private String subRegion;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("latlng")
    @Expose
    private RealmList<RealmDouble> latLng = new RealmList<>( );
    @SerializedName("demonym")
    @Expose
    private String demonym;
    @SerializedName("area")
    @Expose
    private Double area;
    @SerializedName("alpha2Code")
    @Expose
    private String alpha2Code;
    @SerializedName("alpha3Code")
    @Expose
    private String alpha3Code;

    /**
     * @return The name
     */
    public String getName ()
    {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * @return The capital
     */
    public String getCapital ()
    {
        return capital;
    }

    /**
     * @param capital The capital
     */
    public void setCapital (String capital)
    {
        this.capital = capital;
    }

    /**
     * @return The relevance
     */
    public String getRelevance ()
    {
        return relevance;
    }

    /**
     * @param relevance The relevance
     */
    public void setRelevance (String relevance)
    {
        this.relevance = relevance;
    }

    /**
     * @return The region
     */
    public String getRegion ()
    {
        return region;
    }

    /**
     * @param region The region
     */
    public void setRegion (String region)
    {
        this.region = region;
    }

    /**
     * @return The subregion
     */
    public String getSubregion ()
    {
        return subRegion;
    }

    /**
     * @param subregion The subregion
     */
    public void setSubregion (String subregion)
    {
        this.subRegion = subregion;
    }

    /**
     * @return The population
     */
    public Integer getPopulation ()
    {
        return population;
    }

    /**
     * @param population The population
     */
    public void setPopulation (Integer population)
    {
        this.population = population;
    }

    /**
     * @return The latlng
     */
    public RealmList<RealmDouble> getLatlng ()
    {
        return latLng;
    }

    /**
     * @param latlng The latlng
     */
    public void setLatlng (RealmList<RealmDouble> latlng)
    {
        this.latLng = latlng;
    }

    /**
     * @return The demonym
     */
    public String getDemonym ()
    {
        return demonym;
    }

    /**
     * @param demonym The demonym
     */
    public void setDemonym (String demonym)
    {
        this.demonym = demonym;
    }

    /**
     * @return The area
     */
    public Double getArea ()
    {
        return area;
    }

    /**
     * @param area The area
     */
    public void setArea (Double area)
    {
        this.area = area;
    }


    /**
     * @return The alpha2Code
     */
    public String getAlpha2Code ()
    {
        return alpha2Code;
    }

    /**
     * @param alpha2Code The alpha2Code
     */
    public void setAlpha2Code (String alpha2Code)
    {
        this.alpha2Code = alpha2Code;
    }

    /**
     * @return The alpha3Code
     */
    public String getAlpha3Code ()
    {
        return alpha3Code;
    }

    /**
     * @param alpha3Code The alpha3Code
     */
    public void setAlpha3Code (String alpha3Code)
    {
        this.alpha3Code = alpha3Code;
    }

}
