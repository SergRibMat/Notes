package com.example.notes;

public class Folder extends Section{

    private String name;
    private static int image;
    private String path;

    public Folder(String name){
        this.name = name;
        image = R.drawable.folder_image;

    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static int getImage() {
        return image;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
