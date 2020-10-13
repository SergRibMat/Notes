package com.example.notes;

import android.content.Context;

public class MainActContext{
    private static Context myContext;

    public static Context getContext() {
        return myContext;
    }

    public static void setContext(Context mContext) {
        myContext = mContext;
    }

}
