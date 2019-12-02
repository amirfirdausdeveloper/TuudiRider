package com.tuudi3pl.tuudirider.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tuudi3pl.tuudirider.Activity.Dashboard.JobAccept.JobAcceptActivity;
import com.tuudi3pl.tuudirider.Class.OpenJobClass;
import com.tuudi3pl.tuudirider.R;
import com.tuudi3pl.tuudirider.Utils.TypeFaceClass;

import java.lang.reflect.Type;
import java.util.List;

public class OpenJobAdapter extends RecyclerView.Adapter<OpenJobAdapter.ProductViewHolder> {

    String date_current;
    private Context mCtx;
    public static List<OpenJobClass> jobByMonthList;
    private OpenJobAdapter.onClickJobByMonth mListener;
    Activity activity;

    public OpenJobAdapter(Context mCtx, List<OpenJobClass> jobByMonthList, OpenJobAdapter.onClickJobByMonth mListener,
                               Activity activity) {
        this.mCtx = mCtx;
        this.jobByMonthList = jobByMonthList;
        this.mListener = mListener;
        this.activity = activity;
    }

    public interface onClickJobByMonth {
        void onClick(OpenJobClass jobByMonthClass);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.custom_open_job_layout, null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        return new ProductViewHolder(view);


    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final OpenJobClass jobByMonthClass = jobByMonthList.get(position);

        holder.texView_date.setText(jobByMonthClass.getDate());
        holder.textView_shipping_type.setText(jobByMonthClass.getShipping_type());
        holder.textView_delivery_type.setText(jobByMonthClass.getDelivery_type());
        holder.textView_delivery_weight.setText(jobByMonthClass.getDelivery_weight());
        holder.textView_address.setText(jobByMonthClass.getCN());

        holder.textView_city_sender.setText(jobByMonthClass.getSender_city());
        holder.textView_city_receiver.setText(jobByMonthClass.getReceiver_city());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(mCtx, JobAcceptActivity.class);
                next.putExtra("order_id",jobByMonthClass.getOrder_id());
                next.putExtra("CN",jobByMonthClass.getCN());
                next.putExtra("which",jobByMonthClass.getWhich_one());
                mCtx.startActivity(next);
                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });

    }

    @Override
    public int getItemCount() {
        return jobByMonthList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView texView_date,textView_shipping_type,textView_words,textView_delivery_type,textView_delivery_weight,
                textView_address,textView_words2,textView_remarks,textView_city_sender,textView_city_receiver;


        public ProductViewHolder(View itemView) {
            super(itemView);

            texView_date = itemView.findViewById(R.id.texView_date);
            textView_shipping_type  = itemView.findViewById(R.id.textView_shipping_type);
            textView_words  = itemView.findViewById(R.id.textView_words);
            textView_delivery_type  = itemView.findViewById(R.id.textView_delivery_type);
            textView_delivery_weight  = itemView.findViewById(R.id.textView_delivery_weight);
            textView_address  = itemView.findViewById(R.id.textView_address);
            textView_city_sender  = itemView.findViewById(R.id.textView_city_sender);
            textView_city_receiver  = itemView.findViewById(R.id.textView_city_receiver);

            TypeFaceClass.setTypeFaceTextViewBOLD(texView_date,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_shipping_type,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_words,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_delivery_type,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_delivery_weight,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_address,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_city_sender,mCtx);
            TypeFaceClass.setTypeFaceTextView(textView_city_receiver,mCtx);


        }
    }


}