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

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecycleViewItemListener implements View.OnClickListener {

    private SectionList list;
    private RecyclerView myRecycleView;
    private MyFilesHandler myFilesHandler;
    private AdapterDataUpdater adapterDataUpdater;


    public MyRecycleViewItemListener(SectionList sectionList, RecyclerView myRecycleView, MyFilesHandler myFilesHandler, AdapterDataUpdater adapterDataUpdater){
        this.myRecycleView = myRecycleView;
        this.list = sectionList;
        this.myFilesHandler = myFilesHandler;
        this.adapterDataUpdater = adapterDataUpdater;
    }

    @Override
    public void onClick(View v) {
        int itemPosition = myRecycleView.getChildLayoutPosition(v);
        Section item = list.getItemAtPosition(itemPosition);
        if (item instanceof Folder){
            String name = item.getName();
            myFilesHandler.addNameToUpdatedPath(name);
            list.setList(myFilesHandler.listInstance());
            adapterDataUpdater.updateAdapter();

        }else if (item instanceof Note){
            Intent intent = new Intent(MainActContext.getContext(), CreateNoteActivity.class);
            String title = item.getName();
            intent.putExtra("title", title);
            String text = myFilesHandler.readFile(((Note) item));
            intent.putExtra("text", text);
            ((MainActivity) MainActContext.getContext()).startActivityForResult(intent, 2);
            //iniciar la activity de notas pasando a los edittext la info dentro del file seleccionado
        }

    }
}
