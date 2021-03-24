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
package nl.mwsoft.www.superheromatch.viewLayer.verifyIdentity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import nl.mwsoft.www.superheromatch.R;
import nl.mwsoft.www.superheromatch.coordinator.RootCoordinator;
import nl.mwsoft.www.superheromatch.dependencyRegistry.DependencyRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.constantRegistry.ConstantRegistry;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.presenterLayer.verifyIdentity.VerifyIdentityPresenter;
import nl.mwsoft.www.superheromatch.viewLayer.dialog.loadingDialog.LoadingDialogFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.widget.Toast.LENGTH_SHORT;

public class VerifyIdentityActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "VerifyIdentityActivity";
    private static final int RC_SIGN_IN = 9001;
    @BindView(R.id.btnSignIn)
    SignInButton btnSignIn;
    @BindView(R.id.tbVerifyIdentity)
    Toolbar tbVerifyIdentity;
    private Unbinder unbinder;
    private RootCoordinator rootCoordinator;
    private VerifyIdentityPresenter verifyIdentityPresenter;
    private CompositeDisposable disposable;
    private Disposable subscribeCheckEmailExists;
    private LoadingDialogFragment loadingDialogFragment;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_identity);
        unbinder = ButterKnife.bind(this);
        DependencyRegistry.shared.inject(this);
        setSupportActionBar(tbVerifyIdentity);
        disposable = new CompositeDisposable();

        configureFirebase();
    }

    private void configureFirebase() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();
    }

    public void configureWith(RootCoordinator rootCoordinator, VerifyIdentityPresenter verifyIdentityPresenter) {
        this.rootCoordinator = rootCoordinator;
        this.verifyIdentityPresenter = verifyIdentityPresenter;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(VerifyIdentityActivity.this, "Authentication Succeeded.", LENGTH_SHORT).show();

                            // Make network call here to check if email already registered.
                            // If so, redirect to MainActivity.
                            // If not, redirect to RegisterActivity.
                            checkIfEmailRegistered(acct);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    @OnClick(R.id.btnSignIn)
    public void signInListener() {
        signIn();
    }

    @OnClick(R.id.tvConfirmInfoPP)
    public void privacyPolicyClickListener(){
        rootCoordinator.navigateToPrivacyPolicyWebPage(VerifyIdentityActivity.this);
    }

    @OnClick(R.id.tvWelcomeInfoTP2)
    public void termsAndPoliciesClickListener(){
        rootCoordinator.navigateToTermsAndPoliciesWebPage(VerifyIdentityActivity.this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runOnDestroyRoutine();
    }

    private void runOnDestroyRoutine() {
        cleanUpData();
        unbindButterKnife();
    }

    private void unbindButterKnife() {
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void cleanUpData() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void navigateToRegister(Context context, String name, String email) {
        rootCoordinator.navigateToRegister(context, name, email);
        finish();
    }

    private void navigateToMain() {
        rootCoordinator.navigateToMain(VerifyIdentityActivity.this);
        finish();
    }

    private void closeLoadingDialog() {
        if (loadingDialogFragment != null) {
            loadingDialogFragment.dismiss();
        }
    }

    private void showLoadingDialog() {
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.setCancelable(false);
        loadingDialogFragment.show(getSupportFragmentManager(), ConstantRegistry.LOADING);
    }

    private void checkIfEmailRegistered(GoogleSignInAccount acct) {
        showLoadingDialog();

        subscribeCheckEmailExists = verifyIdentityPresenter.checkEmailAlreadyExists(acct.getEmail())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe((CheckEmailResponse res) -> {
                    closeLoadingDialog();

                    if (res.getStatus() == ConstantRegistry.SERVER_RESPONSE_ERROR) {
                        Toast.makeText(VerifyIdentityActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();

                        return;
                    }

                    if (!res.isRegistered()) {
                        navigateToRegister(VerifyIdentityActivity.this, acct.getDisplayName(), acct.getEmail());

                        return;
                    }

                    if (res.isBlocked() || res.isDeleted()) {
                        Toast.makeText(VerifyIdentityActivity.this, R.string.blocked_deleted, Toast.LENGTH_LONG).show();

                        return;
                    }

                    // Save user to local db
                    if (verifyIdentityPresenter.getUserId(VerifyIdentityActivity.this).isEmpty()) {
                        verifyIdentityPresenter.saveUserToDB(res.getSuperhero(), VerifyIdentityActivity.this);
                    } else {
                        verifyIdentityPresenter.updateInitiallyRegisteredUser(res.getSuperhero(), VerifyIdentityActivity.this);
                    }

                    navigateToMain();
                }, throwable -> handleError());

        disposable.add(subscribeCheckEmailExists);
    }

    private void handleError() {
        closeLoadingDialog();
        Toast.makeText(VerifyIdentityActivity.this, R.string.smth_went_wrong, Toast.LENGTH_LONG).show();
    }
}
