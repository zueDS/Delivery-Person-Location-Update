package com.example.iconichandcrafters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

import java.sql.ResultSet;

public class SingleViewCompletedOrders extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textview8,textView9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_view_completed_orders);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);



        textView1 = findViewById(R.id.loadcustomername2);
        textView2 = findViewById(R.id.loadcustomeraddress2);
        textView3 = findViewById(R.id.loadcustomercontact2);
        textView4 = findViewById(R.id.loadcustomerdistrict2);
        textView5 = findViewById(R.id.loadcustomercity2);
        textView6 = findViewById(R.id.loadcustomersubtotal2);
        textView7 = findViewById(R.id.loadcustomerdeliverycost2);
        textview8 = findViewById(R.id.loadcustomernettotal2);


        try {

            String orderid_loading = DbConnection.completed_orderid;
            String sql_que = "select * from orderz where orderidgenarated='" + orderid_loading + "'";
            ResultSet rs = DbConnection.search(sql_que);
            if (rs.next()) {
                textView1.setText(rs.getString("nameinfull"));
                textView2.setText(rs.getString("addressz"));
                textView3.setText(rs.getString("contactz"));
                textView4.setText(rs.getString("districtsz"));
                textView5.setText(rs.getString("cityz"));
                textView6.setText("LKR " + rs.getString("ordersubz") + " /=");
                textView7.setText("LKR " + rs.getString("orderdelcost") + " /=");
                textview8.setText("LKR " + rs.getString("ordernetcost") + " /=");
            }
        }catch (Exception ez){
            Toast.makeText(this, "--"+ez, Toast.LENGTH_SHORT).show();
        }
    }
}