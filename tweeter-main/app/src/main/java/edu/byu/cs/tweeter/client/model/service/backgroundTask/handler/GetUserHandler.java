package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetUserTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

/**
 * Message handler (i.e., observer) for GetUserTask.
 */
public class GetUserHandler extends BackgroundTaskHandler<UserService.UserObserver> {

    public GetUserHandler(UserService.UserObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(UserService.UserObserver observer, Bundle data) {
        observer.startActivity(data);
    }
}
