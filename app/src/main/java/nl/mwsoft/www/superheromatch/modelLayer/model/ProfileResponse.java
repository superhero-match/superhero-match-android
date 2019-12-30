package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ProfileResponse implements Parcelable {

    private int status;
    private Superhero suggestion;

    public ProfileResponse() {
    }

    public ProfileResponse(int status, Superhero suggestion) {
        this.status = status;
        this.suggestion = suggestion;
    }

    protected ProfileResponse(Parcel in) {
        status = in.readInt();
        suggestion = in.readParcelable(Superhero.class.getClassLoader());
    }

    public static final Creator<ProfileResponse> CREATOR = new Creator<ProfileResponse>() {
        @Override
        public ProfileResponse createFromParcel(Parcel in) {
            return new ProfileResponse(in);
        }

        @Override
        public ProfileResponse[] newArray(int size) {
            return new ProfileResponse[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Superhero getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Superhero suggestion) {
        this.suggestion = suggestion;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeParcelable(suggestion, flags);
    }
}
