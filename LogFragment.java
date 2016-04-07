/*
 * Decompiled with CFR 0_92.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.fsoft.vktest.windows;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fsoft.vktest.windows.ConsoleView;
import com.fsoft.vktest.windows.TabsActivity;

/*
 * Failed to analyse overrides
 */
public class LogFragment
extends Fragment {
    ConsoleView consoleView = null;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ConsoleView consoleView;
        this.consoleView = consoleView = new ConsoleView((Context)this.getActivity());
        TabsActivity.consoleView = consoleView;
        return consoleView;
    }

    public void onPause() {
        this.consoleView.setVisibility(4);
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        this.consoleView.setVisibility(0);
    }
}

