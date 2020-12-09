package com.example.notes;

import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyLongClickListener implements View.OnCreateContextMenuListener {

    private AdapterDataUpdater adapterDataUpdater;
    private SectionList list;
    private RecyclerView myRecycleView;
    private MyFilesHandler myFilesHandler;

    public MyLongClickListener(AdapterDataUpdater adapterDataUpdater, SectionList sectionList, RecyclerView myRecycleView, MyFilesHandler myFilesHandler) {
        this.adapterDataUpdater = adapterDataUpdater;
        this.myRecycleView = myRecycleView;
        this.list = sectionList;
        this.myFilesHandler = myFilesHandler;
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        final int itemPosition = myRecycleView.getChildLayoutPosition(view);
        contextMenu.add(itemPosition, 0, itemPosition, "Delete " + list.getItemAtPosition(itemPosition).toString());
    }
}
