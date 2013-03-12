package ru.sf.finances.moneylog.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import ru.sf.finances.moneylog.R;

/**
 * Main activity. It is an enter point to all functions of application
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    /**
     * Budget button click listener
     *
     * @param view
     */
    public void budgetButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, BudgetActivity.class);
        startActivity(intent);
    }
}