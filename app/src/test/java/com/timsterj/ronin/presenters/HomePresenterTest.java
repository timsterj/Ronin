package com.timsterj.ronin.presenters;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.HomeContract;
import com.timsterj.ronin.fragments.HomeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HomePresenterTest {

    private HomePresenter presenter;

    @Mock
    HomeContract.View homeView;

    @Mock
    FragmentTransaction transaction;
    @Mock
    FragmentManager fragmentManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new HomePresenter();
        presenter.attachView(homeView);

        when(fragmentManager.findFragmentByTag(Contracts.NavigationConstant.TAB_HOME)).thenReturn(new HomeFragment());
        when(fragmentManager.beginTransaction()).thenReturn(transaction);
    }

    @Test
    public void selectTab_NullFragmentManager_NoNavigate() {
        presenter.selectTab(Contracts.NavigationConstant.TAB_HOME, 1, null);
        verify(homeView).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void selectTab_NormalFragmentManager_Navigate() {
        presenter.selectTab(Contracts.NavigationConstant.TAB_HOME, 1, fragmentManager);
        verify(fragmentManager.beginTransaction()).commitNow();
    }

    @Test
    public void selectTab_NullTabNameString_NoNavigate() {
        presenter.selectTab(null, 1, fragmentManager);
        verify(homeView).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void selectTab_EmptyTabNameString_NoNavigate() {
        presenter.selectTab("", 1, fragmentManager);
        verify(homeView).onError(Contracts.ErrorConstant.ERROR_EMPTY);
    }

    @Test
    public void selectTab_NormalTabNameString_Navigate() {
        presenter.selectTab(Contracts.NavigationConstant.TAB_HOME, 1, fragmentManager);
        verify(fragmentManager.beginTransaction()).commitNow();
    }

}