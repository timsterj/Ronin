package com.timsterj.ronin.contracts;

public class Contracts {
    public static final String APP_PREFERENCES = "ronin_settings";
    public static final String ORDER_STATUS_CHANNEL_ID = "Last_Order_Status_Channel";

    public static final String TAG = "Ronin_";

    public static final int NORMAL = 228;
    public static final String NORMAL_MESSAGE = "Успешно";

    public static final class ProductsConstant {

        public static final String ROLLS_CATEGORY_NAME = "Роллы";
        public static final String HOT_ROLLS_CATEGORY_NAME = "Горячие роллы";
        public static final String BAKED_ROLLS_CATEGORY_NAME = "Запечённые роллы";
        public static final String CLASSIC_ROLLS_CATEGORY_NAME = "Классические роллы";
        public static final String SUSHI_CATEGORY_NAME = "Суши";
        public static final String SETS_CATEGORY_NAME = "Сеты";
        public static final String SNACKS_CATEGORY_NAME = "Закуски";
        public static final String SALADS_CATEGORY_NAME = "Салаты";
        public static final String WOK_CATEGORY_NAME = "ВОК";
        public static final String DRINKS_CATEGORY_NAME = "Напитки";
        public static final String ADDITIONAL_NAME = "Дополнительно";

        public static final String ROLLS_CATEGORY = "01";
        public static final String HOT_ROLLS_CATEGORY = "02";
        public static final String BAKED_ROLLS_CATEGORY = "03";
        public static final String CLASSIC_ROLLS_CATEGORY = "04";
        public static final String SUSHI_CATEGORY = "05";
        public static final String SETS_CATEGORY = "06";
        public static final String SNACKS_CATEGORY = "07";
        public static final String SALADS_CATEGORY = "08";
        public static final String WOK_CATEGORY = "09";
        public static final String DRINKS_CATEGORY = "11";
        public static final String ADDITIONAL_CATEGORY = "12";

    }

    public static final class PreferencesConstant {
        public static final String FIRST_RUN = "first-run";

        public static final String REGISTRATION_TYPE = "reg-type";
    }

    public static final class NavigationConstant {

        public static final String TAB_HOME = "tab_home";
        public static final String TAB_BASKET = "tab_basket";
        public static final String TAB_FAVORITE = "tab_favorite";
        public static final String TAB_SEARCH = "tab_search";

        public static final String SIGN_IN_AUTHORIZATION = "sign_in";
        public static final String SIGN_UP_AUTHORIZATION = "sign_up";
        public static final String SIGN_IN_GUEST_AUTHORIZATION = "sign_in_guest";
        public static final String AUTHORIZATION = "authorization";

        public static final String PRODUCT_FAVORITE = "product_favorite";
        public static final String ORDER_FAVORITE = "order_favorite";

        public static final String PRODUCT_INFO = "product_info";

        public static final String ORDER = "order_it";
        public static final String ORDER_INFO = "order_done_info";
        public static final String ORDER_HISTORY = "order_done_info";

        public static final String USER_INFO = "user_info";

    }

    public static final class ErrorAuthorization {
        public static final String LOGIN_EMPTY_ERROR_MESSAGE = "Пустое поле";
        public static final int LOGIN_INVALID_VALIDATION = 2;

        public static final int PASSWORD_EMPTY_ERROR = 3;
        public static final String PASSWORD_EMPTY_ERROR_MESSAGE = "Пустое поле";
        public static final int PASSWORD_EASY_ERROR = 4;
        public static final String PASSWORD_EASY_ERROR_MESSAGE = "Слабый пароль";

        //SignUp
        public static final int NAME_EMPTY_ERROR = 5;
        public static final String NAME_EMPTY_ERROR_MESSAGE = "Пустое поле";

        public static final int PHONE_EMPTY_ERROR = 6;
        public static final String PHONE_EMPTY_ERROR_MESSAGE = "Пустое поле";
        public static final int PHONE_INVALID_VALIDATION = 7;
        public static final String PHONE_INVALID_ERROR_MESSAGE = "Пример: +79876543210";

        public static final int BIRTHDATE_INVALID_VALIDATION = 8;
        public static final String BIRTHDATE_INVALID_ERROR_MESSAGE = "Пример: 20.20.2000";

        public static final int EMAIL_EMPTY_ERROR = 9;
        public static final String EMAIL_EMPTY_ERROR_MESSAGE = "Пустое поле";
        public static final int EMAIL_INVALID_VALIDATION = 10;
        public static final String EMAIL_INVALID_ERROR_MESSAGE = "Некорректный email";

        public static final int LOCATION_EMPTY_ERROR = 11;
        public static final String LOCATION_EMPTY_ERROR_MESSAGE = "Пустое поле";

        public static final int LOCATION_STREET_EMPTY_ERROR = 12;
        public static final String LOCATION_STREET_EMPTY_ERROR_MESSAGE = "Пустое поле";

        public static final int LOCATION_HOME_EMPTY_ERROR = 13;
        public static final String LOCATION_HOME_EMPTY_ERROR_MESSAGE = "Пустое поле";

        public static final int LOCATION_POD_EMPTY_ERROR = 14;
        public static final String LOCATION_POD_EMPTY_ERROR_MESSAGE = "Пустое поле";

        public static final int LOCATION_ET_EMPTY_ERROR = 15;
        public static final String LOCATION_ET_EMPTY_ERROR_MESSAGE = "Пустое поле";

        public static final int LOCATION_APART_EMPTY_ERROR = 16;
        public static final String LOCATION_APART_EMPTY_ERROR_MESSAGE = "Пустое поле";

    }

    public static final class ErrorConstant {

        public static final int ERROR_NULL = 0;
        public static final String ERROR_NULL_MESSAGE = "ERROR: NULL";
        public static final int ERROR_EMPTY = 1;
    }

    public static final class RetrofitConstant {

        public static final String FRONT_PAD_SECRET = "kEYF3fNAHsTt4K7DknTSBTBDdEs6hKkyb844eDrQY88KH6bYfYBGD39GtsBD4Q25KQTRe8t37rBEhEnskQ2RE2k2dQr6rbZ4RFFZE2KAda5nEfFSYtzneHahkrbEt3t9t2YZNyiSyd63iBF6T5h2Dh7QB9eT2BTekD53ZbGfeB8r3DiGytyZErA6rFQb6yhfZa8HbESf5QATeHrB5Kkz4Kree5h9nsfasdHyGrEB64h9BBBsaNQ4k68sZf";
        public static final String YANDEX_MAP_API_KEY = "623fc85b-ca27-41f2-a2c9-9725ca778dc8";

        public static final String DEMO_BASE_URL = "https://164a8a85-5e71-4d48-b300-a3de0d356c4c.mock.pstmn.io/";
        public static final String FRONT_PAD_BASE_URL = "https://app.frontpad.ru/api/";
    }

    public static final class AuthorizationConstant {

        public static final String REGISTRATION_TYPE_USER = "reg-type_user";
        public static final String REGISTRATION_TYPE_NONE = "reg-type_none";
    }

    public static final class AdapterConstant {

        public static final int ABOUT_US_VIEW_HOLDER = 0;

        public static final int PRODUCT_VIEW_TYPE = 1;
        public static final int TITLE_VIEW_TYPE = 2;

        public static final int ABOUT_US_VIEW_TYPE = 3;
        public static final int POSTER_VIEW_TYPE = 4;

        public static final int ORDER_VIEW_TYPE = 5;

    }

    public static final class ContactsConstant {

        public static final String SUPPORT_PHONENUMBER = "8452700-711";
        public static final String SUPPORT_EMAIL = "azamattemirgalievj@mail.ru";
        public static final String VK_GROUP_LINK = "https://vk.com/4ch";
        public static final String INSTA_GROUP_LINK = "https://instagram.com/a.shatr";

    }
}
