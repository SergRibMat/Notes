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


    public boolean onLongClick(View v) {
        final Context context =v.getContext();
        //para quitar txt tambien borrar el if
        final int itemPosition = myRecycleView.getChildLayoutPosition(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
        builder.setCancelable(true);
        builder.setTitle(context.getString(R.string.deleteelement));
        builder.setMessage(context.getString(R.string.deletequestion) + list.getItemAtPosition(itemPosition).toString());
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActContext.getContext(), context.getString(R.string.canceldelete), Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton(context.getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Section section = list.getItemAtPosition(itemPosition);
                String name;
                if(section instanceof Folder){
                    name = section.getName();
                }else{
                    name = ((Note) section).getNameWithExtension();
                }
                adapterDataUpdater.removeSingleItem(itemPosition);
                myFilesHandler.deleteFileOrFolder(name);
            }
        });
        builder.show();
        return true;//el return true es para indicar que no haga nada mas tras acabar esto. Si pone false, perfectamente
        //lanza un onclickevent despues del longonclick
    }

    private void remane(){

    }

    private void delete(){

    }


    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        final int itemPosition = myRecycleView.getChildLayoutPosition(view);
        contextMenu.add(itemPosition, 0, itemPosition, "Delete " + list.getItemAtPosition(itemPosition).toString());
    }
}
