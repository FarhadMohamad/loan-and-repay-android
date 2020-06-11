package com.example.loanandrepay;

import androidx.test.rule.ActivityTestRule;

import com.example.loanandrepay.client.InstallmentRequestActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;

public class InstallmentRequestActivityTest {
InstallmentRequestActivity installmentRequestActivity;

    @Rule
    public ActivityTestRule<InstallmentRequestActivity> installmentRequestActivityActivityTestRule = new ActivityTestRule<InstallmentRequestActivity>(InstallmentRequestActivity.class);
    //private InstallmentRequestActivity installmentRequestActivity2 = null;

    //This method is called before you execute the test case
    @Before
    public void setUp() throws Exception {
        installmentRequestActivity = installmentRequestActivityActivityTestRule.getActivity();


    }

    @Test
    public void calculationSixMonthsTestPass() {
        double getResult = installmentRequestActivity.calculationSixMonths(1000);
        assertEquals(191.67, getResult, 0);
    }

    @Test
    public void calculationSixMonthsTestFail() {

        double getResult = installmentRequestActivity.calculationSixMonths(1000);

        assertNotEquals(190.67, getResult, 0);

    }

    @Test
    public void calculationTwelveMonthsPass() {
        double getResult = installmentRequestActivity.calculationTwelveMonths(1000);

        assertEquals(104.17, getResult, 0);
    }

    @Test
    public void calculationTwelveMonthsFail() {
        double getResult = installmentRequestActivity.calculationTwelveMonths(1000);

        assertNotEquals(103.17, getResult, 0);
    }

    @Test
    public void activityTestPass() {
        assertNotNull(installmentRequestActivity);
        assertNotNull(onView(withId(R.id.txtFirstName)).perform(typeText("Daniel")));

    }

    @Test
    public void activityTestFail() {
        InstallmentRequestActivity installmentActivity = null;
        assertNull(installmentActivity);

    }
}