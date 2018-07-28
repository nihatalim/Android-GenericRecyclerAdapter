package com.nihatalim.genericrecycler.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nihatalim.genericrecycle.business.GenericRecycleAdapter;
import com.nihatalim.genericrecycle.interfaces.OnAdapter;
import com.nihatalim.genericrecycle.interfaces.OnPaginate;
import com.nihatalim.genericrecycler.R;
import com.nihatalim.genericrecycler.models.User;
import com.nihatalim.genericrecycler.views.UserHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PaginationRecycler extends AppCompatActivity implements View.OnClickListener{
    List<User> userList = null;
    GenericRecycleAdapter<UserHolder, User> adapter = null;
    SimpleDateFormat dateFormat = null;
    RecyclerView recyclerView = null;

    Button btnLeft, btnRight;

    private static int  START_PAGE_NUMBER = 1;
    private static int  PAGINATION_SIZE = 9;
    private static long  PAGINATION_TIME_LIMIT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paginationrecycler);

        this.btnLeft = findViewById(R.id.btnLeft);
        this.btnRight = findViewById(R.id.btnRight);

        this.btnLeft.setOnClickListener(this);
        this.btnRight.setOnClickListener(this);

        this.recyclerView = findViewById(R.id.recycler_view);
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.setUpUsers();

        adapter = new GenericRecycleAdapter<>(getFirstPage(), getContext(), R.layout.user_card_vertical);
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
                return defaultLayoutManager;
            }
        });

        adapter.setOnPaginate(new OnPaginate<User>() {
            @Override
            public void paginate(int nextPageNumber, int paginationSize, User firstItem, User lastItem, Bundle bundle) {
                if(nextPageNumber>0){
                    List<User> list = getSubListForPage(userList, nextPageNumber, paginationSize);
                    if(list.size()>0){
                        adapter.clear(true);
                        adapter.addAll(list, true);
                        adapter.pageNumber = nextPageNumber;
                    }
                }
            }
        });

        adapter.pageNumber = START_PAGE_NUMBER;
        adapter.paginationSize = PAGINATION_SIZE;
        adapter.paginationTimeLimit = PAGINATION_TIME_LIMIT;

        adapter.build(this.recyclerView);
    }

    private void setUpUsers(){
        this.userList = new ArrayList<User>();
        for(int i=0;i<100;i++) this.userList.add(new User("User_" + Integer.toString(i)));
    }

    private Context getContext(){
        return this;
    }

    @Override
    public void onClick(View v) {
        int requestedPage = 0;
        switch (v.getId()){
            case R.id.btnLeft:
                requestedPage = adapter.pageNumber -1;
                adapter.paginate(requestedPage, null);
                break;

            case R.id.btnRight:
                requestedPage = adapter.pageNumber + 1;
                adapter.paginate(requestedPage, null);
                break;
        }
    }

    public List<User> getSubListForPage(List<User> mainList, int page, int paginationSize){
        // Note: adapter.pageNumber is start with 1 to ... so pageNumber is greater than 0 default
        // If you want for first page, you need to set 1

        List<User> results = new ArrayList<>(paginationSize);
        int startBound = (page-1)*paginationSize;
        int endBound = startBound + paginationSize;

        if(mainList != null && mainList.size()>0 && mainList.size() > startBound){
            if(endBound>mainList.size()){
                endBound = mainList.size();
            }

            results.addAll(mainList.subList(startBound,endBound));
        }

        return results;
    }


    public List<User> getFirstPage(){
        return getSubListForPage(userList, START_PAGE_NUMBER, PAGINATION_SIZE);
    }
}
