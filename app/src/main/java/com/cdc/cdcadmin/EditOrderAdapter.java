package com.cdc.cdcadmin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class EditOrderAdapter extends RecyclerView.Adapter<EditOrderAdapter.ViewHolder> {

    List<UserInvoiceStrings> userInvoiceStringsList;
    Context context;

    public static int totalItemCount;
    public static int totalPrice;

    public EditOrderAdapter()
    {

    }

    public EditOrderAdapter(List<UserInvoiceStrings> userInvoiceStringsList, Context context)
    {
        this.userInvoiceStringsList = userInvoiceStringsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_wash_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        UserInvoiceStrings userInvoiceStrings = userInvoiceStringsList.get(position);

        holder.itemNameText.setText(userInvoiceStrings.getItemName());
        holder.itemPriceText.setText(userInvoiceStrings.getItemPrice());
        holder.serialText.setText(userInvoiceStrings.getSerialNum());

        holder.addItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int quantity = Integer.parseInt(holder.itemQuantityText.getText().toString());
                    quantity += 1;
                    holder.itemQuantityText.setText(String.valueOf(quantity));
                }
                catch (Exception e)
                {

                }

            }
        });

        holder.removeItemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int quantity = Integer.parseInt(holder.itemQuantityText.getText().toString());

                    if(quantity > 0)
                    {
                        quantity -= 1;
                        holder.itemQuantityText.setText(String.valueOf(quantity));
                    }
                }
                catch (Exception e)
                {

                }


            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {
                if(isLongClick)
                {
                    holder.addCartImage.setImageResource(R.drawable.checked);
                    holder.overlayImage.setVisibility(View.VISIBLE);

                    try
                    {
                        int quantity = Integer.parseInt(holder.itemQuantityText.getText().toString());
                        int price = quantity * Integer.parseInt(holder.itemPriceText.getText().toString());

                        totalPrice = totalPrice + price;
                        totalItemCount = totalItemCount + quantity;

                        SharedPreferences.Editor editor = context.getSharedPreferences("userInvoice", MODE_PRIVATE).edit();
                        editor.putInt("totalPrice", totalPrice);
                        editor.putInt("totalItemCount", totalItemCount);
                        editor.apply();

                        Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show();


                        new CountDownTimer(1500, 1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {

                            }

                            @Override
                            public void onFinish()
                            {
                                holder.addCartImage.setImageResource(R.drawable.cart);
                                holder.overlayImage.setVisibility(View.INVISIBLE);

                            }
                        }.start();
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(context,"ERROR! Item can't be ADDED! Check your input Price",Toast.LENGTH_LONG).show();
                        holder.addCartImage.setImageResource(R.drawable.close);

                        new CountDownTimer(1500, 1000)
                        {

                            @Override
                            public void onTick(long millisUntilFinished)
                            {

                            }

                            @Override
                            public void onFinish()
                            {
                                holder.addCartImage.setImageResource(R.drawable.cart);
                                holder.overlayImage.setVisibility(View.INVISIBLE);

                            }
                        }.start();
                    }

                }
                else
                {
                    Toast.makeText(context, "Long press to add to Cart!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return userInvoiceStringsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView itemNameText, serialText;
        EditText itemPriceText, itemQuantityText;
        ImageView overlayImage, addItemImage, removeItemImage, addCartImage;
        RelativeLayout itemRelativeLayout;

        ItemClickListener itemClickListener;


        public ViewHolder(View itemView)
        {
            super(itemView);

            itemNameText = (TextView) itemView.findViewById(R.id.itemNameText);
            serialText = (TextView) itemView.findViewById(R.id.serialNumText);
            itemPriceText = (EditText) itemView.findViewById(R.id.itemPriceText);
            itemQuantityText = (EditText) itemView.findViewById(R.id.itemQuantityText);
            overlayImage = (ImageView) itemView.findViewById(R.id.overlayImage);
            addItemImage = (ImageView) itemView.findViewById(R.id.addIcon);
            removeItemImage = (ImageView) itemView.findViewById(R.id.removeIcon);
            itemRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.itemRelativeLayout);
            addCartImage = (ImageView) itemView.findViewById(R.id.cartIcon);

            itemQuantityText.setEnabled(false);


            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);


        }

        private void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v)
        {
            itemClickListener.onClick(v,getAdapterPosition(),false);

        }

        @Override
        public boolean onLongClick(View v)
        {
            itemClickListener.onClick(v,getAdapterPosition(),true);
            return true;
        }




        /*


        holder.addCartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int pos = holder.getAdapterPosition();

                holder.itemRelativeLayout.setEnabled(false);
                holder.itemPriceText.setEnabled(false);
                holder.itemQuantityText.setEnabled(false);
                holder.addCartImage.setImageResource(R.drawable.checked);
                holder.addItemImage.setVisibility(View.INVISIBLE);
                holder.removeItemImage.setVisibility(View.INVISIBLE);

                try
                {
                    int quantity = Integer.parseInt(holder.itemQuantityText.getText().toString());
                    int price = quantity * Integer.parseInt(holder.itemPriceText.getText().toString());

                    totalPrice = totalPrice + price;
                    totalItemCount = totalItemCount + quantity;
                }
                catch (Exception e)
                {

                }
            }
        });




        holder.itemRelativeLayout.setOnClickListener(new View.OnClickListener() {

            int toggle = 0;
            @Override
            public void onClick(View v)
            {
                Log.i("Cart", "onClick:" + position);

                if(toggle == 0)
                {
                    toggle = 1;
                    holder.overlayImage.setVisibility(View.VISIBLE);

                    holder.addItemImage.setVisibility(View.VISIBLE);
                    holder.removeItemImage.setVisibility(View.VISIBLE);
                    holder.addCartImage.setVisibility(View.VISIBLE);
                    holder.itemQuantityText.setEnabled(true);
                    holder.itemPriceText.setEnabled(true);


                }
                else
                {
                    toggle = 0;
                    holder.overlayImage.setVisibility(View.INVISIBLE);

                    holder.addItemImage.setVisibility(View.INVISIBLE);
                    holder.removeItemImage.setVisibility(View.INVISIBLE);
                    holder.addCartImage.setVisibility(View.INVISIBLE);
                    holder.itemQuantityText.setEnabled(false);
                    holder.itemPriceText.setEnabled(false);
                }
            }
        });
         */
    }























}
