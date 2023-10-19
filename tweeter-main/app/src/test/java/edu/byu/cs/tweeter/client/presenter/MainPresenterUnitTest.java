package edu.byu.cs.tweeter.client.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.StatusService;

public class MainPresenterUnitTest {
    private MainActivityPresenter.View mockView;
    private StatusService mockStatusService;
    private  MainActivityPresenter mainPresenterSpy;

    @BeforeEach
    public void setup() {
        //Create mocks
        mockView = Mockito.mock(MainActivityPresenter.View.class);
        mockStatusService = Mockito.mock(StatusService.class);

        mainPresenterSpy = Mockito.spy(new MainActivityPresenter(mockView));
        //Mockito.doReturn(mockStatusService).when(mainPresenterSpy).getStatusService();
        Mockito.when(mainPresenterSpy.getStatusService()).thenReturn(mockStatusService);
    }

    @Test
    public void testPostStatus_postStatusSuccess() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(2,
                        StatusService.PostStatusObserver.class);
                observer.displaySuccess("testing");
                return null;
            }
        };

        verifyResult(answer, "testing");
    }

    @Test
    public void testPostStatus_postStatusError() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(2,
                        StatusService.PostStatusObserver.class);
                observer.handleError("Failure");
                return null;
            }
        };

        verifyResult(answer, "Failure");
    }

    @Test
    public void testPostStatus_postStatusException() {
        Answer<Void> answer = new Answer<>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StatusService.PostStatusObserver observer = invocation.getArgument(2,
                        StatusService.PostStatusObserver.class);
                observer.handleException(new Exception("ex"));
                return null;
            }
        };

        verifyResult(answer, "Failed to post status because of exception: ex");
    }

    private void verifyResult(Answer<Void> answer, String message) {
        Mockito.doAnswer(answer).when(mockStatusService).postStatus(Mockito.any(), Mockito.any(), Mockito.any());
        mainPresenterSpy.postStatus("testing");

        Mockito.verify(mockView).displayMessage(message);
    }
}
