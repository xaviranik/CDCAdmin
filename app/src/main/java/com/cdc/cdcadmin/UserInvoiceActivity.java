package com.cdc.cdcadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class UserInvoiceActivity extends AppCompatActivity {

    String orderID;
    String totalQuantity, totalPrice;
    TextView itemPriceText, itemQuantityText, orderIDText;
    Button confirmOrderButton;
    ImageView headerImage;

    ConstraintLayout parentLayout;

    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
    DocumentReference userOrderDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invoice);

        orderID = getIntent().getStringExtra("userOrderID");


        itemPriceText = (TextView) findViewById(R.id.itemPriceText);
        itemQuantityText = (TextView) findViewById(R.id.itemQuantityText);
        orderIDText = (TextView) findViewById(R.id.orderIDH);
        confirmOrderButton = (Button) findViewById(R.id.invoiceConfirmButton);
        parentLayout = (ConstraintLayout) findViewById(R.id.parentLayout);
        headerImage = (ImageView) findViewById(R.id.headerImage);

        userOrderDoc = mFireStore.document("AllOrders/" + orderID);


        getInvoiceData();

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                confirmInvoice();
            }
        });

    }

    private void confirmInvoice()
    {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(UserInvoiceActivity.this);
        builder.setTitle("Make Invoice?")
                .setMessage("Are you sure you want to make this Invoice?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Map<String,Object> orderInvoice = new HashMap<>();
                        orderInvoice.put("totalItems", totalQuantity);
                        orderInvoice.put("totalPrice", totalPrice);
                        orderInvoice.put("accepted", true);



                        userOrderDoc.set(orderInvoice, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                //Toast.makeText(getApplicationContext(), "Invoice Updated!", Toast.LENGTH_SHORT).show();

                                confirmOrderButton.setEnabled(false);
                                confirmOrderButton.setText("Confirmed");
                                headerImage.setImageResource(R.drawable.cartchecked);


                                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("userInvoice", MODE_PRIVATE).edit();
                                editor.clear().apply();

                                SharedPreferences.Editor editor_id = getApplicationContext().getSharedPreferences("currentOrderID", MODE_PRIVATE).edit();
                                editor_id.clear().apply();

                                EditOrderAdapter editOrderAdapter = new EditOrderAdapter();
                                editOrderAdapter.totalPrice = 0;
                                editOrderAdapter.totalItemCount = 0;

                                new CountDownTimer(1000, 1000)
                                {

                                    @Override
                                    public void onTick(long millisUntilFinished)
                                    {

                                    }

                                    @Override
                                    public void onFinish()
                                    {
                                        Intent intent = new Intent(UserInvoiceActivity.this, HomeActivity.class);

                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        //finish();
                                    }
                                }.start();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {

                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        // do nothing
                    }
                })
                .setIcon(R.drawable.laundry_basket)
                .show();

    }

    private void getInvoiceData()
    {
        SharedPreferences prefs = getSharedPreferences("userInvoice", MODE_PRIVATE);

        int totalPrice = prefs.getInt("totalPrice", 0);
        int totalItemCount = prefs.getInt("totalItemCount", 0);

        this.totalPrice = String.valueOf(totalPrice);
        totalQuantity = String.valueOf(totalItemCount);

        //Toast.makeText(getApplicationContext(), "" + totalItemCount + " : " + totalPrice + " " + orderID, Toast.LENGTH_SHORT).show();

        setInvoice();


    }

    private void setInvoice()
    {
        orderIDText.setText("Order ID: " + orderID);
        itemPriceText.setText("BDT.  " + totalPrice);
        itemQuantityText.setText(totalQuantity);

    }
}
