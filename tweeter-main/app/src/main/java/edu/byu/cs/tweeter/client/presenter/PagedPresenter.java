package edu.byu.cs.tweeter.client.presenter;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends BasePresenter {

    //pageSize
    //targetUser
    //authToken
    //T lastitem
    //hasMorePages
    //isLoading
    //isGettingUser

    public interface PagedView<S> extends BaseView {
        void setLoadingFooter(boolean value);

        void addMoreItems(List<S> items);

        void startMainActivity(Bundle bundle);
    }
}
