package ru.sf.finances.moneylog.sqlproviders;


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * SQL CRUD operations for budgets
 */
public class MoneyLogContentProvider extends ContentProvider {

    // database
    private MoneyLogDatabaseHelper database;

    // Used for the UriMacher
    private static final int BUDGETS = 10;
    private static final int BUDGET_ID = 20;
    private static final int ACCOUNTS = 30;
    private static final int ACCOUNT_ID = 40;

    private static final String AUTHORITY = "ru.sf.finances.moneylog.contentprovider";

    private static final String BUDGET_PATH = "budgets";
    private static final String ACCOUNT_PATH = "accounts";

    public static final Uri BUDGET_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BUDGET_PATH);
    public static final Uri ACCOUNT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + ACCOUNT_PATH);

//    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
//            + "/budgets";
//    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
//            + "/budget";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BUDGET_PATH, BUDGETS);
        sURIMatcher.addURI(AUTHORITY, BUDGET_PATH + "/#", BUDGET_ID);
        sURIMatcher.addURI(AUTHORITY, ACCOUNT_PATH, ACCOUNTS);
        sURIMatcher.addURI(AUTHORITY, ACCOUNT_PATH + "/#", ACCOUNT_ID);
    }

    @Override
    public boolean onCreate() {
        database = new MoneyLogDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = sURIMatcher.match(uri);
        // Check if the caller has requested a column which does not exists
        checkColumns(projection, uriType);
        switch (uriType) {
            case BUDGET_ID:
                queryBuilder.appendWhere(BudgetTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
            case BUDGETS:
                queryBuilder.setTables(BudgetTable.TABLE_BUDGET);
                break;
            case ACCOUNT_ID:
                queryBuilder.appendWhere(AccountTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
            case ACCOUNTS:
                queryBuilder.setTables(AccountTable.TABLE_ACCOUNT);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        // Make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        String path;
        long id;
        switch (uriType) {
            case BUDGETS:
                id = sqlDB.insert(BudgetTable.TABLE_BUDGET, null, values);
                path = BUDGET_PATH;
                break;
            case ACCOUNTS:
                id = sqlDB.insert(AccountTable.TABLE_ACCOUNT, null, values);
                path = ACCOUNT_PATH;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(path + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case BUDGETS:
                rowsDeleted = deleteRows(BudgetTable.TABLE_BUDGET, selection, selectionArgs, sqlDB);
                break;
            case BUDGET_ID:
                rowsDeleted = deleteRow(BudgetTable.TABLE_BUDGET, BudgetTable.COLUMN_ID, uri, selection, selectionArgs, sqlDB);
                break;
            case ACCOUNTS:
                rowsDeleted = deleteRows(AccountTable.TABLE_ACCOUNT, selection, selectionArgs, sqlDB);
                break;
            case ACCOUNT_ID:
                rowsDeleted = deleteRow(AccountTable.TABLE_ACCOUNT, BudgetTable.COLUMN_ID, uri, selection, selectionArgs, sqlDB);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    /**
     * Deletes only one row
     *
     * @param columnId
     * @param uri
     * @param selection
     * @param selectionArgs
     * @param sqlDB
     * @return
     */
    private int deleteRow(String tableName, String columnId, Uri uri, String selection, String[] selectionArgs, SQLiteDatabase sqlDB) {
        int rowsDeleted;
        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
            rowsDeleted = deleteRows(tableName, columnId + "=" + id, null, sqlDB);
        } else {
            rowsDeleted = deleteRows(tableName, columnId + "=" + id
                    + " and " + selection, selectionArgs, sqlDB);
        }
        return rowsDeleted;
    }

    /**
     * Deletes several rows
     *
     * @param selection
     * @param selectionArgs
     * @param sqlDB
     * @return
     */
    private int deleteRows(String tableName, String selection, String[] selectionArgs, SQLiteDatabase sqlDB) {
        return sqlDB.delete(tableName, selection,
                selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case BUDGETS:
                rowsUpdated = updateRows(BudgetTable.TABLE_BUDGET, values, selection, selectionArgs, sqlDB);
                break;
            case BUDGET_ID:
                rowsUpdated = updateRow(BudgetTable.TABLE_BUDGET, BudgetTable.COLUMN_ID, uri, values, selection, selectionArgs, sqlDB);
                break;
            case ACCOUNTS:
                rowsUpdated = updateRows(AccountTable.TABLE_ACCOUNT, values, selection, selectionArgs, sqlDB);
                break;
            case ACCOUNT_ID:
                rowsUpdated = updateRow(AccountTable.TABLE_ACCOUNT, AccountTable.COLUMN_ID, uri, values, selection, selectionArgs, sqlDB);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    /**
     * Updates single row
     *
     * @param tableName
     * @param columnId
     * @param uri
     * @param values
     * @param selection
     * @param selectionArgs
     * @param sqlDB
     * @return
     */
    private int updateRow(String tableName, String columnId, Uri uri, ContentValues values, String selection, String[] selectionArgs, SQLiteDatabase sqlDB) {
        int rowsUpdated;
        String id = uri.getLastPathSegment();
        if (TextUtils.isEmpty(selection)) {
            rowsUpdated = updateRows(tableName, values, columnId + "=" + id, null, sqlDB);
        } else {
            rowsUpdated = updateRows(tableName, values, columnId + "=" + id
                    + " and "
                    + selection, selectionArgs, sqlDB);
        }
        return rowsUpdated;
    }

    /**
     * Updates several rows
     *
     * @param tableName
     * @param values
     * @param selection
     * @param selectionArgs
     * @param sqlDB
     * @return
     */
    private int updateRows(String tableName, ContentValues values, String selection, String[] selectionArgs, SQLiteDatabase sqlDB) {
        int rowsUpdated;
        rowsUpdated = sqlDB.update(tableName,
                values,
                selection,
                selectionArgs);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection, int uriType) {
        String[] available;
        switch (uriType) {
            case BUDGETS:
            case BUDGET_ID:
                available = BudgetTable.AVAILABLE;
                break;
            case ACCOUNTS:
            case ACCOUNT_ID:
                available = AccountTable.AVAILABLE;
                break;
            default:
                available = new String[0];
        }

        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // Check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }
}
