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

public class User implements Parcelable {
    private String id;
    @Nullable
    private String email;
    private String name;
    private String superHeroName;
    private String mainProfilePicUrl;
    @Nullable
    private ArrayList<ProfilePicture> profilePictures;
    private int gender;
    @Nullable
    private int lookingForGender;
    private int age;
    @Nullable
    private int lookingForAgeMin;
    @Nullable
    private int lookingForAgeMax;
    @Nullable
    private int lookingForDistanceMax;
    @Nullable
    private String distanceUnit;
    @Nullable
    private double lat;
    @Nullable
    private double lon;
    @Nullable
    private String birthday;
    @Nullable
    private String country;
    @Nullable
    private String city;
    private String superPower;
    private String accountType;
    @Nullable
    private String status = "success";

    public User() {
    }

    public User(@Nullable String status) {
        this.status = status;
    }

    public User(String id, @Nullable String email, String name, String superHeroName, String mainProfilePicUrl, @Nullable ArrayList<ProfilePicture> profilePictures, int gender, int lookingForGender, int age, int lookingForAgeMin, int lookingForAgeMax, int lookingForDistanceMax, @Nullable String distanceUnit, double lat, double lon, @Nullable String birthday, @Nullable String country, @Nullable String city, String superPower, String accountType, @Nullable String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.superHeroName = superHeroName;
        this.mainProfilePicUrl = mainProfilePicUrl;
        this.profilePictures = profilePictures;
        this.gender = gender;
        this.lookingForGender = lookingForGender;
        this.age = age;
        this.lookingForAgeMin = lookingForAgeMin;
        this.lookingForAgeMax = lookingForAgeMax;
        this.lookingForDistanceMax = lookingForDistanceMax;
        this.distanceUnit = distanceUnit;
        this.lat = lat;
        this.lon = lon;
        this.birthday = birthday;
        this.country = country;
        this.city = city;
        this.superPower = superPower;
        this.accountType = accountType;
        this.status = status;
    }

    protected User(Parcel in) {
        id = in.readString();
        email = in.readString();
        name = in.readString();
        superHeroName = in.readString();
        mainProfilePicUrl = in.readString();
        profilePictures = in.createTypedArrayList(ProfilePicture.CREATOR);
        gender = in.readInt();
        lookingForGender = in.readInt();
        age = in.readInt();
        lookingForAgeMin = in.readInt();
        lookingForAgeMax = in.readInt();
        lookingForDistanceMax = in.readInt();
        distanceUnit = in.readString();
        lat = in.readDouble();
        lon = in.readDouble();
        birthday = in.readString();
        country = in.readString();
        city = in.readString();
        superPower = in.readString();
        accountType = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(email);
        dest.writeString(name);
        dest.writeString(superHeroName);
        dest.writeString(mainProfilePicUrl);
        dest.writeTypedList(profilePictures);
        dest.writeInt(gender);
        dest.writeInt(lookingForGender);
        dest.writeInt(age);
        dest.writeInt(lookingForAgeMin);
        dest.writeInt(lookingForAgeMax);
        dest.writeInt(lookingForDistanceMax);
        dest.writeString(distanceUnit);
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeString(birthday);
        dest.writeString(country);
        dest.writeString(city);
        dest.writeString(superPower);
        dest.writeString(accountType);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuperHeroName() {
        return superHeroName;
    }

    public void setSuperHeroName(String superHeroName) {
        this.superHeroName = superHeroName;
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

    public int getLookingForGender() {
        return lookingForGender;
    }

    public void setLookingForGender(int lookingForGender) {
        this.lookingForGender = lookingForGender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLookingForAgeMin() {
        return lookingForAgeMin;
    }

    public void setLookingForAgeMin(int lookingForAgeMin) {
        this.lookingForAgeMin = lookingForAgeMin;
    }

    public int getLookingForAgeMax() {
        return lookingForAgeMax;
    }

    public void setLookingForAgeMax(int lookingForAgeMax) {
        this.lookingForAgeMax = lookingForAgeMax;
    }

    public int getLookingForDistanceMax() {
        return lookingForDistanceMax;
    }

    public void setLookingForDistanceMax(int lookingForDistanceMax) {
        this.lookingForDistanceMax = lookingForDistanceMax;
    }

    @Nullable
    public String getDistanceUnit() {
        return distanceUnit;
    }

    public void setDistanceUnit(@Nullable String distanceUnit) {
        this.distanceUnit = distanceUnit;
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

    @Nullable
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(@Nullable String birthday) {
        this.birthday = birthday;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public void setCountry(@Nullable String country) {
        this.country = country;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", superHeroName='" + superHeroName + '\'' +
                ", mainProfilePicUrl='" + mainProfilePicUrl + '\'' +
                ", profilePictures=" + profilePictures +
                ", gender=" + gender +
                ", lookingForGender=" + lookingForGender +
                ", age=" + age +
                ", lookingForAgeMin=" + lookingForAgeMin +
                ", lookingForAgeMax=" + lookingForAgeMax +
                ", lookingForDistanceMax=" + lookingForDistanceMax +
                ", distanceUnit='" + distanceUnit + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", birthday='" + birthday + '\'' +
                ", country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", superPower='" + superPower + '\'' +
                ", accountType='" + accountType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
