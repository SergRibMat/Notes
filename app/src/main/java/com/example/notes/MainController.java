package com.example.notes;

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
        myFilesHandler.createFolder(section.getName());//esto tiiene que ser un boolean de si se ha creado satisfactoriamente
        adapterDataUpdater.insertSingleItem(section);
    }
}
