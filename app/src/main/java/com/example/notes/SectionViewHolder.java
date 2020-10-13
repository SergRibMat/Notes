package com.example.notes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

class SectionViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView1;
    TextView textView2;
    ViewGroup parent;

    public SectionViewHolder(View itemView, ViewGroup parent) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_item);
        textView1 = itemView.findViewById(R.id.tv_folder_title);
        textView2 = itemView.findViewById(R.id.tv_folder_text);
        this.parent = parent;
    }

    public void bind(String name, int image){
        textView1.setText(name);
        Glide.with(
                parent.getContext())
                .load(image)
                .apply(new RequestOptions().override(250, 250))
                .into(imageView);

    }
    public void bind(String name, String text){
        textView1.setText(name);
        textView2.setText(text);
    }

}
