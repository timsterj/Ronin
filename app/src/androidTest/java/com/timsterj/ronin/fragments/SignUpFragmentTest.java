package com.timsterj.ronin.fragments;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.timsterj.ronin.R;
import com.timsterj.ronin.activities.AuthorizationActivity;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.rules.FragmentTestRule;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SignUpFragmentTest {

    @Rule
    public FragmentTestRule fragmentTestRule = new FragmentTestRule<>(AuthorizationActivity.class, new SignUpFragment());


    @Test
    public void launchFragment_IsDisplayed() {
        onView(ViewMatchers.withId(R.id.ftb_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void signUp_Empty_NameError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_name))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_name))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void signUp_Normal_Name_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_name))
                .perform(ViewActions.replaceText("КамаПуля"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_name))
                .check(ViewAssertions
                        .matches(not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR_MESSAGE))));
    }

    @Test
    public void signUp_Empty_PhoneNumberError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR_MESSAGE)));
    }


    @Test
    public void signUp_Normal_PhoneNumberError_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.typeText("+79876543210"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .check(ViewAssertions
                        .matches(Matchers.not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE))));

    }

    @Test
    public void signUp_Invalid_PhoneNumberError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.typeText("9876543210"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE)));
    }


    @Test
    public void signUp_Empty_BirthDateError_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .check(ViewAssertions
                        .matches(Matchers.not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE))));
    }


    @Test
    public void signUp_Invalid_BirthDateError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .perform(ViewActions.replaceText("20 марта 2020"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.BIRTHDATE_INVALID_ERROR_MESSAGE)));
    }

    @Test
    public void signUp_Normal_BirthDateError_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .perform(ViewActions.typeText("20.20.2020"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .check(ViewAssertions
                        .matches(Matchers.not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.BIRTHDATE_INVALID_ERROR_MESSAGE))));
    }

    @Test
    public void signUp_Empty_EmailError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_email))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_email))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.EMAIL_EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void signUp_Invalid_EmailError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_email))
                .perform(ViewActions.typeText("mailRu"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_email))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.EMAIL_INVALID_ERROR_MESSAGE)));
    }

    @Test
    public void signUp_Normal_EmailError_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_email))
                .perform(ViewActions.typeText("mail@mail.ru"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_email))
                .check(ViewAssertions
                        .matches(Matchers.not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.EMAIL_INVALID_ERROR_MESSAGE))));
    }

    @Test
    public void signUp_Empty_LocationError_NoSignUp() {
        onView(ViewMatchers.withId(R.id.edt_location))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_location))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.LOCATION_EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void signUp_Normal_LocationError_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_location))
                .perform(ViewActions.replaceText("Улица, дом, подъезд, этаж, квартира"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_location))
                .check(ViewAssertions
                        .matches(not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.LOCATION_EMPTY_ERROR_MESSAGE))));
    }


    @Test
    public void signUp_Normal_All_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_name))
                .perform(ViewActions.replaceText("КамаПуля"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.replaceText("+79876543210"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.edt_birthdate))
                .perform(ViewActions.replaceText("20.20.2000"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.edt_email))
                .perform(ViewActions.replaceText("mail@mail.ru"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.edt_location))
                .perform(ViewActions.replaceText("Улица, дом, подъезд, этаж, квартира"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.scroll_view))
                .perform(ViewActions.swipeUp());

        onView(ViewMatchers.withId(R.id.btn_signUp))
                .perform(ViewActions.click());

        onView(ViewMatchers.withText(Contracts.NORMAL_MESSAGE))
                .inRoot(withDecorView(not(fragmentTestRule.getActivity().getWindow().getDecorView())))
                .check(ViewAssertions.matches(ViewMatchers.withText(Contracts.NORMAL_MESSAGE)));
    }

}