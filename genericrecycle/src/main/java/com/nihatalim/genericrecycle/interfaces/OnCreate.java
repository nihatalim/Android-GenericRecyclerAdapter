package com.nihatalim.genericrecycle.interfaces;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by thecower on 11/30/17.
 */

public interface OnCreate<THolder> {
    THolder onCreate(ViewGroup parent, int viewType, View view);
}
