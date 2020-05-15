package com.timsterj.ronin.utils;

import com.timsterj.ronin.contracts.Contracts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static int loginValidation(String login) {
        return phoneNumberValidation(login);
    }

    public static int passwordValidation(String password) {

        if (password == null) {
            return Contracts.ErrorConstant.ERROR_NULL;
        }
        if (password.isEmpty()) {
            return Contracts.ErrorAuthorization.PASSWORD_EMPTY_ERROR;
        }

        if (password.length() <= 3) {
            return Contracts.ErrorAuthorization.PASSWORD_EASY_ERROR;
        }

        return Contracts.NORMAL;
    }

    public static int phoneNumberValidation(String phoneNumber){
        if (phoneNumber == null) {
            return Contracts.ErrorConstant.ERROR_NULL;
        }
        if (phoneNumber.isEmpty()) {
            return Contracts.ErrorAuthorization.PHONE_EMPTY_ERROR;
        }
        String phonePatten = "[+]7([0-9]{3})[0-9]{3}[0-9]{2}[0-9]{2}";

        Pattern pattern = Pattern.compile(phonePatten);
        Matcher matcher = pattern.matcher(phoneNumber);

        if (!matcher.matches()) {
            return Contracts.ErrorAuthorization.PHONE_INVALID_VALIDATION;
        }

        return Contracts.NORMAL;
    }

    public static int birthdateValidation(String birthdate){
        if (birthdate == null) {
            return Contracts.ErrorConstant.ERROR_NULL;
        }

        if (birthdate.isEmpty()) {
            return Contracts.NORMAL;
        }

        String birthdatePattern = "[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}";

        Pattern pattern = Pattern.compile(birthdatePattern);
        Matcher matcher = pattern.matcher(birthdate);

        if (!matcher.matches()) {
            return Contracts.ErrorAuthorization.BIRTHDATE_INVALID_VALIDATION;
        }

        return Contracts.NORMAL;
    }

    public static int emailValidation(String email){
        if (email == null) {
            return Contracts.ErrorConstant.ERROR_NULL;
        }

        if (email.isEmpty()) {
            return Contracts.ErrorAuthorization.EMAIL_EMPTY_ERROR;
        }

        String emailPattern = "^(.+)@(.+)$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            return Contracts.ErrorAuthorization.EMAIL_INVALID_VALIDATION;
        }

        return Contracts.NORMAL;
    }

    public static int nameValidation(String name){
        if (name == null) {
            return Contracts.ErrorConstant.ERROR_NULL;
        }

        if (name.isEmpty()) {
            return Contracts.ErrorAuthorization.NAME_EMPTY_ERROR;
        }

        return Contracts.NORMAL;
    }

    public static int locationValidation(String locaiton){
        if (locaiton == null) {
            return Contracts.ErrorConstant.ERROR_NULL;
        }

        if (locaiton.isEmpty()) {
            return Contracts.ErrorAuthorization.LOCATION_EMPTY_ERROR;
        }

        return Contracts.NORMAL;
    }

}
