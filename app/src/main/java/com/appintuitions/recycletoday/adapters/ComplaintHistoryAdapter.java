package com.appintuitions.recycletoday.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.pojo.Complaint;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ComplaintHistoryAdapter extends RecyclerView.Adapter<ComplaintHistoryAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Complaint> users;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());

    public ComplaintHistoryAdapter(Context ctx, ArrayList<Complaint> imageModelArrayList) {

        context = ctx;
        inflater = LayoutInflater.from(ctx);
        this.users = imageModelArrayList;

    }

    @NonNull
    @Override
    public ComplaintHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.complaint_history_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintHistoryAdapter.MyViewHolder holder, final int position) {

        Complaint complaint = users.get(position);

//        Date date = new Date();
//        date.setTime(complaint.getDate());
//        holder.tv_name.setText(sdf.format(date));

        holder.tv_cid.setText("CID : "+complaint.getCid());
        holder.tv_comment.setText("Comment : "+complaint.getComment());
        holder.tv_status.setText("Status : "+complaint.getStatus());

        if(complaint.getImages()!=null && complaint.getImages().size()>0 && !complaint.getImages().get(0).equalsIgnoreCase("Not Available")) {
            Glide.with(context).load(complaint.getImages().get(0)).into(holder.iv_image);
        }
        holder.tv_position.setText(String.valueOf(position + 1));

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View root;
        TextView tv_cid;
        TextView tv_comment;
        TextView tv_status;
        TextView tv_position;
        ImageView iv_image;

        MyViewHolder(View itemView) {

            super(itemView);
            root = itemView.findViewById(R.id.root);
            tv_cid = itemView.findViewById(R.id.tv_cid);
            tv_comment = itemView.findViewById(R.id.tv_comment);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_position = itemView.findViewById(R.id.tv_position);
            iv_image = itemView.findViewById(R.id.iv_image);
        }

    }
}
