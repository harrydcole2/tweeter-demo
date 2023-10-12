package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class BasePresenter<T extends BasePresenter.BaseView> {
    protected FollowService followService;
    protected UserService userService;
    protected StatusService statusService;

    protected T view;
    public interface BaseView {
        void displayMessage(String message);
    }
    public BasePresenter() {
        this.followService = new FollowService();
        this.userService = new UserService();
        this.statusService = new StatusService();
    }

    protected abstract class BaseServiceObserver implements ServiceObserver {
        @Override
        public void handleError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void handleException(Exception ex) {
            view.displayMessage("Failed to " + getTaskString() + " because of exception: " + ex.getMessage());
        }
        protected abstract String getTaskString();
    }
}
