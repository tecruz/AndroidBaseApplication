package co.base.androidbaseapplication.model.entities.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CountryItemResponse {

    @SerializedName("name")
    @Expose
    private String mName;
    @SerializedName("capital")
    @Expose
    private String mCapital;
    @SerializedName("altSpellings")
    @Expose
    private List<String> mAltSpellings = new ArrayList<String>();
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
    private List<Double> mLatlng = new ArrayList<Double>();
    @SerializedName("demonym")
    @Expose
    private String mDemonym;
    @SerializedName("area")
    @Expose
    private Double mArea;
    @SerializedName("gini")
    @Expose
    private Object mGini;
    @SerializedName("timezones")
    @Expose
    private Object mTimezones;
    @SerializedName("borders")
    @Expose
    private List<Object> mBorders = new ArrayList<Object>();
    @SerializedName("nativeName")
    @Expose
    private String mNativeName;
    @SerializedName("callingCodes")
    @Expose
    private List<String> mCallingCodes = new ArrayList<String>();
    @SerializedName("topLevelDomain")
    @Expose
    private List<String> mTopLevelDomain = new ArrayList<String>();
    @SerializedName("alpha2Code")
    @Expose
    private String mAlpha2Code;
    @SerializedName("alpha3Code")
    @Expose
    private String mAlpha3Code;
    @SerializedName("currencies")
    @Expose
    private List<String> mCurrencies = new ArrayList<String>();
    @SerializedName("languages")
    @Expose
    private List<String> mLanguages = new ArrayList<String>();

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return mName;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     *
     * @return
     * The capital
     */
    public String getCapital() {
        return mCapital;
    }

    /**
     *
     * @param capital
     * The capital
     */
    public void setCapital(String capital) {
        this.mCapital = capital;
    }

    /**
     *
     * @return
     * The altSpellings
     */
    public List<String> getAltSpellings() {
        return mAltSpellings;
    }

    /**
     *
     * @param altSpellings
     * The altSpellings
     */
    public void setAltSpellings(List<String> altSpellings) {
        this.mAltSpellings = altSpellings;
    }

    /**
     *
     * @return
     * The relevance
     */
    public String getRelevance() {
        return mRelevance;
    }

    /**
     *
     * @param relevance
     * The relevance
     */
    public void setRelevance(String relevance) {
        this.mRelevance = relevance;
    }

    /**
     *
     * @return
     * The region
     */
    public String getRegion() {
        return mRegion;
    }

    /**
     *
     * @param region
     * The region
     */
    public void setRegion(String region) {
        this.mRegion = region;
    }

    /**
     *
     * @return
     * The subregion
     */
    public String getSubregion() {
        return mSubregion;
    }

    /**
     *
     * @param subregion
     * The subregion
     */
    public void setSubregion(String subregion) {
        this.mSubregion = subregion;
    }

    /**
     *
     * @return
     * The population
     */
    public Integer getPopulation() {
        return mPopulation;
    }

    /**
     *
     * @param population
     * The population
     */
    public void setPopulation(Integer population) {
        this.mPopulation = population;
    }

    /**
     *
     * @return
     * The latlng
     */
    public List<Double> getLatlng() {
        return mLatlng;
    }

    /**
     *
     * @param latlng
     * The latlng
     */
    public void setLatlng(List<Double> latlng) {
        this.mLatlng = latlng;
    }

    /**
     *
     * @return
     * The demonym
     */
    public String getDemonym() {
        return mDemonym;
    }

    /**
     *
     * @param demonym
     * The demonym
     */
    public void setDemonym(String demonym) {
        this.mDemonym = demonym;
    }

    /**
     *
     * @return
     * The area
     */
    public Double getArea() {
        return mArea;
    }

    /**
     *
     * @param area
     * The area
     */
    public void setArea(Double area) {
        this.mArea = area;
    }

    /**
     *
     * @return
     * The gini
     */
    public Object getGini() {
        return mGini;
    }

    /**
     *
     * @param gini
     * The gini
     */
    public void setGini(Object gini) {
        this.mGini = gini;
    }

    /**
     *
     * @return
     * The timezones
     */
    public Object getTimezones() {
        return mTimezones;
    }

    /**
     *
     * @param timezones
     * The timezones
     */
    public void setTimezones(Object timezones) {
        this.mTimezones = timezones;
    }

    /**
     *
     * @return
     * The borders
     */
    public List<Object> getBorders() {
        return mBorders;
    }

    /**
     *
     * @param borders
     * The borders
     */
    public void setBorders(List<Object> borders) {
        this.mBorders = borders;
    }

    /**
     *
     * @return
     * The nativeName
     */
    public String getNativeName() {
        return mNativeName;
    }

    /**
     *
     * @param nativeName
     * The nativeName
     */
    public void setNativeName(String nativeName) {
        this.mNativeName = nativeName;
    }

    /**
     *
     * @return
     * The callingCodes
     */
    public List<String> getCallingCodes() {
        return mCallingCodes;
    }

    /**
     *
     * @param callingCodes
     * The callingCodes
     */
    public void setCallingCodes(List<String> callingCodes) {
        this.mCallingCodes = callingCodes;
    }

    /**
     *
     * @return
     * The topLevelDomain
     */
    public List<String> getTopLevelDomain() {
        return mTopLevelDomain;
    }

    /**
     *
     * @param topLevelDomain
     * The topLevelDomain
     */
    public void setTopLevelDomain(List<String> topLevelDomain) {
        this.mTopLevelDomain = topLevelDomain;
    }

    /**
     *
     * @return
     * The alpha2Code
     */
    public String getAlpha2Code() {
        return mAlpha2Code;
    }

    /**
     *
     * @param alpha2Code
     * The alpha2Code
     */
    public void setAlpha2Code(String alpha2Code) {
        this.mAlpha2Code = alpha2Code;
    }

    /**
     *
     * @return
     * The alpha3Code
     */
    public String getAlpha3Code() {
        return mAlpha3Code;
    }

    /**
     *
     * @param alpha3Code
     * The alpha3Code
     */
    public void setAlpha3Code(String alpha3Code) {
        this.mAlpha3Code = alpha3Code;
    }

    /**
     *
     * @return
     * The currencies
     */
    public List<String> getCurrencies() {
        return mCurrencies;
    }

    /**
     *
     * @param currencies
     * The currencies
     */
    public void setCurrencies(List<String> currencies) {
        this.mCurrencies = currencies;
    }

    /**
     *
     * @return
     * The languages
     */
    public List<String> getLanguages() {
        return mLanguages;
    }

    /**
     *
     * @param languages
     * The languages
     */
    public void setLanguages(List<String> languages) {
        this.mLanguages = languages;
    }
}
