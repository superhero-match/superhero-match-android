package nl.mwsoft.www.superheromatch.modelLayer.event;

import android.net.Uri;

public class SuperheroProfilePicEvent {

    public Uri profilePicURI;

    public SuperheroProfilePicEvent() {
    }

    public SuperheroProfilePicEvent(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }

    public Uri getProfilePicURI() {
        return profilePicURI;
    }

    public void setProfilePicURI(Uri profilePicURI) {
        this.profilePicURI = profilePicURI;
    }
}
