package edu.byu.cs.tweeter.client.model.service.observer;

import android.os.Bundle;

public interface ActivityObserver extends ServiceObserver {
    void startActivity(Bundle bundle);
}