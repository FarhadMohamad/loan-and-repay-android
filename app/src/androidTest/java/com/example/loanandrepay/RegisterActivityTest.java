package com.example.loanandrepay;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    // private MainActivity mainActivity = null;

    @Test
    public void RegisterTextFieldsTest(){
        onView(withId(R.id.txtFirstName)).perform(typeText("Daniel"));
        onView(withId(R.id.txtLastName)).perform(typeText("Hello"));
    }

}