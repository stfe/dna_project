package ru.sf.finances.moneylog.sqlproviders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helps to work with tables
 */
public class BudgetDatabaseHelper extends SQLiteOpenHelper {

    public BudgetDatabaseHelper(Context context) {
        super(context, DBConstants.DATABASE_NAME, null, DBConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BudgetTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BudgetTable.onUpgrade(db, oldVersion, newVersion);
    }
}
