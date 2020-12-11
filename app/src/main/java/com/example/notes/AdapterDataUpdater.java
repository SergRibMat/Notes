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
