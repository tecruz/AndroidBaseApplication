package co.base.data.realm;

import io.realm.RealmObject;

/**
 * Custom Double object for compatibility with Realm.
 */
public class RealmDouble extends RealmObject
{

    private Double val;

    public RealmDouble ()
    {
    }

    public RealmDouble (Double val)
    {
        this.val = val;
    }

    // Getters and setters

    public Double getVal ()
    {
        return val;
    }

    public void setVal (Double val)
    {
        this.val = val;
    }
}