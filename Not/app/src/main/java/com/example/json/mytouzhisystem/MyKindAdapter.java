package com.example.json.mytouzhisystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.json.mytouzhisystem.Bean.DBUserInvestment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//主界面 也是首界面
public class MyKindAdapter extends BaseAdapter {

    private List<DBUserInvestment> dbUserInvestmentList;
    private LayoutInflater inflater;
    private MyVidewHolder myViewHolder;

    public MyKindAdapter( Context context) {
        inflater = LayoutInflater.from(context);
    }
    public void setLists(List<DBUserInvestment> dbUserInvestmentList) {
        this.dbUserInvestmentList = dbUserInvestmentList;

    }

    @Override
    public int getCount() {
        return dbUserInvestmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return dbUserInvestmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_db_chu_xu_activity, null);
            myViewHolder = new MyVidewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyVidewHolder) convertView.getTag();
        }
        SimpleDateFormat sdr1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        String CreatedTime1 = sdr1.format(new Date(dbUserInvestmentList.get(position).getCreatTimeAsId()));
        myViewHolder.tvItemTime.setText("笔记记录时间:" + CreatedTime1);
        myViewHolder.tvItemCount.setText("笔记标题："+dbUserInvestmentList.get(position).getInvestmentCount());
        myViewHolder.tvItemSign.setText("笔记内容："+dbUserInvestmentList.get(position).getSign());
        return convertView;
    }

    class MyVidewHolder {
        @BindView(R.id.tvItemTitle)
        TextView tvItemTitle;
        @BindView(R.id.tvItemCount)
        TextView tvItemCount;
        @BindView(R.id.tvItemTime)
        TextView tvItemTime;
        @BindView(R.id.tvItemSign)
        TextView tvItemSign;

        MyVidewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


