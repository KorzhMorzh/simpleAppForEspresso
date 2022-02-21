package ru.kkuzmichev.simpleappforespresso;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.view.Gravity;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testGallery() {
        ViewInteraction drawer = onView(withId(R.id.drawer_layout));
        drawer.check(matches(isClosed(Gravity.START)))
                .perform(DrawerActions.open());

        onView(withId(R.id.nav_view))
                .perform(navigateTo(R.id.nav_gallery));

        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()));
    }

    @Test
    public void testFABVisibility(){
        ViewInteraction drawer = onView(withId(R.id.drawer_layout));
        ViewInteraction nav = onView(withId(R.id.nav_view));
        ViewInteraction fab = onView(withId(R.id.fab));

        fab.check(matches(isDisplayed()));

        drawer.perform(DrawerActions.open());
        nav.perform(navigateTo(R.id.nav_gallery));

        drawer.perform(DrawerActions.open());
        nav.perform(navigateTo(R.id.nav_home));

        fab.check(matches(isDisplayed()));

    }
}
