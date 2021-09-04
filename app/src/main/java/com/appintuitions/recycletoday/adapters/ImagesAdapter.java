package com.appintuitions.recycletoday.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.listeners.ImageClick;

import java.io.IOException;
import java.util.ArrayList;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<String> imageModelArrayList;
    Context context;
    ImageClick listener;

    public ImagesAdapter(Context ctx, ArrayList<String> imageModelArrayList, ImageClick listener) {
        context = ctx;
        inflater = LayoutInflater.from(ctx);
        this.imageModelArrayList = imageModelArrayList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ImagesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lt_add_doc, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.MyViewHolder holder, final int position) {
        if (position != imageModelArrayList.size()) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(imageModelArrayList.get(position)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            holder.iv.setImageBitmap(bitmap);
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.removeImage(position);
                }
            });
        } else if(imageModelArrayList.size()==3){
            holder.root.setVisibility(View.GONE);
        } else{
            holder.iv.setPadding(50,50,50,50);
            holder.tv.setVisibility(View.VISIBLE);
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.addNewImage();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return imageModelArrayList.size() + 1;
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

        TextView tv;
        ImageView iv;
        View root;

        MyViewHolder(View itemView) {
            super(itemView);

            root=itemView.findViewById(R.id.root);
            tv = (TextView) itemView.findViewById(R.id.btn_add);
            iv = (ImageView) itemView.findViewById(R.id.image);
        }

    }
}
