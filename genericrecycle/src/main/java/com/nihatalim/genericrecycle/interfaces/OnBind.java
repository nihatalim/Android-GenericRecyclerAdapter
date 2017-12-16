package com.nihatalim.genericrecycle.interfaces;

/**
 * Created by thecower on 11/30/17.
 */

public interface OnBind<THolder> {
    void OnBind(THolder holder, int position);
}
