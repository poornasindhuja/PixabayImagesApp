package com.example.sindhu.pixabayimagesapp;

import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ImagesActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<String>{

    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<ItemModel> itemModels;
    private static int loaderId=2;
    Configuration configuration;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView errorMsg;
    String q;
    String mainUrl="https://pixabay.com/api/?key=10850875-46e46d658c3d412f355918423&q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        q=getIntent().getStringExtra("name");
        progressBar=findViewById(R.id.progress);
        recyclerView=findViewById(R.id.img_recycler);
        swipeRefreshLayout=findViewById(R.id.swipe);
        errorMsg=findViewById(R.id.emsg);
        itemModels=new ArrayList<>();
        configuration=getResources().getConfiguration();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                defaultSetup();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        defaultSetup();
    }
    public  void defaultSetup(){
        Bundle bundle=new Bundle();
        bundle.putString("url",mainUrl+q);
        if(isNetworkAvailable()){
            errorMsg.setVisibility(View.GONE);
            getLoaderManager().initLoader(loaderId,bundle,this);
        }else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            errorMsg.setVisibility(View.VISIBLE);
        }

        if(configuration.orientation==Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,1));
        }else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,1));
        }
        recyclerView.setAdapter(new ImageAdapter(itemModels,this));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public android.content.Loader<String> onCreateLoader(int id, final Bundle args) {
        return new android.content.AsyncTaskLoader<String>(this) {
            @Override
            public String loadInBackground() {
                try {
                    URL url=new URL(args.getString("url"));
                    HttpURLConnection httpURLConnection=
                            (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream=httpURLConnection.getInputStream();
                    Scanner scanner=new Scanner(inputStream);
                    scanner.useDelimiter("\\A");
                    if(scanner.hasNext()){
                        return scanner.next();
                    }else {
                        return null;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(android.content.Loader<String> loader, String data) {
        progressBar.setVisibility(View.GONE);
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray hits=jsonObject.getJSONArray("hits");
            for (int pos=0;pos<hits.length();pos++){
                JSONObject obj=hits.getJSONObject(pos);
                String likes=obj.optString("likes");
                String views=obj.optString("views");
                String tags=obj.optString("tags");
                String mainImg=obj.optString("webformatURL");
                itemModels.add(new ItemModel(likes,views,tags,mainImg));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<String> loader) {

    }
}
