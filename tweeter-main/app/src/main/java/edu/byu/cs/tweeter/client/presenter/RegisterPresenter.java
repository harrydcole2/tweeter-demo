package edu.byu.cs.tweeter.client.presenter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class RegisterPresenter extends AuthenticationPresenter {

    public interface View extends AuthView {}

    public RegisterPresenter(RegisterPresenter.View view) {
        super(view);
    }
    public void registerProcedure(String firstName, String lastName, String alias, String password, Drawable imageToUpload) { //TODO: remove dependency
        try {
            validateRegistration(firstName, lastName, alias, password, imageToUpload);
            view.setErrorViewText(null);

            String imageBytesBase64 = view.convertImageToBytesBase64(imageToUpload);
            // Send register request.
            register(firstName, lastName, alias, password, imageBytesBase64);

        } catch (Exception e) {
            view.setErrorViewText(e.getMessage());
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

    private class RegisterServiceObserver extends AuthServiceObserver<RegisterTask> implements UserService.RegisterObserver {
        @Override
        protected String getTaskString() {
            return "register";
        }
    }
}
