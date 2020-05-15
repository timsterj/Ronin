package com.timsterj.ronin.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.timsterj.ronin.fragments.AuthorizationFragment;
import com.timsterj.ronin.fragments.BasketFragment;
import com.timsterj.ronin.fragments.FavoriteFragment;
import com.timsterj.ronin.fragments.HomeFragment;
import com.timsterj.ronin.fragments.OrderFragment;
import com.timsterj.ronin.fragments.OrderHistoryFragment;
import com.timsterj.ronin.fragments.OrderInfoFragment;
import com.timsterj.ronin.fragments.ProductInfoFragment;
import com.timsterj.ronin.fragments.SearchFragment;
import com.timsterj.ronin.fragments.SignUpFragment;
import com.timsterj.ronin.fragments.UserInfoFragment;

import ru.terrakok.cicerone.android.support.SupportAppScreen;

public class Screens {

    public static final class BottomNavigationFlow {

        public static final class TabScreen extends SupportAppScreen {
            private final String tabName;

            public TabScreen(String tabName) {
                this.tabName = tabName;
            }

            @Override
            public Fragment getFragment() {
                return TabContainerFragment.getNewInstance(tabName);
            }
        }

        // Экран "Главное"
        public static final class HomeScreen extends SupportAppScreen {
            private final String containerName;

            public HomeScreen(String containerName) {
                this.containerName = containerName;
            }

            @Override
            public Fragment getFragment() {
                return HomeFragment.getNewInstance(containerName);
            }
        }

        // Экран "Корзина"
        public static final class BasketScreen extends SupportAppScreen {
            private final String containerName;

            public BasketScreen(String containerName) {
                this.containerName = containerName;
            }

            @Override
            public Fragment getFragment() {
                return BasketFragment.getNewInstance(containerName);
            }
        }

        // Экран "Любимое"
        public static final class FavoriteScreen extends SupportAppScreen {
            private final String containerName;

            public FavoriteScreen(String containerName) {
                this.containerName = containerName;
            }

            @Override
            public Fragment getFragment() {
                return FavoriteFragment.getNewInstance(containerName);
            }
        }

        // Экран "Поиск"
        public static final class SearchScreen extends SupportAppScreen {
            private final String containerName;

            public SearchScreen(String containerName) {
                this.containerName = containerName;
            }

            @Override
            public Fragment getFragment() {
                return SearchFragment.getNewInstance(containerName);
            }
        }
    }

    public static final class AuthorizationFlow {

        public static final class AuthorizationScreen extends SupportAppScreen {

            private String fragmentName;

            public AuthorizationScreen(String fragmentName) {
                this.fragmentName = fragmentName;
            }

            @Override
            public Fragment getFragment() {
                return AuthorizationFragment.getNewInstance(fragmentName);
            }
        }

        public static final class SignUpScreen extends SupportAppScreen {

            private String fragmentName;
            private String phoneNumber;

            public SignUpScreen(String fragmentName, String phoneNumber) {
                this.fragmentName = fragmentName;
                this.phoneNumber = phoneNumber;
            }

            @Override
            public Fragment getFragment() {
                return SignUpFragment.getNewInstance(fragmentName, phoneNumber);
            }
        }


    }

    public static final class ProductInfoScreen extends SupportAppScreen {

        private final String name;
        private final Bundle arguments;

        public ProductInfoScreen(String name, Bundle arguments) {
            this.name = name;
            this.arguments = arguments;
        }

        @Override
        public Fragment getFragment() {
            return ProductInfoFragment.getNewInstance(name, arguments);
        }
    }


    public static final class OrderScreen extends SupportAppScreen {

        private final String name;

        public OrderScreen(String name) {
            this.name = name;
        }

        @Override
        public Fragment getFragment() {
            return OrderFragment.getNewInstance(name);
        }
    }

    public static final class OrderInfoScreen extends SupportAppScreen {

        private final String name;
        private final int index;

        public OrderInfoScreen(String name, int index) {
            this.name = name;
            this.index = index;
        }

        @Override
        public Fragment getFragment() {
            return OrderInfoFragment.getNewInstance(name, index);
        }
    }

    public static final class OrderHistoryScreen extends SupportAppScreen {

        private final String name;

        public OrderHistoryScreen(String name) {
            this.name = name;
        }

        @Override
        public Fragment getFragment() {
            return OrderHistoryFragment.getNewInstance(name);
        }
    }

    public static final class UserInfoScreen extends SupportAppScreen {

        private final String name;

        public UserInfoScreen(String name) {
            this.name = name;
        }

        @Override
        public Fragment getFragment() {
            return UserInfoFragment.getNewInstance(name);
        }
    }

}
