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

import android.Manifest;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        initViews();
        printTextIfNecessary();
    }

    private void initViews(){
        et_title = findViewById(R.id.et_title);
        et_text = findViewById(R.id.et_text);
    }

    public void buttonReset(View view){
        String title = et_title.getText().toString().trim();
        String text = et_text.getText().toString().trim();
        if(!nullOrEmpty(title) || !nullOrEmpty(text)){
            resetNoteAlertDialog();
        }

    }

    private void resetAction(){
        et_title.setText("");
        et_text.setText("");
    }

    public void buttonBack(View view){
        finish();
    }

    public void buttonSave(View view){
        if(!storagePermissionGranted()){
            showToast("Permission Not Granted");
            return;
        }
        String title = et_title.getText().toString().trim();
        String text = et_text.getText().toString().trim();
        if(!nullOrEmpty(title) && !nullOrEmpty(text)){
            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("text", text);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            showToast(getString(R.string.settextandtitle));
        }


    }

    private boolean nullOrEmpty(String str){
        if (str == null || str.equalsIgnoreCase("")){
            return true;
        }
        return false;
    }

    private void printTextIfNecessary(){
        String title = getIntent().getStringExtra("title");
        String text = getIntent().getStringExtra("text");
        if(!nullOrEmpty(title) || nullOrEmpty(text) ){
            et_title.setText(title);
            et_text.setText(text);
        }
    }

    private void resetNoteAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.deleted));
        builder.setMessage(getString(R.string.deletenotecontent));
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast(getString(R.string.actioncanceled));
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               resetAction();
            }
        });
        builder.show();
    }

    private void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int checkSelfPermission(String permission) {
        return super.checkSelfPermission(permission);
    }

    //return 0 if both granted
    //return -1 if 1 of them not granted or none are granted
    private boolean storagePermissionGranted(){
        int writeExternalStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission == -1){
            return false;
        }
        return true;
    }

}
