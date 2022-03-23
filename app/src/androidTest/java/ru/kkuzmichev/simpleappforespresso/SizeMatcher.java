package ru.kkuzmichev.simpleappforespresso;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class SizeMatcher extends TypeSafeMatcher<View> {
    private final int size;

    public SizeMatcher(int size) {
        this.size = size;
    }

    public static SizeMatcher of(int size) {
        return new SizeMatcher(size);
    }

    @Override
    protected boolean matchesSafely(View item) {
        boolean result = false;
        if (item != null) {
            RecyclerView recycler = (RecyclerView) item;
            if (recycler.getAdapter() != null) {
                result = recycler.getAdapter().getItemCount() == size;
            }
        }
        return result;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Item should have size" + size);
    }
}
