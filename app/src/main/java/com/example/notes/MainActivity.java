package com.example.notes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private MyFilesHandler myFilesHandler;
    private TextView tv_path;
    private EditText et_folder;


    private SectionRecicleAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchView searchView;

    private MainController mainController;
    private SectionList sectionList;
    private AdapterDataUpdater adapterDataUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainActContext.setContext(this);
        myFilesHandler = new MyFilesHandler(this);
        sectionList = new SectionList(myFilesHandler.listInstance());
        filesMessage(myFilesHandler);
        setUpRecyclerView();
        requestReadWritePermissions();
        adapterDataUpdater = new AdapterDataUpdater(adapter, sectionList);
        mainController = new MainController(myFilesHandler, sectionList, adapter, adapterDataUpdater);
        adapter.setAdapterDataUpdater(adapterDataUpdater);
        tv_path = findViewById(R.id.tv_path);
        tv_path.setText(myFilesHandler.getUpdatedPath());

        et_folder = createFolderEditText();

        //initSearchViewListeners();
    }

    private void requestReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
    }

    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new SectionRecicleAdapter(sectionList, recyclerView, myFilesHandler);
        recyclerView.setAdapter(adapter);
    }



    public void buttonNewNote(View view){
        Intent i = new Intent(this, CreateNoteActivity.class);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        tv_path.setText(myFilesHandler.getUpdatedPath());
        if (requestCode == 2){

            if (resultCode == RESULT_OK){

                String title = data.getStringExtra("title");
                String text = data.getStringExtra("text");
                //aqui cabe el codigo para comprobar si la nota ya existe en la propia carpeta en la que estas
                if (myFilesHandler.searchIfNoteAlreadyExists(title)){

                    overrideAlertDialog(title,text);
                    return;
                }
                Note note = myFilesHandler.createFile(title, text);
                adapterDataUpdater.insertSingleItem(note);
                Toast.makeText(this, "Nota creada" , Toast.LENGTH_SHORT).show();
            }

            if(resultCode == RESULT_CANCELED){
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                showToast("Permiso concedido");
            } else {

                showToast("Permiso no concedido");
                finish();
            }
        }
    }

    private void filesMessage(MyFilesHandler a){
        if(a.getIsEmptry()){
            showToast("No hay archivos");
        }else{
            showToast("Hay archivos");
        }
    }

    public void createNewFolder(){

        String name = et_folder.getText().toString().trim();
        if (!nullOrEmpty(name)){
            if (myFilesHandler.searchIfNoteAlreadyExists(name)){
                overrideAlertDialog(name);
            }else{
                mainController.createFolderMethod(new Folder(name));
            }
        }else{
            showToast("No has intoducido un titulo");
        }
    }

    @Override
    public void onBackPressed() {

        if (myFilesHandler.getRootPath().equals(myFilesHandler.getUpdatedPath())){

            super.onBackPressed();
        }else{

            myFilesHandler.removeNameFromUpdatedPath();
            sectionList.setList(myFilesHandler.listInstance());
            adapterDataUpdater.updateAdapter();
        }

    }

    private void showToast(String text){

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void overrideAlertDialog(final String title, final String text){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
        builder.setCancelable(true);
        builder.setTitle("Ya existe");
        builder.setMessage("El elemento " + title + " ya existe. ¿Quieres sustituirlo?");
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(MainActContext.getContext(), "accion cancelada", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Note nota = myFilesHandler.createFile(title, text);
                showToast("Nota Creada");
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void overrideAlertDialog(final String name){

        showToast("El grupo " + name + " ya existe");
    }

    public void createFolderAlertDialog(View v){

        if(et_folder.getParent() != null) {
            ((ViewGroup)et_folder.getParent()).removeView(et_folder); // esto soluciona el problema java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
        }
        et_folder = createFolderEditText();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
        builder.setCancelable(true);
        builder.setTitle("Crear grupo");
        builder.setMessage("Introduce el nombre del grupo");
        builder.setView(et_folder);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(MainActContext.getContext(), "accion cancelada", Toast.LENGTH_SHORT).show();
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                createNewFolder();
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private boolean nullOrEmpty(String str){
        if (str == null || str.equalsIgnoreCase("")){
            return true;
        }
        return false;
    }

    private EditText createFolderEditText(){
        EditText myEditText = new EditText(this); // Pass it an Activity or Context
        myEditText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)); // Pass two args; must be LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, or an integer pixel value.
        return myEditText;
    }

    private void initSearchViewListeners(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //este metodo se ejecuta cuando se le da a la lupa del techado.
                showToast("esto es Submit");
                return false;
            }
            /**
             *el metodo onQueryTextChange se ejecuta cada vez que introduces una letra
             * tambien se ejecuta cuando le das a la X del buscador para borrar su contenido
             */

            @Override
            public boolean onQueryTextChange(String newText) {
                showToast("esto es change");
                return false;
            }
        });
    }

}
