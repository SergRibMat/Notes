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

package com.sergioribera.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SectionRecicleAdapter extends RecyclerView.Adapter<SectionViewHolder> {

    private ArrayList<Section> list;
    private RecyclerView myRecycleView;
    private MyFilesHandler myFilesHandler;
    private AdapterDataUpdater adapterDataUpdater;
    private SectionList sectionList;

    public SectionRecicleAdapter(SectionList sectionList, RecyclerView myRecycleView, MyFilesHandler myFilesHandler){
        submitList(sectionList);
        this.myRecycleView = myRecycleView;
        this.myFilesHandler = myFilesHandler;
        this.sectionList = sectionList;

    }

    @NonNull
    @Override
    public SectionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_folder_item,
                parent, false);
        v.setOnClickListener(new MyRecycleViewItemListener(sectionList, myRecycleView, myFilesHandler, adapterDataUpdater));
        //v.setOnLongClickListener(new MyLongClickListener(adapterDataUpdater, sectionList, myRecycleView, myFilesHandler));
        v.setOnCreateContextMenuListener(new MyLongClickListener(adapterDataUpdater, sectionList, myRecycleView, myFilesHandler));;
        return new SectionViewHolder(v, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SectionViewHolder holder, int position) {
        Object item = list.get(position);
        if(item instanceof Folder){
            holder.bind(((Folder) item).getName(), ((Folder) item).getImage());
        }else if(item instanceof Note){
            holder.bind(((Note) item).getName(), ((Note) item).getImage());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(SectionList sectionList){
        list = sectionList.getList();
    }

    public void setAdapterDataUpdater(AdapterDataUpdater adapterDataUpdater) {
        this.adapterDataUpdater = adapterDataUpdater;
    }
}
