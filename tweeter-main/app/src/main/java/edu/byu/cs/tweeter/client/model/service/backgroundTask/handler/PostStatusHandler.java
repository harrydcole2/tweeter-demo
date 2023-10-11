package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.PostStatusTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;

public class PostStatusHandler extends BackgroundTaskHandler<StatusService.PostStatusObserver> {
    public PostStatusHandler(StatusService.PostStatusObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(StatusService.PostStatusObserver observer, Bundle data) {
        observer.displaySuccess("Successfully Posted!");
    }
}
