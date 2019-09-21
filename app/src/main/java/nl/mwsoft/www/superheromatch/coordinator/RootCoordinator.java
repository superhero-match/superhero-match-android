package nl.mwsoft.www.superheromatch.coordinator;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.model.RegistrationUser;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.viewLayer.intro.activity.IntroActivity;
import nl.mwsoft.www.superheromatch.viewLayer.main.activity.MainActivity;
import nl.mwsoft.www.superheromatch.viewLayer.permissionsRequest.activity.PermissionsRequestActivity;
import nl.mwsoft.www.superheromatch.viewLayer.register.activity.RegisterActivity;
import nl.mwsoft.www.superheromatch.viewLayer.verifyIdentity.VerifyIdentityActivity;

public class RootCoordinator {

    public RootCoordinator() {
    }

    public void navigateToMainFromRegister(Context context, User user) {
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mainIntent.setAction("User");
        mainIntent.putExtra("User", user);
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
