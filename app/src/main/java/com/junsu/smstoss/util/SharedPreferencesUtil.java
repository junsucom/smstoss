package com.junsu.smstoss.util;

import android.content.Context;
import android.content.SharedPreferences;

public enum SharedPreferencesUtil {
    SETTING_DATAS("{}");

    private static final String NAME = "eligaagent";

    private static SharedPreferences mPref = null;

    private Object mDefaultValue;

    private <I> SharedPreferencesUtil(I def) {
        mDefaultValue = def;
    }

    public <I> I get(Context context) {
        I ret = null;

        if (mPref == null) {
            mPref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }

        try {
            if (mDefaultValue instanceof String)
                ret = (I) ((Object) mPref.getString(name(), (String) mDefaultValue));
            else if (mDefaultValue instanceof Boolean)
                ret = (I) ((Object) mPref.getBoolean(name(), (Boolean) mDefaultValue));
            else if (mDefaultValue instanceof Integer)
                ret = (I) ((Object) mPref.getInt(name(), (Integer) mDefaultValue));
            else if (mDefaultValue instanceof Long)
                ret = (I) ((Object) mPref.getLong(name(), (Long) mDefaultValue));
            else if (mDefaultValue instanceof Float)
                ret = (I) ((Object) mPref.getFloat(name(), (Float) mDefaultValue));
        } catch (ClassCastException e) {
            SharedPreferences.Editor editor = mPref.edit();
            editor.remove(name());
            editor.commit();
            ret = get(context);
        }
        return ret;
    }

    public <T> void set(Context context, T data) {
        if (mPref == null) {
            mPref = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        }

        SharedPreferences.Editor edit = mPref.edit();
        if (mDefaultValue instanceof String)
            edit.putString(name(), (String) data);
        else if (mDefaultValue instanceof Boolean)
            edit.putBoolean(name(), (Boolean) data);
        else if (mDefaultValue instanceof Integer)
            edit.putInt(name(), (Integer) data);
        else if (mDefaultValue instanceof Long)
            edit.putLong(name(), (Long) data);
        else if (mDefaultValue instanceof Float)
            edit.putFloat(name(), (Float) data);
        edit.commit();
    }
}
