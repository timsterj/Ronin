package com.timsterj.ronin.di.modules;

import com.timsterj.data.common.RetrofitClient;
import com.timsterj.ronin.contracts.Contracts;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RetrofitModule {

    @Provides
    Retrofit provideRetrofitFrontPad() {
        return RetrofitClient.getClient(Contracts.RetrofitConstant.FRONT_PAD_BASE_URL);
    }


}
