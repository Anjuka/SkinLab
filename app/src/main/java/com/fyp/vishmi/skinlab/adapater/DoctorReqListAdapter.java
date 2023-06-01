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
import com.fyp.vishmi.skinlab.model.MyDocDataList;
import com.fyp.vishmi.skinlab.model.RequestList;

import java.util.ArrayList;

public class DoctorReqListAdapter extends RecyclerView.Adapter<DoctorReqListAdapter.ViewHolder> {

    private ArrayList<RequestList> shelfItemListArrayList;
    private Context context;
    itemClickListner itemClickListner;
    itemClickListnerAccept itemClickListnerAccept;
    itemClickListnerReject itemClickListnerReject;

    public DoctorReqListAdapter(Context context,
                                ArrayList<RequestList> shelfItemListArrayList,
                                itemClickListner itemClickListner,
                                itemClickListnerAccept itemClickListnerAccept,
            itemClickListnerReject itemClickListnerReject) {
        this.context = context;
        this.shelfItemListArrayList = shelfItemListArrayList;
        this.itemClickListner = itemClickListner;
        this.itemClickListnerAccept = itemClickListnerAccept;
        this.itemClickListnerReject = itemClickListnerReject;
    }

    @NonNull
    @Override
    public DoctorReqListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_paitent_list, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorReqListAdapter.ViewHolder holder, int position) {

        holder.tv_pait_name.setText(shelfItemListArrayList.get(position).getName());

        holder.iv_next.setOnClickListener(view -> {
            itemClickListner.onItemClick(position);
        });

        holder.btn_reject.setOnClickListener(view -> {
            itemClickListnerReject.onItemClickRej(position);
        });

        holder.btn_accept.setOnClickListener(view -> {
            itemClickListnerAccept.onItemClickAcc(position);
        });

        }

    @Override
    public int getItemCount() {
        return shelfItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_pait_name = itemView.findViewById(R.id.tv_pait_name);
        ImageView iv_next = itemView.findViewById(R.id.iv_next);
        Button btn_accept = itemView.findViewById(R.id.btn_accept);
        Button btn_reject = itemView.findViewById(R.id.btn_reject);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {
        }
    }

    public interface itemClickListner{
        void onItemClick(int postion); }

    public interface itemClickListnerAccept{
        void onItemClickAcc(int postion); }

    public interface itemClickListnerReject{
        void onItemClickRej(int postion); }
}
