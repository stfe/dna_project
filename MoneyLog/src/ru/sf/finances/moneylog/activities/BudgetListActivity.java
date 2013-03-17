package ru.sf.finances.moneylog.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import ru.sf.finances.moneylog.R;
import ru.sf.finances.moneylog.sqlproviders.BudgetTable;
import ru.sf.finances.moneylog.sqlproviders.MoneyLogContentProvider;

/**
 * Budget activity
 */
public class BudgetListActivity extends FragmentActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.budget_list);

    }


    public void addBudgetButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, AddBudgetActivity.class);
        startActivity(intent);
    }
}