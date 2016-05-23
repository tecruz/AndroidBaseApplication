package co.base.androidbaseapplication.model.entities;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class Country extends RealmObject {

    @Required private String mCountryCode;
    private String mCountryName;
    private Double mLat;
    private Double mLng;

    public String getmCountryCode() {
        return mCountryCode;
    }

    public void setmCountryCode(String mCountryCode) {
        this.mCountryCode = mCountryCode;
    }

    public String getmCountryName() {
        return mCountryName;
    }

    public void setmCountryName(String mCountryName) {
        this.mCountryName = mCountryName;
    }

    public Double getmLat() {
        return mLat;
    }

    public void setmLat(Double mLat) {
        this.mLat = mLat;
    }

    public Double getmLng() {
        return mLng;
    }

    public void setmLng(Double mLng) {
        this.mLng = mLng;
    }
}
