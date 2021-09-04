package com.appintuitions.recycletoday.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.adapters.RewardAdapter;
import com.appintuitions.recycletoday.pojo.Reward;

import java.util.ArrayList;
import java.util.List;

public class RewardsFragment extends Fragment {

    RewardAdapter adapter;
    List<Reward> rewards = new ArrayList<>();
    private RecyclerView rv_rewards;
    TextView tv_amount, tv_coupon, tv_cashback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_rewards, container, false);

        rv_rewards = root.findViewById(R.id.rv_rewards);
        tv_amount = root.findViewById(R.id.tv_tamount);
        tv_cashback = root.findViewById(R.id.tv_tcashback);
        tv_coupon = root.findViewById(R.id.tv_tcoupon);

        init();

        int amount = 0, coupon = 0;

        for (Reward reward : rewards) {
            if (reward.getType().equals("Coupon"))
                coupon++;
            else
                amount += Integer.parseInt(reward.getAmount());
        }

        tv_coupon.setText(""+coupon);
        tv_amount.setText(""+amount);
        tv_cashback.setText(""+rewards.size());

        adapter = new RewardAdapter(getActivity(), rewards);

        rv_rewards.setAdapter(adapter);

        return root;
    }

    private void init() {

        Reward reward = new Reward();

        reward.setAmount("20");
        reward.setDate("18/01/2020");
        reward.setId("" + 1);
        reward.setType("Cashback");

        rewards.add(reward);

        Reward coupon = new Reward();
        coupon.setType("Coupon");
        coupon.setId("" + 2);
        coupon.setDate("26/01/2020");
        coupon.setCoupon_description("Flat 30% OFF on Flipkart");

        rewards.add(coupon);

        Reward reward1 = new Reward();

        reward1.setAmount("10");
        reward1.setDate("18/01/2020");
        reward1.setId("" + 1);
        reward1.setType("Cashback");

        rewards.add(reward1);

        Reward coupon1 = new Reward();
        coupon1.setType("Coupon");
        coupon1.setId("" + 2);
        coupon1.setDate("24/01/2020");
        coupon1.setCoupon_description("Flat 40% OFF on Amazon");

        rewards.add(coupon1);

        Reward reward2 = new Reward();

        reward2.setAmount("30");
        reward2.setDate("18/01/2020");
        reward2.setId("" + 1);
        reward2.setType("Cashback");

        rewards.add(reward2);

        Reward reward3 = new Reward();

        reward3.setAmount("25");
        reward3.setDate("18/01/2020");
        reward3.setId("" + 1);
        reward3.setType("Cashback");

        rewards.add(reward3);

        Reward coupon2 = new Reward();
        coupon2.setType("Coupon");
        coupon2.setId("" + 2);
        coupon2.setDate("30/01/2020");
        coupon2.setCoupon_description("Flat 50% OFF on Snapdeal");

        rewards.add(coupon2);

        Reward reward4 = new Reward();

        reward4.setAmount("50");
        reward4.setDate("18/01/2020");
        reward4.setId("" + 1);
        reward4.setType("Cashback");

        rewards.add(reward4);

        Reward reward5 = new Reward();

        reward5.setAmount("10");
        reward5.setDate("18/01/2020");
        reward5.setId("" + 1);
        reward5.setType("Cashback");

        rewards.add(reward5);

        /*Reward reward6=new Reward();

        reward6.setAmount("20");
        reward6.setDate("18/01/2020");
        reward6.setId(""+1);
        reward6.setType("Cashback");

        rewards.add(reward6);

        Reward reward7=new Reward();

        reward7.setAmount("20");
        reward7.setDate("18/01/2020");
        reward7.setId(""+1);
        reward7.setType("Cashback");

        rewards.add(reward7);*/

    }
}