package com.timsterj.ronin.utils;

import com.timsterj.ronin.contracts.Contracts;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;


public class ValidationUtilTest {


    @Test
    public void loginValidation_Null_LoginString_NoValidate(){
        String login = null;

        int expected = Contracts.ErrorConstant.ERROR_NULL;

        int actual = ValidationUtil.loginValidation(login);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void loginValidation_Empty_LoginString_NoValidate(){
        String login = "";

        int expected = Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR;

        int actual = ValidationUtil.loginValidation(login);

        assertThat(actual).isEqualTo(expected);    }


    @Test
    public void loginValidation_Invalid_LoginString_NoValidate(){
        String login = "9876543210";

        int expected = Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION;

        int actual = ValidationUtil.loginValidation(login);

        assertThat(actual).isEqualTo(expected);    }

    @Test
    public void loginValidation_Normal_LoginString_Validated(){
        String login = "+79876543210";

        int expected = Contracts.NORMAL;

        int actual = ValidationUtil.loginValidation(login);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void passwordValidation_Null_PasswordString_NoValidate(){
        String password = null;

        int expected = Contracts.ErrorConstant.ERROR_NULL;

        int actual = ValidationUtil.passwordValidation(password);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void passwordValidation_Empty_PasswordString_NoValidate(){
        String password = "";

        int expected = Contracts.ErrorAuthorization.PASSWORD_EMPTY_ERROR;

        int actual = ValidationUtil.passwordValidation(password);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void passwordValidation_Easy_PasswordString_NoValidate(){
        String password = "123";

        int expected = Contracts.ErrorAuthorization.PASSWORD_EASY_ERROR;

        int actual = ValidationUtil.passwordValidation(password);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void passwordValidation_Normal_PasswordString_Validated(){
        String password = "123456";

        int expected = Contracts.NORMAL;

        int actual = ValidationUtil.passwordValidation(password);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void phoneNumberValidation_Null_PhoneNumberString_NoValidate(){
        String phoneNumber = null;

        int expected = Contracts.ErrorConstant.ERROR_NULL;

        int actual = ValidationUtil.phoneNumberValidation(phoneNumber);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void phoneNumberValidation_Empty_PhoneNumberString_NoValidate(){
        String phoneNumber = "";

        int expected = Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR;

        int actual = ValidationUtil.phoneNumberValidation(phoneNumber);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void phoneNumberValidation_Normal_PhoneNumberString_Validated(){
        String phoneNumber = "+79876543210";

        int expected = Contracts.NORMAL;

        int actual = ValidationUtil.phoneNumberValidation(phoneNumber);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void birthdateValidation_Null_BirthDateString_NoValidate(){
        String birthdate = null;

        int expected = Contracts.ErrorConstant.ERROR_NULL;

        int actual = ValidationUtil.birthdateValidation(birthdate);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void birthdateValidation_Empty_BirthDateString_NoValidate(){
        String birthdate = "";

        int expected = Contracts.NORMAL;

        int actual = ValidationUtil.birthdateValidation(birthdate);

        assertThat(actual).isEqualTo(expected);
    }


    @Test
    public void birthdateValidation_Normal_BirthDateString_Validated(){
        String birthdate = "20.20.2000";

        int expected = Contracts.NORMAL;

        int actual = ValidationUtil.birthdateValidation(birthdate);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void emailValidation_Null_EmailString_NoValidate() {
        String email = null;

        int expected = Contracts.ErrorConstant.ERROR_NULL;
        int actual = ValidationUtil.emailValidation(email);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void emailValidation_Empty_EmailString_NoValidate() {
        String email = "";

        int expected = Contracts.ErrorAuthorization.EMAIL_EMPTY_ERROR;
        int actual = ValidationUtil.emailValidation(email);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void emailValidation_Invalid_EmailString_NoValidate() {
        String email = "mailRu";

        int expected = Contracts.ErrorAuthorization.EMAIL_INVALID_VALIDATION;
        int actual = ValidationUtil.emailValidation(email);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void emailValidation_Normal_EmailString_NoValidate() {
        String email = "mail@mail.ru";

        int expected = Contracts.NORMAL;
        int actual = ValidationUtil.emailValidation(email);

        assertThat(actual).isEqualTo(expected);
    }

}