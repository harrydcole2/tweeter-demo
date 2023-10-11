package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

// RegisterHandler
public class RegisterHandler extends BackgroundTaskHandler {
    public RegisterHandler(UserService.RegisterObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        UserService.RegisterObserver registerObserver = (UserService.RegisterObserver) observer;

        registerObserver.registerToActivity(data);

    }
}
