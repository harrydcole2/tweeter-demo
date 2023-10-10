package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends Handler {
    private UserService.LoginObserver observer;

    public LoginHandler(UserService.LoginObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(LoginTask.SUCCESS_KEY);
        if (success) {
            observer.loginToActivity(msg);
        } else if (msg.getData().containsKey(LoginTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(LoginTask.MESSAGE_KEY);
            observer.displayError("Failed to login: " + message);
        } else if (msg.getData().containsKey(LoginTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(LoginTask.EXCEPTION_KEY);
            observer.displayException(ex);
        }
    }
}
