package com.example.notes;

public class AddNoteThread extends Thread{

    private String title;
    private String text;
    private MyFilesHandler myFilesHandler;
    private AdapterDataUpdater adapterDataUpdater;

    public AddNoteThread(
            String title,
            String text,
            MyFilesHandler myFilesHandler,
            AdapterDataUpdater adapterDataUpdater
    ){
        this.title = title;
        this.text = text;
        this.myFilesHandler = myFilesHandler;
        this.adapterDataUpdater = adapterDataUpdater;
    }

    @Override
    public void run() {
        Note note = myFilesHandler.createFile(title, text);
        adapterDataUpdater.insertSingleItem(note);
    }
}
