package nl.mwsoft.www.superheromatch.modelLayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class Superhero implements Parcelable {

    private String  id;
    private String  superheroName;
    private String mainProfilePicUrl;
    @Nullable
    private ArrayList<ProfilePicture> profilePictures;
    private int gender;
    private int age;
    private double lat;
    private double lon;
    private String country;
    private String city;
    private String superpower;
    private String accountType;

    public Superhero() {
    }

    public Superhero(String id, String superheroName, String mainProfilePicUrl, @Nullable ArrayList<ProfilePicture> profilePictures, int gender, int age, double lat, double lon, String country, String city, String superpower, String accountType) {
        this.id = id;
        this.superheroName = superheroName;
        this.mainProfilePicUrl = mainProfilePicUrl;
        this.profilePictures = profilePictures;
        this.gender = gender;
        this.age = age;
        this.lat = lat;
        this.lon = lon;
        this.country = country;
        this.city = city;
        this.superpower = superpower;
        this.accountType = accountType;
    }

    protected Superhero(Parcel in) {
        id = in.readString();
        superheroName = in.readString();
        mainProfilePicUrl = in.readString();
        profilePictures = in.createTypedArrayList(ProfilePicture.CREATOR);
        gender = in.readInt();
        age = in.readInt();
        lat = in.readDouble();
        lon = in.readDouble();
        country = in.readString();
        city = in.readString();
        superpower = in.readString();
        accountType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(superheroName);
        dest.writeString(mainProfilePicUrl);
        dest.writeTypedList(profilePictures);
        dest.writeInt(gender);
        dest.writeInt(age);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(superpower);
        dest.writeString(accountType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Superhero> CREATOR = new Creator<Superhero>() {
        @Override
        public Superhero createFromParcel(Parcel in) {
            return new Superhero(in);
        }

        @Override
        public Superhero[] newArray(int size) {
            return new Superhero[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSuperheroName() {
        return superheroName;
    }

    public void setSuperheroName(String superheroName) {
        this.superheroName = superheroName;
    }

    public String getMainProfilePicUrl() {
        return mainProfilePicUrl;
    }

    public void setMainProfilePicUrl(String mainProfilePicUrl) {
        this.mainProfilePicUrl = mainProfilePicUrl;
    }

    @Nullable
    public ArrayList<ProfilePicture> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(@Nullable ArrayList<ProfilePicture> profilePictures) {
        this.profilePictures = profilePictures;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuperpower() {
        return superpower;
    }

    public void setSuperpower(String superpower) {
        this.superpower = superpower;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
