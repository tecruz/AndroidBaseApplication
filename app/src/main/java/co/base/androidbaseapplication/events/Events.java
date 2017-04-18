package co.base.androidbaseapplication.events;

public enum Events
{
    SYNC_COMPLETED
            {
                public String toString ()
                {
                    return "sync-completed";
                }
            },
    SYNC_ERROR
            {
                public String toString ()
                {
                    return "sync-error";
                }
            }
}
