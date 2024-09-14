package com.example.iconichandcrafters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class CompletedOrders extends AppCompatActivity {
    ListView listView;
    ArrayList<LoadAssignedOrders> arrayList;
    ArrayAdapter adp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_completed_orders);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(policy);

        listView = findViewById(R.id.listviewassignedorderz2);
        arrayList = new ArrayList<LoadAssignedOrders>();
        try {
            String loading_emailz = DbConnection.emailzloadingz;
            String sqlload = "select * from orderassigntodelveryperson where deliverypersonemailz='"+loading_emailz+"' and statuszoforderz='completed' ORDER BY idorderzassign DESC";
            ResultSet rs = DbConnection.search(sqlload);
            while (rs.next()){
                LoadAssignedOrders i = new LoadAssignedOrders();
                i.orderidload =rs.getString("orderidz");
                i.datez=rs.getString("datez");
                i.timez=rs.getString("timez");

                arrayList.add(i);
            }

            CompletedOrders.missingloadAdapter my = new CompletedOrders.missingloadAdapter(this,arrayList);
            listView.setAdapter(my);

        }catch (Exception ee){
            Toast.makeText(this, "err--"+ee, Toast.LENGTH_SHORT).show();
        }

    }

    class missingloadAdapter extends ArrayAdapter{
        Context c;
        ArrayList<LoadAssignedOrders> list;

        public missingloadAdapter(Context context ,ArrayList<LoadAssignedOrders> ar) {

            super(context, R.layout.loadassigned_orders_template,ar);
            c= context;
            list = ar;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater li =(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View v= li.inflate(R.layout.loadassigned_orders_template,null);

            final LoadAssignedOrders loadTimeTables = list.get(position);

            TextView orderid =(TextView) v.findViewById(R.id.loadindborderid);
            orderid.setText("Order ID / "+loadTimeTables.orderidload);

            TextView orderidhidden =(TextView) v.findViewById(R.id.loadingorderidz);
            orderidhidden.setText(loadTimeTables.orderidload);
            // localVari.Preg_topic_Name = loadTimeTables.topicname;


            final TextView topidid =(TextView) v.findViewById(R.id.loadindborderdate);
            topidid.setText(""+loadTimeTables.datez+"/"+loadTimeTables.timez);


            Button vieworderdetailsz =(Button) v.findViewById(R.id.viewdetailsorder);


            vieworderdetailsz.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    String ordidz = orderidhidden.getText().toString();
                    DbConnection.completed_orderid = ordidz;
                    Intent ii = new Intent(CompletedOrders.this, SingleViewCompletedOrders.class);
                    startActivity(ii);


                }
            });


            return v;
        }


    }


}