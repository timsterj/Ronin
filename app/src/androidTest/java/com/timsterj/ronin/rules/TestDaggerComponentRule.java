package com.timsterj.ronin.rules;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.rule.ActivityTestRule;

import com.timsterj.ronin.App;
import com.timsterj.ronin.di.AppComponent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TestDaggerComponentRule<A extends AppCompatActivity> implements TestRule {

    private final ActivityTestRule<A> activityTestRule;
    private final AppComponent component;

    public TestDaggerComponentRule(ActivityTestRule<A> activityTestRule, AppComponent component) {
        this.activityTestRule = activityTestRule;
        this.component = component;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                App applicaton = (App) activityTestRule.getActivity().getApplication();
                AppComponent originalComponent = applicaton.getAppComponent();
                applicaton.setComponentForTest(component);
                try {
                    base.evaluate();
                } finally {
                    applicaton.setComponentForTest(originalComponent);
                }
            }
        };
    }
}
