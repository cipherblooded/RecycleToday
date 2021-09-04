package com.appintuitions.recycletoday.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.adapters.ComplaintHistoryAdapter;
import com.appintuitions.recycletoday.pojo.Complaint;

import static com.appintuitions.recycletoday.AppController.complaints;
import static com.appintuitions.recycletoday.AppController.requestFromLink;
import static com.appintuitions.recycletoday.AppController.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Complaint_History extends Fragment {

    private RecyclerView rv_history;
    private ComplaintHistoryAdapter adapter;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_complaint__history, container, false);

        context = getContext();
        rv_history = view.findViewById(R.id.rv_history);
        rv_history.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_history.setAdapter(adapter);

        complaintHistory();

        return view;
    }

    private void complaintHistory() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestFromLink, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                ArrayList<HashMap<String, String>> hashMaps = parseItems(response);

                ArrayList<Complaint> list = new ArrayList<>();

                for (HashMap<String, String> hashMap : hashMaps) {

                    Complaint complaint = new Complaint();

                    complaint.setCid(hashMap.get("cid"));
                    complaint.setUid(hashMap.get("uid"));
                    complaint.setName(hashMap.get("name"));
                    complaint.setMobile(hashMap.get("mobileNo"));
                    complaint.setEmail(hashMap.get("email"));
                    complaint.setAddress(hashMap.get("address"));
                    complaint.setCaddress(hashMap.get("complaintAddress"));
                    complaint.setComment(hashMap.get("comment"));
                    complaint.setStatus(hashMap.get("status"));

                    ArrayList<String> imgList = new ArrayList<>();
                    imgList.add(hashMap.get("image1"));
                    imgList.add(hashMap.get("image2"));
                    imgList.add(hashMap.get("image3"));

                    complaint.setImages(imgList);

                    list.add(complaint);

                }
                Log.e("Bhadwa", "onResponse: "+list+"\n"+response);
                adapter = new ComplaintHistoryAdapter(getActivity(), list);
                rv_history.setAdapter(adapter);
            }},
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "getAllEntries");
                parmas.put("uid", user.getUid());

                return parmas;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

    }

    private ArrayList<HashMap<String, String>> parseItems(String jsonResponse) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("user");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jo = jsonArray.getJSONObject(i);

                String cid = jo.getString("cid");
                String uid = jo.getString("uid");
                String name = jo.getString("name");
                String mobileNo = jo.getString("mobileNo");
                String email = jo.getString("email");
                String address = jo.getString("address");
                String complaintAddress = jo.getString("complaintAddress");
                String comment = jo.getString("comment");
                String status = jo.getString("status");
                String image1 = jo.getString("image1");
                String image2 = jo.getString("image2");
                String image3 = jo.getString("image3");

                HashMap<String, String> item = new HashMap<>();

                item.put("cid", cid);
                item.put("uid", uid);
                item.put("name", name);
                item.put("mobileNo", mobileNo);
                item.put("email", email);
                item.put("address", address);
                item.put("complaintAddress", complaintAddress);
                item.put("comment", comment);
                item.put("status", status);
                item.put("image1", image1);
                item.put("image2", image2);
                item.put("image3", image3);

                list.add(item);

            }
        } catch (JSONException e) {
            Log.e("erro in parse",e.getLocalizedMessage());
        }
        return list;
    }

}
