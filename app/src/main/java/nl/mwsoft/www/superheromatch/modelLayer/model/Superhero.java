/*
  Copyright (C) 2019 - 2020 MWSOFT
  This program is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.
  This program is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.
  You should have received a copy of the GNU General Public License
  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    private boolean hasLikedMe;
    private String createdAt;

    public Superhero() {
    }

    public Superhero(String id, String superheroName, String mainProfilePicUrl, @Nullable ArrayList<ProfilePicture> profilePictures, int gender, int age, double lat, double lon, String country, String city, String superpower, String accountType, boolean hasLikedMe, String createdAt) {
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
        this.hasLikedMe = hasLikedMe;
        this.createdAt = createdAt;
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
        hasLikedMe = in.readByte() != 0;
        createdAt = in.readString();
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
        dest.writeByte((byte) (hasLikedMe ? 1 : 0));
        dest.writeString(createdAt);
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

    public boolean isHasLikedMe() {
        return hasLikedMe;
    }

    public void setHasLikedMe(boolean hasLikedMe) {
        this.hasLikedMe = hasLikedMe;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "id='" + id + '\'' +
                ", superheroName='" + superheroName + '\'' +
                ", mainProfilePicUrl='" + mainProfilePicUrl + '\'' +
                ", profilePictures=" + profilePictures +
                ", gender=" + gender +
                ", age=" + age +
                ", lat=" + lat +
                ", lon=" + lon +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", superpower='" + superpower + '\'' +
                ", accountType='" + accountType + '\'' +
                ", hasLikedMe=" + hasLikedMe +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
