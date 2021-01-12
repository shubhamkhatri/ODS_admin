package com.shubham.odsadmin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shubham.odsadmin.R;

public class BillInfoActivity extends AppCompatActivity {

    private TextView phone, from, name, number, ifsc, date, amount,status;
    private Button paid;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_info);

        Intent i = getIntent();
        Id = i.getStringExtra("Id");
        String From = i.getStringExtra("From");

        setDefault();
        progressDialog = new ProgressDialog(BillInfoActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        if (From.equals("History"))
            paid.setVisibility(View.GONE);

        setText();

        paid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWarning();
            }
        });
    }

    private void setDefault() {
        phone = (TextView) findViewById(R.id.bill_info_phone_tv);
        from = (TextView) findViewById(R.id.bill_info_from_tv);
        name = (TextView) findViewById(R.id.bill_info_name_tv);
        number = (TextView) findViewById(R.id.bill_info_number_tv);
        ifsc = (TextView) findViewById(R.id.bill_info_ifsc_tv);
        date = (TextView) findViewById(R.id.bill_info_date_tv);
        amount = (TextView) findViewById(R.id.bill_info_amount_tv);
        status= (TextView) findViewById(R.id.bill_info_status_tv);
        paid = (Button) findViewById(R.id.bill_info_paid_button);
    }

    private void setText() {
        db.collection("bills").document(Id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    String Phone = documentSnapshot.getString("Phone");
                    String From = documentSnapshot.getString("From");
                    String Name = documentSnapshot.getString("Account Name");
                    String Number = documentSnapshot.getString("Account Number");
                    String Ifsc = documentSnapshot.getString("Ifsc");
                    String Date = documentSnapshot.getString("Date");
                    String Amount = documentSnapshot.getString("Amount");
                    String Status= documentSnapshot.getString("Status");

                    phone.setText(Phone);
                    from.setText(From);
                    name.setText(Name);
                    number.setText(Number);
                    ifsc.setText(Ifsc);
                    date.setText(Date);
                    amount.setText(Amount);
                    status.setText(Status);

                    progressDialog.dismiss();
                }
            }
        });
    }

    public void setWarning() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(BillInfoActivity.this);
        builder.setTitle("Warning!!");
        builder.setIcon(R.drawable.warning);
        builder.setMessage("Are you sure payment is done to this user? Once status changed to Paid can not be set unpaid.\nAre you sure you want to continue? ").setCancelable(false)
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        progressDialog.setMessage("Updating...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        setStatus();
                    }
                }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    private void setStatus() {
        db.collection("bills").document(Id).update("Status","Paid")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        Toast.makeText(BillInfoActivity.this,"Status Update successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BillInfoActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(BillInfoActivity.this,"Error #606",Toast.LENGTH_SHORT).show();
            }
        });
    }
}