package nl.mwsoft.www.superheromatch.modelLayer.event;

import android.net.Uri;

public class ProfilePic2SettingsEvent {

    public Uri profilePicURI;

    public ProfilePic2SettingsEvent() {
    }

    public ProfilePic2SettingsEvent(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }
}
