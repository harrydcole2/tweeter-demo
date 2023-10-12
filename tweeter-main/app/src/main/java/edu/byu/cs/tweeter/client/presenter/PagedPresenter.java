package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.ActivityObserver;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends BasePresenter {

    protected static final int PAGE_SIZE = 10;
    protected T lastItem;
    protected boolean hasMorePages;
    protected boolean isLoading = false;

    protected PagedView<T> view;

    public interface PagedView<S> extends BaseView {
        void setLoadingFooter(boolean value);

        void addMoreItems(List<S> items);

        void startMainActivity(Bundle bundle);
    }

    public boolean hasMorePages() {
        return hasMorePages;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void loadMoreItems(User user) {
        if (!isLoading) {   // This guard is important for avoiding a race condition in the scrolling code.
            isLoading = true;
            view.setLoadingFooter(true);
            callServiceToLoad(user);
        }
    }

    public void getUserProfile(String userAlias) {
        userService.getUserProfile(Cache.getInstance().getCurrUserAuthToken(), userAlias, new UserServiceObserver());
    }


    protected abstract void callServiceToLoad(User user);
    protected class PagedServiceObserver extends BaseServiceObserver implements PagedObserver<T>{
        @Override
        public void addMoreItems(List items, boolean hasMorePages) {
            isLoading = false;
            view.setLoadingFooter(false);
            PagedPresenter.this.hasMorePages = hasMorePages;
            lastItem = (items.size() > 0) ? (T) items.get(items.size() - 1) : null;
            view.addMoreItems(items);
        }

        @Override
        protected String getTaskString() {
            return "get items";
        }
    }

    protected class UserServiceObserver extends BaseServiceObserver implements ActivityObserver {
        @Override
        public void startActivity(Bundle bundle) {
            view.startMainActivity(bundle);
        }
        @Override
        protected String getTaskString() {
            return "get user's profile";
        }
    }
}
