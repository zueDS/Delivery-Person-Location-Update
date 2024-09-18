package com.example.iconichandcrafters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        EditText un,pw;
        Button btnz;
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_page);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        un = findViewById(R.id.logunamez);
        pw = findViewById(R.id.logpwz);

        btnz = findViewById(R.id.loahinbtn);


        btnz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = un.getText().toString();
                String passwordz = pw.getText().toString();
                System.out.println("uname entered=="+uname);
                System.out.println("pw entered=="+passwordz);
                if(uname.equals("") || passwordz.equals("") ){
                    Toast.makeText(LoginPage.this, "Please fill all feilds...!", Toast.LENGTH_LONG).show();
                }else{

                    try {

                        String sql = "select * from deliveryperson where emailz='"+uname+"' and passwordz='"+passwordz+"'";
                        ResultSet rs = DbConnection.search(sql);
                        if(rs.next()){
                            DbConnection.usernameloadingz = rs.getString("nameinfull");
                            DbConnection.emailzloadingz = uname;
                            Toast.makeText(LoginPage.this, "Login Sucess", Toast.LENGTH_SHORT).show();
                            Intent ii = new Intent(LoginPage.this, Homepage.class);
                            startActivity(ii);
                        }else{
                            Toast.makeText(LoginPage.this, "Invalid Username and Passwords", Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception ee){
                        Toast.makeText(LoginPage.this, "errz--"+ee, Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        



    }
}