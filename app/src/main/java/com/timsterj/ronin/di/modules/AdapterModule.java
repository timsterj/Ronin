package com.timsterj.ronin.di.modules;

import com.timsterj.ronin.adapters.AboutUsAdapter;
import com.timsterj.ronin.adapters.AdditionalFoodAdapter;
import com.timsterj.ronin.adapters.OrderHistoryAdapter;
import com.timsterj.ronin.adapters.OrderListAdapter;
import com.timsterj.ronin.adapters.ProductAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {

    @Provides
    AboutUsAdapter provideAboutUsAdapter() {
        return new AboutUsAdapter();
    }


    @Provides
    ProductAdapter provideProductAdapter() {
        return new ProductAdapter();
    }

    @Provides
    OrderListAdapter provideOrderListAdapter() {
        return new OrderListAdapter();
    }

    @Provides
    AdditionalFoodAdapter provideAdditionalFoodAdapter() {
        return new AdditionalFoodAdapter();
    }

    @Provides
    OrderHistoryAdapter provideOrderHistoryAdapter() {
        return new OrderHistoryAdapter();
    }
}
