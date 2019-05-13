package com.example.json.mytouzhisystem.Utils;

import android.content.Context;

import com.aidebar.greendaotest.gen.DBUserInvestmentDao;
import com.aidebar.greendaotest.gen.DaoManager;
import com.example.json.mytouzhisystem.Bean.DBUserInvestment;

import java.util.List;


public class DBUserInvestmentUtils {

    private DBUserInvestmentDao dbUserInvestmentDao ;
    private static DBUserInvestmentUtils dbUserInvestmentUtils=null;

    public DBUserInvestmentUtils  (Context context){
        dbUserInvestmentDao= DaoManager.getInstance(context).getNewSession().getDBUserInvestmentDao();
    }

    public static DBUserInvestmentUtils getInstance(){
        return dbUserInvestmentUtils;
    }
    public static void Init(Context context){
        if(dbUserInvestmentUtils == null){
            dbUserInvestmentUtils=new  DBUserInvestmentUtils(context);
        }
    }

    /**
     * 完成对数据库中插入一条数据操作
     * @param dbUserInvestment
     * @return
     */
    public void insertOneData(DBUserInvestment dbUserInvestment){
        dbUserInvestmentDao.insertOrReplace(dbUserInvestment);
    }

    /**
     * 完成对数据库中插入多条数据操作
     * @param dbUserInvestmentList
     * @return
     */
    public boolean insertManyData(List<DBUserInvestment> dbUserInvestmentList){
        boolean flag = false;
        try{
            dbUserInvestmentDao.insertOrReplaceInTx(dbUserInvestmentList);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据操作
     * @param dbUserInvestment
     * @return
     */
    public boolean deleteOneData(DBUserInvestment dbUserInvestment){
        boolean flag = false;
        try{
            dbUserInvestmentDao.delete(dbUserInvestment);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中删除一条数据 ByKey操作
     * @return
     */
    public boolean deleteOneDataByKey(long id){
        boolean flag = false;
        try{
            dbUserInvestmentDao.deleteByKey(id);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库中批量删除数据操作
     * @return
     */
    public boolean deleteManData(List<DBUserInvestment> dbUserInvestmentList){
        boolean flag = false;
        try{
            dbUserInvestmentDao.deleteInTx(dbUserInvestmentList);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库更新数据操作
     * @return
     */
    public boolean updateData(DBUserInvestment dbUserInvestment){
        boolean flag = false;
        try{
            dbUserInvestmentDao.update(dbUserInvestment);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库批量更新数据操作
     * @return
     */
    public boolean updateManData(List<DBUserInvestment> dbUserInvestmentList){
        boolean flag = false;
        try{
            dbUserInvestmentDao.updateInTx(dbUserInvestmentList);
            flag = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 完成对数据库查询数据操作
     * @return
     */
    public DBUserInvestment queryData(long id) {
        return dbUserInvestmentDao.load(id);
    }

    /**
     * 完成对数据库查询所有数据操作
     * @return
     */
    public List<DBUserInvestment> queryData() {
        return dbUserInvestmentDao.loadAll();
    }

    /**
     * 完成对数据库条件查询数据操作
     * @return
     */
    public List<DBUserInvestment> queryDataDependMedia(String name) {
        return dbUserInvestmentDao.queryBuilder().where(DBUserInvestmentDao.Properties.Name.eq(name)).build().list();
    }

    /**
     * 完成对数据库按笔记标题查询数据操作
     * @return
     */
    public List<DBUserInvestment> queryDataDependNotTitle(String investmentCount) {
        return dbUserInvestmentDao.queryBuilder().where(DBUserInvestmentDao.Properties.InvestmentCount.like("%"+investmentCount+"%")).build().list();
    }

}

