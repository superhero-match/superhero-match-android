/*
  Copyright (C) 2019 - 2021 MWSOFT
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
package nl.mwsoft.www.superheromatch.coordinator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.viewLayer.intro.activity.IntroActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.permissionsRequest.activity.PermissionsRequestActivity;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;
import nl.mwsoft.www.superheromatch.viewLayer.verifyIdentity.VerifyIdentityActivity;

public class RootCoordinator {

    public RootCoordinator() {
    }

    public void navigateToMainFromRegister(Context context) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(mainIntent);
    }

    public void navigateToMain(Context context) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mainIntent.setAction("UserExists");
        mainIntent.putExtra("Exists", true);
        context.startActivity(mainIntent);
    }

    public void navigateToIntroFromMain(Context context){
        Intent introIntent = new Intent(context, IntroActivity.class);
        introIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(introIntent);
    }

    public void navigateToVerifyIdentityActivity(Context context){
        Intent introIntent = new Intent(context, VerifyIdentityActivity.class);
        introIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(introIntent);
    }

    public void navigateToRegisterFromMain(Context context){
        Intent registerIntent = new Intent(context, RegisterActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(registerIntent);
    }


    public void navigateToRegister(Context context, String name, String email){
        Intent registerIntent = new Intent(context, RegisterActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        registerIntent.setAction("register");
        registerIntent.putExtra("name", name);
        registerIntent.putExtra("email", email);
        context.startActivity(registerIntent);
    }

    public void navigateToRegisterFromPermissionsRequest(Context context){
        Intent registerIntent = new Intent(context, RegisterActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(registerIntent);
    }

    public void navigateToPermissionsRequestActivity(Context context){
        Intent registerIntent = new Intent(context, PermissionsRequestActivity.class);
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(registerIntent);
    }

    public void navigateToPrivacyPolicyWebPage(Context context){
        Uri privacyPolicy = Uri.parse(ConstantRegistry.SUPERHERO_MATCH_PRIVACY_POLICY_URL);
        Intent intent = new Intent(Intent.ACTION_VIEW, privacyPolicy);
        context.startActivity(intent);
    }
}
