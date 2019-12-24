package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfilePicture implements Parcelable {
    private int id;
    private String superheroId;
    private String profilePicUrl;
    private int position;

    public ProfilePicture() {
    }

    public ProfilePicture(int id, String superheroId, String profilePicUrl, int position) {
        this.id = id;
        this.superheroId = superheroId;
        this.profilePicUrl = profilePicUrl;
        this.position = position;
    }

    protected ProfilePicture(Parcel in) {
        id = in.readInt();
        superheroId = in.readString();
        profilePicUrl = in.readString();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(superheroId);
        dest.writeString(profilePicUrl);
        dest.writeInt(position);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfilePicture> CREATOR = new Creator<ProfilePicture>() {
        @Override
        public ProfilePicture createFromParcel(Parcel in) {
            return new ProfilePicture(in);
        }

        @Override
        public ProfilePicture[] newArray(int size) {
            return new ProfilePicture[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSuperheroId() {
        return superheroId;
    }

    public void setSuperheroId(String superheroId) {
        this.superheroId = superheroId;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "ProfilePicture{" +
                "id=" + id +
                ", superheroId='" + superheroId + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", position=" + position +
                '}';
    }
}
