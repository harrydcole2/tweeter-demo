package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.observer.ActivityObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter extends AuthenticationPresenter {

    public LoginPresenter(AuthView view) {
        super(view);
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

    private class LoginServiceObserver extends AuthServiceObserver<LoginTask> implements ActivityObserver {
        @Override
        protected String getTaskString() {
            return "log in";
        }
    }
}
