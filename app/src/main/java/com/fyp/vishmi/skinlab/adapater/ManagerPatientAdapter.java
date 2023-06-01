package com.fyp.vishmi.skinlab.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.PatientDataList;
import com.fyp.vishmi.skinlab.model.RequestList;

import java.util.ArrayList;

public class ManagerPatientAdapter extends RecyclerView.Adapter<ManagerPatientAdapter.ViewHolder> {

    private ArrayList<PatientDataList> shelfItemListArrayList;
    private Context context;
    itemClickListner itemClickListner;

    public ManagerPatientAdapter(Context context,
                                 ArrayList<PatientDataList> shelfItemListArrayList,
                                 itemClickListner itemClickListner) {
        this.context = context;
        this.shelfItemListArrayList = shelfItemListArrayList;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ManagerPatientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paitent_manager, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerPatientAdapter.ViewHolder holder, int position) {

        holder.tv_name.setText(shelfItemListArrayList.get(position).getRequestData().get(0).getName());
        holder.iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListner.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shelfItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_name = itemView.findViewById(R.id.tv_name);
        ImageView iv_next = itemView.findViewById(R.id.imageView4);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface itemClickListner {
        void onItemClick(int postion);
    }
}
