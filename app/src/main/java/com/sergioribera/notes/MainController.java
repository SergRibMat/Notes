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

public class MainController {

    private MyFilesHandler myFilesHandler;
    private SectionList sectionList;
    private AdapterDataUpdater adapterDataUpdater;
    private SectionRecicleAdapter adapter;

    public MainController(MyFilesHandler myFilesHandler, SectionList sectionList,SectionRecicleAdapter adapter, AdapterDataUpdater adapterDataUpdater){
        this.myFilesHandler = myFilesHandler;
        this.sectionList = sectionList;
        this.adapter = adapter;
        this.adapterDataUpdater = adapterDataUpdater;

    }

    public void createFolderMethod(Section section){
        myFilesHandler.createFolder(section.getName());
        adapterDataUpdater.insertSingleItem(section);
    }
}
