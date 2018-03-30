package com.nihatalim.genericrecycler.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nihatalim.genericrecycle.business.GenericRecycleAdapter;
import com.nihatalim.genericrecycle.interfaces.ItemFeeder;
import com.nihatalim.genericrecycle.interfaces.OnAdapter;
import com.nihatalim.genericrecycler.R;
import com.nihatalim.genericrecycler.models.User;
import com.nihatalim.genericrecycler.views.UserHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<User> userList = null;
    GenericRecycleAdapter<UserHolder, User> adapter = null;
    SimpleDateFormat dateFormat = null;
    RecyclerView recyclerView = null;
    private static final int PAGINATION_SIZE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerView = findViewById(R.id.recycler_view);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.setUpUsers();

        adapter = new GenericRecycleAdapter<>(new ArrayList<User>(userList.subList(0, PAGINATION_SIZE)), getContext(), R.layout.user_card);
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
        });

        adapter.setPaginationSize(PAGINATION_SIZE);

        adapter.setItemFeeder(new ItemFeeder<User>() {
            @Override
            public List<User> feedItems(int paginationSize, int paginationOrder, User lastItem) {
                // You need to bind yourself using paginationSize and paginationOrder
                // Note that, paginationSize is your defined number but paginationOrder can be determine with recyclerView's elements(binding object) % paginationSize
                // So the pagination size start on  to max object size % paginationSize
                // Note: " % " is MOD sign in MATH
                if(userList.size()> (paginationOrder + 1) * PAGINATION_SIZE){
                    return userList.subList(paginationOrder * PAGINATION_SIZE, (paginationOrder + 1) * PAGINATION_SIZE);
                }
                return userList.subList(paginationOrder * PAGINATION_SIZE, userList.size());
            }
        });

        adapter.build(this.recyclerView);
    }

    private void setUpUsers(){
        this.userList = new ArrayList<User>();
        for(int i=0;i<100;i++) this.userList.add(new User("User_" + Integer.toString(i)));
    }

    private Context getContext(){
        return this;
    }
}
