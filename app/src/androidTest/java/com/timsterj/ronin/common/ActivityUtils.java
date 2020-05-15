package com.timsterj.ronin.common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.timsterj.ronin.R;

public class ActivityUtils {


    private ActivityUtils(){}

    public static void openFragment(AppCompatActivity activity, Fragment newFragment) {

        activity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ftb_container, newFragment)
                .commitAllowingStateLoss();

    }

}
