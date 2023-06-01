package com.fyp.vishmi.skinlab.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.vishmi.skinlab.R;
import com.fyp.vishmi.skinlab.model.ShelfItemList;

import java.util.ArrayList;

public class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ViewHolder> {

    private ArrayList<ShelfItemList> shelfItemListArrayList;
    private Context context;
    itemClickListner itemClickListner;

    public ShelfAdapter(Context context, ArrayList<ShelfItemList> shelfItemListArrayList, itemClickListner itemClickListner) {
        this.context = context;
        this.shelfItemListArrayList = shelfItemListArrayList;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ShelfAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_shelf, parent, false);

        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShelfAdapter.ViewHolder holder, int position) {

        holder.tv_god_name.setText(shelfItemListArrayList.get(position).getfName());
        holder.tv_shop.setText(shelfItemListArrayList.get(position).getBrand());
        holder.tv_date.setText(shelfItemListArrayList.get(position).getOpen_date());

        if (shelfItemListArrayList.get(position).getImg_url() != null){
            Glide.with(context)
                    .load(shelfItemListArrayList.get(position).getImg_url())
                    .into(holder.iv_product);

        }
        else {
            Glide.with(context)
                    .load(R.drawable.skincare)
                    .into(holder.iv_product);
        }


        switch (shelfItemListArrayList.get(position).getRating()){
            case 0:
                holder.s1.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s2.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s3.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s4.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s5.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                break;
            case 1:
                holder.s1.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s2.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s3.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s4.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s5.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                break;
            case 2:
                holder.s1.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s2.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s3.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s4.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s5.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                break;
            case 3:
                holder.s1.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s2.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s3.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s4.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                holder.s5.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                break;
            case 4:
                holder.s1.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s2.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s3.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s4.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s5.setBackgroundResource(R.drawable.ic_outline_star_rate_24);
                break;
            case 5:
                holder.s1.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s2.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s3.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s4.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                holder.s5.setBackgroundResource(R.drawable.ic_baseline_star_rate_24_fill);
                break;
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                itemClickListner.onItemClick(position);
                return true;
            }
        });

       /* holder.itemView.setOnClickListener(view -> {
            itemClickListner.onItemClick(position);
        });*/

    }

    @Override
    public int getItemCount() {
        return shelfItemListArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_shop = itemView.findViewById(R.id.tv_shop);
        TextView tv_god_name = itemView.findViewById(R.id.tv_god_name);
        TextView tv_date = itemView.findViewById(R.id.tv_date);
        ImageView iv_product = itemView.findViewById(R.id.iv_product);
        ImageView s1 = itemView.findViewById(R.id.s1);
        ImageView s2 = itemView.findViewById(R.id.s2);
        ImageView s3 = itemView.findViewById(R.id.s3);
        ImageView s4 = itemView.findViewById(R.id.s4);
        ImageView s5 = itemView.findViewById(R.id.s5);

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
