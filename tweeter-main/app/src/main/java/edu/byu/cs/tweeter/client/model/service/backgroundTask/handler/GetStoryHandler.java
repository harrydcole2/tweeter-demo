package edu.byu.cs.tweeter.client.model.service.backgroundTask.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.util.List;

import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.backgroundTask.GetStoryTask;
import edu.byu.cs.tweeter.client.model.service.observer.ServiceObserver;
import edu.byu.cs.tweeter.model.domain.Status;

/**
 * Message handler (i.e., observer) for GetStoryTask.
 */
public class GetStoryHandler extends BackgroundTaskHandler {

    public GetStoryHandler(StatusService.StoryObserver observer) {
        super(observer);
    }

    @Override
    protected void handleSuccessMessage(ServiceObserver observer, Bundle data) {
        StatusService.StoryObserver storyObserver = (StatusService.StoryObserver) observer;

        List<Status> statuses = (List<Status>) data.getSerializable(GetStoryTask.ITEMS_KEY);
        boolean hasMorePages = data.getBoolean(GetStoryTask.MORE_PAGES_KEY);
        storyObserver.addMoreStatusesToStory(statuses, hasMorePages);
    }
}
