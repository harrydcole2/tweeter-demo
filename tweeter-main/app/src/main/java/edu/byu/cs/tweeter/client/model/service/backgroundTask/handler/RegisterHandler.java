package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.RegisterTask;

// RegisterHandler
public class RegisterHandler extends Handler {
    private UserService.RegisterObserver observer;

    public RegisterHandler(UserService.RegisterObserver observer) {
        super(Looper.getMainLooper());
        this.observer = observer;
    }

    @Override
    public void handleMessage(@NonNull Message msg) {
        boolean success = msg.getData().getBoolean(RegisterTask.SUCCESS_KEY);
        if (success) {
            observer.registerToActivity(msg);
        } else if (msg.getData().containsKey(RegisterTask.MESSAGE_KEY)) {
            String message = msg.getData().getString(RegisterTask.MESSAGE_KEY);
            observer.displayError("Failed to login: " + message);
        } else if (msg.getData().containsKey(RegisterTask.EXCEPTION_KEY)) {
            Exception ex = (Exception) msg.getData().getSerializable(RegisterTask.EXCEPTION_KEY);
            observer.displayException(ex);
        }
    }
}
