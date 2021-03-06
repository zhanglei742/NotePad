package com.aidebar.greendaotest.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.json.mytouzhisystem.Bean.DBUserInvestment;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DBUSER_INVESTMENT".
*/
public class DBUserInvestmentDao extends AbstractDao<DBUserInvestment, Long> {

    public static final String TABLENAME = "DBUSER_INVESTMENT";

    /**
     * Properties of entity DBUserInvestment.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property CreatTimeAsId = new Property(0, long.class, "creatTimeAsId", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "DBUserInvestment");
        public final static Property InvestmentCount = new Property(2, String.class, "InvestmentCount", false, "INVESTMENT_COUNT");
        public final static Property Sign = new Property(3, String.class, "sign", false, "SIGN");
    }


    public DBUserInvestmentDao(DaoConfig config) {
        super(config);
    }
    
    public DBUserInvestmentDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DBUSER_INVESTMENT\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: creatTimeAsId
                "\"DBUserInvestment\" TEXT," + // 1: name
                "\"INVESTMENT_COUNT\" TEXT," + // 2: InvestmentCount
                "\"SIGN\" TEXT);"); // 3: sign
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DBUSER_INVESTMENT\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DBUserInvestment entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCreatTimeAsId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String InvestmentCount = entity.getInvestmentCount();
        if (InvestmentCount != null) {
            stmt.bindString(3, InvestmentCount);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(4, sign);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DBUserInvestment entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getCreatTimeAsId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String InvestmentCount = entity.getInvestmentCount();
        if (InvestmentCount != null) {
            stmt.bindString(3, InvestmentCount);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(4, sign);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public DBUserInvestment readEntity(Cursor cursor, int offset) {
        DBUserInvestment entity = new DBUserInvestment( //
            cursor.getLong(offset + 0), // creatTimeAsId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // InvestmentCount
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // sign
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DBUserInvestment entity, int offset) {
        entity.setCreatTimeAsId(cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setInvestmentCount(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSign(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DBUserInvestment entity, long rowId) {
        entity.setCreatTimeAsId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DBUserInvestment entity) {
        if(entity != null) {
            return entity.getCreatTimeAsId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DBUserInvestment entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
