package com.nihatalim.genericrecycle.interfaces;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by nihat on 30.03.2018.
 */

public interface OnAdapter<THolder> {
    THolder onCreate(ViewGroup parent, int viewType, View view);
    void OnBind(THolder holder, int position);

}
