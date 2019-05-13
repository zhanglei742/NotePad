package com.example.json.mytouzhisystem;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.path;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btnMainActivityKind)
    Button btnMainActivityKind;
    @BindView(R.id.btnMainActivityChangeBg)
    Button btnMainActivityChangeBg;
    @BindView(R.id.llMainActivityAll)
    LinearLayout llMainActivityAll;
   // @BindView(R.id.AddNote)
  //  RelativeLayout AddNote;
    @BindView(R.id.btnMainActivityChaXun)
    Button btnMainActivityChaXun;

    private Button btn_picture, btn_photo, btn_cancle;
    private Bitmap head;// 头像Bitmap

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            callCamera();
        }

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            llMainActivityAll.setBackground(drawable);
        }
    }

    //设置点击事件
    @OnClick({R.id.btnMainActivityKind, R.id.btnMainActivityChangeBg,R.id.btnMainActivityChaXun})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理添加笔记点击事件
            case R.id.btnMainActivityKind:
                toActivity(AddActivity.class);
                break;
            //处理点击更换背景点击事件
            case R.id.btnMainActivityChangeBg:
                showDialog();
                break;
            //处理点击查询笔记点击事件
            case R.id.btnMainActivityChaXun:
                toActivity(DBChaXunActivity.class);
                break;
            default:
                break;
        }
    }


    //处理申请权限的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                int gsize = grantResults.length;
                int grantResult = grantResults[0];
                int flag = 0;
                for (int i = 0; i < gsize; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        flag = 1;
                    }
                }
                if (flag == 0) {
                    Toast.makeText(MainActivity.this, "申请权限成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "you refused the camera function", Toast.LENGTH_SHORT).show();
                    this.finish();
                }
                break;
        }
    }

    public void callCamera() {
        String callPhone = Manifest.permission.CAMERA;
        String writestorage = Manifest.permission.WRITE_EXTERNAL_STORAGE;
        String writeContacts = Manifest.permission.WRITE_CONTACTS;
        String readContacts = Manifest.permission.READ_CONTACTS;
        String callTel = Manifest.permission.CALL_PHONE;
        String[] permissions = new String[]{callPhone, writestorage, writeContacts, readContacts, callTel};
        int selfPermission = ActivityCompat.checkSelfPermission(this, callPhone);
        int selfwrite = ActivityCompat.checkSelfPermission(this, writestorage);
        int selfwritecon = ActivityCompat.checkSelfPermission(this, writeContacts);
        int sefreadcon = ActivityCompat.checkSelfPermission(this, readContacts);
        int sefcallTel = ActivityCompat.checkSelfPermission(this, callTel);
        if (selfPermission != PackageManager.PERMISSION_GRANTED || selfwrite != PackageManager.PERMISSION_GRANTED || selfwritecon != PackageManager.PERMISSION_GRANTED || sefreadcon != PackageManager.PERMISSION_GRANTED || sefcallTel != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {

        }
    }

    public void startphoto() {
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory().toString() + "/DCIM" + "head0.jpg");
        if (file.exists())
            file.delete();
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent2, 2);// 采用ForResult打开
    }


    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        final Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        btn_picture = (Button) window.findViewById(R.id.btn_picture);
        btn_photo = (Button) window.findViewById(R.id.btn_photo);
        btn_cancle = (Button) window.findViewById(R.id.btn_cancle);

        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 3:

                if (data != null) {

                    Bundle extras = data.getExtras();

                    head = extras.getParcelable("data");


                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中

                        Drawable drawable = new BitmapDrawable(head);

                        llMainActivityAll.setBackground(drawable);
                       // AddNote.setBackground(drawable);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1.5);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, 3);

    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;

        File file = new File(String.valueOf(path));
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                if (b != null) {
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
