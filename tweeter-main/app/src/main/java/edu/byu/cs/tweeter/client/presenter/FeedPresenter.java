package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedPresenter {
    private static final int PAGE_SIZE = 10;
    private Status lastStatus;
    private boolean hasMorePages;
    private boolean isLoading = false;
    private StatusService statusService;

    private UserService userService;
    private View view;

    public FeedPresenter(FeedPresenter.View view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(),
                userAlias, new UserServiceObserver());
    }

    public interface View {
        void setLoadingFooter(boolean value);

        void displayMessage(String message);

        void addMoreStatuses(List<Status> statuses);

        void startMainActivity(Message msg);
    }
    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);

            statusService.loadMoreItemsForFeed(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                    lastStatus, new FeedServiceObserver());
        }
    }

    private class FeedServiceObserver implements StatusService.FeedObserver {

        @Override
        public void displayError(String message) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            isLoading = false;
            view.setLoadingFooter(false);
            view.displayMessage("Failed to get following because of exception: " + ex.getMessage());
        }

        @Override
        public void addMoreStatusesToFeed(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            FeedPresenter.this.hasMorePages = hasMorePages;
            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() - 1) : null;
            view.addMoreStatuses(statuses);
        }
    }

    private class UserServiceObserver implements UserService.UserObserver {

        @Override
        public void displayError(String message) {
            view.displayMessage(message);
        }

        @Override
        public void displayException(Exception ex) {
            view.displayMessage("Failed to get user's profile because of exception: " + ex.getMessage());
        }

        @Override
        public void startActivity(Message msg) {
            view.startMainActivity(msg);
        }
    }
}
