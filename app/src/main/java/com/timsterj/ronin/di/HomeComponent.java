package com.timsterj.ronin.di;

import com.timsterj.ronin.di.modules.AdapterModule;
import com.timsterj.ronin.di.modules.LocalNavigationModule;
import com.timsterj.ronin.di.scopes.HomeActivityScope;
import com.timsterj.ronin.fragments.BasketFragment;
import com.timsterj.ronin.fragments.FavoriteFragment;
import com.timsterj.ronin.fragments.HomeFragment;
import com.timsterj.ronin.fragments.OrderFragment;
import com.timsterj.ronin.fragments.OrderHistoryFragment;
import com.timsterj.ronin.fragments.OrderInfoFragment;
import com.timsterj.ronin.fragments.ProductInfoFragment;
import com.timsterj.ronin.fragments.SearchFragment;
import com.timsterj.ronin.fragments.TutorialFragment;
import com.timsterj.ronin.fragments.UserInfoFragment;
import com.timsterj.ronin.navigation.TabContainerFragment;
import com.timsterj.ronin.presenters.HomeFragmentPresenter;
import com.timsterj.ronin.presenters.OrderHistoryPresenter;
import com.timsterj.ronin.presenters.OrderInfoPresenter;
import com.timsterj.ronin.presenters.OrderPresenter;
import com.timsterj.ronin.presenters.ProductInfoPresenter;
import com.timsterj.ronin.presenters.SearchPresenter;

import dagger.Subcomponent;

@HomeActivityScope
@Subcomponent(modules = {
        LocalNavigationModule.class,
        AdapterModule.class

})
public interface HomeComponent {

    //Presenter
    void inject(HomeFragmentPresenter homeFragmentPresenter);
    void inject(SearchPresenter searchPresenter);
    void inject(ProductInfoPresenter productInfoPresenter);
    void inject(OrderPresenter orderPresenter);
    void inject(OrderInfoPresenter orderInfoPresenter);
    void inject(OrderHistoryPresenter orderHistoryPresenter);

    //Fragments
    void inject(TabContainerFragment tabContainerFragment);
    void inject(HomeFragment homeFragment);
    void inject(SearchFragment searchFragment);
    void inject(BasketFragment basketFragment);
    void inject(FavoriteFragment favoriteFragment);
    void inject(ProductInfoFragment productInfoFragment);
    void inject(OrderFragment orderFragment);
    void inject(OrderInfoFragment orderInfoFragment);
    void inject(OrderHistoryFragment orderHistoryFragment);
    void inject(UserInfoFragment userInfoFragment);
    void inject(TutorialFragment tutorialFragment);

}
