package com.example.hitesh.savemywifi;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hitesh on 2/19/2018.
 */

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {
    private List<Device> deviceList;
   public DevicesAdapter(List<Device> deviceList){
       this.deviceList=deviceList;

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       Device device =deviceList.get(position);
       holder.ipRec.setText(device.getIp());
       holder.macAddr.setText(device.getMac());
//       holder.vendors.setText(device.getVendor());

        }


    @Override
    public int getItemCount() {
        return deviceList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
public TextView ipRec,macAddr,vendors;
        public MyViewHolder(View itemView) {
            super(itemView);
            ipRec =(TextView) itemView.findViewById(R.id.ip_rec);
            macAddr =(TextView) itemView.findViewById(R.id.mac_address);
//            vendors=(TextView) itemView.findViewById(R.id.vendor);
        }
    }
}
