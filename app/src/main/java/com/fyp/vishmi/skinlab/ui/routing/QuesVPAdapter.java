package com.fyp.vishmi.skinlab.ui.routing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.vishmi.skinlab.R;

import java.util.ArrayList;

public class QuesVPAdapter extends RecyclerView.Adapter<QuesVPAdapter.Viewholder> {

    ArrayList<Qu_PageViewModel> qu_pageViewModels;
    OnRadioGroupSelectedListener mCallback;

    public QuesVPAdapter(ArrayList<Qu_PageViewModel> qu_pageViewModels, OnRadioGroupSelectedListener mCallback) {
        this.qu_pageViewModels = qu_pageViewModels;
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater .from(parent.getContext())
                .inflate(R.layout.item_quistions_pager, parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.tv_quHead.setText(qu_pageViewModels.get(position).getHead());
        holder.tv_qu_sub.setText(qu_pageViewModels.get(position).getSub());
        holder.rb_1.setText(qu_pageViewModels.get(position).getQue_sub().get(0));
        holder.rb_2.setText(qu_pageViewModels.get(position).getQue_sub().get(1));
        holder.rb_3.setText(qu_pageViewModels.get(position).getQue_sub().get(2));

        if (qu_pageViewModels.get(position).getQue_sub().get(0).isEmpty()){
            holder.rb_1.setVisibility(View.INVISIBLE);
        }

        if (qu_pageViewModels.get(position).getQue_sub().get(1).isEmpty()){
            holder.rb_2.setVisibility(View.INVISIBLE);
        }

        if (qu_pageViewModels.get(position).getQue_sub().get(2).isEmpty()){
            holder.rb_3.setVisibility(View.INVISIBLE);
        }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = radioGroup.findViewById(i);

                mCallback.onButtonSelected((String) radioButton.getText());
            }
        });

        switch (position){
            case 0:
                holder.iv_1.setBackgroundResource(R.drawable.ic_baseline_circle_24_select);
                holder.iv_2.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_3.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_4.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_5.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                break;
            case 1:
                holder.iv_1.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_2.setBackgroundResource(R.drawable.ic_baseline_circle_24_select);
                holder.iv_3.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_4.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_5.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                break;
            case 2:
                holder.iv_1.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_2.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_3.setBackgroundResource(R.drawable.ic_baseline_circle_24_select);
                holder.iv_4.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_5.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                break;
            case 3:
                holder.iv_1.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_2.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_3.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_4.setBackgroundResource(R.drawable.ic_baseline_circle_24_select);
                holder.iv_5.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                break;
            case 4:
                holder.iv_1.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_2.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_3.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_4.setBackgroundResource(R.drawable.ic_baseline_circle_24);
                holder.iv_5.setBackgroundResource(R.drawable.ic_baseline_circle_24_select);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return qu_pageViewModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView tv_quHead;
        TextView tv_qu_sub;
        RadioButton rb_1;
        RadioButton rb_2;
        RadioButton rb_3;
        ImageView iv_1;
        ImageView iv_2;
        ImageView iv_3;
        ImageView iv_4;
        ImageView iv_5;
        RadioGroup radioGroup;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tv_quHead = itemView.findViewById(R.id.tv_que_head);
            tv_qu_sub = itemView.findViewById(R.id.tv_que);
            rb_1 = itemView.findViewById(R.id.rb_1);
            rb_2 = itemView.findViewById(R.id.rb_2);
            rb_3 = itemView.findViewById(R.id.rb_3);
            iv_1 = itemView.findViewById(R.id.iv_1);
            iv_2 = itemView.findViewById(R.id.iv_2);
            iv_3 = itemView.findViewById(R.id.iv_3);
            iv_4 = itemView.findViewById(R.id.iv_4);
            iv_5 = itemView.findViewById(R.id.iv_5);
            radioGroup = itemView.findViewById(R.id.radioGroup);
        }
    }

    public interface OnRadioGroupSelectedListener {
        public void onButtonSelected(String value);
    }
}
