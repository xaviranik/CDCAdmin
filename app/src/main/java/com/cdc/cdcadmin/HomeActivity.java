package com.cdc.cdcadmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button incomingOrderButton, activeOrderButton, trackingOrderButton, currentOrderButton;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        incomingOrderButton = (Button) findViewById(R.id.incomingOrderButton);
        incomingOrderButton.setOnClickListener(this);
        activeOrderButton = (Button) findViewById(R.id.activeOrdersButton);
        activeOrderButton.setOnClickListener(this);
        trackingOrderButton = (Button) findViewById(R.id.trackingOrderButton);
        trackingOrderButton.setOnClickListener(this);
        currentOrderButton = (Button) findViewById(R.id.currentOrderButton);
        currentOrderButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
            {
                mAuth.signOut();

                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {
                        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 2000);

                break;
            }

            case R.id.about:
            {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("About")
                        .setMessage("© Calcutta Dry Cleaners, All Rights reserved.")
                        .show();

                break;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.incomingOrderButton:
            {
                startActivity(new Intent(HomeActivity.this, IncomingOrderActivity.class));
                break;
            }
            case R.id.activeOrdersButton:
            {
                startActivity(new Intent(HomeActivity.this, ActiveOrderActivity.class));
                break;
            }
            case R.id.trackingOrderButton:
            {
                startActivity(new Intent(HomeActivity.this, TrackingActivity.class));
                break;
            }
            case R.id.currentOrderButton:
            {
                SharedPreferences prefs = getSharedPreferences("currentOrderID", MODE_PRIVATE);
                String orderID = prefs.getString("currentOrderID", "default");

                if(orderID.equals("default"))
                {
                    Toast.makeText(getApplicationContext(), "No order has been tracking", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                {
                    Intent intent = new Intent(HomeActivity.this, ViewOrderActivity.class);
                    intent.putExtra("userOrderID", orderID);
                    startActivity(intent);
                }
                break;
            }
        }
    }






















}
