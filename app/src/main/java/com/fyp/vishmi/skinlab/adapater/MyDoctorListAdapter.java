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
import com.fyp.vishmi.skinlab.model.DocDataList;
import com.fyp.vishmi.skinlab.model.MyDocDataList;

import java.util.ArrayList;

public class MyDoctorListAdapter extends RecyclerView.Adapter<MyDoctorListAdapter.ViewHolder> {

    private ArrayList<MyDocDataList> shelfItemListArrayList;
    private Context context;
    itemClickListner itemClickListner;

    public MyDoctorListAdapter(Context context, ArrayList<MyDocDataList> shelfItemListArrayList, itemClickListner itemClickListner) {
        this.context = context;
        this.shelfItemListArrayList = shelfItemListArrayList;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public MyDoctorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyDoctorListAdapter.ViewHolder holder, int position) {

        holder.tv_doc_name.setText(shelfItemListArrayList.get(position).getDocName());
        holder.tv_god_name.setText(shelfItemListArrayList.get(position).getDesignation());
        holder.tv_start.setText(String.valueOf(shelfItemListArrayList.get(position).getRating()));

        holder.itemView.setOnClickListener(view -> {
            itemClickListner.onItemClick(position);
        });

        }

    @Override
    public int getItemCount() {
        return shelfItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_doc_name = itemView.findViewById(R.id.tv_doc_name);
        TextView tv_god_name = itemView.findViewById(R.id.tv_god_name);
        TextView tv_start = itemView.findViewById(R.id.tv_start);

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
