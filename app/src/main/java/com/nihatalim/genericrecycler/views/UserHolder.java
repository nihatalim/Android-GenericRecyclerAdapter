package com.nihatalim.genericrecycler.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nihatalim.genericrecycler.R;

/**
 * Created by thecower on 12/17/17.
 */

public class UserHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView date;


    public UserHolder(View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.name);
        this.date = itemView.findViewById(R.id.date);
    }
}
