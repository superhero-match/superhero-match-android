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

public class ProfilePicture implements Parcelable {
    private String superheroId;
    private String profilePicUrl;
    private int position;

    public ProfilePicture() {
    }

    public ProfilePicture(String superheroId, String profilePicUrl, int position) {
        this.superheroId = superheroId;
        this.profilePicUrl = profilePicUrl;
        this.position = position;
    }

    protected ProfilePicture(Parcel in) {
        superheroId = in.readString();
        profilePicUrl = in.readString();
        position = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
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
                "superheroId='" + superheroId + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", position=" + position +
                '}';
    }
}
