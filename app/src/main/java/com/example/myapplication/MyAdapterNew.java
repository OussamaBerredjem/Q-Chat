package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyAdapterNew extends RecyclerView.Adapter<MyAdapterNew.MyViewHolder> {

    private ArrayList<String> mData;
    public static Context context;
    int position;

    @Override
    public void onViewRecycled(MyViewHolder holder) {
        super.onViewRecycled(holder);

        // Reset the state of the view
        holder.resetState();
    }


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    private OnItemClickListener mListener;

    public MyAdapterNew(ArrayList<String> data, Context context)
    {
        this.context = context;
        mData = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_2, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String photourl = mData.get(position);
        holder.bind(photourl);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

       private ImageView photo;



        public MyViewHolder(View itemView) {
            super(itemView);

            position = mData.size()-1;

            photo = itemView.findViewById(R.id.pro);

            itemView.setOnClickListener((View.OnClickListener) this);
        }



        public void bind(String photourl) {

           Glide.with(MyAdapterNew.context)
                    .load(photourl)
                    .into(photo);

        }

        public void resetState() {
            photo = null;
            // Clear any references to the previous item that was bound to the view

        }




        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                mListener.onItemClick(position);
            }
        }
    }
}

