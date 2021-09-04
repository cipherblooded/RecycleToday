package com.appintuitions.recycletoday.ui.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.appintuitions.recycletoday.AppController;
import com.appintuitions.recycletoday.R;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    View lt_district,lt_city,lt_state;
    EditText et_locality,et_street;
    Switch loc_switch;
    Button btn_save;
    ImageButton ib_editProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvName = v.findViewById(R.id.tv_name);
        TextView tvEmail = v.findViewById(R.id.tv_email);
        TextView tvMobileNo = v.findViewById(R.id.tv_mobileNo);
        TextView tvUid = v.findViewById(R.id.tv_uid);


        tvName.setText(AppController.user.getName());
        tvEmail.setText(AppController.user.getEmail());
        tvMobileNo.setText(AppController.user.getMobileNo());
        tvUid.setText(AppController.user.getUid());

        ib_editProfile = v.findViewById(R.id.ib_editProfile);

        ib_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_SHORT).show();
            }
        });


        Spinner sp_state,sp_city,sp_district;

        sp_state=v.findViewById(R.id.sp_state);
        sp_city=v.findViewById(R.id.sp_city);
        sp_district=v.findViewById(R.id.sp_district);

        lt_district=v.findViewById(R.id.lt_district);
        lt_city=v.findViewById(R.id.lt_city);
        lt_state=v.findViewById(R.id.lt_state);

        et_locality=v.findViewById(R.id.et_locality);
        et_street=v.findViewById(R.id.et_street);

        loc_switch=v.findViewById(R.id.switch_loc);

        btn_save=v.findViewById(R.id.btn_save);

        ArrayList<String> states=new ArrayList<>();
        states.add("Uttar Pradesh");

        ArrayList<String> city=new ArrayList<>();
        city.add("Noida");

        ArrayList<String> district=new ArrayList<>();
        district.add("Gautam Budh Nagar");

        sp_state.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item,states));
        sp_city.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item,city));
        sp_district.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_item,district));

        loc_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    lt_city.setVisibility(View.GONE);
                    lt_district.setVisibility(View.GONE);
                    lt_state.setVisibility(View.GONE);
                    et_locality.setVisibility(View.GONE);
                    et_street.setVisibility(View.GONE);
                    btn_save.setVisibility(View.GONE);
                }else {
                    lt_city.setVisibility(View.VISIBLE);
                    lt_district.setVisibility(View.VISIBLE);
                    lt_state.setVisibility(View.VISIBLE);
                    et_locality.setVisibility(View.VISIBLE);
                    et_street.setVisibility(View.VISIBLE);
                    btn_save.setVisibility(View.VISIBLE);
                }
            }
        });

        return v;
    }

}
