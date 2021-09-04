package com.appintuitions.recycletoday;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.appintuitions.recycletoday.pojo.Complaint;
import com.appintuitions.recycletoday.pojo.User;
import com.appintuitions.recycletoday.ui.activities.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppController extends Application {

    // App script loginFormLink
    public static String loginFormLink =
            "https://script.google.com/macros/s/AKfycbyuL0PVynwM_gECDDwJsNBhvP0AaZXYrrtMywpkQd-f8KEgtnq_QVjc3tiSXKusXJy9ig/exec";

    public static String requestFromLink =
            "https://script.google.com/macros/s/AKfycby9nT1plAQjD9jqaVCu0zH6itNyB8o6dWDCfrSEDB-vanu16nTPNuxIPlj27fXg_b3J/exec";

    public static ArrayList<Complaint> complaints;
    public static SharedPreferences sp;
    public static Type type = new TypeToken<ArrayList<Complaint>>() {}.getType();
    public static Gson gson;
    public static User user;

    public static void addComplaintUser(Activity context,Complaint complaint) {
        complaints.add(complaint);
        sp.edit().putString("complaints", gson.toJson(complaints)).apply();
    }

    public static void login(User user) {
        AppController.user = user;
        sp.edit().putString("user", gson.toJson(user)).apply();
    }

    public static void logout() {
        user = null;
        sp.edit().remove("user").apply();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new Gson();

        sp = getSharedPreferences("Complaints", 0);
        String json = sp.getString("complaints", null);

        user = gson.fromJson(sp.getString("user", ""), User.class);

        if (json != null)
            complaints = gson.fromJson(json, type);
        else
            complaints = new ArrayList<>();

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void registerUser(final Activity context, final String email, final String password, final String name, final String mobile, final String uid) {

        final ProgressDialog loading = ProgressDialog.show(context, "Signing Up", "Please wait");

        // Main Google App Script loginFormLink

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginFormLink, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(context, Html.fromHtml(response), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);

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
                parmas.put("action", "addItem");
                parmas.put("name", name);
                parmas.put("mobileNo", mobile);
                parmas.put("email", email);
                parmas.put("password", password);
                parmas.put("uid", uid);

                return parmas;
            }
        };

        int socketTimeOut = 50000;// u can change this .. here it is 50 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(context);

        queue.add(stringRequest);

    }


    public static void signIn(final Activity context, final String uemail, final String password) {

        final ProgressDialog loading = ProgressDialog.show(context, "Signing In", "please wait", false, true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, loginFormLink,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        ArrayList<HashMap<String, String>> hashMaps = parseItems(loading, response);

                        List<Complaint> list = new ArrayList<>();

                        for (HashMap<String, String> hashMap : hashMaps) {
                            String email = hashMap.get("email");
                            String pass = hashMap.get("password");

                            if (email == null || pass == null)
                                continue;

                            if (email.equals(uemail) && pass.equals(password)) {

                                user = new User();
                                user.setName(hashMap.get("name"));
                                user.setEmail(hashMap.get("email"));
                                user.setMobileNo(hashMap.get("mobileNo"));
                                user.setPassword(hashMap.get("password"));
                                user.setUid(hashMap.get("uid"));

                                login(user);

                                list.add(new Complaint());

                                context.startActivity(new Intent(context, MainActivity.class));
                                context.finishAffinity();

                            }
                        }

                        loading.dismiss();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 50000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);

    }

    private static ArrayList<HashMap<String, String>> parseItems(ProgressDialog loading, String jsonResposnce) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        try {
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("user");

            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);

                String itemName = jo.getString("name");
                String brand = jo.getString("email");
                String price = jo.getString("password");
                String mobile = jo.getString("mobileNo");
                String uid = jo.getString("uid");

                HashMap<String, String> item = new HashMap<>();
                item.put("name", itemName);
                item.put("email", brand);
                item.put("password", price);
                item.put("mobileNo", mobile);
                item.put("uid", uid);

                list.add(item);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void showAlertDialog(Context context, String mssg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mssg)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert11 = builder.create();
        alert11.show();
    }

}
