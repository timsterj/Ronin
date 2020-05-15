package com.timsterj.ronin.di.modules;

import com.timsterj.ronin.di.scopes.HomeActivityScope;
import com.timsterj.ronin.navigation.LocalCiceroneHolder;

import dagger.Module;
import dagger.Provides;


@Module
public class LocalNavigationModule {


    @Provides
    @HomeActivityScope
    LocalCiceroneHolder provideLocalCiceroneHolder() {
        return new LocalCiceroneHolder();
    }


}
