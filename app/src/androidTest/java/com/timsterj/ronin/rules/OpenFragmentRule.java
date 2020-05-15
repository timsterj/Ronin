package com.timsterj.ronin.rules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import static com.timsterj.ronin.common.ActivityUtils.openFragment;

public class OpenFragmentRule<A extends AppCompatActivity> implements TestRule {

    private final ActivityTestRule<A> activityTestRule;
    private final Fragment fragment;

    public OpenFragmentRule(ActivityTestRule<A> aActivityTestRule, Fragment fragment) {
        this.activityTestRule = aActivityTestRule;
        this.fragment = fragment;

    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                openFragment(activityTestRule.getActivity(), fragment);
                base.evaluate();
            }
        };
    }
}

