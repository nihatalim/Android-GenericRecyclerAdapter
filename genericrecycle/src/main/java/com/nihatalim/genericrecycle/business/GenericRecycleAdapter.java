package com.nihatalim.genericrecycle.business;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.nihatalim.genericrecycle.interfaces.OnAdapter;
import com.nihatalim.genericrecycle.interfaces.OnPaginate;

import java.util.Date;
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

    private OnAdapter<THolder> OnAdapter = null;

    private OnPaginate OnPaginate = null;

    public RecyclerView.LayoutManager LayoutManager = null;

    public Date LastPaginationTime = new Date();

    public int pageNumber = 1;
    public int paginationSize = 10;
    public long paginationTimeLimit = 1000;

    private boolean isLoading = false;

    private boolean isSnap = false;

    // You can set techniques on setter method but this is default animation
    private Techniques techniques = Techniques.Bounce;

    // CONSTRUCTORS
    public GenericRecycleAdapter() {

    }

    public GenericRecycleAdapter(List<TListObject> objectList, Context context) {
        this();
        this.setObjectList(objectList);
        this.setContext(context);
    }

    public GenericRecycleAdapter(List<TListObject> objectList, Context context, int layout_element) {
        this(objectList,context);
        this.setLayout_element(layout_element);
    }

    @Override
    public THolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(this.layout_element,parent,false);
        //return this.OnCreateInterface.onCreate(parent,viewType,view);
        return this.OnAdapter.onCreate(parent,viewType,view);
    }

    @Override
    public void onBindViewHolder(THolder holder, int position) {
        //this.OnBindInterface.OnBind(holder,position);
        this.OnAdapter.OnBind(holder, position);
    }

    @Override
    public int getItemCount() {
        if(this.objectList==null) return 0;
        return this.objectList.size();
    }

    public void snap(boolean snp){
        this.isSnap = snp;
    }

    // BUILD RECYCLEVIEW

    public void build(RecyclerView recyclerView){
        // Added for fragments if this LM is not binded already
        this.LayoutManager = OnAdapter.setLayoutManager(getDefaultLayoutManager());
        if(this.LayoutManager == null) this.LayoutManager = getDefaultLayoutManager();

        recyclerView.setLayoutManager(this.LayoutManager);

        recyclerView.setAdapter(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        if(this.isSnap){
            SnapHelper snapHelper = new PagerSnapHelper();
            snapHelper.attachToRecyclerView(recyclerView);
        }
    }

    public void add(TListObject obj, boolean notify){
        if(this.objectList!=null){
            this.objectList.add(obj);
            if(notify) this.notifyDataSetChanged();
        }
    }

    public void addAll(List<TListObject> obj, boolean notify){
        if(this.objectList!=null){
            this.objectList.addAll(obj);
            if(notify) this.notifyDataSetChanged();
        }
    }

    public void remove(TListObject obj, boolean notify){
        this.objectList.remove(obj);
        if (notify) this.notifyDataSetChanged();
    }

    public void clear(boolean notify){
        this.objectList.clear();
        if (notify) this.notifyDataSetChanged();
    }

    public void paginate(int pageNumber, Bundle bundle){
        TListObject firstItem = null;
        TListObject lastItem = null;

        Date nextTime = new Date(this.LastPaginationTime.getTime() + this.paginationTimeLimit);
        Date currentTime = new Date();

        if(nextTime.before(currentTime)){
            if(this.objectList.size()>0){
                firstItem = this.objectList.get(0);
                lastItem = this.objectList.get(this.objectList.size()-1);
            }
            this.OnPaginate.<TListObject>paginate(pageNumber, this.paginationSize, firstItem, lastItem, bundle);
            LastPaginationTime = currentTime;
        }
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

    public com.nihatalim.genericrecycle.interfaces.OnAdapter<THolder> getOnAdapter() {
        return OnAdapter;
    }

    public void setOnAdapter(com.nihatalim.genericrecycle.interfaces.OnAdapter<THolder> onAdapter) {
        OnAdapter = onAdapter;
    }

    public com.nihatalim.genericrecycle.interfaces.OnPaginate getOnPaginate() {
        return OnPaginate;
    }

    public void setOnPaginate(com.nihatalim.genericrecycle.interfaces.OnPaginate onPaginate) {
        OnPaginate = onPaginate;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public Techniques getTechniques() {
        return techniques;
    }

    public void setTechniques(Techniques techniques) {
        this.techniques = techniques;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return LayoutManager;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        LayoutManager = layoutManager;
    }

    public LinearLayoutManager getDefaultLayoutManager() {
        LinearLayoutManager defaultLayoutManager = new LinearLayoutManager(this.context);
        defaultLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        defaultLayoutManager.scrollToPosition(0);
        return defaultLayoutManager;
    }

}
