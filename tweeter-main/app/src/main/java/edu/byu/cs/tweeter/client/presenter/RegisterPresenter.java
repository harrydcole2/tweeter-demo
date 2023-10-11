package edu.byu.cs.tweeter.client.presenter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter {

    public interface View {
        void displayMessage(String message);

        void registerToActivity(String name, User registeredUser);

        void setErrowViewText(String text);

        String convertImageToBytesBase64(Drawable imageToUpload);
    }
    private UserService userService;
    private View view;

    public RegisterPresenter(RegisterPresenter.View view) {
        this.userService = new UserService();
        this.view = view;
    }
    public void registerProcedure(String firstName, String lastName, String alias, String password, Drawable imageToUpload) {
        try {
            validateRegistration(firstName, lastName, alias, password, imageToUpload);
            view.setErrowViewText(null);
            //errorView.setText(null);

            String imageBytesBase64 = view.convertImageToBytesBase64(imageToUpload);

            // Send register request.
            register(firstName, lastName, alias, password, imageBytesBase64);


        } catch (Exception e) {
            view.setErrowViewText(e.getMessage());
            //errorView.setText(e.getMessage());
        }
    }

    public void validateRegistration(String firstName, String lastName, String alias, String password,
                                     Drawable imageToUpload) throws IllegalArgumentException{
        if (firstName.length() == 0) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (lastName.length() == 0) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (alias.length() == 0) {
            throw new IllegalArgumentException("Alias cannot be empty.");
        }
        if (alias.charAt(0) != '@') {
            throw new IllegalArgumentException("Alias must begin with @.");
        }
        if (alias.length() < 2) {
            throw new IllegalArgumentException("Alias must contain 1 or more characters after the @.");
        }
        if (password.length() == 0) {
            throw new IllegalArgumentException("Password cannot be empty.");
        }
        if (imageToUpload == null) { //changed to drawable, which is checked... UI element problem? TODO isEmpty for fix
            throw new IllegalArgumentException("Profile image must be uploaded.");
        }
    }

    public void register(String firstName, String lastName, String alias, String password, String imageBytesBase64) {
        userService.register(firstName, lastName, alias, password, imageBytesBase64, new RegisterServiceObserver());
    }

    private class RegisterServiceObserver implements UserService.RegisterObserver {
        @Override
        public void handleError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to register because of exception: " + ex.getMessage());
        }

        @Override
        public void startActivity(Bundle data) {
            User registeredUser = (User) data.getSerializable(RegisterTask.USER_KEY);
            AuthToken authToken = (AuthToken) data.getSerializable(RegisterTask.AUTH_TOKEN_KEY);

            Cache.getInstance().setCurrUser(registeredUser);
            Cache.getInstance().setCurrUserAuthToken(authToken);

            view.registerToActivity(Cache.getInstance().getCurrUser().getName(), registeredUser);
        }
    }
}
