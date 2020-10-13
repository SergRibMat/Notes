package com.example.notes;

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
        }else{
            showToast("Ya esta en blanco");
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
        String title = et_title.getText().toString().trim();
        String text = et_text.getText().toString().trim();
        if(!nullOrEmpty(title) && !nullOrEmpty(text)){
            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("text", text);
            setResult(RESULT_OK, intent);
            finish();
        }else{
            showToast("Introduce titulo y texto");
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
        builder.setTitle("Borrado");
        builder.setMessage("¿Desea borrar todo el contenido de la nota?");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                showToast("accion cancelada");
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
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

}
