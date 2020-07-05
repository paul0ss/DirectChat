package com.example.chatwifi_direct;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.chatwifi_direct.gui.PeersActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.matcher.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<PeersActivity> mPeersActivityTestRule = new ActivityTestRule<>(PeersActivity.class);

/*    @Test
    public void useAppContext() throws  Exception{
        // Context of the app under test.
        //Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        //assertEquals("com.example.chatwifi_direct", appContext.getPackageName());
        onView(withId("R.id.awdka")).perform().check();
    }*/

    @Test
    public void listGoesOverTheFold() {
        onView(withText("Peer1")).check(matches(isDisplayed()));
/*        onView(allOf(withId(R.id.checkBox))).perform(click());
        onView(withId(R.id.item2)).perform(click())
                .check(matches(isDisplayed()));*/
    }


}
