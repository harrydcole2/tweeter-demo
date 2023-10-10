package edu.byu.cs.tweeter.client.presenter;

import android.os.Message;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryPresenter {
    private static final int PAGE_SIZE = 10;
    private Status lastStatus;

    private boolean hasMorePages;
    private boolean isLoading = false;

    private StatusService statusService;
    private UserService userService;
    private View view;

    public StoryPresenter(StoryPresenter.View view) {
        this.view = view;
        statusService = new StatusService();
        userService = new UserService();
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);

            statusService.loadMoreItemsForStory(Cache.getInstance().getCurrUserAuthToken(), user, PAGE_SIZE,
                    lastStatus, new StoryServiceObserver());
        }
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

    private class StoryServiceObserver implements StatusService.StoryObserver {

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
        public void addMoreStatusesToStory(List<Status> statuses, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            StoryPresenter.this.hasMorePages = hasMorePages;
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