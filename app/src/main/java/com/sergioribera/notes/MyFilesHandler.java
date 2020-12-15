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

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MyFilesHandler {


    private final String rootPath;
    private String updatedPath;
    private boolean isEmptry;
    private File file;
    private ArrayList<Section> list;
    private ArrayList<Section> searchList;

    public MyFilesHandler(Context context){
        rootPath = context.getFilesDir().getPath();
        updatedPath = rootPath;
        file = new File(updatedPath);//actualizar esto cuando el path cambie
        appIsEmpty();
    }

    private void appIsEmpty(){
        File file = new File(rootPath);
        String[] filesContent = file.list();
        if (filesContent.length == 0){
            isEmptry = true;
        }else{
            isEmptry = false;
        }

    }

    public boolean getIsEmptry(){
        return isEmptry;
    }



    public ArrayList<Section> listInstance(){
        File[] fileContent = file.listFiles();
        //controlar las listas nulas aqui

        list = new ArrayList<>();

        if(fileContent == null){
            return list;
        }
        for (int i = 0; i < fileContent.length; i++) {
            if(fileContent[i].isDirectory()){
                Folder folder = new Folder(fileContent[i].getName());
                folder.setPath(getPathOfNoteOrDirectory(fileContent[i]));
                list.add(folder);
            }else{
                Note note = new Note(removeExtension(fileContent[i].getName()));
                note.setPath(getPathOfNoteOrDirectory(fileContent[i]));
                list.add(note);
            }
        }
        return list;
    }


    private String removeExtension(String fileName){
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    public void createFolder(String folderName){
        file = new File(updatedPath + File.separator + folderName);
        file.mkdir();
    }

    public void addNameToUpdatedPath(String folderName){
        updatedPath += (File.separator + folderName);
        updateFile();
    }

    public void removeNameFromUpdatedPath(){
        int position = updatedPath.lastIndexOf(File.separator);
        updatedPath = updatedPath.substring(0, position);
        updateFile();
    }

    private void updateFile(){
        file = new File(updatedPath);
    }

    public String getRootPath(){
        return rootPath;
    }

    public String getUpdatedPath(){
        return updatedPath;
    }

    public Note createFile(String title, String text){
        Note note = new Note(title, text);
        note.setPath(updatedPath + File.separator + note.getName() + ".txt");
        File file = new File(updatedPath + File.separator + note.getName() + ".txt");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(note.getText().getBytes());
            out.close();

        } catch (FileNotFoundException e) {

        } catch(IOException ex){

        }catch (Exception exx){

        }

        return note;
    }

    public String readFile(Note note){
        File file = new File(note.getPath());
        BufferedReader br = null;
        String text = "";
        try {
            br = new BufferedReader(new FileReader(file));

            String str;
            while ((str = br.readLine()) != null) {
                text += str;
            }

        } catch (FileNotFoundException e) {

        } catch(IOException ex){

        } catch (Exception exx){

        }
        return text;
    }

    public String deleteFileOrFolder(String fileName){
        File directory = new File(updatedPath + File.separator + fileName);
        File file = new File(updatedPath + File.separator + fileName + ".txt");
        if (deleteDirectory(directory)){
            return "Grupo eliminado";
        }else if(deleteFile(file)){
            return "Nota eliminada";
        }else{
            return "no se ha podido eliminar";
        }
    }

    private boolean deleteFile(File file){
        if (!file.delete()){
            return false;
        }

        return true;
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    public ArrayList<Section> getList() {
        return list;
    }

    public boolean searchIfNoteAlreadyExists(String noteName){

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equalsIgnoreCase(noteName)){
                return true;
            }
        }
        return false;
    }

    private String getPathOfNoteOrDirectory(File file){
        return file.getAbsolutePath();
    }

    private void listf(String directoryName) {
        File directory = new File(directoryName);

        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    Note note = new Note(removeExtension(file.getName()));
                    note.setPath(getPathOfNoteOrDirectory(file));
                    searchList.add(note);
                } else if (file.isDirectory()) {
                    Folder folder = new Folder(file.getName());
                    folder.setPath(getPathOfNoteOrDirectory(file));
                    searchList.add(folder);
                    listf(getPathOfNoteOrDirectory(file));
                }
            }
    }

    private void selectFiles(String text){
        for (int i = 0; i < searchList.size(); i++) {
            System.out.println("" + searchList.get(i).getName() + " " + searchList.get(i).getName().contains(text));
            if (!searchList.get(i).getName().contains(text)) {

                searchList.remove(i);

            }
        }
    }

    public void createSearchList(String textToSearch){
        searchList = new ArrayList<>();
        listf(rootPath);
        selectFiles(textToSearch);
    }

    public ArrayList<Section> getSearchList() {
        return searchList;
    }

    public void clearSearchList(){
        searchList.clear();
    }
}

