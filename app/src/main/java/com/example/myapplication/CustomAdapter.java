package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FieldValue;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Context context;
        ArrayList<MessageModel> list;
        MessageModel messageModele;
        public static final int MESSAGE_TYPE_IN  = 1;
        public static final int MESSAGE_TYPE_OUT = 2;
        public static final int MESSAGE_TYPE_JAIME = 3;

        /*public static final int MESSAGE_TYPE_SEND_JAIME = 3;
        public static final int MESSAGE_TYPE_SEND = 4;*/

        public CustomAdapter(Context context, ArrayList<MessageModel> list) { // you can pass other parameters in constructor
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

        private class MessageInViewHolder extends RecyclerView.ViewHolder {

            TextView messageTV,dateTV;
            MessageInViewHolder(final View itemView) {
                super(itemView);
                messageTV = itemView.findViewById(R.id.message_text);
                dateTV = itemView.findViewById(R.id.date_text);

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
                MessageModel messageModel = list.get(position);
                messageTV.setText(messageModel.message);




// Get the class name of the timestampFieldValue object


// Convert the timestamp value to a string with the pattern "yyyy-MM-dd HH:mm:ss"
                Date date = messageModel.messageTime.toDate();

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

// Format the date to display the hour
                String hour = formatter.format(date);

               dateTV.setText(hour);
            }
        }

        /** ************************************ **/

        private class MessageOutViewHolder extends RecyclerView.ViewHolder {

            TextView messageTV,dateTV;
            MessageOutViewHolder(final View itemView) {
                super(itemView);
                messageTV = itemView.findViewById(R.id.message_text_send);
                dateTV = itemView.findViewById(R.id.date_text_send);

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
                MessageModel messageModel = list.get(position);
                messageTV.setText(messageModel.message);




// Get the class name of the timestampFieldValue object


// Convert the timestamp value to a string with the pattern "yyyy-MM-dd HH:mm:ss"
                Date date = messageModel.messageTime.toDate();

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

// Format the date to display the hour
                String hour = formatter.format(date);

                dateTV.setText(hour);
            }
        }

   /* private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV_send,dateTV_send;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV_send = itemView.findViewById(R.id.message_text_send);
            dateTV_send = itemView.findViewById(R.id.date_text_send);
        }
        void bind(int position) {
            MessageModel messageModel = list.get(position);
            messageTV_send.setText(messageModel.message);
            dateTV_send.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(messageModel.messageTime));
        }
    }*/

        private class MessageLikeViewHolder extends RecyclerView.ViewHolder {

            TextView messageTV,dateTV;
            RelativeLayout relativeLayout;
            MessageLikeViewHolder(final View itemView) {
                super(itemView);
                relativeLayout = itemView.findViewById(R.id.message_layout1);

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
                MessageModel messageModel = list.get(position);

            }
        }

   private class MessageOutLikeViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        RelativeLayout relativeLayout;
        MessageOutLikeViewHolder(final View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.message_layout1_send);

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
            MessageModel messageModel = list.get(position);

        }
    }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == MESSAGE_TYPE_IN) {
                return new MessageInViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.datashow, parent, false));
            }if (viewType == MESSAGE_TYPE_JAIME){
                return new MessageOutLikeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.send_jaime, parent, false));
            }if (viewType == MESSAGE_TYPE_OUT) {
                return new MessageLikeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.jaime, parent, false));
            }
            return new MessageOutViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send, parent, false));

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            MessageModel item = list.get(position);
            if (list.get(position).messageType == MESSAGE_TYPE_IN) {
                ((MessageInViewHolder) holder).bind(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onItemClick(position);
                        }
                    }
                });

            }else if(list.get(position).messageType == MESSAGE_TYPE_JAIME){
                ((MessageOutLikeViewHolder) holder).bind(position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onItemClick(position);
                        }
                    }
                });

            }

            else if (list.get(position).messageType == MESSAGE_TYPE_OUT){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onItemClick(position);
                        }
                    }
                });
                ((MessageLikeViewHolder) holder).bind(position);

            }else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mListener != null) {
                            mListener.onItemClick(position);
                        }
                    }
                });
                ((MessageOutViewHolder) holder).bind(position);
            }



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return list.get(position).messageType;
        }
    }


