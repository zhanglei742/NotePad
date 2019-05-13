# NotePad
## 简介
手机备忘录APP功能为：添加、查询、修改、删除笔记内容，以及更换背景。
## 实现要求
基本要求：</br>
（1）	增加时间戳</br>
（2）	标题查询</br>
附加功能：</br>
（1）更改背景</br>
（2）界面优化</br>
## 功能展示
打开APP，进入主菜单界面。
![Alt text](https://raw.githubusercontent.com/mingxikay/NotePad/master/Not/ScreenShots/主界面.jpg)
点击“添加笔记”按钮，进入笔记编辑界面。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/添加笔记.jpg)
点击“保存”按钮，将笔记保存至本地数据库。</br>
在主菜单界面点击“查询笔记”按钮，进入笔记列表。<font color=#FF0000>注:此处未输入查询内容时，默认为显示所有笔记。</font></br>
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/笔记列表.jpg)
在文本框中输入要查询的标题，点击“查询”按钮，则在列表中显示相应标题的笔记。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/笔记标题查询.jpg)
此处支持模糊查询。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/标题模糊查询.jpg)
输入不存在的标题时，无内容显示，并出现提示。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/未存在标题查询提示.jpg)
点击列表中的笔记，进入笔记修改界面。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/笔记修改.jpg)
点击“保存修改”按钮，则将修改内容保存至数据库。</br>
在主界面中，点击“更改皮肤”按钮，则弹出选择框。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/更换背景1.jpg)
点击“图库”按钮，进入手机本地图片库。</br>
选择一张图片，进行下一步操作。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/更换背景2.jpg)
点击“确定”选择图片，进入图片裁剪。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/更换背景3.jpg)
裁剪完成，成功显示于主菜单背景。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/更换背景4.jpg)
“拍照”添加背景的功能由于比较丑的关系，在此处就不多加赘述，操作和“图库”添加背景差不多。
在添加笔记的界面中，同样有“更换皮肤”的功能，操作同上。此处仅显示操作完之后的结果。
![Alt text](https://github.com/mingxikay/NotePad/blob/master/Not/ScreenShots/更换背景5.jpg)
## 代码详解
### 时间戳功能
```JAVA
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
```
直接使用系统自带函数SimpleDateFormat来格式化时间，格式定为"yyyy-MM-dd HH:mm:ss"。

### 查询功能
```JAVA
       public void setChaXunClick(){
            tvMainActivityChaXun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvMainActivityChaXun.setClickable(false);
                String content = edtMainActivity.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(DBChaXunActivity.this, "请输入笔记标题后再查询", Toast.LENGTH_SHORT).show();
                    tvMainActivityChaXun.setClickable(true);
                } else {
                    if ((resultDaoList!=null)){
                        resultDaoList.clear();
                        resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependNotTitle(content);

                        if (myAdapter == null) {
                            myAdapter = new MyKindAdapter(DBChaXunActivity.this);
                        }
                        if (resultDaoList != null && resultDaoList.size() > 0) {
                            lvDBChuXuActivity.setVisibility(View.VISIBLE);
                            myAdapter.setLists(resultDaoList);
                            lvDBChuXuActivity.setAdapter(myAdapter);
                        } else {
                            Toast.makeText(DBChaXunActivity.this, "此笔记标题未录入，请输入其他标题查询", Toast.LENGTH_SHORT).show();
                            lvDBChuXuActivity.setVisibility(View.GONE);
                        }

                        hideSoftInput(v.getWindowToken());
                        tvMainActivityChaXun.setClickable(true);
                    }

                }
            }
        });
    }
```    
### 更换皮肤
```JAVA    
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

        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从Sd中找图片，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            AddNote.setBackground(drawable);
        }
    }
```    
### 附加：裁剪图片的过程：
```JAVA 
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
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 调用系统的裁剪
     *
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
```
