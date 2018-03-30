package com.nihatalim.genericrecycle.business;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.nihatalim.genericrecycle.interfaces.ItemFeeder;
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

    private int paginationSize = 0;

    public RecyclerView.LayoutManager LayoutManager = null;

    private ItemFeeder<TListObject> ItemFeeder = null;

    private boolean isLoading = false;

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

    // BUILD RECYCLEVIEW

    public void build(RecyclerView recyclerView){
        // Added for fragments if this LM is not binded already
        this.LayoutManager = OnAdapter.setLayoutManager(getDefaultLayoutManager());
        if(this.LayoutManager == null) this.LayoutManager = getDefaultLayoutManager();

        recyclerView.setLayoutManager(this.LayoutManager);

        recyclerView.setAdapter(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        if(this.paginationSize>0){
            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if(!isLoading){
                        if(objectList.size() <= 0 || objectList.size() % paginationSize != 0  || ((LinearLayoutManager)LayoutManager).findLastVisibleItemPosition()!= objectList.size()-1) return;

                        TListObject obj = objectList.get(((LinearLayoutManager)LayoutManager).findLastVisibleItemPosition());
                        if(obj != null){
                            isLoading = true;
                            YoYo.with(techniques).duration(1000).playOn(recyclerView);
                            objectList.addAll(ItemFeeder.feedItems(paginationSize, getPaginationOrder(), obj));
                            isLoading = false;
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
            });
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

    public com.nihatalim.genericrecycle.interfaces.ItemFeeder<TListObject> getItemFeeder() {
        return ItemFeeder;
    }

    public void setItemFeeder(com.nihatalim.genericrecycle.interfaces.ItemFeeder<TListObject> itemFeeder) {
        ItemFeeder = itemFeeder;
    }

    public int getPaginationSize() {
        return paginationSize;
    }

    public void setPaginationSize(int paginationSize) {
        this.paginationSize = paginationSize;
    }

    public int getPaginationOrder() {
        return objectList.size() / paginationSize;
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

    public LinearLayoutManager getDefaultLayoutManager() {
        LinearLayoutManager defaultLayoutManager = new LinearLayoutManager(this.context);
        defaultLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        defaultLayoutManager.scrollToPosition(0);
        return defaultLayoutManager;
    }

}
