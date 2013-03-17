package ru.sf.finances.moneylog.activities;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import ru.sf.finances.moneylog.R;
import ru.sf.finances.moneylog.sqlproviders.BudgetTable;
import ru.sf.finances.moneylog.sqlproviders.MoneyLogContentProvider;

/**
 * Fragment contains list of fragmets
 */
public class BudgetListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.budget_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {BudgetTable.COLUMN_ID, BudgetTable.COLUMN_NAME, BudgetTable.COLUMN_COMMENT};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                MoneyLogContentProvider.BUDGET_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.changeCursor(null);
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
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.budget_row, null, from,
                to);

        ListView listView = (ListView) getView().findViewById(R.id.lst_budget);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Pos: " + i + ", Id: " + l, Toast.LENGTH_LONG).show();
            }
        });
    }
}
