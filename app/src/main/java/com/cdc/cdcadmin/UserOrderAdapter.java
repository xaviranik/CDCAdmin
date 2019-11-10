package com.cdc.cdcadmin;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {

    List<UserOrderStrings> userOrderStringsList;
    Context context;

    public UserOrderAdapter(List<UserOrderStrings> userOrderStringsList, Context context)
    {
        this.userOrderStringsList = userOrderStringsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        UserOrderStrings userOrderStrings = userOrderStringsList.get(position);

        final String orderID = userOrderStrings.getUserOrderID();

        holder.userPhoneText.setText("Phone : " + userOrderStrings.getUserPhoneNumber());
        holder.userOrderID.setText("Order ID : " + userOrderStrings.getUserOrderID());
        holder.userAddressText.setText("Address : " + userOrderStrings.getUserAddress());

        switch (userOrderStrings.orderStatus)
        {
            case 1:
            {
                holder.iconImage.setImageResource(R.drawable.incoming);
                break;
            }

            case 2:
            {
                holder.iconImage.setImageResource(R.drawable.run);
                break;
            }

            case 3:
            {
                holder.iconImage.setImageResource(R.drawable.washing);
                break;
            }
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Intent intent = new Intent(v.getContext(), ViewOrderActivity.class);
                intent.putExtra("userOrderID", orderID);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return userOrderStringsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userPhoneText, userAddressText, userOrderID;
        ImageView iconImage;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            userPhoneText = (TextView) itemView.findViewById(R.id.itemNameText);
            userAddressText = (TextView) itemView.findViewById(R.id.userAddressTextC);
            userOrderID = (TextView) itemView.findViewById(R.id.userOrderIDTextC);
            iconImage = (ImageView) itemView.findViewById(R.id.iconImage);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.itemRelativeLayout);

        }
    }























}
