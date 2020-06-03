package com.example.loanandrepay;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.loanandrepay.client.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

////////////////////
import static org.junit.Assert.assertNotNull;
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
    private MainActivity mainActivity = null;
//This method is called before you execute the test case
    @Before
    public void setUp() throws Exception {
        mainActivity = mainActivityActivityTestRule.getActivity();
    }

    //Before testLaunch setUp will be executed first and then after testLaunch the tearDown will
    //be executed
    @Test
    public void testLaunch()
    {
    View view = mainActivity.findViewById(R.id.textView3);
    assertNotNull(view);
    }
    //This method is called after you execute the test case
    @After
    public void tearDown() throws Exception {
        mainActivity = null;
    }
}