package com.timsterj.ronin.fragments;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.timsterj.ronin.R;
import com.timsterj.ronin.activities.AuthorizationActivity;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.rules.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4ClassRunner.class)
public class SignInGuestFragmentTest {

    @Rule
    public FragmentTestRule fragmentTestRule = new FragmentTestRule<>(AuthorizationActivity.class, new SignInGuestFragment());

    @Test
    public void launchFragment_IsDisplayed() {
        onView(ViewMatchers.withId(R.id.ftb_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void signInGuest_Empty_NameError_NoSignIn() {
        onView(ViewMatchers.withId(R.id.edt_name))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.btn_signInGuest))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_name))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void signInGuest_Normal_NameString_SignIn() {
        onView(ViewMatchers.withId(R.id.edt_name))
                .perform(ViewActions.replaceText("КамаПуля"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.btn_signInGuest))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_name))
                .check(ViewAssertions
                        .matches(not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.NAME_EMPTY_ERROR_MESSAGE))));
    }

    @Test
    public void signInGuest_Empty_PhoneNumberString_NoSignIn() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.typeText(""))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.btn_signInGuest))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR_MESSAGE)));
    }


    @Test
    public void signInGuest_Normal_PhoneNumberError_SignIn() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.typeText("+79876543210"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.btn_signInGuest))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .check(ViewAssertions
                        .matches(not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE))));

    }

    @Test
    public void signInGuest_Invalid_PhoneNumberError_NoSignIn() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.replaceText("9876543210"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.btn_signInGuest))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .check(ViewAssertions
                        .matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE)));
    }

    @Test
    public void signInGuest_Normal_All_SignUp() {
        onView(ViewMatchers.withId(R.id.edt_phone_number))
                .perform(ViewActions.replaceText("+79876543210"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.edt_name))
                .perform(ViewActions.replaceText("КамаПуля"))
                .perform(ViewActions.closeSoftKeyboard());

        onView(ViewMatchers.withId(R.id.btn_signInGuest))
                .perform(ViewActions.click());

        onView(ViewMatchers.withText(Contracts.NORMAL_MESSAGE))
                .inRoot(withDecorView(not(fragmentTestRule.getActivity().getWindow().getDecorView())))
                .check(ViewAssertions.matches(ViewMatchers.withText(Contracts.NORMAL_MESSAGE)));
    }
}