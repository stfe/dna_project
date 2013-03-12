package ru.sf.finances.moneylog.sqlproviders;


import android.database.sqlite.SQLiteDatabase;

/**
 * Contains SQL scripts
 */
public class BudgetTable {

    // Database table
    public static final String TABLE_BUDGET = "ML_BUDGET";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_BALANCE = "balance";
    public static final String COLUMN_ACCOUNT = "account";
    public static final String COLUMN_COMMENT = "comment";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_BUDGET
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_VALUE + " text not null,"
            + COLUMN_BALANCE + " text not null,"
            + COLUMN_ACCOUNT + " text not null,"
            + COLUMN_COMMENT + " text"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("insert into " + TABLE_BUDGET + "(" + COLUMN_ID + ", " + COLUMN_NAME+ ", " + COLUMN_VALUE+ ", " + COLUMN_BALANCE+ ", " + COLUMN_ACCOUNT+ ", " + COLUMN_COMMENT + ") " +
                "values (1, 'Budget_1', '123.123', '43.34', '14', 'Super puper budget...');");
        database.execSQL("insert into " + TABLE_BUDGET + "(" + COLUMN_ID + ", " + COLUMN_NAME+ ", " + COLUMN_VALUE+ ", " + COLUMN_BALANCE+ ", " + COLUMN_ACCOUNT+ ", " + COLUMN_COMMENT + ") " +
                "values (2, 'Budget_2', '123.123', '43.34', '14', 'Super puper budget...');");
        database.execSQL("insert into " + TABLE_BUDGET + "(" + COLUMN_ID + ", " + COLUMN_NAME+ ", " + COLUMN_VALUE+ ", " + COLUMN_BALANCE+ ", " + COLUMN_ACCOUNT+ ", " + COLUMN_COMMENT + ") " +
                "values (3, 'Budget_3', '123.123', '43.34', '14', 'Super puper budget...');");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        // TODO потом тут будут находится скрипты по обновлению БД
    }

}
