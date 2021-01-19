package com.shubham.odsadmin.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shubham.odsadmin.R;
import com.shubham.odsadmin.adapter.BookingAdapter;
import com.shubham.odsadmin.adapter.UserAdapter;
import com.shubham.odsadmin.model.bookingList;
import com.shubham.odsadmin.model.userList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookingActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private ListView BookingList;
    private ArrayList<bookingList> booking = new ArrayList<bookingList>();
    private TextView nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        BookingList = (ListView) findViewById(R.id.booking_list);
        nothing = (TextView) findViewById(R.id.booking_nothing_tv);
        progressDialog = new ProgressDialog(BookingActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        updateList();
    }

    private void updateList() {
        booking.clear();

        db.collection("bookings").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String user = document.getString("User");
                                String worker = document.getString("Worker");
                                String carType = document.getString("Car Type");
                                String wash = document.getString("Wash Price");
                                String clean = document.getString("Clean Price");
                                String vacuum = document.getString("Vacuum Price");
                                String price = document.getString("Total Price");
                                String date = document.getString("Date");
                                String time = document.getString("Time");
                                String status = document.getString("Status");

                                String Service = "";
                                if (!wash.equals("0"))
                                    Service = Service + "Wash ";
                                if (!vacuum.equals("0"))
                                    Service = Service + "Vacuum ";
                                if (!clean.equals("0"))
                                    Service = Service + "Clean ";
                                String DateTime = date + " " + time;

                                booking.add(new bookingList(id, user, worker, Service, DateTime, carType, price, status));

                            }

                            booking.sort(Comparator.comparing(o -> o.getDateTime()));

                            if (booking.size() > 0)
                                nothing.setVisibility(View.GONE);

                            BookingAdapter bookingAdapter = new BookingAdapter(BookingActivity.this, booking);
                            progressDialog.dismiss();
                            BookingList.setAdapter(bookingAdapter);
                            BookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    bookingList d = booking.get(position);
                                    Intent intent = new Intent(BookingActivity.this, BookingInfoActivity.class);
                                    intent.putExtra("Id", d.getId());
                                    startActivity(intent);
                                }
                            });

                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(BookingActivity.this, "Error#121", Toast.LENGTH_SHORT).show();
                            Log.d("DATA FETCH ERROR", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

}