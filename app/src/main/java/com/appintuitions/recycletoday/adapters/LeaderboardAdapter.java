package com.appintuitions.recycletoday.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<User> users;
    Context context;

    public LeaderboardAdapter(Context ctx, List<User> imageModelArrayList) {
        context = ctx;
        inflater = LayoutInflater.from(ctx);
        this.users = imageModelArrayList;
    }

    @NonNull
    @Override
    public LeaderboardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.leaderboard_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderboardAdapter.MyViewHolder holder, final int position) {
        User user=users.get(position);
        holder.tv_name.setText(user.getName());
        holder.tv_email.setText(user.getMobileNo());
        holder.tv_rank.setText(""+(position+1));
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

        TextView tv_name;
        TextView tv_email;
        TextView tv_rank;
        ImageView iv_dp;
        View root;

        MyViewHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_rank = itemView.findViewById(R.id.tv_rank);
            iv_dp = itemView.findViewById(R.id.iv_dp);
        }

    }
}
