package com.timsterj.ronin.di.modules;

import com.timsterj.ronin.domain.interfaces.IRepositoryFrontPad;
import com.timsterj.ronin.domain.repositories.RepositoryFrontPad;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    IRepositoryFrontPad provideRepositoryFrontPad(){
        return new RepositoryFrontPad();
    }

}
