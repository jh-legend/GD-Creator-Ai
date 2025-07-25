package com.liveinaura.gdcreator;

import android.app.Application;
import android.graphics.Typeface;

import com.liveinaura.gdcreator.utils.CustomFontContextWrapper;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CustomFontContextWrapper.setDefaultFont(this, "SERIF", "fonts/kalpurush.ttf");
    }
}
