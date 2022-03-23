package ru.kkuzmichev.simpleappforespresso;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.contrib.NavigationViewActions.navigateTo;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;
import android.net.Uri;
import android.view.Gravity;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void registerIdlingResources() {
        IdlingRegistry.getInstance().register(EspressoIdlingResources.IDLING_RESOURCE);
    }

    @After
    public void unregisterIdlingResources() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.IDLING_RESOURCE);
    }

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

    @Test
    public void testGalleryList() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_gallery)).perform(click());
        onView(withId(R.id.recycle_view)).check(ViewAssertions.matches(SizeMatcher.of(10)));
    }

    @Test
    public void testSettings() {
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        ViewInteraction settings = onView(withText(R.string.action_settings));
        Intents.init();

        settings.perform(click());

        intended(allOf(
                hasAction(Intent.ACTION_VIEW),
                hasData(Uri.parse("https://google.com"))
        ));
        Intents.release();
    }

}
