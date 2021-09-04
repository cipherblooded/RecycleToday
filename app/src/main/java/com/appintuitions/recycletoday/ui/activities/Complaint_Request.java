package com.appintuitions.recycletoday.ui.activities;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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
import com.appintuitions.recycletoday.AppController;
import com.appintuitions.recycletoday.R;
import com.appintuitions.recycletoday.adapters.ImagesAdapter;
import com.appintuitions.recycletoday.listeners.ImageClick;
import com.appintuitions.recycletoday.pojo.Complaint;
import com.google.android.material.textfield.TextInputLayout;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.appintuitions.recycletoday.AppController.user;

public class Complaint_Request extends AppCompatActivity {


    RecyclerView rv_images;

    ArrayList<String> images = new ArrayList<>();
    ImagesAdapter adapter;
    TextView tv_uid;
    EditText et_name, et_mobile, et_email, et_comment, et_address, et_caddress;
    TextInputLayout lt_name;
    Button btn_add;
    String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint__request_old);

        rv_images = findViewById(R.id.rv_images);

        tv_uid = findViewById(R.id.tv_uid);
        et_name = findViewById(R.id.et_name);
        et_mobile = findViewById(R.id.et_mobile);
        et_email = findViewById(R.id.et_email);
        et_address = findViewById(R.id.et_address);
        et_caddress = findViewById(R.id.et_caddress);
        et_comment = findViewById(R.id.et_comment);

        et_name.setText(user.getName());
        et_mobile.setText(user.getMobileNo());
        et_email.setText(user.getEmail());

        cid = UUID.randomUUID().toString();
        tv_uid.setText("Compalaint ID: " + cid);


        lt_name = findViewById(R.id.lt_name);

        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Complaint complaint = check();
                if (complaint != null) {
                    addComplaint(complaint);
                    Toast.makeText(Complaint_Request.this, "Your Complaint Was Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Complaint_Request.this, MainActivity.class));
                    finishAffinity();
                }
            }
        });

        rv_images.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new ImagesAdapter(this, images, listener);

        rv_images.setAdapter(adapter);


    }


    ImageClick listener = new ImageClick() {
        @Override
        public void addNewImage() {

            if (images.size() == 3) {
                Toast.makeText(Complaint_Request.this, "You can add up to 3 images only", Toast.LENGTH_SHORT).show();
                return;
            }

            PickSetup setup = new PickSetup()
                    .setTitle("Choose Option")
                    .setFlip(true)
                    .setMaxSize(500)
                    .setPickTypes(EPickType.GALLERY, EPickType.CAMERA)
                    .setIconGravity(Gravity.LEFT)
                    .setButtonOrientation(LinearLayout.HORIZONTAL)
                    .setSystemDialog(false);
            PickImageDialog.build(setup).setOnPickResult(new IPickResult() {
                @Override
                public void onPickResult(PickResult r) {

                    images.add(copy(r.getBitmap()));
                    adapter = new ImagesAdapter(Complaint_Request.this, images, listener);
                    rv_images.setAdapter(adapter);

                }
            }).show(Complaint_Request.this);
        }

        @Override
        public void removeImage(int pos) {
            images.remove(pos);
            adapter = new ImagesAdapter(Complaint_Request.this, images, listener);

            rv_images.setAdapter(adapter);
        }
    };

    public String copy(Bitmap bitmap) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());

        //You can change "yyyyMMdd_HHmmss as per your requirement

        String currentDateandTime = sdf.format(new Date());
        String fname = "image" + currentDateandTime + ".jpg";

        for (File externalFilesDir : Complaint_Request.this.getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS)) {
            Log.e("avail dir", externalFilesDir.getAbsolutePath());
        }
        String FolderName = "RecycleToday";
        File dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dir = new File(Complaint_Request.this.getExternalFilesDirs(Environment.DIRECTORY_DOCUMENTS)[0].getPath() + "/" + FolderName);
        } else {
            dir = new File(Environment.getExternalStorageDirectory() + "/" + FolderName);
        }

        // Make sure the path directory exists.
        if (!dir.exists()) {
            // Make it, if it doesn't exit
            boolean success = dir.mkdirs();
            if (!success) {
                dir = null;
            }
        }


        File file = new File(dir, fname);

        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return String.valueOf(Uri.fromFile(file));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream .toByteArray();
        return Base64.encodeToString(byteArray,Base64.DEFAULT);
    }

    private Complaint check() {

        String name = et_name.getText().toString();
        String mobile = et_mobile.getText().toString();
        String email = et_email.getText().toString();
        String comment = et_comment.getText().toString();
        String address = et_address.getText().toString();
        String caddress = et_caddress.getText().toString();

        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);

        et_name.setError(null);
        et_mobile.setError(null);
        et_email.setError(null);
        et_address.setError(null);
        et_caddress.setError(null);

        if (name.equals("")) {
            et_name.setError("Required Field");
            et_name.requestFocus();
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (mobile.equals("")) {
            et_mobile.setError("Required Field");
            et_mobile.requestFocus();
            Toast.makeText(this, "Enter Mobile No.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (!PhoneNumberUtils.isGlobalPhoneNumber(mobile)) {
            et_mobile.setError("Enter Valid Mobile No.");
            et_mobile.requestFocus();
            Toast.makeText(this, "Enter Valid Mobile No.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (email.equals("")) {
            et_email.setError("Required Field");
            et_email.requestFocus();
            Toast.makeText(this, "Enter Email Address", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (!matcher.find()) {
            et_email.setError("Enter Valid Email");
            et_email.requestFocus();
            Toast.makeText(this, "Enter Valid Email ID", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (address.equals("")) {
            et_address.setError("Required Field");
            et_address.requestFocus();
            Toast.makeText(this, "Enter Your Address", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (caddress.equals("")) {
            et_caddress.setError("Required Field");
            et_caddress.requestFocus();
            Toast.makeText(this, "Enter Complaint Address", Toast.LENGTH_SHORT).show();
            return null;
        }

        if (images.size() == 0) {
            Toast.makeText(this, "Please Add Images", Toast.LENGTH_SHORT).show();
            return null;
        }

        Complaint complaint = new Complaint();

        complaint.setCid(cid);
        complaint.setName(name);
        complaint.setMobile(mobile);
        complaint.setEmail(email);
        complaint.setAddress(address);
        complaint.setCaddress(caddress);
        complaint.setComment(comment);
        complaint.setImages(images);

        return complaint;
    }


    public void addComplaint(Complaint complaint) {

        final ProgressDialog loading = ProgressDialog.show(this, "Uploading", "Please wait");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                AppController.requestFromLink,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        AppController.addComplaintUser(Complaint_Request.this,complaint);
                        try {
                            loading.dismiss();
                        }catch (Exception ignored){

                        }
                        Toast.makeText(Complaint_Request.this, Html.fromHtml(response), Toast.LENGTH_LONG).show();
                        Log.e("response add complaint",Html.fromHtml(response).toString());
                        Intent intent = new Intent(Complaint_Request.this, MainActivity.class);
                        startActivity(intent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();

                //here we pass params
                parmas.put("action", "addEntry");
                parmas.put("cid", complaint.getCid());
                parmas.put("uid", user.getUid());
                parmas.put("name", user.getName());
                parmas.put("mobileNo", user.getMobileNo());
                parmas.put("email", user.getEmail());
                parmas.put("address", complaint.getAddress());
                parmas.put("complaintAddress", complaint.getCaddress());
                parmas.put("comment", complaint.getComment());

                ArrayList<String> images = complaint.getImages();
                for (int i = images.size();i<=3;i++){
                    images.add("Not Available");
                }

                parmas.put("image1", images.get(0));
                parmas.put("image2", images.get(1));
                parmas.put("image3", images.get(2));

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);

        queue.add(stringRequest);

    }
}
