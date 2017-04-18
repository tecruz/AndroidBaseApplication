package co.base.androidbaseapplication.data.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import co.base.androidbaseapplication.data.realm.RealmDouble;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Country Entity used in the data layer.
 */
public class CountryEntity extends RealmObject
{

    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("capital")
    @Expose
    private String mCapital;
    @SerializedName("relevance")
    @Expose
    private String mRelevance;
    @SerializedName("region")
    @Expose
    private String mRegion;
    @SerializedName("subregion")
    @Expose
    private String mSubregion;
    @SerializedName("population")
    @Expose
    private Integer mPopulation;
    @SerializedName("latlng")
    @Expose
    private RealmList<RealmDouble> mLatlng = new RealmList<>( );
    @SerializedName("demonym")
    @Expose
    private String mDemonym;
    @SerializedName("area")
    @Expose
    private Double mArea;
    @SerializedName("alpha2Code")
    @Expose
    private String mAlpha2Code;
    @SerializedName("alpha3Code")
    @Expose
    private String mAlpha3Code;

    /**
     * @return The name
     */
    public String getName ()
    {
        return mName;
    }

    /**
     * @param name The name
     */
    public void setName (String name)
    {
        this.mName = name;
    }

    /**
     * @return The capital
     */
    public String getCapital ()
    {
        return mCapital;
    }

    /**
     * @param capital The capital
     */
    public void setCapital (String capital)
    {
        this.mCapital = capital;
    }

    /**
     * @return The relevance
     */
    public String getRelevance ()
    {
        return mRelevance;
    }

    /**
     * @param relevance The relevance
     */
    public void setRelevance (String relevance)
    {
        this.mRelevance = relevance;
    }

    /**
     * @return The region
     */
    public String getRegion ()
    {
        return mRegion;
    }

    /**
     * @param region The region
     */
    public void setRegion (String region)
    {
        this.mRegion = region;
    }

    /**
     * @return The subregion
     */
    public String getSubregion ()
    {
        return mSubregion;
    }

    /**
     * @param subregion The subregion
     */
    public void setSubregion (String subregion)
    {
        this.mSubregion = subregion;
    }

    /**
     * @return The population
     */
    public Integer getPopulation ()
    {
        return mPopulation;
    }

    /**
     * @param population The population
     */
    public void setPopulation (Integer population)
    {
        this.mPopulation = population;
    }

    /**
     * @return The latlng
     */
    public RealmList<RealmDouble> getLatlng ()
    {
        return mLatlng;
    }

    /**
     * @param latlng The latlng
     */
    public void setLatlng (RealmList<RealmDouble> latlng)
    {
        this.mLatlng = latlng;
    }

    /**
     * @return The demonym
     */
    public String getDemonym ()
    {
        return mDemonym;
    }

    /**
     * @param demonym The demonym
     */
    public void setDemonym (String demonym)
    {
        this.mDemonym = demonym;
    }

    /**
     * @return The area
     */
    public Double getArea ()
    {
        return mArea;
    }

    /**
     * @param area The area
     */
    public void setArea (Double area)
    {
        this.mArea = area;
    }


    /**
     * @return The alpha2Code
     */
    public String getAlpha2Code ()
    {
        return mAlpha2Code;
    }

    /**
     * @param alpha2Code The alpha2Code
     */
    public void setAlpha2Code (String alpha2Code)
    {
        this.mAlpha2Code = alpha2Code;
    }

    /**
     * @return The alpha3Code
     */
    public String getAlpha3Code ()
    {
        return mAlpha3Code;
    }

    /**
     * @param alpha3Code The alpha3Code
     */
    public void setAlpha3Code (String alpha3Code)
    {
        this.mAlpha3Code = alpha3Code;
    }

}
