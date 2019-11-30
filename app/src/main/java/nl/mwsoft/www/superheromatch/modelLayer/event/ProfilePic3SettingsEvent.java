package nl.mwsoft.www.superheromatch.modelLayer.event;

import android.net.Uri;

public class ProfilePic3SettingsEvent {

    public Uri profilePicURI;

    public ProfilePic3SettingsEvent() {
    }

    public ProfilePic3SettingsEvent(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }
}
