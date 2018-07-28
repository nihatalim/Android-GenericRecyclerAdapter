# GenericRecycler

GenericRecycler can decrease code for RecyclerView Adapter.

There is below example in this repository in app module

![Alt Text](https://github.com/nihatalim/DragList/raw/master/GenericRecyclerWithDragListExample.gif)

# Step 1: Installation

## a) Add it in your root build.gradle at the end of repositories

```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## b) Add the dependency

```
dependencies {
    implementation 'com.github.nihatalim:genericrecycler:v1.0.8'
}
```

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

## e) Add pagination base

```
// You can set the pageNumber for initializing. This is default value.
adapter.pageNumber = 1;

// You can set pagination size. This is default value.
adapter.paginationSize = 10;

// You can set pagination time limit as ms (1 sec = 1000 ms). May be useful for some stuff like getting data from servers etc. This is default value.
adapter.paginationTimeLimit = 1000;

adapter.setOnPaginate(new OnPaginate<User>() {
    @Override
    public void paginate(int nextPageNumber, int paginationSize, User firstItem, User lastItem, Bundle bundle) {
        // Write your pagination logic.
        if(nextPageNumber>0){
            List<User> list = getSubListForPage(userList, nextPageNumber, paginationSize);
            if(list.size()>0){
                adapter.clear(true); // Clear all items and pass true parameter for notifyDatasetChange
                adapter.addAll(list, true); // Add the list and pass true parameter for notifyDatasetChange
                adapter.pageNumber = nextPageNumber; // Update adapter's pageNumber
            }
        }
    }
});

// You can call paginate function for calling paginate logic. First param is int variable for next page number and you can determine before calling. Second param is a bundle object for passing your custom params.
adapter.paginate(requestedPage, null);

```


## f) Finally build the adapter 

```
adapter.build(this.recyclerView);
```

Have a nice code...
