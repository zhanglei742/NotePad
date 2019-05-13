package com.example.json.mytouzhisystem;

import android.os.Bundle;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//笔记文件详情页
public class NotXiangQingActivity extends BaseActivity {


    @BindView(R.id.tvNotXiangQingActivityTitle)
    TextView tvNotXiangQingActivityTitle;
    @BindView(R.id.edtNotXiangQingTitle)
    EditText edtNotXiangQingTitle;
    @BindView(R.id.edtNotXiangQingTime)
    EditText edtNotXiangQingTime;
    @BindView(R.id.edtNotXiangQingCount)
    NoteEditText edtNotXiangQingCount;
    @BindView(R.id.btnNotXiangQingActivityUpdata)
    Button btnNotXiangQingActivityUpdata;
    @BindView(R.id.btnNotXiangQingActivityDelete)
    Button btnNotXiangQingActivityDelete;
    @BindView(R.id.btnNotXiangQingActivityCacel)
    Button btnNotXiangQingActivityCacel;
    @BindView(R.id.rlNotXiangQingActivity)
    RelativeLayout rlNotXiangQingActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_xiang_qing);
        ButterKnife.bind(this);
        initData();

    }

    public void initData() {
        DBUserInvestment dbUserInvestment = DBUserInvestmentUtils.getInstance().queryData(getIntent().getLongExtra("NotID", 0));
        edtNotXiangQingTitle.setText(dbUserInvestment.getInvestmentCount());
        SimpleDateFormat sdr1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String CreatedTime1 = sdr1.format(new Date(dbUserInvestment.getCreatTimeAsId()));
        edtNotXiangQingTime.setText(CreatedTime1);
        edtNotXiangQingCount.setText(dbUserInvestment.getSign());
        tvNotXiangQingActivityTitle.setText(/*dbUserInvestment.getName()+*/"记录详情");

    }

    //设置点击事件
    @OnClick({R.id.btnNotXiangQingActivityUpdata, R.id.btnNotXiangQingActivityDelete, R.id.btnNotXiangQingActivityCacel})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理点击更新点击事件
            case R.id.btnNotXiangQingActivityUpdata:
                saveNote();
                RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
                NotXiangQingActivity.this.finish();
                break;
            //处理点击删除点击事件
            case R.id.btnNotXiangQingActivityDelete:
                DBUserInvestmentUtils.getInstance().deleteOneDataByKey(getIntent().getLongExtra("NotID", 0));
                RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
                NotXiangQingActivity.this.finish();
                break;
            case R.id.btnNotXiangQingActivityCacel:
                NotXiangQingActivity.this.finish();
                break;
            default:
                break;
        }
    }

    //保存备忘录
    public void saveNote() {
        //取得输入的内容
        String title = edtNotXiangQingTitle.getText().toString().trim();
        String content = edtNotXiangQingCount.getText().toString().trim();
        //内容和标题都不能为空
        if ("".equals(title) || "".equals(content)) {
            Toast.makeText(NotXiangQingActivity.this, "名称和内容都不能为空", Toast.LENGTH_SHORT).show();
        } else {
            DBUserInvestment dbUserInvestment = new DBUserInvestment();
            dbUserInvestment.setCreatTimeAsId(getIntent().getLongExtra("NotID", 0));
            dbUserInvestment.setInvestmentCount(title);
            dbUserInvestment.setSign(content);
            dbUserInvestment.setName(getIntent().getStringExtra("name"));
            DBUserInvestmentUtils.getInstance().updateData(dbUserInvestment);
            Toast.makeText(NotXiangQingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            RxBus.getDefault().post(new DataSaveEvent(ConstKey.SAVE_DATA_SUCCESS));
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

}
