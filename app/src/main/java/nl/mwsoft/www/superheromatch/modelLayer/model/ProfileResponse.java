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

public class ProfileResponse implements Parcelable {

    private int status;
    private Superhero profile;

    public ProfileResponse() {
    }

    public ProfileResponse(int status) {
        this.status = status;
    }

    public ProfileResponse(int status, Superhero profile) {
        this.status = status;
        this.profile = profile;
    }

    protected ProfileResponse(Parcel in) {
        status = in.readInt();
        profile = in.readParcelable(Superhero.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeParcelable(profile, flags);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public Superhero getProfile() {
        return profile;
    }

    public void setProfile(Superhero profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "ProfileResponse{" +
                "status=" + status +
                ", profile=" + profile.toString() +
                '}';
    }
}
