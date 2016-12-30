package com.app.slowhand.settingssample;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.support.annotation.NonNull;
import android.widget.Spinner;

@InverseBindingMethods({
        @InverseBindingMethod(type = Spinner.class, attribute = "android:selectedItem"),
})

public class Settings extends BaseObservable {

    private static final String PREF_NAME = "settings_sample";

    private static final String KEY_URL = "url";
    private static final String DEFAULT_URLSTRING = "http://localhost";

    private static final String KEY_USER = "user";
    private static final String DEFAULT_USER = "username";

    private static final String KEY_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "C";

    private static final String KEY_CATEGORY_SYNTAX = "category_syntax";
    private static final boolean DEFAULT_CATEGORY_SYNTAX = true;

    private static final String KEY_CATEGORY_IDE = "category_ide";
    private static final boolean DEFAULT_CATEGORY_IDE = false;

    private static final String KEY_CATEGORY_EDITOR = "category_editor";
    private static final boolean DEFAULT_CATEGORY_EDITOR = false;


    private Context mContext;

    /**
     * Constructor.
     *
     * @param context {@link Context}
     */
    public Settings(Context context) {
        mContext = context;
    }

    /**
     * Get url.
     *
     * @return URL
     */
    @Bindable
    public String getUrl() {
        return getString(KEY_URL, DEFAULT_URLSTRING);
    }

    /**
     * Set url.
     *
     * @param url URL
     */
    public void setUrl(String url) {
        putString(KEY_URL, url);
        notifyPropertyChanged(BR.url);
    }

    /**
     * Get user name.
     *
     * @return User name
     */
    @Bindable
    public String getUser() {
        return getString(KEY_USER, DEFAULT_USER);
    }

    /**
     * Set user name.
     *
     * @param user User name
     */
    public void setUser(String user) {
        putString(KEY_USER, user);
        notifyPropertyChanged(BR.user);
    }

    /**
     * Get selected language.
     *
     * @return Selected language position
     */
    @Bindable
    public int getLanguage() {
        String lang = getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
        String[] array = mContext.getResources().getStringArray(R.array.languages);
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(lang)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Set selected language.
     *
     * @param position Selected language position
     */
    public void setLanguage(int position) {
        String[] array = mContext.getResources().getStringArray(R.array.languages);
        if (position > 0 && position < array.length) {
            String language = array[position];
            putString(KEY_LANGUAGE, language);
        }
    }

    /**
     * Is checked category syntax.
     *
     * @return boolean
     */
    @Bindable
    public boolean isCategorySyntax() {
        return getBoolean(KEY_CATEGORY_SYNTAX, DEFAULT_CATEGORY_SYNTAX);
    }

    /**
     * Set checked status in category syntax.
     *
     * @param categorySyntax boolean
     */
    public void setCategorySyntax(boolean categorySyntax) {
        putBoolean(KEY_CATEGORY_SYNTAX, categorySyntax);
        notifyPropertyChanged(BR.categorySyntax);
    }

    /**
     * Is checked category ide.
     *
     * @return boolean
     */
    @Bindable
    public boolean isCategoryIde() {
        return getBoolean(KEY_CATEGORY_IDE, DEFAULT_CATEGORY_IDE);
    }

    /**
     * Set checked status in category ide.
     *
     * @param categoryIde category ide
     */
    public void setCategoryIde(boolean categoryIde) {
        putBoolean(KEY_CATEGORY_IDE, categoryIde);
        notifyPropertyChanged(BR.categoryIde);
    }

    /**
     * Is checked category editor.
     *
     * @return boolean
     */
    @Bindable
    public boolean isCategoryEditor() {
        return getBoolean(KEY_CATEGORY_EDITOR, DEFAULT_CATEGORY_EDITOR);
    }

    /**
     * Set checked status in category editor.
     *
     * @param categoryEditor category editor
     */
    public void setCategoryEditor(boolean categoryEditor) {
        putBoolean(KEY_CATEGORY_EDITOR, categoryEditor);
        notifyPropertyChanged(BR.categoryEditor);
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
        return mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

}
