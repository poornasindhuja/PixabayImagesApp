package com.example.sindhu.pixabayimagesapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {
    ArrayList<ItemModel> models;
    Context context;

    public ImageAdapter(ArrayList<ItemModel> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.each_item_design,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyViewHolder holder, int position) {
        final ItemModel model=models.get(position);
        holder.like.setText(model.getLikes());
        Picasso.with(context).load(model.getMainImg()).into(holder.img);
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                String s1[]=new String[4];
                s1[0]=model.getMainImg();
                s1[1]=model.getLikes();
                s1[2]=model.getViews();
                s1[3]=model.getTags();
                intent.putExtra("data",s1);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models==null?0:models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView like;
        public MyViewHolder(View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.preview);
            like=itemView.findViewById(R.id.likes_cnt);
        }
    }
}
