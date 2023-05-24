package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MySearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Search_info> list;
    MessageModel messageModele;

    private static MediaPlayer mediaPlayer;

    public static void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


        /*public static final int MESSAGE_TYPE_SEND_JAIME = 3;
        public static final int MESSAGE_TYPE_SEND = 4;*/

    public MySearchAdapter(ArrayList<Search_info> list, Context context) { // you can pass other parameters in constructor
        this.context = context;
        this.list = list;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener mListener;

    /** OnLongClickListenner  **/

    private OnItemLongClickListener listener;

    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.listener = listener;
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV,fullname;
        ImageView imageView;
        MessageViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.text1);
            fullname = itemView.findViewById(R.id.text2);
            dateTV = itemView.findViewById(R.id.text3);
            imageView = itemView.findViewById(R.id.img);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemLongClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        void bind(int position) {
            Search_info messageModel = list.get(position);
            messageTV.setText(messageModel.getMessage());
            fullname.setText(messageModel.getFullname());





// Get the class name of the timestampFieldValue object


// Convert the timestamp value to a string with the pattern "yyyy-MM-dd HH:mm:ss"
            if (messageModel.getDate().toDate()!=null) {
                Date date = messageModel.getDate().toDate();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

// Format the date to display the hour
                String hour = formatter.format(date);

                dateTV.setText(hour);
                Glide.with(MyAdapterNew.context)
                        .load(list.get(position).photourl)
                        .into(imageView);
            }else {
                dateTV.setText("null");
                Glide.with(MyAdapterNew.context)
                        .load(list.get(position).photourl)
                        .into(imageView);
            }

            if (list.get(position).vue.equals("false")){
                dateTV.setTypeface(null,Typeface.BOLD);
                messageTV.setTypeface(null,Typeface.BOLD);
                fullname.setTypeface(null,Typeface.BOLD);
                Glide.with(MyAdapterNew.context)
                        .load(list.get(position).photourl)
                        .into(imageView);


            }else {
                dateTV.setTypeface(null,Typeface.NORMAL);
                messageTV.setTypeface(null,Typeface.NORMAL);
                fullname.setTypeface(null,Typeface.NORMAL);
                Glide.with(MyAdapterNew.context)
                        .load(list.get(position).photourl)
                        .into(imageView);
            }



        }
    }

    /** ************************************ **/



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_1, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Search_info item = list.get(position);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(position);
                    }
                }
            });
            ((MessageViewHolder) holder).bind(position);
        }





    @Override
    public int getItemCount() {
        return list.size();
    }


}


