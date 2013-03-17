package ru.sf.finances.moneylog.sqlproviders;


import android.database.sqlite.SQLiteDatabase;

/**
 * Contains SQL scripts
 */
public class AccountTable {

    // Database table
    public static final String TABLE_ACCOUNT = "ML_ACCOUNT";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CURRENCY = "currency";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_MONTHLY_LIMIT = "monthly_limit";

    public static String[] AVAILABLE = {AccountTable.COLUMN_NAME,
            AccountTable.COLUMN_CURRENCY, AccountTable.COLUMN_COMMENT,
            AccountTable.COLUMN_MONTHLY_LIMIT, AccountTable.COLUMN_ID};

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ACCOUNT + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_CURRENCY + " integer not null, "
            + COLUMN_MONTHLY_LIMIT + " text not null,"
            + COLUMN_COMMENT + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("insert into " + TABLE_ACCOUNT + "(" + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_CURRENCY + ", " + COLUMN_MONTHLY_LIMIT + ", " + COLUMN_COMMENT + ") " +
                "values (1, 'Account_1', 1, '43.34', 'Account details ...');");
        database.execSQL("insert into " + TABLE_ACCOUNT + "(" + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_CURRENCY + ", " + COLUMN_MONTHLY_LIMIT + ", " + COLUMN_COMMENT + ") " +
                "values (2, 'Account_2', 1, '10051.34', 'Account details ...');");
        database.execSQL("insert into " + TABLE_ACCOUNT + "(" + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_CURRENCY + ", " + COLUMN_MONTHLY_LIMIT + ", " + COLUMN_COMMENT + ") " +
                "values (3, 'Account_3', 1, '546.50', 'Account details ...');");
        database.execSQL("insert into " + TABLE_ACCOUNT + "(" + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_CURRENCY + ", " + COLUMN_MONTHLY_LIMIT + ", " + COLUMN_COMMENT + ") " +
                "values (4, 'Account_4', 1, '47.98', 'Account details ...');");

    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        // TODO потом тут будут находится скрипты по обновлению БД
    }

}
