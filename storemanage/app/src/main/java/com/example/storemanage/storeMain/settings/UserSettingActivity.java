package com.example.storemanage.storeMain.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.storemanage.R;
import com.example.storemanage.model.SettintMenuModel;
import com.example.storemanage.model.User;
import com.example.storemanage.model.UserSettingModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserSettingActivity extends AppCompatActivity {
    private static final int PICK_FRON_ALBUM = 10;

    ImageView imageView;
    TextView textViewName;
    TextView textViewPhone;
    TextView textViewStatus;

    LinearLayout layoutName;
    LinearLayout layoutPhone;
    LinearLayout layoutStatus;

    UserSettingModel userSettingModel;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);

        userSettingModel = new UserSettingModel();

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("storeBasicInfo")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        userSettingModel = dataSnapshot.getValue(UserSettingModel.class);
                        textViewName.setText(userSettingModel.getName());
                        textViewPhone.setText(userSettingModel.getPhone());

                        Glide.with(imageView.getContext())
                                .load(userSettingModel.getImageUrl())
                                //.apply(new RequestOptions().circleCrop())
                                .into(imageView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("storeStateInfo")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        textViewStatus.setText(dataSnapshot.getValue().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
        imageView = (ImageView) findViewById(R.id.usersetting_imageview);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, PICK_FRON_ALBUM);
            }
        });


        textViewName = (TextView) findViewById(R.id.usersetting_textview_name);
        textViewPhone = (TextView) findViewById(R.id.usersetting_textview_phone);
        textViewStatus = (TextView) findViewById(R.id.usersetting_textview_status);

        layoutName = (LinearLayout) findViewById(R.id.usersetting_layout_name);
        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(UserSettingActivity.this);

                ad.setTitle("매장명 설정");       // 제목 설정
                ad.setMessage("매장의 이름을 입력하세요.");   // 내용 설정

                // EditText 삽입하기
                final EditText et = new EditText(UserSettingActivity.this);
                ad.setView(et);

                // 확인 버튼 설정
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("Yes Btn Click", "");

                        userSettingModel.setName(et.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("storeBasicInfo")
                                .setValue(userSettingModel);
                        // Text 값 받아서 로그 남기기

                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 취소 버튼 설정
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("No Btn Click", "");
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 창 띄우기
                ad.show();

            }
        });

        layoutPhone = (LinearLayout) findViewById(R.id.usersetting_layout_phone);
        layoutPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(UserSettingActivity.this);

                ad.setTitle("전화번호 설정");       // 제목 설정
                ad.setMessage("매장의 전화번호를 입력하세요.");   // 내용 설정

                // EditText 삽입하기
                final EditText et = new EditText(UserSettingActivity.this);
                ad.setView(et);

                // 확인 버튼 설정
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("Yes Btn Click", "");

                        userSettingModel.setPhone(et.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("storeBasicInfo")
                                .setValue(userSettingModel);
                        // Text 값 받아서 로그 남기기

                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 취소 버튼 설정
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("No Btn Click", "");
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 창 띄우기
                ad.show();

            }
        });

        layoutStatus = (LinearLayout) findViewById(R.id.usersetting_layout_status);
        layoutStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(UserSettingActivity.this);

                ad.setTitle("상태 설정");       // 제목 설정
                ad.setMessage("매장의 상태를 입력하세요.");   // 내용 설정

                // EditText 삽입하기


                Button button1 = new Button(UserSettingActivity.this);
                button1.setText("영업중");
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean flag = button1.getText().toString().equals("영업중") ? false : true;
                        ;

                        if (flag) {
                            button1.setText("영업중");
                            button1.setTextColor(Color.BLACK);
                            button1.setBackgroundColor(Color.WHITE);

                        } else {
                            button1.setText("준비중");
                            button1.setTextColor(Color.WHITE);
                            button1.setBackgroundColor(Color.BLACK);
                        }

                    }
                });
                ad.setView(button1);
                // 확인 버튼 설정
                ad.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("Yes Btn Click", "");


                        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getUid()).child("storeStateInfo")
                                .setValue(button1.getText());
                        // Text 값 받아서 로그 남기기


                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 취소 버튼 설정
                ad.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v("No Btn Click", "");
                        dialog.dismiss();     //닫기
                        // Event
                    }
                });

// 창 띄우기
                ad.show();


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FRON_ALBUM && resultCode == RESULT_OK) {
            imageView.setImageURI(data.getData()); // Profile ImageView 변경 (이미지 출력 부분)
            imageUri = data.getData(); //이미지 경로 저장, 이미지 원본 스트림 (이미지 데이터 부분)


            String uid = FirebaseAuth.getInstance().getUid();
            StorageReference profileImageRef = FirebaseStorage.getInstance()
                    .getReference()
                    .child("userImages")
                    .child(uid);

            profileImageRef
                    .putFile(imageUri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();

                            while (!uriTask.isComplete()) ;
                            Uri downloadUrl = uriTask.getResult();
                            String imageUrl = downloadUrl.toString();

                            Log.e("url1", imageUrl);

                            FirebaseDatabase.getInstance().getReference()
                                    .child("users").child(uid).child("storeBasicInfo").child("imageUrl").setValue(imageUrl);

                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });


            //Toast.makeText(getApplicationContext(), uid + "메뉴 추가 완료", Toast.LENGTH_SHORT).show();

            //finish();

        }

    }
}
