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
package nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager;

import android.content.Context;

import java.util.ArrayList;

import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.database.user.UserDatabaseLayer;
import nl.mwsoft.www.superheromatch.modelLayer.model.Choice;
import nl.mwsoft.www.superheromatch.modelLayer.network.NetworkLayer;

public class UserModelLayerManager {

    private UserDatabaseLayer userDatabaseLayer;
    private NetworkLayer networkLayer;

    public UserModelLayerManager() {
        this.userDatabaseLayer = DependencyRegistry.shared.createUserDatabaseLayer();
        this.networkLayer = DependencyRegistry.shared.createNetworkLayer();
    }

    // region User DB

    public String getUserId(Context context) {
        return this.userDatabaseLayer.getUserId(context);
    }

    public String getUserName(Context context) {
        return this.userDatabaseLayer.getUserName(context);
    }

    public String getUserProfilePicUri(Context context) {
        return this.userDatabaseLayer.getUserMainProfilePicUrl(context);
    }

    public void updateUserId(String userID, Context context){
        this.userDatabaseLayer.updateUserId(userID, context);
    }

    public void updateUserProfilePic(String userID, String uri, Context context){
        this.userDatabaseLayer.updateUserMainProfilePic(userID, uri, context);
    }

    public ArrayList<Choice> getAllChoices(Context context) {
        return this.userDatabaseLayer.getAllChoices(context);
    }

    public void insertChoice(String chosenUserId, int choice, String createdAt, Context context) {
        this.userDatabaseLayer.insertChoice(chosenUserId, choice, createdAt, context);
    }

    public void deleteChoice(int id, Context context) {
        this.userDatabaseLayer.deleteChoice(id, context);
    }
}
