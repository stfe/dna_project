package ru.sf.finances.moneylog.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import ru.sf.finances.moneylog.R;
import ru.sf.finances.moneylog.sqlproviders.BudgetContentProvider;
import ru.sf.finances.moneylog.sqlproviders.BudgetTable;

/**
 * Budget activity
 */
public class BudgetListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_list);
        fillData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {BudgetTable.COLUMN_ID, BudgetTable.COLUMN_NAME, BudgetTable.COLUMN_COMMENT};
        CursorLoader cursorLoader = new CursorLoader(this,
                BudgetContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    /**
     * Fills list with data
     */
    private void fillData() {

        // Fields from the database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[]{BudgetTable.COLUMN_NAME, BudgetTable.COLUMN_COMMENT};
        // Fields on the UI to which we map
        int[] to = new int[]{R.id.bud_name, R.id.bud_comment};

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(this, R.layout.budget_row, null, from,
                to, 0);

        ListView listView = (ListView) findViewById(R.id.lst_budget);
        listView.setAdapter(adapter);
    }

    public void addBudgetButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, AddBudgetActivity.class);
        startActivity(intent);
    }
}