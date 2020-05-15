package com.timsterj.ronin.presenters;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.SignUpContract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import ru.terrakok.cicerone.Router;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SignUpPresenterTest {

    @Mock
    Router mRouter;
    @Mock
    SignUpContract.View view;
    @InjectMocks
    private SignUpPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void signUp_Null_NameString_NoSignUp() {
        String name = null;
        presenter.signUp(name,
                "+79876543210",
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signUp_Empty_NameString_NoSignUp() {
        String name = "";
        presenter.signUp(name,
                "+79876543210",
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR);
    }

    @Test
    public void signUp_Normal_NameString_NoSignUp() {
        String name = "КамаПуля";
        presenter.signUp(name,
                "+79876543210",
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onSuccess();
    }

    @Test
    public void signUp_Null_PhoneNumberString_NoSignUp() {
        String phoneNumber = null;
        presenter.signUp("Кама",
                phoneNumber,
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }


    @Test
    public void signUp_Empty_PhoneNumberString_And_Empty_Email_NoSignUp() {
        String phoneNumber = "";
        String email = "";
        presenter.signUp("Кама",
                phoneNumber,
                "20.20.2000",
                email,
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR);
        verify(view).onError(Contracts.ErrorAuthorization.EMAIL_EMPTY_ERROR);
    }

    @Test
    public void signUp_Empty_PhoneNumberString_NoSignUp() {
        String phoneNumber = "";
        presenter.signUp("Кама",
                phoneNumber,
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view, never()).onError(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR);
    }

    @Test
    public void signUp_Normal_PhoneNumberString_NoSignUp() {
        String phoneNumber = "+79876543210";
        presenter.signUp("Кама",
                phoneNumber,
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onSuccess();
    }

    @Test
    public void signUp_Invalid_PhoneNumberString_NoSignUp() {
        String phoneNumber = "9876543210";
        presenter.signUp("Кама",
                phoneNumber,
                "20.20.2000",
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION);
    }

    @Test
    public void signUp_Null_BirthDateString_NoSignUp() {
        String birthDate = null;
        presenter.signUp("Кама",
                "+79876543210",
                birthDate,
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signUp_Invalid_BirthDateString_NoSignUp() {
        String birthDate = "20 марта 2020";
        presenter.signUp("Кама",
                "+79876543210",
                birthDate,
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorAuthorization.BIRTHDATE_INVALID_VALIDATION);
    }

    @Test
    public void signUp_Normal_BirthDateString_SignUp() {
        String birthDate = "20.20.2000";
        presenter.signUp("Кама",
                "+79876543210",
                birthDate,
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onSuccess();
    }

    @Test
    public void signUp_Empty_BirthDateString_SignUp() {
        String birthDate = "";
        presenter.signUp("Кама",
                "+79876543210",
                birthDate,
                "mail@mail.ru",
                "Улица, дом, подъезд, этаж, квартира");
        verify(view, never()).onError(Contracts.ErrorConstant.ERROR_EMPTY);
    }

    @Test
    public void signUp_Null_EmailString_NoSignUp() {
        String email = null;
        presenter.signUp("Кама",
                "+79876543210",
                "20.20.2000",
                email,
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signUp_Empty_EmailString_NoSignUp() {
        String email = "";
        presenter.signUp("Кама",
                "",
                "20.20.2000",
                email,
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorAuthorization.EMAIL_EMPTY_ERROR);
    }

    @Test
    public void signUp_Invalid_EmailString_NoSignUp() {
        String email = "mailRu";
        presenter.signUp("Кама",
                "+79876543210",
                "20.20.2000",
                email,
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onError(Contracts.ErrorAuthorization.EMAIL_INVALID_VALIDATION);
    }

    @Test
    public void signUp_Normal_EmailString_SignUp() {
        String email = "mail@mail.ru";
        presenter.signUp("Кама",
                "+79876543210",
                "20.20.2000",
                email,
                "Улица, дом, подъезд, этаж, квартира");
        verify(view).onSuccess();
    }

    @Test
    public void signUp_Null_LocationString_SignUp() {
        String location = null;
        presenter.signUp("Кама",
                "+79876543210",
                "20.20.2000",
                "mail@mail.ru",
                location);
        verify(view).onError(Contracts.ErrorConstant.ERROR_NULL);
    }

    @Test
    public void signUp_Empty_LocationString_SignUp() {
        String location = "";
        presenter.signUp("Кама",
                "+79876543210",
                "20.20.2000",
                "mail@mail.ru",
                location);
        verify(view).onError(Contracts.ErrorAuthorization.LOCATION_EMPTY_ERROR);
    }

    @Test
    public void signUp_Normal_LocationString_SignUp() {
        String location = "Улица, дом, подъезд, этаж, квартира";
        presenter.signUp("Кама",
                "+79876543210",
                "20.20.2000",
                "mail@mail.ru",
                location);
        verify(view).onSuccess();
    }

    @Test(expected = NullPointerException.class)
    public void setRouter_Null_Router() {
        Router router = null;
        presenter.setRouter(router);
    }

    @Test
    public void setRouter_Normal_Router() {
        presenter.onBackPressed();
        verify(mRouter).exit();
    }
}