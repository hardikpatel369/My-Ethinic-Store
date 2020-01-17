package com.example.myethinicstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myethinicstore.Model.Users;
import com.example.myethinicstore.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private EditText etPhoneNumber, etPassword;
    private CheckBox cbRememberMe;
    private TextView tvForgetPassword, tvAdminPanelLink, tvNotAdminPanelLink;
    private Button btnLogin;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ivLogo = findViewById(R.id.ivLogo);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etPassword = findViewById(R.id.etPassword);
        tvForgetPassword = findViewById(R.id.tvForgetPassword);
        tvAdminPanelLink = findViewById(R.id.tvAdminPanelLink);
        tvNotAdminPanelLink = findViewById(R.id.tvNotAdminPanelLink);
        btnLogin = findViewById(R.id.btnLogin);
        loadingBar = new ProgressDialog(this);

        cbRememberMe = findViewById(R.id.cbRememberMe);
        Paper.init(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                LoginUser();
            }
        });

        tvAdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setText("Login Admin");
                tvAdminPanelLink.setVisibility(View.INVISIBLE);
                tvNotAdminPanelLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        tvNotAdminPanelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLogin.setText("Login");
                tvAdminPanelLink.setVisibility(View.VISIBLE);
                tvNotAdminPanelLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void LoginUser() {

        String phone = etPhoneNumber.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your Phone Number... ", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your Password... ", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credential.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);
        }
    }

    private void AllowAccessToAccount(final String phone, final String password) {

        if(cbRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserPhoneKey,phone);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child(parentDbName).child(phone).exists()){

                    Users userData = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(userData.getPhone().equals(phone))
                    {
                        if(userData.getPassword().equals(password))
                        {
                            if(parentDbName.equals("Admins"))
                            {
                                Toast.makeText(LoginActivity.this, "Welcome Admin, you are Logged in Succesfully...", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                startActivity(intent);
                            }
                            else if(parentDbName.equals("Users"))
                            {
                                Toast.makeText(LoginActivity.this, "Logged in Succesfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                Prevalent.currentOnlineUser = userData;
                                startActivity(intent);
                            }
                        }
                        else 
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {

                    Toast.makeText(LoginActivity.this, "Account with this "+phone+" phone number do not exist.", Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
