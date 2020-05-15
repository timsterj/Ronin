package com.timsterj.ronin.helpers;

import com.timsterj.ronin.App;
import com.timsterj.ronin.di.HomeComponent;
import com.timsterj.ronin.di.LaunchComponent;
import com.timsterj.ronin.di.modules.LocalNavigationModule;

public class InjectHelper {

    private static LaunchComponent launchComponent;
    private static HomeComponent homeComponent;

    public static LaunchComponent plusLaunchComponent() {
        if (launchComponent == null) {
            launchComponent = App.getINSTANCE().appComponent.plusLaunchComponent();
        }
        return launchComponent;
    }

    public static void clearLaunchComponent() {
        launchComponent = null;
    }

    public static HomeComponent plusHomeComponent() {
        if (homeComponent == null) {
            homeComponent = App.getINSTANCE().appComponent.plusHomeComponent(new LocalNavigationModule());
        }
        return homeComponent;
    }

    public static void clearHomeComponent() {
        homeComponent = null;
    }
}
