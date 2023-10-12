package edu.byu.cs.tweeter.client.presenter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.AuthenticateTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.observer.ActivityObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class AuthenticationPresenter extends BasePresenter<AuthenticationPresenter.AuthView> {
    public interface AuthView extends BaseView {
        void startNewActivity(String name, User registeredUser);
        void setErrorViewText(String text);
        String convertImageToBytesBase64(Drawable imageToUpload);
    }

    AuthenticationPresenter(AuthView view) {
        this.view = view;
    }
    protected abstract class AuthServiceObserver<T extends AuthenticateTask> extends
            BaseServiceObserver implements ActivityObserver {
        @Override
        public void startActivity(Bundle data) {
            User currentUser = (User) data.getSerializable(T.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(T.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(currentUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.startNewActivity(Cache.getInstance().getCurrUser().getName(), currentUser);
        }
    }
}