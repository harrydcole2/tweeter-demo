package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends PagedHandler {

    public GetStoryHandler(PagedObserver<Status> observer) {
        super(observer);
    }
}
