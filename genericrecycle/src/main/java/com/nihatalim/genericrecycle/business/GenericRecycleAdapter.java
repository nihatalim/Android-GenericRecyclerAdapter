package com.nihatalim.genericrecycle.business;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nihatalim.genericrecycle.interfaces.OnBind;
import com.nihatalim.genericrecycle.interfaces.OnCreate;
import java.util.List;

/**
 * Created by thecower on 11/30/17.
 */

public class GenericRecycleAdapter<THolder extends RecyclerView.ViewHolder, TListObject> extends RecyclerView.Adapter<THolder> {

    // List of bindable objects
    private List<TListObject> objectList = null;
    // Context of activity
    private Context context = null;

    // This is name of a layout xml file.
    private int layout_element;

    //Interfaces( needs to setters )
    private OnCreate<THolder> OnCreateInterface = null;

    private OnBind<THolder> OnBindInterface = null;

    // CONSTRUCTORS
    public GenericRecycleAdapter() {}

    public GenericRecycleAdapter(List<TListObject> objectList, Context context) {
        this();
        this.setObjectList(objectList);
        this.setContext(context);
    }

    public GenericRecycleAdapter(List<TListObject> objectList, Context context, int layout_element) {
        this(objectList,context);
        this.setLayout_element(layout_element);
    }

    // IMPLEMENTED METHODS
    @Override
    public THolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout_element,parent,false);
        return this.OnCreateInterface.onCreate(parent,viewType,view);
    }

    @Override
    public void onBindViewHolder(THolder holder, int position) {
        this.OnBindInterface.OnBind(holder,position);
    }

    @Override
    public int getItemCount() {
        return this.objectList.size();
    }

    // GETTERS AND SETTERS
    public List<TListObject> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<TListObject> objectList) {
        this.objectList = objectList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getLayout_element() {
        return layout_element;
    }

    public void setLayout_element(int layout_element) {
        this.layout_element = layout_element;
    }

    public OnCreate<THolder> getOnCreateInterface() {
        return OnCreateInterface;
    }

    public void setOnCreateInterface(OnCreate<THolder> onCreateInterface) {
        OnCreateInterface = onCreateInterface;
    }

    public OnBind<THolder> getOnBindInterface() {
        return OnBindInterface;
    }

    public void setOnBindInterface(OnBind<THolder> onBindInterface) {
        OnBindInterface = onBindInterface;
    }
}
