package com.timsterj.ronin.presenters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.timsterj.ronin.R;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.contracts.mvp.HomeContract;
import com.timsterj.ronin.navigation.Screens;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class HomePresenter extends MvpPresenter<HomeContract.View> implements HomeContract.Presenter {

    private CompositeDisposable disposableBag = new CompositeDisposable();

    public void init() {

    }

    public void clear() {
        disposableBag.clear();
    }

    @Override
    public void selectTab(String tab, int nextPosition, FragmentManager fm) {
        Fragment currentFragment = null;

        if (fm == null || tab == null) {
            getViewState().onError(Contracts.ErrorConstant.ERROR_NULL);
            return;
        }

        if (tab.isEmpty()) {
            getViewState().onError(Contracts.ErrorConstant.ERROR_EMPTY);
            return;
        }

        List<Fragment> fragments = fm.getFragments();
        for (Fragment f : fragments) {
            if (f.isVisible()) {
                currentFragment = f;
                break;
            }
        }

        Fragment newFragment = fm.findFragmentByTag(tab);
        FragmentTransaction transaction = fm.beginTransaction();

        if (currentFragment != null && newFragment != null && currentFragment == newFragment)
            return;

        if (newFragment == null) {
            transaction.add(R.id.main_fg_container, new Screens.BottomNavigationFlow.TabScreen(tab).getFragment(), tab);
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }

        if (newFragment != null) {
            transaction.show(newFragment);
        }

        transaction.commitNow();

    }


}
