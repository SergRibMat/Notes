/*
 * Copyright 2015 Karumi
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
