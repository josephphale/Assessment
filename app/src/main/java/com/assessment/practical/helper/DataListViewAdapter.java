package com.assessment.practical.helper;


import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assessment.practical.R;
import com.assessment.practical.model.Articles;
import com.assessment.practical.model.Media;
import com.assessment.practical.model.MediaMetadata;
import com.assessment.practical.model.ResultsList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class DataListViewAdapter  extends RecyclerView.Adapter<DataListViewAdapter.ViewHolder> {

    private ArrayList<ResultsList> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    public DataListViewAdapter(Context context, ArrayList<ResultsList> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }
    ///inflates the cell layout from xml when needed
    @NonNull
    @Override
    public DataListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DataListViewAdapter.ViewHolder holder, int position) {

        // PrFormModel rowItem = getItem(position);
        ResultsList pname = mData.get(position);
        ResultsList byline = mData.get(position);
        ResultsList datepublished = mData.get(position);
        ResultsList profile = mData.get(position);

        holder.myTextView.setText(pname.getTitle());
        holder.myTextView.setTypeface(null, Typeface.BOLD);

        holder.byLineTextview.setText(byline.getByline());
        holder.datepublished.setText(datepublished.getPublished_date());
        ArrayList<Media> mediaList = new ArrayList<Media>();
        mediaList =  mData.get(position).getMedia();

        String url = null;
        //ArrayList
        for(int i = 0;i<mediaList.size();i++)
        {
           // Log.e("Error", String.valueOf(mediaList.get(i).getType()));
            if(mediaList.get(i).getMediametadata()!=null)
           {
                url = mediaList.get(i).getMediametadata().get(0).getUrl();
               Log.e("Error", url);
           }
        }


//        String url = metadata.get(0).getUrl();

//        Glide.with(this.context)
//                .load("https://static01.nyt.com/images/2021/05/17/business/14altGates-print/14Gates--top-thumbStandard.jpg")
//                .into(holder.getImage());


        //   holder.btnEdit.set

        //  holder..setText(pname.getCaller_name());

    }

    ///return the total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView byLineTextview;
        TextView datepublished;
        ImageView profile;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.txtName);
            byLineTextview = itemView.findViewById(R.id.byline);
            datepublished = itemView.findViewById(R.id.datepublished);
            profile = itemView.findViewById(R.id.profile_image);

            itemView.setOnClickListener(this);
        }
        public ImageView getImage(){ return this.profile;}
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public ResultsList getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
