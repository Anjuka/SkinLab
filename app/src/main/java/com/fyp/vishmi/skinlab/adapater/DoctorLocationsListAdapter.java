package com.fyp.vishmi.skinlab.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.AvailableHospital;
import com.fyp.vishmi.skinlab.model.DocDataList;

import java.util.ArrayList;

public class DoctorLocationsListAdapter extends RecyclerView.Adapter<DoctorLocationsListAdapter.ViewHolder> {

    private ArrayList<AvailableHospital> shelfItemListArrayList;
    private Context context;
    itemClickListner itemClickListner;

    public DoctorLocationsListAdapter(Context context, ArrayList<AvailableHospital> shelfItemListArrayList) {
        this.context = context;
        this.shelfItemListArrayList = shelfItemListArrayList;
    }

    @NonNull
    @Override
    public DoctorLocationsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor_location, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorLocationsListAdapter.ViewHolder holder, int position) {

        holder.tv_p1.setText(shelfItemListArrayList.get(position).getName());
        holder.tv_add.setText(shelfItemListArrayList.get(position).getAddress());
        holder.tv_tp.setText(shelfItemListArrayList.get(position).getContact());

        holder.itemView.setOnClickListener(view -> {
            itemClickListner.onItemClick(position);
        });

        }

    @Override
    public int getItemCount() {
        return shelfItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_p1 = itemView.findViewById(R.id.tv_p1);
        TextView tv_add = itemView.findViewById(R.id.tv_add);
        TextView tv_tp = itemView.findViewById(R.id.tv_tp);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface itemClickListner{
        void onItemClick(int postion); }
}
