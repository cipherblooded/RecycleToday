package com.appintuitions.recycletoday.ui.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appintuitions.recycletoday.AppController;
import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.ui.activities.Complaint_Request;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.appintuitions.recycletoday.AppController.requestFromLink;
import static com.appintuitions.recycletoday.AppController.user;

import java.util.HashMap;
import java.util.Map;

public class Complaint_Status extends Fragment {
    
    EditText et_caseid;
    Button btn_check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_complaint__status, container, false);

        FloatingActionButton fab=view.findViewById(R.id.fab);
        btn_check=view.findViewById(R.id.btn_check);
        et_caseid=view.findViewById(R.id.et_caseid);
        
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cid = et_caseid.getText().toString();
                AppController.hideKeyboardFrom(getContext(),view);
                searchComplaint(cid);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Complaint_Request.class));
            }
        });

        return view;
    }

    public void searchComplaint(String cid) {

        final ProgressDialog loading = ProgressDialog.show(getContext(), "Searching", "please wait", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestFromLink, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        AppController.showAlertDialog(getContext(), Html.fromHtml(response).toString());
                    }
                },

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
                parmas.put("action", "searchComplaint");
                parmas.put("cid", cid);
                parmas.put("uid", user.getUid());

                return parmas;
            }
        };

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);

    }
}
