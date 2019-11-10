package com.cdc.cdcadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class EditOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String orderID;
    int categoryPos;

    Spinner itemSpinner;
    RecyclerView editOrderRecycler;
    RecyclerView.Adapter editOrderAdapter;

    List<UserInvoiceStrings> userInvoiceStringsList;

    ImageView invoiceButtonImage, emptyCartButtonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        orderID = getIntent().getStringExtra("userOrderID");
        Log.i("editOrder", "" + orderID);

        itemSpinner = (Spinner) findViewById(R.id.spinner);
        editOrderRecycler = (RecyclerView) findViewById(R.id.editOrderRecycler);
        editOrderRecycler.setHasFixedSize(true);
        editOrderRecycler.setLayoutManager(new LinearLayoutManager(this));

        invoiceButtonImage = (ImageView) findViewById(R.id.cartButton);
        emptyCartButtonImage = (ImageView) findViewById(R.id.emptyBin);

        setCategorySpinner();

        invoiceButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(EditOrderActivity.this, UserInvoiceActivity.class);
                intent.putExtra("userOrderID", orderID);
                startActivity(intent);
                //finish();
            }
        });

        emptyCartButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("userInvoice", MODE_PRIVATE).edit();
                editor.clear().apply();

                EditOrderAdapter editOrderAdapter = new EditOrderAdapter();
                editOrderAdapter.totalItemCount = 0;
                editOrderAdapter.totalPrice = 0;

                Toast.makeText(getApplicationContext(), "Cart is Empty now!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setWashableItems()
    {
        userInvoiceStringsList = new ArrayList<>();

        if(categoryPos == 0)
        {
            // Men
            userInvoiceStringsList.add(new UserInvoiceStrings("#01", "Coat", "240"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#02", "Suit 2 Pcs", "300"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#03", "Suit 3 Pcs", "350"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#04", "Vest", "170"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#05", "Tie", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#06", "Safari Suit", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#07", "Koti/Mujib Coat", "190"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#08", "Pant [Normal]", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#09", "Pant [Cord/Velvet]", "90"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#10", "Shirt [Normal]", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#11", "Shirt [Cord/China Silk]", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#12", "T-Shirt", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#13", "Panjabi [Normal]", "100"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#14", "Panjabi [Silk]", "130"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#15", "Panjabi [Toss]", "130"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#16", "Kabli Suit", "160"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#17", "Panjabi-Pajama Suit [2 Pcs]", "160"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#18", "Pajama [Normal]", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#19", "Pajama [Silk]", "90"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#20", "Jacket [Normal]", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#21", "Jacket [Leather](T&C*)", "750"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#22", "Sweater [Full/Half]", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#23", "Shawl [Gents]", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#24", "Sleeping Suit", "200"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#25", "Over Coat/Long Coat/Hoody", "350"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#26", "Sherowani ", "300"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#27", "Scarf/Maflar", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#28", "Dressing Grown", "150"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#29", "Baby Suit", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#30", "Baby Set", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#31", "Rain Coat", "160"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#32", "Pullover", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#33", "Lunge", "90"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#34", "Under Shirt", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#35", "Under Pant ", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#36", "Shocks [pair]", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#37", "Cap", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#38", "Jubba ", "150"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#39", "Fatua", "70"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#40", "Towel", "120"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#41", "Zainamaj", "120"));

        }
        else if(categoryPos == 1)
        {
            //For Ladies
            userInvoiceStringsList.add(new UserInvoiceStrings("#01", "Salowar", "90"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#02", "Kamiz", "500"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#03", "SL Suit 3 Pcs", "300"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#04", "Dupatta", "160"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#05", "Ghagra Suit", "500"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#06", "Sharee [Cotton]", "160"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#07", "Sharee Silk [Normal]", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#08", "Sharee Quality Silk ", "200"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#09", "Sharee Half Silk", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#10", "Sharee South Katan", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#11", "Sharee Mipuri Katan", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#12", "Sharee Broket/Sating", "300"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#13", "Sharee Karala Katan", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#14", "Sharee France Shiffon", "280"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#15", "Sharee Silk Tossor", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#16", "Sharee Kangi Boran", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#17", "Sharee Potla Katan", "240"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#18", "Sharee Jamdani SP", "300"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#19", "Sharee Jamdani DC", "220"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#20", "Frock", "320"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#21", "Orna", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#22", "Tangail Silk", "250"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#23", "Cota/Tissue", "260"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#24", "Ladies Pants", "320"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#25", "Ladies Shawal", "170"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#26", "Blouse", "120"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#27", "Borka", "200"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#28", "Maxy", "200"));

        }
        else
        {
            // Others
            userInvoiceStringsList.add(new UserInvoiceStrings("#01", "Bed Sheet", "220"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#02", "Bed Cover", "450"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#03", "Pillow Cover", "80"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#04", "Car Seat Cover 06 Pcs", "700"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#05", "Screen Per Single Pcs", "180"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#06", "Screen Per Double Pcs", "400"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#07", "Blanket [Baby]", "350"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#08", "Blanket [Large]", "500"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#09", "Carpet [Wash]", "40"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#10", "Mat [As per sample]", "400"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#11", "Mat [Wash]", "40"));
            userInvoiceStringsList.add(new UserInvoiceStrings("#12", "Doll", "700"));

        }

        editOrderAdapter = new EditOrderAdapter(userInvoiceStringsList, getApplicationContext());
        editOrderRecycler.setAdapter(editOrderAdapter);

    }

    private void setCategorySpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.itemCategory, R.layout.spinner_text);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(adapter);

        itemSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        categoryPos = position;
        Log.i("SpinnerSelect", "" + categoryPos);
        setWashableItems();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        Log.i("SpinnerSelect", "" + parent.getItemAtPosition(0).toString());
        categoryPos = 0;
        setWashableItems();

    }
}
