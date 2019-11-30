package nl.mwsoft.www.superheromatch.modelLayer.event;

import android.net.Uri;

public class MainProfilePicSettingsEvent {

    public Uri mainProfilePicURI;

    public MainProfilePicSettingsEvent() {
    }

    public MainProfilePicSettingsEvent(Uri mainProfilePicURI) {
        this.mainProfilePicURI = mainProfilePicURI;
    }

    public Uri getMainProfilePicURI() {
        return mainProfilePicURI;
    }

    public void setMainProfilePicURI(Uri mainProfilePicURI) {
        this.mainProfilePicURI = mainProfilePicURI;
    }
}
