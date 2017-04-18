package co.base.androidbaseapplication.ui.entity;

import io.realm.annotations.Required;

public class Country
{

    @Required
    private String mCountryCode;
    private String mCountryName;
    private Double mLat;
    private Double mLng;
    private String flagCountryCode;

    public String getCountryCode ()
    {
        return mCountryCode;
    }

    public void setCountryCode (String countryCode)
    {
        this.mCountryCode = countryCode;
    }

    public String getCountryName ()
    {
        return mCountryName;
    }

    public void setCountryName (String countryName)
    {
        this.mCountryName = countryName;
    }

    public Double getLat ()
    {
        return mLat;
    }

    public void setLat (Double lat)
    {
        this.mLat = lat;
    }

    public Double getLng ()
    {
        return mLng;
    }

    public void setLng (Double lng)
    {
        this.mLng = lng;
    }

    public String getFlagCountryCode ()
    {
        return flagCountryCode;
    }

    public void setFlagCountryCode (String flagCountryCode)
    {
        this.flagCountryCode = flagCountryCode;
    }
}
