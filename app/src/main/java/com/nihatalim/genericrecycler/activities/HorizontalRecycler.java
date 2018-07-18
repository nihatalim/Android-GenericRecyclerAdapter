package com.nihatalim.genericrecycler.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nihatalim.genericrecycle.business.GenericRecycleAdapter;
import com.nihatalim.genericrecycle.interfaces.OnAdapter;
import com.nihatalim.genericrecycler.R;
import com.nihatalim.genericrecycler.models.User;
import com.nihatalim.genericrecycler.views.UserHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HorizontalRecycler extends AppCompatActivity {

    List<User> userList = null;
    GenericRecycleAdapter<UserHolder, User> adapter = null;
    SimpleDateFormat dateFormat = null;
    RecyclerView recyclerView = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalrecycler);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.setUpUsers();

        adapter = new GenericRecycleAdapter<>(new ArrayList<User>(userList), getContext(), R.layout.user_card_horizontal);
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

            @Override
            public RecyclerView.LayoutManager setLayoutManager(RecyclerView.LayoutManager defaultLayoutManager) {
                LinearLayoutManager lm = ((LinearLayoutManager) defaultLayoutManager);
                lm.setOrientation(LinearLayoutManager.HORIZONTAL);
                return lm;
            }
        });

        adapter.snap(true);
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
