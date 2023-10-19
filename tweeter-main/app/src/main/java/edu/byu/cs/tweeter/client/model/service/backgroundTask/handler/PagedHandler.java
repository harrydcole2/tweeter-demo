package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetFeedTask;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;

public abstract class PagedHandler<T> extends BackgroundTaskHandler {

    public PagedHandler(ServiceObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        PagedObserver pagedObserver = (PagedObserver) observer;

        List<T> items = (List<T>) data.getSerializable(GetFeedTask.ITEMS_KEY); //TODO from GetFeed to PagedTask
        boolean hasMorePages = data.getBoolean(GetFeedTask.MORE_PAGES_KEY);
        pagedObserver.addMoreItems(items, hasMorePages);
    }
}
