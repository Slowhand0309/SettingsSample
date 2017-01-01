package com.app.slowhand.settingssample;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.CheckBox;

import com.squareup.spoon.Spoon;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SettingsTest {

    private static final String PREF_NAME = "settings_sample";

    private static final String KEY_URL = "url";
    private static final String DEFAULT_URL = "http://localhost";

    private static final String KEY_USER = "user";
    private static final String DEFAULT_USER = "username";

    private static final String KEY_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "C";

    private static final String KEY_CATEGORY_SYNTAX = "category_syntax";

    private static final String KEY_CATEGORY_IDE = "category_ide";

    private static final String KEY_CATEGORY_EDITOR = "category_editor";

    private MainActivity mActivity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    /**
     * Setup
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();

        // Set default value

        onView(withId(R.id.edit_url)).perform(clearText());
        onView(withId(R.id.edit_url)).perform(typeText(DEFAULT_URL));

        closeSoftKeyboard();

        // Set user
        onView(withId(R.id.edit_user)).perform(clearText());
        onView(withId(R.id.edit_user)).perform(typeText(DEFAULT_USER));

        closeSoftKeyboard();

        // Set language
        onView(withId(R.id.spinner_lang)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is(DEFAULT_LANGUAGE))).perform(click());

        // Set category
        // syntax: true, ide: false, editor: false
        CheckBox cb = (CheckBox) mActivity.findViewById(R.id.category_syntax);
        if (!cb.isChecked()) {
            onView(withId(R.id.category_syntax)).perform(click());
        }

        cb = (CheckBox) mActivity.findViewById(R.id.category_ide);
        if (cb.isChecked()) {
            onView(withId(R.id.category_ide)).perform(click());
        }

        cb = (CheckBox) mActivity.findViewById(R.id.category_editor);
        if (cb.isChecked()) {
            onView(withId(R.id.category_editor)).perform(click());
        }
    }

    /**
     * Check init display
     *
     * @throws Exception
     */
    @Test
    public void initSettings() throws Exception {

        Spoon.screenshot(mActivity, "initSettings");

        // Displayed URL
        onView(withId(R.id.edit_url)).check(matches(isDisplayed()));
        // Displayed User
        onView(withId(R.id.edit_user)).check(matches(isDisplayed()));
        // Displayed language
        onView(withId(R.id.spinner_lang)).check(matches(isDisplayed()));
        // Displayed category syntax
        onView(withId(R.id.category_syntax)).check(matches(isDisplayed()));
        // Displayed category ide
        onView(withId(R.id.category_ide)).check(matches(isDisplayed()));
        // Displayed category editor
        onView(withId(R.id.category_editor)).check(matches(isDisplayed()));

        // Not empty URL
        onView(withId(R.id.edit_url)).check(matches(not(withText(""))));
        // Not empty User
        onView(withId(R.id.edit_user)).check(matches(not(withText(""))));
        // Not empty language
        onView(withId(R.id.spinner_lang)).check(matches(not(withText(""))));
    }

    /**
     * Check set values
     *
     * @throws Exception
     */
    @Test
    public void setValues() throws Exception {

        Spoon.screenshot(mActivity, "setValues");

        // Set url
        onView(withId(R.id.edit_url)).perform(clearText());
        onView(withId(R.id.edit_url)).perform(typeText("http://example.com:8080/url"));

        closeSoftKeyboard();

        Spoon.screenshot(mActivity, "setValues_url");

        // Check shared preference value
        Assert.assertEquals("http://example.com:8080/url", getString(KEY_URL, ""));

        // Set user
        onView(withId(R.id.edit_user)).perform(clearText());
        onView(withId(R.id.edit_user)).perform(typeText("super user"));

        closeSoftKeyboard();

        Spoon.screenshot(mActivity, "setValues_user");

        // Check shared preference value
        Assert.assertEquals("super user", getString(KEY_USER, ""));

        // Set language
        onView(withId(R.id.spinner_lang)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Java"))).perform(click());

        Spoon.screenshot(mActivity, "setValues_language");

        // Check shared preference value
        Assert.assertEquals("Java", getString(KEY_LANGUAGE, ""));

        // Set category
        // syntax: false, ide: true, editor: true
        CheckBox cb = (CheckBox) mActivity.findViewById(R.id.category_syntax);
        if (cb.isChecked()) {
            onView(withId(R.id.category_syntax)).perform(click());
        }

        cb = (CheckBox) mActivity.findViewById(R.id.category_ide);
        if (!cb.isChecked()) {
            onView(withId(R.id.category_ide)).perform(click());
        }

        cb = (CheckBox) mActivity.findViewById(R.id.category_editor);
        if (!cb.isChecked()) {
            onView(withId(R.id.category_editor)).perform(click());
        }

        Spoon.screenshot(mActivity, "setValues_category");

        // Check shared preference value
        Assert.assertEquals(false, getBoolean(KEY_CATEGORY_SYNTAX, true));
        Assert.assertEquals(true, getBoolean(KEY_CATEGORY_IDE, false));
        Assert.assertEquals(true, getBoolean(KEY_CATEGORY_EDITOR, false));

    }

    /**
     * Get string value.
     *
     * @param key Key
     * @param defaultValue Default value
     * @return String value
     */
    private String getString(@NonNull String key, String defaultValue) {
        SharedPreferences pref = getPref();
        return pref.getString(key, defaultValue);
    }

    /**
     * Get boolean value.
     *
     * @param key Key
     * @param defaultValue Default value
     * @return Boolean value
     */
    private boolean getBoolean(@NonNull String key, boolean defaultValue) {
        SharedPreferences pref = getPref();
        return pref.getBoolean(key, defaultValue);
    }

    /**
     * Save string key-value.
     *
     * @param key Key
     * @param value Save value
     */
    private void putString(@NonNull String key, String value) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Save boolean key-value.
     *
     * @param key Key
     * @param value Save value
     */
    private void putBoolean(@NonNull String key, boolean value) {
        SharedPreferences pref = getPref();
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Get {@link SharedPreferences}
     *
     * @return {@link SharedPreferences}
     */
    private SharedPreferences getPref() {
        return mActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
