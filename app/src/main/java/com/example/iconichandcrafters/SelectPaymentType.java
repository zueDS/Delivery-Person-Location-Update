package com.example.iconichandcrafters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class SelectPaymentType extends AppCompatActivity {

    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_payment_type);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

    b1 = findViewById(R.id.paybycash);
    //b2 = findViewById(R.id.paybycoupoun);
    b1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

         try {
             String statuz = "completed";
             String orderz_id = DbConnection.assigned_orderid;

             String sql1 = "update orderz set statusz='"+statuz+"' where orderidgenarated='"+orderz_id+"'";
             String sql2 = "update orderassigntodelveryperson set statuszoforderz='"+statuz+"' where orderidz='"+orderz_id+"'";
             DbConnection.iud(sql1);
             DbConnection.iud(sql2);
             Toast.makeText(SelectPaymentType.this, "Order Completed Sucessfully..!", Toast.LENGTH_LONG).show();
             Intent ii = new Intent(SelectPaymentType.this, Homepage.class);
             startActivity(ii);

         }catch (Exception gg){
             Toast.makeText(SelectPaymentType.this, ""+gg, Toast.LENGTH_LONG).show();
         }


        }
    });
//    b2.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent ii = new Intent(SelectPaymentType.this, Customercoupons.class);
//            startActivity(ii);
//        }
//    });


    }
}