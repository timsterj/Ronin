package com.timsterj.ronin.di;

import com.timsterj.ronin.activities.LaunchActivity;
import com.timsterj.ronin.di.scopes.LaunchActivityScope;
import com.timsterj.ronin.domain.repositories.RepositoryFrontPad;
import com.timsterj.ronin.presenters.LaunchPresenter;

import dagger.Subcomponent;

@LaunchActivityScope
@Subcomponent
public interface LaunchComponent {
    // MARK - Как организуем UI

    //Activities
    void inject(LaunchActivity launchActivity);

    //Presenters
    void inject(LaunchPresenter launchPresenter);

    //Repositories
    void inject(RepositoryFrontPad repositoryFrontPad); // MARK - Внимательнее
}
