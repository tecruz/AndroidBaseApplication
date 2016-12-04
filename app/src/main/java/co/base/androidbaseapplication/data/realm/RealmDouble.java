package co.base.androidbaseapplication.data.realm;

import io.realm.RealmObject;

public class RealmDouble extends RealmObject {

    private Double mVal;

    public RealmDouble() {
    }

    public RealmDouble(Double val) {
        this.mVal = val;
    }

    // Getters and setters

    public Double getVal() {
        return mVal;
    }

    public void setVal(Double val) {
        this.mVal = val;
    }
}