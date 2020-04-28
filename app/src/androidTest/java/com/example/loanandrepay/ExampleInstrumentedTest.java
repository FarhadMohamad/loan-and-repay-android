package com.example.loanandrepay;

import android.content.Context;

import androidx.annotation.ContentView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
   // private MainActivity mainActivity = null;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.loanandrepay", appContext.getPackageName());
    }
     @Test
    public void user_can_enter_fist_name(){
        onView(withId(R.id.txtFirstName)).perform(typeText("Daniel"));
    }
}
