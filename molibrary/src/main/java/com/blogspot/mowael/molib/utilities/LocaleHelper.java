package com.blogspot.mowael.molib.utilities;

/**
 * Created by bonga on 4/29/2017.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

/**
 * This class is used to change your application locale and persist this change for the next time
 * that your app is going to be used.
 * <p/>
 * You can also change the locale of your application on the fly by using the setLocale method.
 * <p/>
 * Created by gunhansancar on 07/10/15.
 */
public class LocaleHelper {

    public enum Language {
        EN("en"), AR("ar"), PERSISTED("");

        private String language;

        Language(String language) {
            this.language = language;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }
    }

    private static final String defaultLanguage = Language.EN.getLanguage();
    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";

    public static Context onAttach(Context context) {
        Language lang = Language.PERSISTED;
        lang.setLanguage(getPersistedData(context));
        return setLocale(context, lang, false);
    }


    public static String getLanguage(Context context) {
        return getPersistedData(context);
    }

    public static Context setLocale(Context context, Language language) {
        return setLocale(context, language, true);
    }

    public static Context setLocale(Context context, Language language, boolean save) {
        if (save)
            persist(context, language, save);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return updateResources(context, language);
        }

        return updateResourcesLegacy(context, language);
    }

    public static String getPersistedData(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    public static String getSavedLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, null);
    }

    private static void persist(Context context, Language language, boolean save) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language.getLanguage());
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, Language language) {
        Locale locale = new Locale(language.getLanguage());
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    @SuppressWarnings("deprecation")
    private static Context updateResourcesLegacy(Context context, Language language) {
        Locale locale = new Locale(language.getLanguage());
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        switch (language) {
            case AR:
                configuration.screenLayout = Configuration.SCREENLAYOUT_LAYOUTDIR_LTR;
                break;
            case EN:
                configuration.screenLayout = Configuration.SCREENLAYOUT_LAYOUTDIR_RTL;
                break;
            case PERSISTED:
                if (Language.PERSISTED.getLanguage().equals(Language.EN.getLanguage())) {
                    configuration.screenLayout = Configuration.SCREENLAYOUT_LAYOUTDIR_LTR;
                } else if (Language.PERSISTED.getLanguage().equals(Language.AR.getLanguage())) {
                    configuration.screenLayout = Configuration.SCREENLAYOUT_LAYOUTDIR_RTL;
                } else {
                    configuration.screenLayout = Configuration.SCREENLAYOUT_LAYOUTDIR_LTR;
                }
                break;
            default:
                configuration.screenLayout = Configuration.SCREENLAYOUT_LAYOUTDIR_LTR;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    public static void restartApplication(Context context) {
        Intent i = context.getApplicationContext().getPackageManager()
                .getLaunchIntentForPackage(context.getApplicationContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.getApplicationContext().startActivity(i);
    }
}
