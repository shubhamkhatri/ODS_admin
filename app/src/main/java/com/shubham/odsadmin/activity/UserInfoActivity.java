package com.shubham.odsadmin.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.odsadmin.R;

public class UserInfoActivity extends AppCompatActivity {

    private TextView name, age, gender, address, phone, email, servicesDone;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String PhoneNumber;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        Intent i = getIntent();
        PhoneNumber = i.getStringExtra("Phone");
        setDefault();
        progressDialog = new ProgressDialog(UserInfoActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        setText();
    }

    private void setDefault() {
        name = (TextView) findViewById(R.id.user_info_name_tv);
        age = (TextView) findViewById(R.id.user_info_age_tv);
        gender = (TextView) findViewById(R.id.user_info_gender_tv);
        address = (TextView) findViewById(R.id.user_info_address_tv);
        servicesDone = (TextView) findViewById(R.id.user_info_service_done_tv);
        phone = (TextView) findViewById(R.id.user_info_phone_tv);
        email = (TextView) findViewById(R.id.user_info_email_tv);
    }

    private void setText() {
        db.collection("users").document(PhoneNumber).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String fName = documentSnapshot.getString("First Name");
                    String lName = documentSnapshot.getString("Last Name");
                    String Age = documentSnapshot.getString("Age");
                    String Gender = documentSnapshot.getString("Gender");
                    String Address = documentSnapshot.getString("Address");
                    String Phone = documentSnapshot.getString("Phone");
                    String Email = documentSnapshot.getString("Email");
                    String ServicesDone = documentSnapshot.getString("Services Done");
                    String namee = fName + " " + lName;

                    name.setText(namee);
                    gender.setText(Gender);
                    age.setText(Age);
                    address.setText(Address);
                    servicesDone.setText(ServicesDone);
                    phone.setText(Phone);
                    email.setText(Email);
                    progressDialog.dismiss();
                }
            }
        });
    }
}