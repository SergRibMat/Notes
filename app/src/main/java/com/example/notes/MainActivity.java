package com.example.notes;

import android.view.MenuItem;
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
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private MyFilesHandler myFilesHandler;
    private EditText et_folder;

    private SectionRecicleAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private MainController mainController;
    private SectionList sectionList;
    private AdapterDataUpdater adapterDataUpdater;
    private boolean storagePermissionGrantedVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        storagePermissionGrantedVariable = storagePermissionGranted();
        //requestReadWritePermissions();

        askPermissionsWithDexter();

        setUp();

    }

    private void askPermissionsWithDexter(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        storagePermissionGrantedVariable = storagePermissionGranted();
                        setUp();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        showToast("Permission Denied");
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
                        builder.setCancelable(true);
                        builder.setTitle(getString(R.string.newgroup));
                        builder.setMessage(getString(R.string.entername));
                        builder.setView(et_folder);
                        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                showToast(R.string.actioncanceled);
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        builder.show();
                    }
                }).check();
    }

    private void setUp(){
        if(!storagePermissionGrantedVariable){
            return;
        }
        MainActContext.setContext(this);
        myFilesHandler = new MyFilesHandler(this);
        sectionList = new SectionList(myFilesHandler.listInstance());
        setUpRecyclerView();
        adapterDataUpdater = new AdapterDataUpdater(adapter, sectionList);
        mainController = new MainController(myFilesHandler, sectionList, adapter, adapterDataUpdater);
        adapter.setAdapterDataUpdater(adapterDataUpdater);

        et_folder = createFolderEditText();
    }

    /*private void requestReadWritePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }
    }*/

    private void setUpRecyclerView() {

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new SectionRecicleAdapter(sectionList, recyclerView, myFilesHandler);
        recyclerView.setAdapter(adapter);
    }



    public void buttonNewNote(View view){
        if(!storagePermissionGrantedVariable){
            showToast("Permission not granted");
            return;
        }
        Intent i = new Intent(this, CreateNoteActivity.class);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){

            if (resultCode == RESULT_OK){

                String title = data.getStringExtra("title");
                String text = data.getStringExtra("text");

                if (myFilesHandler.searchIfNoteAlreadyExists(title)){

                    overrideAlertDialog(title,text);
                    return;
                }
                Note note = myFilesHandler.createFile(title, text);
                adapterDataUpdater.insertSingleItem(note);
                showToast(R.string.notecreated);
            }

            if(resultCode == RESULT_CANCELED){
            }
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("Permission Granted");

            } else {
                //execute setUpPermission not granted
                showToast(R.string.permissionnotgranted);

            }
        }
    }*/

    public void createNewFolder(){
        if(!storagePermissionGrantedVariable){
            showToast("Permission not granted");
            return;
        }
        String name = et_folder.getText().toString().trim();
        if (!nullOrEmpty(name)){
            if (myFilesHandler.searchIfNoteAlreadyExists(name)){
                overrideAlertDialog(name);
            }else{
                mainController.createFolderMethod(new Folder(name));
            }
        }else{
            showToast(R.string.settitle);
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

    private void showToast(int text){

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    private void overrideAlertDialog(final String title, final String text){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.alreadycreated));
        builder.setMessage(getString(R.string.replacesetmessage1) + title + getString(R.string.replacesetmessage2));
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showToast(R.string.actioncanceled);
                dialogInterface.cancel();
            }
        });

        builder.setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Note nota = myFilesHandler.createFile(title, text);
                showToast(R.string.notecreated);
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void overrideAlertDialog(final String name){

        showToast(getString(R.string.groupalreadyexists1) + name + getString(R.string.groupalreadyexists2));
    }

    public void createFolderAlertDialog(View v){
        if(!storagePermissionGrantedVariable){
            showToast("Permission not granted");
            return;
        }

        if(et_folder.getParent() != null) {
            ((ViewGroup)et_folder.getParent()).removeView(et_folder); // esto soluciona el problema java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
        }
        et_folder = createFolderEditText();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActContext.getContext());
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.newgroup));
        builder.setMessage(getString(R.string.entername));
        builder.setView(et_folder);
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                showToast(R.string.actioncanceled);
                dialogInterface.cancel();
            }
        }).setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
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

    @Override
    public int checkSelfPermission(String permission) {
        return super.checkSelfPermission(permission);
    }

    //return 0 if both granted
    //return -1 if 1 of them not granted or none are granted
    private boolean storagePermissionGranted(){
        int writeExternalStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //int readExternalStoragePermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
        if (writeExternalStoragePermission == -1){
            return false;
        }
        /*if(readExternalStoragePermission == -1){
            return false;
        }*/
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0://delete action
                int itemPosition = item.getOrder();
                String itemTitle = item.getTitle().toString();
                String sectionName = fromMenuTitleToSectionName(itemTitle);
                showToast(myFilesHandler.deleteFileOrFolder(sectionName));
                adapterDataUpdater.removeSingleItem(itemPosition);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public String fromMenuTitleToSectionName(String title){
        String[] separatedTitle = title.split(" ", 2);
        return separatedTitle[1];
    }
}
