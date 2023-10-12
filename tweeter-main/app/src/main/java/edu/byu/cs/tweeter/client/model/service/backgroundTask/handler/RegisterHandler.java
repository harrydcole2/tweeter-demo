package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.ActivityObserver;

// RegisterHandler
public class RegisterHandler extends BackgroundTaskHandler<ActivityObserver> {
    public RegisterHandler(ActivityObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ActivityObserver observer, Bundle data) {
        observer.startActivity(data);
    }
}
