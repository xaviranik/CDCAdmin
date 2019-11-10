package com.cdc.cdcadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.Map;
import android.Manifest;
import android.widget.Toolbar;

public class ViewOrderActivity extends AppCompatActivity {

    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    String orderID;
    String userPhoneNumber;

    Button trackButton, editOrderButton;
    Button completeButton;
    TextView userPhoneText, userAddressText, orderIDText, orderDateText, orderPrice, orderItems;
    ImageView iconImage, callImage;

    DocumentReference userOrderDoc;


    int CALL_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        orderID = getIntent().getStringExtra("userOrderID");

        userPhoneText = (TextView) findViewById(R.id.itemNameText);
        userAddressText = (TextView) findViewById(R.id.userAddressTextC);
        orderIDText = (TextView) findViewById(R.id.userOrderIDTextC);
        orderDateText = (TextView) findViewById(R.id.userOrderDate);
        orderPrice = (TextView) findViewById(R.id.orderItemPrice);
        orderItems = (TextView) findViewById(R.id.orderItemText);
        iconImage = (ImageView) findViewById(R.id.iconImage);
        callImage = (ImageView) findViewById(R.id.callImage);
        trackButton = (Button) findViewById(R.id.trackButton);
        completeButton = (Button) findViewById(R.id.completeOrderButton);
        editOrderButton = (Button) findViewById(R.id.editOrderButton);

        orderIDText.setText(orderID);

        userOrderDoc = mFireStore.document("AllOrders/" + orderID);

        getOrderInfo();

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                setOrderTracker();
            }
        });

        editOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                editOrder();
            }
        });

        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(ViewOrderActivity.this);
                builder.setTitle("Order Completed?")
                        .setMessage("Are you sure you want to flag this order as Completed?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                completeOrder();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.checked)
                        .show();


            }
        });

        callImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                callUser();
            }
        });


    }

    private void completeOrder()
    {

        Map<String,Object> isCompleted = new HashMap<>();
        isCompleted.put("completed", true);

        userOrderDoc.set(isCompleted, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                completeButton.setText("Order Completed");
                completeButton.setEnabled(false);
                //Toast.makeText(getApplicationContext(), "Order Completed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void callUser()
    {
        if (ContextCompat.checkSelfPermission(ViewOrderActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
        {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + userPhoneNumber));
            startActivity(callIntent);
        }
        else
        {
            requestPhoneCallPermission();
        }

    }

    private void requestPhoneCallPermission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed for CALLING")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions(ViewOrderActivity.this, new String[] {Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialog.dismiss();
                        }
                    })
                    .create().show();

        } else {
            ActivityCompat.requestPermissions(ViewOrderActivity.this, new String[] {Manifest.permission.CALL_PHONE}, CALL_PERMISSION_CODE);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == CALL_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void setOrderTracker()
    {

        Map<String,Object> isTracking = new HashMap<>();
        isTracking.put("tracking", true);

        userOrderDoc.set(isTracking, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                setCanOrder();
            }
        });


        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("currentOrderID", MODE_PRIVATE).edit();
        editor.putString("currentOrderID", orderID);
        editor.apply();


    }

    private void setCanOrder()
    {
        Map<String,Object> canOrder = new HashMap<>();
        canOrder.put("canOrder", true);

        DocumentReference userCanOrder = mFireStore.document("UserInfo/" + userPhoneNumber + "/CurrentOrder/CanOrder" );

        userCanOrder.set(canOrder, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(getApplicationContext(), "Tracking Order!", Toast.LENGTH_SHORT).show();
                trackButton.setEnabled(false);
                trackButton.setText("Tracking Completed");

                editOrderButton.setEnabled(true);
            }
        });


    }


    private void editOrder()
    {
        Intent intent = new Intent(ViewOrderActivity.this, EditOrderActivity.class);
        intent.putExtra("userOrderID", orderID);
        startActivity(intent);
        finish();


    }

    private void getOrderInfo()
    {
        editOrderButton.setEnabled(false);

        userOrderDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                if(documentSnapshot.exists())
                {
                    UserOrderInfo userOrderInfo = documentSnapshot.toObject(UserOrderInfo.class);

                    userPhoneNumber = userOrderInfo.getUserPhoneNumber();
                    String userAddress = userOrderInfo.getUserAddress();
                    String orderDate = DateFormat.getDateTimeInstance().format(userOrderInfo.getTimeStamp());

                    String totalItems = userOrderInfo.getTotalItems();
                    String totalPrice = userOrderInfo.getTotalPrice();

                    userPhoneText.setText("Phone:  " + userPhoneNumber);
                    userAddressText.setText("Address:  " + userAddress);
                    orderDateText.setText("Timestamp:  " + orderDate);

                    orderPrice.setText("Price: BDT.  " + totalPrice);
                    orderItems.setText("Ordered Items:  " + totalItems);

                    callImage.setVisibility(View.VISIBLE);

                    if(userOrderInfo.isTracking())
                    {
                        trackButton.setEnabled(false);
                        trackButton.setText("This order is being tracked");

                        editOrderButton.setEnabled(true);
                        editOrderButton.setVisibility(View.VISIBLE);

                        if(userOrderInfo.isAccepted())
                        {
                            trackButton.setVisibility(View.INVISIBLE);
                            editOrderButton.setVisibility(View.INVISIBLE);
                            completeButton.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        trackButton.setEnabled(true);
                        trackButton.setText("Track Order");

                        editOrderButton.setEnabled(false);
                        //editOrderButton.setVisibility(View.INVISIBLE);
                    }

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


    }


}
