package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LoginTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

/**
 * Message handler (i.e., observer) for LoginTask
 */
public class LoginHandler extends BackgroundTaskHandler {
    public LoginHandler(UserService.LoginObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        UserService.LoginObserver loginObserver = (UserService.LoginObserver) observer;

        loginObserver.loginToActivity(data);
    }
}
