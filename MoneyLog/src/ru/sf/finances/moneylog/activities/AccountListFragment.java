package ru.sf.finances.moneylog.activities;

import android.content.Intent;
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
import ru.sf.finances.moneylog.R;
import ru.sf.finances.moneylog.sqlproviders.AccountTable;
import ru.sf.finances.moneylog.sqlproviders.MoneyLogContentProvider;


/**
 * Fragent which contains list of accounts
 */
public class AccountListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // private Cursor cursor;
    private SimpleCursorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_list_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fillData();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {AccountTable.COLUMN_ID, AccountTable.COLUMN_NAME, AccountTable.COLUMN_MONTHLY_LIMIT};
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                MoneyLogContentProvider.ACCOUNT_URI, projection, null, null, null);
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
        String[] from = new String[]{AccountTable.COLUMN_NAME, AccountTable.COLUMN_MONTHLY_LIMIT};
        // Fields on the UI to which we map
        int[] to = new int[]{R.id.account_name, R.id.account_limit_value};

        getLoaderManager().initLoader(0, null, this);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.account_row, null, from,
                to);


        ListView listView = (ListView) getView().findViewById(R.id.lst_account);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddTransaction.class);
                startActivity(intent);
            }
        });
    }
}
