package com.example.iconichandcrafters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Customercoupons extends AppCompatActivity {
    ListView listView;
    ArrayList<LoadCustomerCoupons> arrayList;
    ArrayAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_customercoupons);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        listView = findViewById(R.id.listviewusercouponz);
        arrayList = new ArrayList<LoadCustomerCoupons>();
        try {
            String loading_emailz = DbConnection.assigned_buyeremailz;
            String sqlload = "select * from userhascoupoun where gotusernamez='"+loading_emailz+"' and statusz='Notused' ORDER BY id DESC";
            ResultSet rs = DbConnection.search(sqlload);
            while (rs.next()){
                LoadCustomerCoupons i = new LoadCustomerCoupons();
                i.copid =rs.getString("coupounidgenarated");
                i.coupammount=rs.getString("amountzcoupon");

                arrayList.add(i);
            }

            Customercoupons.missingloadAdapterz my = new Customercoupons.missingloadAdapterz(this,arrayList);
            listView.setAdapter(my);

        }catch (Exception ee){
            Toast.makeText(this, "err--"+ee, Toast.LENGTH_SHORT).show();
        }





    }
    class missingloadAdapterz extends ArrayAdapter{
        Context c;
        ArrayList<LoadCustomerCoupons> list;

        public missingloadAdapterz(Context context ,ArrayList<LoadCustomerCoupons> ar) {

            super(context, R.layout.loadusercoupons_template,ar);
            c= context;
            list = ar;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater li =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v= li.inflate(R.layout.loadusercoupons_template,null);

            final LoadCustomerCoupons loadTimeTables = list.get(position);

            TextView couponid =(TextView) v.findViewById(R.id.loadincouponid);
            couponid.setText("Order ID / "+loadTimeTables.copid);

            TextView couponidhidden =(TextView) v.findViewById(R.id.loadingcouponidhidden);
            couponidhidden.setText(loadTimeTables.copid);



            final TextView couponammount =(TextView) v.findViewById(R.id.loadincouponamountz);
            couponammount.setText("LKR "+loadTimeTables.coupammount +" /=");


            Button vieworderdetailsz =(Button) v.findViewById(R.id.viewdetailscoupon);


            vieworderdetailsz.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {


                    String coupidz = couponidhidden.getText().toString();
                    try {
                        String statuz = "completed";
                        String statuzofcoup = "Used";
                        String orderz_id = DbConnection.assigned_orderid;

                        String sql1 = "update orderz set statusz='"+statuz+"' where orderidgenarated='"+orderz_id+"'";
                        String sql2 = "update orderassigntodelveryperson set statuszoforderz='"+statuz+"' where orderidz='"+orderz_id+"'";
                        String sql3 = "update userhascoupoun set statusz='"+statuzofcoup+"' where coupounidgenarated='"+coupidz+"'";
                        DbConnection.iud(sql1);
                        DbConnection.iud(sql2);
                        DbConnection.iud(sql3);
                        Toast.makeText(Customercoupons.this, "Order Completed Sucessfully..!", Toast.LENGTH_LONG).show();
                        Intent ii = new Intent(Customercoupons.this, Homepage.class);
                        startActivity(ii);

                    }catch (Exception gg){
                        Toast.makeText(Customercoupons.this, ""+gg, Toast.LENGTH_LONG).show();
                    }


                    Intent ii = new Intent(Customercoupons.this, Homepage.class);
                    startActivity(ii);


                }
            });


            return v;
        }


    }
}