package nl.mwsoft.www.superheromatch.presenterLayer.verifyIdentity;

import android.content.Context;

import io.reactivex.Observable;
import nl.mwsoft.www.superheromatch.modelLayer.model.CheckEmailResponse;
import nl.mwsoft.www.superheromatch.modelLayer.model.User;
import nl.mwsoft.www.superheromatch.modelLayer.modelLayerManager.ModelLayerManager;

public class VerifyIdentityPresenter {

    private ModelLayerManager modelLayerManager;

    public VerifyIdentityPresenter() {
    }

    public VerifyIdentityPresenter(ModelLayerManager modelLayerManager) {
        this.modelLayerManager = modelLayerManager;
    }

    // region Check If Email Already Exists

    public Observable<CheckEmailResponse> checkEmailAlreadyExists(String email) {
        return this.modelLayerManager.checkEmailAlreadyExists(email);
    }

    // endregion

    // region User Database Layer

    public String getUserId(Context context) {
        return this.modelLayerManager.getUserId(context);
    }

    public void saveUserToDB(User user, Context context) {
        this.modelLayerManager.saveUserToDB(user, context);
    }

    public void updateInitiallyRegisteredUser(User user, Context context) {
        this.modelLayerManager.updateInitiallyRegisteredUser(user, context);
    }

    // endregion
}
