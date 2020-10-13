package com.example.notes;

public class AdapterDataUpdater {

    private SectionRecicleAdapter adapter;
    private SectionList list;

    public AdapterDataUpdater(SectionRecicleAdapter adapter, SectionList list){
        this.adapter = adapter;
        this.list = list;
    }

    public void insertSingleItem(Section section){

        //si la operacion sale correctamente, pasa a aladirse en memoria y aavisar al adapter, de lo contrario suelta un error y ya
        list.addToList(section);
        adapter.notifyItemInserted(0);//esta linea es la que actualiza el updater
        adapter.notifyDataSetChanged();//despues de actualizarlo, siempre hay que avisar al adapter de que ha habido un cambio con esta linea
    }

    public void removeSingleItem(int position){

        list.removeFromList(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
    }

    public void updateAdapter(){
        adapter.submitList(list);
        adapter.notifyDataSetChanged();
    }
}
