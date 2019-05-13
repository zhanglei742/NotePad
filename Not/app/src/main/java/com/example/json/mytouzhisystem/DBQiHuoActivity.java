package com.example.json.mytouzhisystem;

import android.content.Intent;
import android.os.Bundle;
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

public class DBQiHuoActivity extends BaseActivity {

    @BindView(R.id.btnDBQihuoActivityAdd)
    Button btnDBQihuoActivityAdd;
    @BindView(R.id.btnDBQihuoActivityZhiXun)
    Button btnDBQihuoActivityZhiXun;
    @BindView(R.id.lvDBQihuoActivity)
    ListView lvDBQihuoActivity;
    private Subscription rxSubscription;
    private List<DBUserInvestment> resultDaoList = new ArrayList<>();
    private MyKindAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbqi_huo);
        ButterKnife.bind(this);
        refreshMediaUpdateEvent();
        getListViewData();
        setListViewClick();
    }

    public void getListViewData(){

        resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependMedia("期货");
        if (resultDaoList!=null&&resultDaoList.size()>0){
            lvDBQihuoActivity.setVisibility(View.VISIBLE);
            myAdapter = new MyKindAdapter(DBQiHuoActivity.this);
            myAdapter.setLists(resultDaoList);
            lvDBQihuoActivity.setAdapter(myAdapter);
        }else {
            lvDBQihuoActivity.setVisibility(View.GONE);
        }
    }


    //设置点击事件
    @OnClick({R.id.btnDBQihuoActivityAdd,R.id.btnDBQihuoActivityZhiXun})
    public void onClick(View view) {
        switch (view.getId()) {
            //处理点击添加点击事件
            case R.id.btnDBQihuoActivityAdd:
                Intent intent = new Intent(DBQiHuoActivity.this, AddActivity.class);
                intent.putExtra("name", "期货");
                startActivity(intent);
                break;


            default:
                break;
        }
    }

    public void refreshMediaUpdateEvent() {
        rxSubscription = RxBus.getDefault()
                .toObservable(DataSaveEvent.class)
                .subscribe(new Action1<DataSaveEvent>() {
                    @Override
                    public void call(DataSaveEvent dataSaveEvent) {
                        if (ConstKey.SAVE_DATA_SUCCESS.equals(dataSaveEvent.getDataSaveEvent())) {
                            resultDaoList = DBUserInvestmentUtils.getInstance().queryDataDependMedia("期货");
                            if (myAdapter ==null){
                                myAdapter = new MyKindAdapter(DBQiHuoActivity.this);
                            }
                            if (resultDaoList!=null&&resultDaoList.size()>0){
                                lvDBQihuoActivity.setVisibility(View.VISIBLE);
                                myAdapter.setLists(resultDaoList);
                                lvDBQihuoActivity.setAdapter(myAdapter);
                            }else {
                                lvDBQihuoActivity.setVisibility(View.GONE);
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
    public void setListViewClick(){
        lvDBQihuoActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DBQiHuoActivity.this,NotXiangQingActivity.class);
                intent.putExtra("NotID",  resultDaoList.get(position).getCreatTimeAsId());
                intent.putExtra("name", "期货");
                startActivity(intent);
            }
        });
    }
}
