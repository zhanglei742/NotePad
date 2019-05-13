package com.example.json.mytouzhisystem.Bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;


@Entity // 标识实体类，greenDAO会映射成sqlite的一个表，表名为实体类名的大写形式
public class DBUserInvestment {
    @Id(autoincrement = false)
    public long creatTimeAsId;  //把创建时间作为表的ID
    @Property(nameInDb = "DBUserInvestment")
    public String name;  // 每个项目名称
    public String InvestmentCount;//笔记标题
    public String sign;  // 项目描述
    @Generated(hash = 1349221561)
    public DBUserInvestment(long creatTimeAsId, String name, String InvestmentCount,
            String sign) {
        this.creatTimeAsId = creatTimeAsId;
        this.name = name;
        this.InvestmentCount = InvestmentCount;
        this.sign = sign;
    }
    @Generated(hash = 1378341061)
    public DBUserInvestment() {
    }
    public long getCreatTimeAsId() {
        return this.creatTimeAsId;
    }
    public void setCreatTimeAsId(long creatTimeAsId) {
        this.creatTimeAsId = creatTimeAsId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getInvestmentCount() {
        return this.InvestmentCount;
    }
    public void setInvestmentCount(String InvestmentCount) {
        this.InvestmentCount = InvestmentCount;
    }
    public String getSign() {
        return this.sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }

}
