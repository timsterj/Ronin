package com.timsterj.ronin.activities;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.timsterj.ronin.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4ClassRunner.class)
public class LaunchActivityTest {
    @Rule
    public ActivityTestRule<LaunchActivity> activityTestRule = new ActivityTestRule<>(LaunchActivity.class);

    @Before
    public void setUp(){
        Intents.init();
    }

    @Test
    public void user_click_button_and_go_to_authorization_activity() {
        onView(ViewMatchers.withId(R.id.button_prepareIsDone)).perform(ViewActions.click());
        intended(hasComponent(AuthorizationActivity.class.getName()));
    }
}