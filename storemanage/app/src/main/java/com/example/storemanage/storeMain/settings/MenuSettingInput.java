package com.example.storemanage.storeMain.settings;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.storemanage.R;
import com.example.storemanage.model.SettintMenuModel;
import com.example.storemanage.sign.SignActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class MenuSettingInput extends AppCompatActivity {
    private static final int PICK_FRON_ALBUM = 10;

    ImageView menuImage;
    EditText name;
    EditText price;
    EditText option;

    Button buttonOk;
    Button buttonCancle;


    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_setting_input);

        menuImage = (ImageView)findViewById(R.id.menusetting_input_dialog_image);
        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FRON_ALBUM);
            }
        });


        name = (EditText) findViewById(R.id.menusetting_input_dialog_name);
        price = (EditText) findViewById(R.id.menusetting_input_dialog_price);
        option = (EditText) findViewById(R.id.menusetting_input_dialog_option);


        buttonOk = (Button)findViewById(R.id.menusetting_input_dialog_button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().trim().equals("")
                        || price.getText().toString().trim().equals("")
                        || option.getText().toString().trim().equals("")
                        || imageUri == null){
                    return ;

                }else {
                    final String uid = FirebaseAuth.getInstance().getUid();
                    final StorageReference profileImageRef = FirebaseStorage.getInstance()
                            .getReference()
                            .child("menuImage")
                            .child(uid);

                    profileImageRef
                            .putFile(imageUri)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();

                                    while(!uriTask.isComplete());
                                    Uri downloadUrl = uriTask.getResult();
                                    String imageUrl = downloadUrl.toString();

                                    Log.e("url1", imageUrl);

                                    FirebaseDatabase.getInstance().getReference()
                                            .child("users").child(uid).child("menus").push()
                                            .setValue(new SettintMenuModel(imageUrl, name.getText().toString(), price.getText().toString(), option.getText().toString()));

                                }})
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                }
                            });


                    Toast.makeText(getApplicationContext(), uid + "메뉴 추가 완료", Toast.LENGTH_SHORT).show();

                    finish();

                }

            }
        });

        buttonCancle = (Button)findViewById(R.id.menusetting_input_dialog_button_cancle);
        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_FRON_ALBUM && resultCode == RESULT_OK){
            menuImage.setImageURI(data.getData()); // Profile ImageView 변경 (이미지 출력 부분)
            imageUri = data.getData(); //이미지 경로 저장, 이미지 원본 스트림 (이미지 데이터 부분)

        }
    }


}
