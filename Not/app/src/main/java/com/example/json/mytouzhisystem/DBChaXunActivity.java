package com.example.json.mytouzhisystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.json.mytouzhisystem.Bean.DBUserInvestment;
import com.example.json.mytouzhisystem.Utils.DBUserInvestmentUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


//笔记查询
public class DBChaXunActivity extends BaseActivity {

    @BindView(R.id.lvDBChuXuActivity)
    ListView lvDBChuXuActivity;
    @BindView(R.id.edtMainActivity)
    EditText edtMainActivity;
    @BindView(R.id.tvMainActivityChaXun)
    TextView tvMainActivityChaXun;
    @BindView(R.id.llMainActivitySend)
    LinearLayout llMainActivitySend;

    private List<DBUserInvestment> resultDaoList = new ArrayList<>();
    private MyKindAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbchu_xu);
        ButterKnife.bind(this);
        setEditClick();
        getListViewData();
        setChaXunClick();
        setListViewClick();
    }

    public void getListViewData(){
        resultDaoList = DBUserInvestmentUtils.getInstance().queryData();
        if (resultDaoList!=null&&resultDaoList.size()>0){
            lvDBChuXuActivity.setVisibility(View.VISIBLE);
            myAdapter = new MyKindAdapter(DBChaXunActivity.this);
            myAdapter.setLists(resultDaoList);
            lvDBChuXuActivity.setAdapter(myAdapter);
        }else {
            lvDBChuXuActivity.setVisibility(View.GONE);
        }
    }

    public void setListViewClick() {
            lvDBChuXuActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(DBChaXunActivity.this, NotXiangQingActivity.class);
                    intent.putExtra("NotID", resultDaoList.get(position).getCreatTimeAsId());
                    intent.putExtra("name", resultDaoList.get(position).getInvestmentCount());
                    startActivity(intent);
                }
            });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void setEditClick() {
        edtMainActivity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvMainActivityChaXun.setTextColor(ContextCompat.getColor(DBChaXunActivity.this, R.color.attention_others_activity_log_in_text_color));
                if (TextUtils.isEmpty(edtMainActivity.getText().toString())) {
                    tvMainActivityChaXun.setTextColor(ContextCompat.getColor(DBChaXunActivity.this, R.color.comment_activity_send_text_color));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(DBChaXunActivity.this.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

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

}
