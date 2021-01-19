package com.shubham.odsadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.odsadmin.R;

import java.util.Calendar;

public class BookingInfoActivity extends AppCompatActivity {

    private TextView userTv, workerTv, carCompany, carNumber, date, time, payment, wash, clean, vacuum, extra, total, report;
    private ImageView statusImage, carImage;
    private RatingBar ratings;
    private Button cancel;
    private String id, worker, user;
    private String Payment, Status, CarCompany, CarName, CarNumber, CarType, Date, Time, WashPrice, CleanPrice, VacuumPrice, ExtraPrice, TotalPrice;
    private String Name, Rating;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog2;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);

        setDefault();
        progressDialog = new ProgressDialog(BookingInfoActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Intent intent = getIntent();
        id = intent.getStringExtra("Id");

        setText();
    }

    private void setText() {
        db.collection("bookings").document(id).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        worker = documentSnapshot.getString("Worker");
                        user = documentSnapshot.getString("User");
                        Status = documentSnapshot.getString("Status");
                        CarCompany = documentSnapshot.getString("Car Company");
                        CarName = documentSnapshot.getString("Car Model");
                        CarNumber = documentSnapshot.getString("Car Number");
                        CarType = documentSnapshot.getString("Car Type");
                        Date = documentSnapshot.getString("Date");
                        Time = documentSnapshot.getString("Time");
                        Payment = documentSnapshot.getString("Payment");
                        WashPrice = documentSnapshot.getString("Wash Price");
                        CleanPrice = documentSnapshot.getString("Clean Price");
                        VacuumPrice = documentSnapshot.getString("Vacuum Price");
                        ExtraPrice = documentSnapshot.getString("Extra Price");
                        TotalPrice = documentSnapshot.getString("Total Price");
                        Rating = documentSnapshot.getString("Rating");

                        userTv.setText("User: "+user);
                        workerTv.setText("Worker: "+worker);

                        if (Status.equals("Ongoing")) {
                            statusImage.setImageResource(R.drawable.pending);
                        } else if (Status.equals("Complete")) {
                            statusImage.setImageResource(R.drawable.complete);
                        } else {
                            statusImage.setImageResource(R.drawable.cancel);
                        }

                        if (!Rating.equals("NA")) {
                            ratings.setRating(Float.parseFloat(Rating));
                        }

                        carCompany.setText(CarCompany + " " + CarName);
                        carNumber.setText(CarNumber);
                        date.setText(Date);
                        time.setText(Time);
                        payment.setText("Payment: " + Payment);

                        if (CarType.equals("Mini")) {
                            carImage.setImageResource(R.drawable.car_mini);
                        } else if (CarType.equals("Sedan")) {
                            carImage.setImageResource(R.drawable.car_sedan);
                        } else if (CarType.equals("Suv")) {
                            carImage.setImageResource(R.drawable.car_suv);
                        } else {
                            carImage.setImageResource(R.drawable.car_premium);
                        }

                        wash.setText(WashPrice);
                        clean.setText(CleanPrice);
                        vacuum.setText(VacuumPrice);
                        extra.setText(ExtraPrice);
                        total.setText(TotalPrice);
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(BookingInfoActivity.this, "Error650", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void setDefault() {
        userTv = (TextView) findViewById(R.id.booking_history_info_user_tv);
        workerTv = (TextView) findViewById(R.id.booking_history_info_worker_tv);
        carCompany = (TextView) findViewById(R.id.booking_history_info_car_name_tv);
        carNumber = (TextView) findViewById(R.id.booking_history_info_car_number_tv);
        date = (TextView) findViewById(R.id.booking_history_info_date_tv);
        time = (TextView) findViewById(R.id.booking_history_info_time_tv);
        payment = (TextView) findViewById(R.id.booking_history_info_payment_tv);
        wash = (TextView) findViewById(R.id.booking_history_info_wash_price_tv);
        clean = (TextView) findViewById(R.id.booking_history_info_clean_price_tv);
        vacuum = (TextView) findViewById(R.id.booking_history_info_vacuum_price_tv);
        extra = (TextView) findViewById(R.id.booking_history_info_extra_price_tv);
        total = (TextView) findViewById(R.id.booking_history_info_total_prize_tv);
        carImage = (ImageView) findViewById(R.id.booking_history_info_car_image);
        statusImage = (ImageView) findViewById(R.id.booking_history_info_status_image);
        ratings = (RatingBar) findViewById(R.id.booking_history_info_ratingBar);
        cancel = (Button) findViewById(R.id.booking_history_info_cancel_button);

    }
}