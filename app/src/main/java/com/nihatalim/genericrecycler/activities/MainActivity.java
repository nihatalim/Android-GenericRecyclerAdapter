package com.nihatalim.genericrecycler.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nihatalim.genericrecycle.business.GenericRecycleAdapter;
import com.nihatalim.genericrecycle.interfaces.OnBind;
import com.nihatalim.genericrecycle.interfaces.OnClick;
import com.nihatalim.genericrecycle.interfaces.OnCreate;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerView = findViewById(R.id.recycler_view);
        this.dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        this.userList = new ArrayList<User>();
        this.userList.add(new User("Nihat"));
        this.userList.add(new User("Veli"));
        this.userList.add(new User("Ali"));
        this.userList.add(new User("Ahmet"));
        this.userList.add(new User("Zeytin"));

        adapter = new GenericRecycleAdapter<>(this.userList, getContext(), R.layout.user_card);
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
        adapter.build(this.recyclerView);

    }

    private Context getContext(){
        return this;
    }
}
