package com.appintuitions.recycletoday.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.adapters.ImagesAdapter;
import com.appintuitions.recycletoday.adapters.LeaderboardAdapter;
import com.appintuitions.recycletoday.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class LeaderboardFragment extends Fragment {

    List<User> users=new ArrayList<>();
    private RecyclerView rv_leaderboard;
    private LeaderboardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);

        rv_leaderboard=root.findViewById(R.id.rv_leaderboard);

        init();

        rv_leaderboard.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        adapter=new LeaderboardAdapter(getActivity(),users);

        rv_leaderboard.setAdapter(adapter);

        return root;
    }

    private void init() {
        User user=new User();

        user.setName("Deepak Kumar");

        users.add(user);

        User user2=new User();

        user2.setName("Ankit Tripathi");

        users.add(user2);

        User user3=new User();

        user3.setName("Bharti Kumari");

        users.add(user3);

        User user4=new User();

        user4.setName("Anurag Sharma");

        users.add(user4);

        User user5=new User();

        user5.setName("Ankush Bhagel");

        users.add(user5);

        User user1=new User();

        user1.setName("Aryan tripathi");

        users.add(user1);
    }
}