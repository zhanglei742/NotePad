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
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.json.mytouzhisystem.Bean.DBUserInvestment;
import com.example.json.mytouzhisystem.Bean.DataSaveEvent;
import com.example.json.mytouzhisystem.Utils.DBUserInvestmentUtils;
import com.example.json.mytouzhisystem.Utils.RxBus;
import com.example.json.mytouzhisystem.constants.ConstKey;
import com.example.json.mytouzhisystem.view.NoteEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.RunnableFuture;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.path;

//添加笔记页
public class AddActivity extends BaseActivity {
    @BindView(R.id.tvAddActivityTitle)
    TextView tvAddActivityTitle;
    @BindView(R.id.AddNote)
    RelativeLayout AddNote;
    //标题、内容和时间
    private EditText editText_add_title, editText_add_time;
    private NoteEditText noteEditText_add_content;
    private Button btn_picture, btn_photo, btn_cancle;
    private Bitmap head;// 头像Bitmap

    @Override
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);
        initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            callCamera();
        }

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            AddNote.setBackground(drawable);
        }
    }


    private void initView() {
        editText_add_title = (EditText) findViewById(R.id.editText_add_title);
        editText_add_time = (EditText) findViewById(R.id.editText_add_time);
        noteEditText_add_content = (NoteEditText) findViewById(R.id.noteEditText_add_content);
        editText_add_time.setText(formatTime());
    }

    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.button_add_save:
                //保存备忘录信息
                saveNote();
                break;
            case R.id.button_add_cacel:
                AddActivity.this.finish();
                break;
            case R.id.button_add_change:
                showDialog();
                break;
            default:
                break;
        }
    }

    //保存备忘录
    public void saveNote() {
        //取得输入的内容
        String title = editText_add_title.getText().toString().trim();
        String content = noteEditText_add_content.getText().toString().trim();
        String time = editText_add_time.getText().toString().trim();
        //内容和标题都不能为空
        if ("".equals(title) || "".equals(content)) {
            Toast.makeText(AddActivity.this, "名称和内容都不能为空", Toast.LENGTH_SHORT).show();
        } else {
            DBUserInvestment dbUserInvestment = new DBUserInvestment();
            dbUserInvestment.setCreatTimeAsId(getTime());
            dbUserInvestment.setInvestmentCount(title);
            dbUserInvestment.setSign(content);
            dbUserInvestment.setName(getIntent().getStringExtra("name"));
            DBUserInvestmentUtils.getInstance().insertOneData(dbUserInvestment);
            RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
            Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            AddActivity.this.finish();
        }


    }

    //返回当前的时间
    public String formatTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(d);
        return time;
    }

    public long getTime() {
        return System.currentTimeMillis();//获取系统时间戳
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

                        AddNote.setBackground(drawable);
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
