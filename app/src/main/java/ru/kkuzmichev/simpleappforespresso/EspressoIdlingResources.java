package ru.kkuzmichev.simpleappforespresso;

import androidx.test.espresso.idling.CountingIdlingResource;

public class EspressoIdlingResources {
    private static final String RESOURCE = "GALLERY";
    public static final CountingIdlingResource IDLING_RESOURCE = new CountingIdlingResource(RESOURCE);

    public static void increment() {
        IDLING_RESOURCE.increment();
    }

    public static void decrement() {
        if (!IDLING_RESOURCE.isIdleNow()) {
            IDLING_RESOURCE.decrement();
        }
    }
}
