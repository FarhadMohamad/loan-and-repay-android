package com.example.loanandrepay;

import android.widget.EditText;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> registerActivityActivityTestRule = new ActivityTestRule<RegisterActivity>(RegisterActivity.class);
    private RegisterActivity registerActivity = null;

    //This method is called before you execute the test case
    @Before
    public void setUp() throws Exception {
        registerActivity = registerActivityActivityTestRule.getActivity();
        // simulate user action to input some value into EditText:


    }


    @Test
    public void RegisterTextFieldsTest(){
       onView(withId(R.id.txtFirstName)).perform(typeText("Daniel"));
        onView(withId(R.id.txtLastName)).perform(typeText("Hello"));


    }

}