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

import java.util.ArrayList;

public class SectionList {

    private ArrayList<Section> list;


    public SectionList (ArrayList<Section> section){
        list = section;

    }

    public void setList(ArrayList<Section> list) {
        this.list = list;
    }

    public ArrayList<Section> getList() {
        return list;
    }

    public void removeFromList(int position){
        list.remove(position);

    }

    public void removeFromList(Section section){
        list.remove(section);

    }

    public void updateSectionInfo(Section section, String newName){
        list.get(list.indexOf(section)).setName(newName);

    }

    public void addToList(Section section){
        list.add(section);

    }

    public int getItemAtPosition(Section section){
        return list.indexOf(section);
    }
    public Section getItemAtPosition(int position){
        return list.get(position);
    }
}
