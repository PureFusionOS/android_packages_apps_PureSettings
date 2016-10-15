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

import android.content.ContentResolver;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.support.v7.preference.PreferenceScreen;
import android.support.v14.preference.SwitchPreference;

import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class NotificationMediaSettings extends SettingsPreferenceFragment
        implements OnPreferenceChangeListener {

    private static final String KEY_SHOW_TICKER = "status_bar_show_ticker";

    private ListPreference mTicker;

    private static final String PREF_MEDIA_SCANNER_ON_BOOT = "media_scanner_on_boot";

    private ListPreference mMsob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.notification_media_settings);
        PreferenceScreen prefScreen = getPreferenceScreen();

        mTicker = (ListPreference) findPreference(KEY_SHOW_TICKER);
        mTicker.setOnPreferenceChangeListener(this);
        int tickerMode = Settings.System.getIntForUser(getContentResolver(),
                Settings.System.STATUS_BAR_SHOW_TICKER,
                0, UserHandle.USER_CURRENT);
        mTicker.setValue(String.valueOf(tickerMode));
        mTicker.setSummary(mTicker.getEntry());

        mMsob = (ListPreference) findPreference(PREF_MEDIA_SCANNER_ON_BOOT);
        mMsob.setValue(String.valueOf(Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.MEDIA_SCANNER_ON_BOOT, 0)));
        mMsob.setSummary(mMsob.getEntry());
        mMsob.setOnPreferenceChangeListener(this);

    }

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.PURE;
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();
        if (preference == mTicker) {
            int tickerMode = Integer.parseInt(newValue.toString());
            Settings.System.putIntForUser(getContentResolver(),
                    Settings.System.STATUS_BAR_SHOW_TICKER, tickerMode,
                    UserHandle.USER_CURRENT);
            int index = mTicker.findIndexOfValue((String) newValue);
            mTicker.setSummary(mTicker.getEntries()[index]);
            return true;
        }

        if (preference == mMsob) {
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.MEDIA_SCANNER_ON_BOOT,
                    Integer.valueOf(String.valueOf(newValue)));
            mMsob.setValue(String.valueOf(newValue));
            mMsob.setSummary(mMsob.getEntry());
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
