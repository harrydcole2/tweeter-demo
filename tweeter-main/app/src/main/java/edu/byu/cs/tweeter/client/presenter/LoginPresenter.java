package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticationPresenter {

    public interface View extends AuthView {}
    public LoginPresenter(LoginPresenter.View view) {
        this.view = view;
    }

    public void loginProcedure(String alias, String password) {
        try {
            validateLogin(alias, password);
            view.setErrorViewText(null);
            login(alias, password);
        } catch (Exception e) {
            view.setErrorViewText(e.getMessage());
        }
    }

    public void login(String username, String password) {
        userService.login(username, password, new LoginServiceObserver());
    }

    public void validateLogin(String alias, String password) throws IllegalArgumentException { //I dislike the static
        if (alias.length() > 0 && alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
    }

    private class LoginServiceObserver extends BaseServiceObserver implements UserService.LoginObserver {
        @Override
        public void startActivity(Bundle data) {
            User loggedInUser = (User) data.getSerializable(LoginTask.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(LoginTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.startNewActivity(Cache.getInstance().getCurrUser().getName(), loggedInUser);
        }

        @Override
        protected String getTaskString() {
            return "get user's profile";
        }
    }
}
