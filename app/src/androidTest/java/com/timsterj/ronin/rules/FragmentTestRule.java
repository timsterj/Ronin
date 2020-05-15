package com.timsterj.ronin.rules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.test.rule.ActivityTestRule;

import com.timsterj.ronin.di.AppComponent;

import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class FragmentTestRule<A extends AppCompatActivity, F extends Fragment> implements TestRule {

    private ActivityTestRule<A> activityTestRule;
    private F fragment;
    private RuleChain ruleChain;

    public FragmentTestRule(Class<A> activityClass, F fragment) {

        ruleChain = init(activityClass, fragment)
                .around(new OpenFragmentRule<>(activityTestRule,  fragment));
    }

    public FragmentTestRule(Class<A> activityClass, F fragment, AppComponent component) {

        ruleChain = init(activityClass, fragment)
                .around(new TestDaggerComponentRule<>(activityTestRule,component))
                .around(new OpenFragmentRule<>(activityTestRule,  fragment));
    }

    private RuleChain init(Class<A> activityClass, F fragment) {
        this.fragment = fragment;
        this.activityTestRule = new ActivityTestRule<A>(activityClass, true, true);
        return RuleChain.outerRule(activityTestRule);
    }

    public ActivityTestRule<A> getActivityTestRule() {
        return activityTestRule;
    }

    public void runOnUIThread(Runnable runnable) throws Throwable {
        activityTestRule.runOnUiThread(runnable);
    }

    public A getActivity(){
        return activityTestRule.getActivity();
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return ruleChain.apply(base, description);
    }
}
