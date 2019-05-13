package com.example.json.mytouzhisystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.json.mytouzhisystem.Bean.DBUserInvestment;
import com.example.json.mytouzhisystem.Bean.DataSaveEvent;
import com.example.json.mytouzhisystem.Utils.DBUserInvestmentUtils;
import com.example.json.mytouzhisystem.Utils.RxBus;
import com.example.json.mytouzhisystem.constants.ConstKey;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class DBBaoXianActivity extends BaseActivity {

    @BindView(R.id.btnDBBaoXianActivityAdd)
    Button btnDBBaoXianActivityAdd;
    @BindView(R.id.btnDBBaoXianActivityZhiXun)
    Button btnDBBaoXianActivityZhiXun;
    @BindView(R.id.lvDBBaoXianActivity)
    ListView lvDBBaoXianActivity;
    private Subscription rxSubscription;
    private List<DBUserInvestment> resultDaoList = new ArrayList<>();
    private MyKindAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbbao_xian);
        ButterKnife.bind(this);
        refreshMediaUpdateEvent();
        getListViewData();
        setListViewClick();

    }

    public void getListViewData(){

        resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependMedia("保险");
        if (resultDaoList!=null&&resultDaoList.size()>0){
            lvDBBaoXianActivity.setVisibility(View.VISIBLE);
            myAdapter = new MyKindAdapter(DBBaoXianActivity.this);
            myAdapter.setLists(resultDaoList);
            lvDBBaoXianActivity.setAdapter(myAdapter);
        }else {
            lvDBBaoXianActivity.setVisibility(View.GONE);
        }
    }


    //设置点击事件
    @OnClick({R.id.btnDBBaoXianActivityAdd, R.id.btnDBBaoXianActivityZhiXun})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理点击添加点击事件
            case R.id.btnDBBaoXianActivityAdd:
                Intent intent = new Intent(DBBaoXianActivity.this, AddActivity.class);
                intent.putExtra("name", "保险");
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void setListViewClick(){
        lvDBBaoXianActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DBBaoXianActivity.this,NotXiangQingActivity.class);
                intent.putExtra("NotID",  resultDaoList.get(position).getCreatTimeAsId());
                intent.putExtra("name", "保险");
                startActivity(intent);
            }
        });
    }

    public void refreshMediaUpdateEvent() {
        rxSubscription = RxBus.getDefault()
                .toObservable(DataSaveEvent.class)
                .subscribe(new Action1<DataSaveEvent>() {
                    @Override
                    public void call(DataSaveEvent dataSaveEvent) {
                        if (ConstKey.SAVE_DATA_SUCCESS.equals(dataSaveEvent.getDataSaveEvent())) {
                            resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependMedia("保险");;
                            if (myAdapter ==null){
                                myAdapter = new MyKindAdapter(DBBaoXianActivity.this);
                            }
                            if (resultDaoList!=null&&resultDaoList.size()>0){
                                lvDBBaoXianActivity.setVisibility(View.VISIBLE);
                                myAdapter.setLists(resultDaoList);
                                lvDBBaoXianActivity.setAdapter(myAdapter);
                            }else {
                                lvDBBaoXianActivity.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterRxBus();
    }

    private void unregisterRxBus() {
        if (rxSubscription != null && !rxSubscription.isUnsubscribed()) {
            rxSubscription.unsubscribe();
        }
    }
}
