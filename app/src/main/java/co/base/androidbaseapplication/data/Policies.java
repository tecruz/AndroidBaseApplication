package co.base.androidbaseapplication.data;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Policies
{
    // Constants
    public static final int DATABASE = 0;
    public static final int NETWORK = 1;
    private int policy;

    public Policies (@Policy int policy)
    {
        this.policy = policy;
    }

    // Declare the @ IntDef for these constants:
    @IntDef({NETWORK, DATABASE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Policy
    {
    }

    @Policy
    public int getPolicy ()
    {
        return this.policy;
    }
}


