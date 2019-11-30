package nl.mwsoft.www.superheromatch.modelLayer.event;

import android.net.Uri;

public class ProfilePic4SettingsEvent {

    public Uri profilePicURI;

    public ProfilePic4SettingsEvent() {
    }

    public ProfilePic4SettingsEvent(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }
}
