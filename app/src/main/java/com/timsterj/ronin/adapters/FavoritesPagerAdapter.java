package com.timsterj.ronin.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.fragments.OrderFavoriteFragment;
import com.timsterj.ronin.fragments.ProductFavoriteFragment;

public class FavoritesPagerAdapter extends FragmentPagerAdapter {


    public FavoritesPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ProductFavoriteFragment.getNewInstance(Contracts.NavigationConstant.PRODUCT_FAVORITE);

            case 1:
                return OrderFavoriteFragment.getNewInstance(Contracts.NavigationConstant.ORDER_FAVORITE);

            default: throw new IllegalArgumentException();
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Продукты";

            case 1:
                return "Заказы";
        }

        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }


}
