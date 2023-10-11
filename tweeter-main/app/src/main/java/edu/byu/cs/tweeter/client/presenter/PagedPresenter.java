package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;
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
}
