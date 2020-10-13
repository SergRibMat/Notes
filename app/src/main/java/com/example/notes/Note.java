package com.example.notes;

public class Note extends Section{

    private String name;
    private String text;
    private int image;
    private String path;

    public Note(String name, String text){
        this.name = name;
        this.text = text;
        this.image = R.drawable.note_image;

    }
    public Note(String name){
        this.name = name;
        this.image = R.drawable.note_image;

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String toString() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getNameWithExtension(){
        return name + ".txt";
    }

    private String removeExtension(String fileName){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
