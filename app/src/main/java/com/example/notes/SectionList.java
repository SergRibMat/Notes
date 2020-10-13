package com.example.notes;

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
