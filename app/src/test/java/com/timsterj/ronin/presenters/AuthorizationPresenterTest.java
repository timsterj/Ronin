package com.timsterj.ronin.presenters;

import com.timsterj.ronin.contracts.mvp.AuthorizationContract;
import com.timsterj.ronin.navigation.Screens;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ru.terrakok.cicerone.Router;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthorizationPresenterTest {

    @Mock
    Router mRouter;
    @Mock
    AuthorizationContract.View view;
    @InjectMocks
    private AuthorizationPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void goSignIn(){
        presenter.goSignIn();
        verify(mRouter).navigateTo(Mockito.argThat(argument -> {
            if (argument instanceof Screens.AuthorizationFlow.SignInScreen) {
                return true;
            }
            return false;
        }));
    }

    @Test
    public void goSignUp(){
        presenter.goSignUp();
        verify(mRouter).navigateTo(Mockito.argThat(argument -> {
            if (argument instanceof Screens.AuthorizationFlow.SignUpScreen) {
                return true;
            }
            return false;
        }));    }

    @Test
    public void goSignInGuest(){
        presenter.goSignInGuest();
        verify(mRouter).navigateTo(Mockito.argThat(argument -> {
            if (argument instanceof Screens.AuthorizationFlow.SignInGuestScreen) {
                return true;
            }
            return false;
        }));    }

    @Test(expected = NullPointerException.class)
    public void setRouter_Null_Router() {
        Router router = null;
        presenter.setRouter(router);
    }

    @Test
    public void setRouter_Normal_Router() {
        Router router = mock(Router.class);
        presenter.setRouter(router);
        assertThat(presenter.router).isNotNull();
    }

    @Test
    public void onBackPressed_Normal(){
        presenter.onBackPressed();
        verify(mRouter).exit();
    }

}