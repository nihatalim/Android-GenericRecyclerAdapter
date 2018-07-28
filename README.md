# GenericRecycler

GenericRecycler can decrease code for RecyclerView Adapter.

There is below example in this repository in app module

![Alt Text](https://github.com/nihatalim/DragList/raw/master/GenericRecyclerWithDragListExample.gif)

# Step 1: Installation

Click this button
[![](https://jitpack.io/v/nihatalim/DragList.svg)](https://jitpack.io/#nihatalim/genericrecycler)

In the opening page, click the GET button which latest version.
Then follow the instruction.

# Step 2: Usage

## a) Define GenericRecycleAdapter instance on class level like this:

```
private GenericRecycleAdapter<THolder, TModel> adapter = null;
```

THolder is extends from RecyclerView.ViewHolder class which you bind.

TModel is model class which you bind.

## b) Get a new instance GenericRecycleAdapter with pass 3 parameters:

```
this.adapter = new GenericRecycleAdapter<>(this.userList, getContext(), R.layout.user_card);
```

this.userList is list of TModel.

getContext is a method which returns current context like this:

```
private Context getContext(){
    return this;
}
```

R.layout.user_card is a layout file for fill each TModel object. 

## c) Define onAdapter

```
adapter.setOnAdapter(new OnAdapter<UserHolder>() {
    @Override
    public UserHolder onCreate(ViewGroup parent, int viewType, View view) {
        final UserHolder holder = new UserHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), adapter.getObjectList().get(holder.getPosition()).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void OnBind(UserHolder userHolder, int position) {
        userHolder.name.setText(adapter.getObjectList().get(position).getName());
        userHolder.date.setText(dateFormat.format(adapter.getObjectList().get(position).getDate()));
    }

    // If you wanna check another LayoutManager options, go to here and see other options
    // https://github.com/nihatalim/Android-GenericRecyclerAdapter/tree/master/app/src/main/java/com/nihatalim/genericrecycler/activities
        
    @Override
    public RecyclerView.LayoutManager setLayoutManager(RecyclerView.LayoutManager defaultLayoutManager) {
        LinearLayoutManager lm = ((LinearLayoutManager) defaultLayoutManager);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        return lm;
    }
});

```

## d) Add snap functionality 

```
// If you set LayoutManager in setOnAdapter function as LinearLayoutManager oriented HORIZONTAL, you can set snap as true and recyclerview gain each swipe one pass [Some one may be fix this words later :) ]

adapter.snap(true);
```


## e) Finally build the adapter 

```
adapter.build(this.recyclerView);
```

Have a nice code...
