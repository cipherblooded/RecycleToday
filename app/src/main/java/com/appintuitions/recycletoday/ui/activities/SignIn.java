package com.appintuitions.recycletoday.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appintuitions.recycletoday.AppController;
import com.appintuitions.recycletoday.R;

public class SignIn extends AppCompatActivity {

    String email,password;
    Button bt_signUpTemp, bt_logIn;
    EditText et_logInEmail, et_logInPassword;
    Vibrator vibrator;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(AppController.user!=null){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bt_signUpTemp=findViewById(R.id.signup_temp);
        et_logInEmail=findViewById(R.id.login_email);
        et_logInPassword=findViewById(R.id.login_password);
        bt_logIn=findViewById(R.id.login);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        bt_signUpTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(28);

                Intent intent=new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        bt_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(28);

                email = et_logInEmail.getText().toString().trim();
                password = et_logInPassword.getText().toString().trim();

                if(!(email.isEmpty()||password.isEmpty())){
                    AppController.signIn(SignIn.this,email,password);
                }
                else {
                    if(email.isEmpty()){
                        AppController.showAlertDialog(SignIn.this,"Please Enter Your Registered Email");
                    }else {
                        AppController.showAlertDialog(SignIn.this,"Please Enter Your Password");
                    }
                }
            }
        });

    }
}