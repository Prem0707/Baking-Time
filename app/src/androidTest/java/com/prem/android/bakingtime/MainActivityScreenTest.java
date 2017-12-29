package com.prem.android.bakingtime;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.prem.android.bakingtime.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

/**
 * Created by Prem on 28-12-2017.
 * This test demos a user clicking on a Recycler item in MenuActivity which opens up the
 * corresponding StepActivity.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    public static final String RECIPE_NAME= "Nutella Pie";

    // Add the rule that provides functional testing of single activity
    @Rule
   public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_RecipeSteps() {

        // Uses {@link Espresso#onData(org.hamcrest.Matcher)} to get a reference to a specific
        // gridview item and clicks it.
        onData(anything()).inAdapterView(withId(R.id.recipe_cardview)).atPosition(0).perform(click());

        // Checks that the mainActivity opens with the correct Recipe name displayed
        //onView(withId(R.id.recipe_cardview)).check(matches(withText(RECIPE_NAME)));
    }
}

