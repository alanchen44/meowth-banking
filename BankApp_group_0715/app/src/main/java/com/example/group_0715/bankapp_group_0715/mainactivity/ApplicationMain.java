package com.example.group_0715.bankapp_group_0715.mainactivity;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Created by Alan on 2017-09-02.
 */

// lock all activities into portrait mode in one place, instead of going through all activities
    // used in AndroidManifest as application name
public class ApplicationMain extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // when an activity is started...
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                // force orientation to portrait mode
                activity.setRequestedOrientation(
                        ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }

            public void onActivityDestroyed(Activity activity) {
            }

            public void onActivityResumed(Activity activity) {
            }

            public void onActivitySaveInstanceState(Activity activity, Bundle savedInstanceState) {
            }

            public void onActivityStopped(Activity activity) {
            }

            public void onActivityStarted(Activity activity) {
            }

            public void onActivityPaused(Activity activity) {
            }

        });

    }


}
