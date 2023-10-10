package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class LoginPresenter {

    public interface View {
        void displayMessage(String message);

        void loginToActivity(Message msg, String name, User loggedInUser);

        void setErrowViewText(String text);

    }

    private UserService userService;
    private View view;

    public LoginPresenter(LoginPresenter.View view) {
        this.userService = new UserService();
        this.view = view;
    }

    public void loginProcedure(String alias, String password) {
        try { //TODO determine if this needs a gravestone
            validateLogin(alias, password);
            view.setErrowViewText(null);
            //errorView.setText(null);
            // Send the login request.
            login(alias, password);
        } catch (Exception e) {
            view.setErrowViewText(e.getMessage());
            //errorView.setText(e.getMessage());
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

    private class LoginServiceObserver implements UserService.LoginObserver {
        @Override
        public void displayError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }

        @Override
        public void loginToActivity(Message msg) {
            User loggedInUser = (User) msg.getData().getSerializable(LoginTask.USER_KEY);
            AuthToken authToken = (AuthToken) msg.getData().getSerializable(LoginTask.AUTH_TOKEN_KEY);

            // Cache user session information
            Cache.getInstance().setCurrUser(loggedInUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.loginToActivity(msg, Cache.getInstance().getCurrUser().getName(), loggedInUser);
        }
    }
}
