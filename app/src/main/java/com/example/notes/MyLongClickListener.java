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
