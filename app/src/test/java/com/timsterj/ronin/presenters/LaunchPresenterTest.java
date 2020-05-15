package com.timsterj.ronin.presenters;

import com.timsterj.ronin.contracts.mvp.LaunchContract;
import com.timsterj.ronin.domain.interfaces.IRepositoryFrontPad;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LaunchPresenterTest {

    @Mock
    private LaunchContract.View launchView;

    @InjectMocks
    private LaunchPresenter presenter;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = spy(new LaunchPresenter());
        presenter.attachView(launchView);

        presenter.repositoryFrontPad = mock(IRepositoryFrontPad.class);
    }

    @Test
    public void startPrepareSession() {

        presenter.startPrepareSession();

        verify(presenter.repositoryFrontPad).getProducts();
    }

    @Test
    public void endPrepareSession() {
        presenter.endPrepareSession();

        verify(presenter).clear();
        verify(presenter).nextActivity();
    }


    @Test
    public void nextActivity() {
        presenter.nextActivity();

        verify(launchView).nextActivity();
    }
}