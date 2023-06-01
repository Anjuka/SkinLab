package com.fyp.vishmi.skinlab.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.DiaryDataList;
import com.fyp.vishmi.skinlab.model.ShelfItemList;

import java.util.ArrayList;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    private ArrayList<DiaryDataList> shelfItemListArrayList;
    private Context context;
    itemClickListner itemClickListner;
    CbClickListner cbClickListner;

    public DiaryAdapter(Context context, ArrayList<DiaryDataList> shelfItemListArrayList, itemClickListner itemClickListner,
                        CbClickListner cbClickListner) {
        this.context = context;
        this.shelfItemListArrayList = shelfItemListArrayList;
        this.itemClickListner = itemClickListner;
        this.cbClickListner = cbClickListner;
    }

    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diary, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, int position) {

        holder.tv_date.setText(shelfItemListArrayList.get(position).getCapture_date());
        holder.tv_time.setText(shelfItemListArrayList.get(position).getCapture_time());

        if (shelfItemListArrayList.get(position).getImg_url() != null){
            Glide.with(context)
                    .load(shelfItemListArrayList.get(position).getImg_url())
                    .into(holder.iv_face);

        }
        else {
            Glide.with(context)
                    .load(R.drawable.woman)
                    .into(holder.iv_face);

        }

        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListner.onItemClick(position);
            }
        });

        holder.cb_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cbClickListner.onCBClick(position, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shelfItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_date = itemView.findViewById(R.id.tv_shop);
        TextView tv_time = itemView.findViewById(R.id.tv_god_name);
        ImageView iv_delete = itemView.findViewById(R.id.v_delete);
        ImageView iv_face = itemView.findViewById(R.id.iv_product);
        CheckBox cb_select = itemView.findViewById(R.id.cb_select);

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface itemClickListner{
        void onItemClick(int postion); }

    public interface CbClickListner{
        void onCBClick(int postion, boolean isCheck); }


}
