# GenericRecycler

GenericRecycler can decrease code for RecyclerView Adapter.

There is below example in this repository in app module

![Alt Text](https://github.com/nihatalim/DragList/raw/master/GenericRecyclerWithDragListExample.gif)

Step 1: Installation

Click this button
[![](https://jitpack.io/v/nihatalim/DragList.svg)](https://jitpack.io/#nihatalim/genericrecycler)

In the opening page, click the GET button which latest version.
Then follow the instruction.

Step 2: Usage

a) Define GenericRecycleAdapter instance on class level like this:

```
private GenericRecycleAdapter<THolder, TModel> adapter = null;
```

THolder is extends from RecyclerView.ViewHolder class which you bind.

TModel is model class which you bind.

b) Get a new instance GenericRecycleAdapter with pass 3 parameters:

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

c) Define onCreate and onBind methods

```
adapter.setOnCreateInterface(new OnCreate<UserHolder>() {
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
});

adapter.setOnBindInterface(new OnBind<UserHolder>() {
    @Override
    public void OnBind(UserHolder userHolder, int position) {
        userHolder.name.setText(adapter.getObjectList().get(position).getName());
        userHolder.date.setText(dateFormat.format(adapter.getObjectList().get(position).getDate()));
    }
});

```

d) Finally build the adapter 

```
adapter.build(this.recyclerView);
```

this.recyclerView is recyclerview which in your layout in activity or fragment.
