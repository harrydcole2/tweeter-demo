package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.LogoutTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class LogoutHandler extends BackgroundTaskHandler {

    public LogoutHandler(UserService.LogoutObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        UserService.LogoutObserver logoutObserver = (UserService.LogoutObserver) observer;

        logoutObserver.logout();
    }
}
