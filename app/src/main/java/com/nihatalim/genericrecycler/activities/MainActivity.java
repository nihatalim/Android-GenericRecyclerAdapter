package com.nihatalim.genericrecycler.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nihatalim.genericrecycler.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnVertical, btnHorizontal, btnGrid, btnStaggeredGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnVertical = findViewById(R.id.btnVertical);
        this.btnHorizontal = findViewById(R.id.btnHorizontal);
        this.btnGrid = findViewById(R.id.btnGrid);
        this.btnStaggeredGrid = findViewById(R.id.btnStaggeredGrid);
        this.btnHorizontal.setOnClickListener(this);
        this.btnVertical.setOnClickListener(this);
        this.btnGrid.setOnClickListener(this);
        this.btnStaggeredGrid.setOnClickListener(this);
    }

    private Context getContext(){
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnHorizontal:
                startActivity(new Intent(this, HorizontalRecycler.class));
                break;

            case R.id.btnVertical:
                startActivity(new Intent(this, VerticalRecycler.class));
                break;

            case R.id.btnGrid:
                startActivity(new Intent(this, GridRecycler.class));
                break;

            case R.id.btnStaggeredGrid:
                startActivity(new Intent(this, StaggeredGridRecycler.class));
                break;
        }
    }
}
