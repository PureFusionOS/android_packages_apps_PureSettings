/*
 * Copyright (C) 2017 The Pure Nexus Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pure.settings.fragments;

import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.internal.util.omni.PackageUtils;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.pure.settings.utils.Utils;

public class LogSettings extends SettingsPreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.log_settings);
        PreferenceScreen prefScreen = getPreferenceScreen();

        // check for disabled logcat app
        Preference logcatApp = findPreference("logcat_app");
        if (logcatApp != null) {
            PreferenceCategory systemPrefs = (PreferenceCategory) findPreference("category_system");
            if (systemPrefs != null && !PackageUtils.isAvailableApp("org.omnirom.logcat", getActivity())) {
                systemPrefs.removePreference(logcatApp);
            }
        }
    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.PURE;
    }
}

