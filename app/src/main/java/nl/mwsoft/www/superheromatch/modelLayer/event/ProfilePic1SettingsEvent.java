package nl.mwsoft.www.superheromatch.modelLayer.event;

import android.net.Uri;

public class ProfilePic1SettingsEvent {

    public Uri profilePicURI;

    public ProfilePic1SettingsEvent() {
    }

    public ProfilePic1SettingsEvent(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }
}
