package com.nihatalim.genericrecycle.business;

import android.app.Activity;
import android.content.Context;
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

    public RecyclerView.LayoutManager LayoutManager = null;

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
