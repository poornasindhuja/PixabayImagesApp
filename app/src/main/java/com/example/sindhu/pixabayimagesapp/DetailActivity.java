package com.example.sindhu.pixabayimagesapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView img1;
    TextView like,view,tag,derr;
    String s[];
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        img1=findViewById(R.id.main_img);
        like=findViewById(R.id.ilikes);
        view=findViewById(R.id.iviews);
        tag=findViewById(R.id.itags);
        derr=findViewById(R.id.det_error);
        swipeRefreshLayout=findViewById(R.id.detailSwap);

        s=getIntent().getStringArrayExtra("data");
        defaultSetup();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                defaultSetup();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    public void defaultSetup(){
        if(isNetworkAvailable()) {
            derr.setVisibility(View.GONE);
            Picasso.with(this).load(s[0]).into(img1);
            like.setText(s[1]);
            view.setText(s[2]);
            tag.setText(s[3]);
        }else {
            derr.setVisibility(View.VISIBLE);
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
