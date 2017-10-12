package co.base.androidbaseapplication.ui.entity;

import io.realm.annotations.Required;

public class Country
{

    @Required
    private String countryCode;
    private String countryName;
    private Double lat;
    private Double lng;
    private String flagCountryCode;

    public String getCountryCode ()
    {
        return countryCode;
    }

    public void setCountryCode (String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getCountryName ()
    {
        return countryName;
    }

    public void setCountryName (String countryName)
    {
        this.countryName = countryName;
    }

    public Double getLat ()
    {
        return lat;
    }

    public void setLat (Double lat)
    {
        this.lat = lat;
    }

    public Double getLng ()
    {
        return lng;
    }

    public void setLng (Double lng)
    {
        this.lng = lng;
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
