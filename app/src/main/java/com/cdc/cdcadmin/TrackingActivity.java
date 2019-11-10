package com.cdc.cdcadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends AppCompatActivity {
    RecyclerView orderList;
    RecyclerView.Adapter orderlistAdapter;

    List<UserOrderStrings> userOrderStringsList;

    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        orderList = (RecyclerView) findViewById(R.id.main_list);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(this));
        userOrderStringsList = new ArrayList<>();

        mFireStore.collection("AllOrders")
                .whereEqualTo("accepted", false)
                .whereEqualTo("tracking", true)
                .whereEqualTo("completed", false)
                .addSnapshotListener(this, new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e)
                    {
                        if(e != null)
                        {
                            Log.i("Error", "" + e.getMessage());
                        }

                        for(DocumentChange doc: documentSnapshots.getDocumentChanges())
                        {
                            if(doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.MODIFIED)
                            {
                                UserOrderInfo userOrderInfo = doc.getDocument().toObject(UserOrderInfo.class);

                                String userPhoneNumber = userOrderInfo.getUserPhoneNumber();
                                String userAddress = userOrderInfo.getUserAddress();
                                String userOrderID = doc.getDocument().getId();

                                //Log.i("RecylerView", "" + userPhoneNumber +  userAddress + userOrderID);

                                UserOrderStrings userOrder = new UserOrderStrings(userPhoneNumber,userAddress,userOrderID,2);

                                userOrderStringsList.add(userOrder);

                                orderlistAdapter = new UserOrderAdapter(userOrderStringsList, getApplicationContext());
                                orderList.setAdapter(orderlistAdapter);
                            }


                        }
                    }
                });
    }
}
