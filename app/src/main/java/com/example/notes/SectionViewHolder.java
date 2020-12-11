/*
 * Copyright 2020 Sergio Ribera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
