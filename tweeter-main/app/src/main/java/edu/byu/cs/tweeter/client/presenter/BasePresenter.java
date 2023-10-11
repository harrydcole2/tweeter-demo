package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class BasePresenter {
    protected FollowService followService;
    protected UserService userService;
    protected StatusService statusService;
    public interface BaseView {
        void displayMessage(String message);
    }
    public BasePresenter() {
        this.followService = new FollowService();
        this.userService = new UserService();
        this.statusService = new StatusService();
    }
}
