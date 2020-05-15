package com.timsterj.ronin.presenters;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SignInContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import ru.terrakok.cicerone.Router;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SignInPresenterTest {

    @Mock
    SignInContract.View view;

    @Mock
    Router mRouter;
    @InjectMocks
    private SignInPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signIn_Null_LoginString_NoSignIn() {
        String login = null;
        presenter.signIn(login, "Password");
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signIn_Empty_LoginString_NoSignIn() {
        String login = "";
        presenter.signIn(login, "Password");
        verify(view).onError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR);
    }

    @Test
    public void signIn_Invalid_LoginString_NoSignIn() {
        String login = "90334568";
        presenter.signIn(login, "Password");
        verify(view).onError(Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION);
    }

    @Test
    public void signIn_Normal_LoginString_NoSignIn() {
        String login = "+79876543210";
        presenter.signIn(login, "Password");
        verify(view, never()).onError(Contracts.ErrorAuthorization.LOGIN_INVALID_VALIDATION);
    }

    @Test
    public void signIn_Null_PasswordString_NoSignIn() {
        String password = null;
        presenter.signIn("login", password);
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signIn_Empty_PasswordString_NoSignIn() {
        String password = "";
        presenter.signIn("login", password);
        verify(view).onError(Contracts.ErrorAuthorization.PASSWORD_EMPTY_ERROR);
    }

    @Test
    public void signIn_Invalid_PasswordString_NoSignIn() {
        String password = "123";
        presenter.signIn("login", password);
        verify(view).onError(Contracts.ErrorAuthorization.PASSWORD_EASY_ERROR);
    }

    @Test
    public void signIn_Normal_PasswordString_NoSignIn() {
        String password = "1234567";
        presenter.signIn("login", password);
        verify(view, never()).onError(Contracts.ErrorAuthorization.PASSWORD_EASY_ERROR);
    }

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
    public void onBackPressed_Normal() {
        presenter.onBackPressed();
        verify(mRouter).exit();
    }

}