package com.tuudi3pl.tuudirider.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tuudi3pl.tuudirider.Activity.Dashboard.JobAccept.JobAcceptActivity;
import com.tuudi3pl.tuudirider.Activity.Dashboard.JobInProgress.JobInProgressActivity;
import com.tuudi3pl.tuudirider.Class.MyJobClass;
import com.tuudi3pl.tuudirider.Class.OpenJobClass;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import java.util.List;

public class MyJobAdapter extends RecyclerView.Adapter<MyJobAdapter.ProductViewHolder> {

    String date_current;
    private Context mCtx;
    public static List<MyJobClass> jobByMonthList;
    private MyJobAdapter.onClickJobByMonth mListener;
    Activity activity;

    public MyJobAdapter(Context mCtx, List<MyJobClass> jobByMonthList, MyJobAdapter.onClickJobByMonth mListener,
                        Activity activity) {
        this.mCtx = mCtx;
        this.jobByMonthList = jobByMonthList;
        this.mListener = mListener;
        this.activity = activity;
    }

    public interface onClickJobByMonth {
        void onClick(MyJobClass jobByMonthClass);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_my_job_layout, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final MyJobClass jobByMonthClass = jobByMonthList.get(position);

        holder.texView_date.setText(jobByMonthClass.getDate());
        holder.textView_shipping_type.setText(jobByMonthClass.getShipping_type());
        holder.textView_delivery_type.setText(jobByMonthClass.getDelivery_type());
        holder.textView_delivery_weight.setText(jobByMonthClass.getDelivery_weight());
        holder.textView_address.setText(jobByMonthClass.getCN());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(mCtx, JobInProgressActivity.class);
                next.putExtra("order_id",jobByMonthClass.getOrder_id());
                mCtx.startActivity(next);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

        holder.textView_status_parcel.setText(jobByMonthClass.getStatus_description().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return jobByMonthList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView texView_date,textView_shipping_type,textView_words,textView_delivery_type,textView_delivery_weight,
                textView_address,textView_words2,textView_remarks,textView_words3,textView_status_parcel;


        public ProductViewHolder(View itemView) {
            super(itemView);

            texView_date = itemView.findViewById(R.id.texView_date);
            textView_shipping_type  = itemView.findViewById(R.id.textView_shipping_type);
            textView_words  = itemView.findViewById(R.id.textView_words);
            textView_delivery_type  = itemView.findViewById(R.id.textView_delivery_type);
            textView_delivery_weight  = itemView.findViewById(R.id.textView_delivery_weight);
            textView_address  = itemView.findViewById(R.id.textView_address);
            textView_words3  = itemView.findViewById(R.id.textView_words3);
            textView_status_parcel  = itemView.findViewById(R.id.textView_status_parcel);

            TypeFaceClass.setTypeFaceTextViewBOLD(texView_date,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_shipping_type,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_words,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_delivery_type,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_delivery_weight,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_address,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_words3,mCtx);
            TypeFaceClass.setTypeFaceTextViewBOLD(textView_status_parcel,mCtx);

        }
    }


}