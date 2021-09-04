package com.appintuitions.recycletoday.adapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.pojo.Reward;

import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Reward> users;
    Context context;

    public RewardAdapter(Context ctx, List<Reward> imageModelArrayList) {
        context = ctx;
        inflater = LayoutInflater.from(ctx);
        this.users = imageModelArrayList;
    }

    @NonNull
    @Override
    public RewardAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.lt_reward_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardAdapter.MyViewHolder holder, final int position) {
        Reward reward = users.get(position);

        if (reward.getType().equals("Coupon")) {
            holder.iv_logo.setImageResource(R.drawable.ic_coupon);
            holder.tv_won.setText("Coupon Won");
            holder.tv_offer.setText(reward.getCoupon_description());
            holder.tv_date.setText("Expires " + reward.getDate());
            holder.tv_offer.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        } else {
            holder.iv_logo.setImageResource(R.drawable.ic_rupee);
            holder.tv_won.setText("Cashback Won");
            holder.tv_offer.setText("\u20B9" + reward.getAmount());
            holder.tv_date.setText(reward.getDate());
        }
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

        TextView tv_won;
        TextView tv_offer;
        TextView tv_date;
        ImageView iv_logo;
        View root;

        MyViewHolder(View itemView) {
            super(itemView);

            root = itemView.findViewById(R.id.root);
            tv_won = (TextView) itemView.findViewById(R.id.tv_won);
            tv_offer = (TextView) itemView.findViewById(R.id.tv_offer);
            tv_date = itemView.findViewById(R.id.tv_date);
            iv_logo = itemView.findViewById(R.id.iv_logo);
        }

    }
}
