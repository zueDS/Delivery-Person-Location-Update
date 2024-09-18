package com.example.iconichandcrafters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

public class Homepage extends AppCompatActivity {
    TextView tv1,tv2;
    CardView assignedorderz;
    CardView takeoverorderz;
    Button logoutz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_homepage);

        try {
            String getunamez = DbConnection.usernameloadingz;
            tv1 = findViewById(R.id.textuname);
            tv1.setText(""+getunamez);
            tv2 = findViewById(R.id.tt1);
            logoutz = findViewById(R.id.logoutdetailz);
            try {
                String loading_emailz = DbConnection.emailzloadingz;
                String sqlload = "select * from orderassigntodelveryperson where deliverypersonemailz='"+loading_emailz+"' and statuszoforderz='Assigned' ORDER BY idorderzassign DESC";
                ResultSet rs = DbConnection.search(sqlload);
                if (rs.next()){
                    tv2.setText(""+"You have assigned new orders check it out");
                }else{
                    tv2.setText(""+"No new assigned orders");
                }
            }catch (Exception ee){
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
            }
            logoutz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ii = new Intent(Homepage.this, LoginPage.class);
                    startActivity(ii);
                }
            });


            assignedorderz = findViewById(R.id.assignedorders);
            assignedorderz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(Homepage.this, AssignedOrders.class);
                    startActivity(ii);
                }
            });
            takeoverorderz =findViewById(R.id.takeoverorderz);
            takeoverorderz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ii = new Intent(Homepage.this, CompletedOrders.class);
                    startActivity(ii);
                }
            });
        }catch (Exception ee){
            Toast.makeText(this, "error"+ee, Toast.LENGTH_SHORT).show();

        }





    }
}