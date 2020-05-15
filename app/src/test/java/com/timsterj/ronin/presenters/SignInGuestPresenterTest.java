package com.timsterj.ronin.presenters;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SignInGuestContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.terrakok.cicerone.Router;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SignInGuestPresenterTest {

    @Mock
    Router mRouter;
    @Mock
    SignInGuestContract.View view;

    @InjectMocks
    private SignInGuestPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void signInGuest_Null_PhoneNumberString_NoSignIn() {
        String phoneNumber = null;
        presenter.signInGuest(phoneNumber, "КамаПуля");
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signInGuest_Empty_PhoneNumberString_NoSignIn() {
        String phoneNumber = "";
        presenter.signInGuest(phoneNumber, "КамаПуля");
        verify(view).onError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR);
    }

    @Test
    public void signInGuest_Invalid_PhoneNumberString_NoSignIn() {
        String phoneNumber = "90334568";
        presenter.signInGuest(phoneNumber, "КамаПуля");
        verify(view).onError(Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION);
    }

    @Test
    public void signInGuest_Normal_PhoneNumberString_NoSignIn() {
        String phoneNumber = "+79876543210";
        presenter.signInGuest(phoneNumber, "КамаПуля");
        verify(view).onSuccess();
    }


    @Test
    public void signInGuest_Null_NameString_NoSignUp() {
        String name = null;
        presenter.signInGuest("+79876543210", name);
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signInGuest_Empty_NameString_NoSignUp() {
        String name = "";
        presenter.signInGuest("+79876543210", name);
        verify(view).onError(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR);
    }

    @Test
    public void signInGuest_Normal_NameString_NoSignUp() {
        String name = "КамаПуля";
        presenter.signInGuest("+79876543210", name);
        verify(view).onSuccess();
    }

    @Test
    public void signInGuest_Normal_Router() {
        Router router = mock(Router.class);
        presenter.setRouter(router);
        assertThat(presenter.router).isNotNull();
    }

    @Test(expected = NullPointerException.class)
    public void signInGuest_Null_Router(){
        Router router = null;
        presenter.setRouter(router);
    }

    @Test
    public void onBackPressed(){
        presenter.onBackPressed();
        verify(mRouter).exit();
    }
}