package com.example.iconichandcrafters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.sql.ResultSet;

public class SingleViewAssignedOrderDetails extends AppCompatActivity {
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textview8,textView9;
    Button b1,b2;
    private static  final int REQUEST_LOCATION=1;

    LocationManager locationManager;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_single_view_assigned_order_details);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);




        textView1 = findViewById(R.id.loadcustomername);
        textView2 = findViewById(R.id.loadcustomeraddress);
        textView3 = findViewById(R.id.loadcustomercontact);
        textView4 = findViewById(R.id.loadcustomerdistrict);
        textView5 = findViewById(R.id.loadcustomercity);
        textView6 = findViewById(R.id.loadcustomersubtotal);
        textView7 = findViewById(R.id.loadcustomerdeliverycost);
        textview8 = findViewById(R.id.loadcustomernettotal);
        textView9 = findViewById(R.id.hiidenuseremailz);
        b1 = findViewById(R.id.updatelocatonz);
        b2 = findViewById(R.id.completeorderz);
        try {

            String orderid_loading = DbConnection.assigned_orderid;
            String sql_que = "select * from orderz where orderidgenarated='" + orderid_loading + "'";
            ResultSet rs = DbConnection.search(sql_que);
            if (rs.next()) {
                textView1.setText(rs.getString("nameinfull"));
                textView2.setText(rs.getString("addressz"));
                textView3.setText(rs.getString("contactz"));
                textView4.setText(rs.getString("districtsz"));
                textView5.setText(rs.getString("cityz"));
                textView9.setText(rs.getString("buyeremailaddressz"));
                textView6.setText("LKR " + rs.getString("ordersubz") + " /=");
                textView7.setText("LKR " + rs.getString("orderdelcost") + " /=");
                textview8.setText("LKR " + rs.getString("ordernetcost") + " /=");
            }


            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    try {
                        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
                        {
                            //Write Function To enable gps

                            OnGPS();
                        }
                        else
                        {
                            //GPS is already On then

                            getLocation();
                        }


                        //System.out.println("out location latz"+latitude);
                        //System.out.println("out location longz"+longitude);
                        String sqlupdate = "Update orderassigntodelveryperson set curlatz='"+latitude+"',curlongz='"+longitude+"' where orderidz='"+orderid_loading+"'  ";
                        DbConnection.iud(sqlupdate);
                        Toast.makeText(SingleViewAssignedOrderDetails.this, "Location updated...", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(SingleViewAssignedOrderDetails.this, "latz---"+latitude+"--Long---"+longitude, Toast.LENGTH_LONG).show();
                    }catch (Exception ex){
                        Toast.makeText(SingleViewAssignedOrderDetails.this, "-"+ex, Toast.LENGTH_SHORT).show();
                    }



                }
            });


            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DbConnection.assigned_buyeremailz = textView9.getText().toString();
                    Intent ii = new Intent(SingleViewAssignedOrderDetails.this, SelectPaymentType.class);
                    startActivity(ii);
                }
            });


        }catch (Exception ee){
            Toast.makeText(this, "--"+ee, Toast.LENGTH_SHORT).show();
        }






    }
    private void getLocation() {

        //Check Permissions again

        if (ActivityCompat.checkSelfPermission(SingleViewAssignedOrderDetails.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SingleViewAssignedOrderDetails.this,

                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else
        {
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                // showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();

                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                //showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
            }
            else
            {
                Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
            }

            //Thats All Run Your App
        }

    }

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

}