package com.example.notes;

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
        v.setOnLongClickListener(new MyLongClickListener(adapterDataUpdater, sectionList, myRecycleView, myFilesHandler));
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
