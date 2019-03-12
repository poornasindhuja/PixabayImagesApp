package com.example.sindhu.pixabayimagesapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    EditText imgName;
    Button search;
    TextView err;
    String i_name,msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgName=findViewById(R.id.img_name);
        search=findViewById(R.id.search_img);
        err=findViewById(R.id.error);
        search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i_name = imgName.getText().toString().trim();
                    if (i_name.equals("")) {
                        err.setVisibility(View.VISIBLE);
                        err.setText(R.string.error_msg);
                    } else {
                        Intent i = new Intent(getApplicationContext(), ImagesActivity.class);
                        i.putExtra("name", imgName.getText().toString());
                        startActivity(i);
                    }
                }
            });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        imgName.setText("");
    }
}
