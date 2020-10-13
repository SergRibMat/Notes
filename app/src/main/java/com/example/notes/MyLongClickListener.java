package com.example.notes;

import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyLongClickListener implements View.OnLongClickListener {

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
    public boolean onLongClick(View v) {
        //para quitar txt tambien borrar el if
        final int itemPosition = myRecycleView.getChildLayoutPosition(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
        builder.setCancelable(true);
        builder.setTitle("Borrar libro");
        builder.setMessage("Estas a punto de borrar el elemento " + list.getItemAtPosition(itemPosition).toString());
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActContext.getContext(), "borrado cancelado", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
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
}
