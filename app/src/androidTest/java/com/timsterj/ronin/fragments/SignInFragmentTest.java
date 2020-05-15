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
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4ClassRunner.class)
public class SignInFragmentTest {

    @Rule
    public FragmentTestRule fragmentTestRule =
            new FragmentTestRule<>(AuthorizationActivity.class,
                    new SignInFragment());


    @Test
    public void launchFragment_IsDisplayed(){
        onView(ViewMatchers.withId(R.id.ftb_container))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void login_LoginEmptyError_NoSignIn(){

        onView(ViewMatchers.withId(R.id.edt_login))
                .perform(ViewActions.typeText(""));

        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_login))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void login_LoginInvalidError_NoSignIn(){

        onView(ViewMatchers.withId(R.id.edt_login))
                .perform(ViewActions.typeText("123456"));

        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_login))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PHONE_INVALID_ERROR_MESSAGE)));
    }

    @Test
    public void login_LoginNormal_SignIn(){
        onView(ViewMatchers.withId(R.id.edt_login))
                .perform(ViewActions.typeText("+79876543210"));


        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_login))
                .check(ViewAssertions.matches(not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.LOGIN_EMPTY_ERROR_MESSAGE))));
    }


    @Test
    public void password_PasswordEmpty_NoSignIn(){
        onView(ViewMatchers.withId(R.id.edt_password))
                .perform(ViewActions.typeText(""));

        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_password))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PASSWORD_EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void password_PasswordInvalid_NoSignIn(){
        onView(ViewMatchers.withId(R.id.edt_password))
                .perform(ViewActions.typeText("123"));

        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_password))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PASSWORD_EASY_ERROR_MESSAGE)));
    }

    @Test
    public void password_PasswordNormal_SignIn(){
        onView(ViewMatchers.withId(R.id.edt_password))
                .perform(ViewActions.typeText("12345678"));

        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.click());

        onView(ViewMatchers.withId(R.id.edt_password))
                .check(ViewAssertions.matches(not(ViewMatchers.hasErrorText(Contracts.ErrorAuthorization.PASSWORD_EASY_ERROR_MESSAGE))));
    }

    @Test
    public void signIn_NormalLogin_NormalPassword_SignIn(){
        onView(ViewMatchers.withId(R.id.edt_login))
                .perform(ViewActions.typeText("+79876543210"));

        onView(ViewMatchers.withId(R.id.edt_password))
                .perform(ViewActions.typeText("12345678"));

        onView(ViewMatchers.withId(R.id.btn_signIn))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.click());

        onView(ViewMatchers.withText(Contracts.NORMAL_MESSAGE))
                .inRoot(withDecorView(not(fragmentTestRule.getActivity().getWindow().getDecorView())))
                .check(ViewAssertions.matches(ViewMatchers.withText(Contracts.NORMAL_MESSAGE)));
    }
}
