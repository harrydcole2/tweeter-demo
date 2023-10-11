package edu.byu.cs.tweeter.client.model.service.observer;

import android.os.Bundle;
import android.os.Message;

public interface ServiceObserver {
    void displayError(String message);
    void displayException(Exception exception);
}