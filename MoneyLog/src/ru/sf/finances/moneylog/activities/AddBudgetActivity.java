package ru.sf.finances.moneylog.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import ru.sf.finances.moneylog.R;

/**
 * Add budget
 */
public class AddBudgetActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_budget);
    }


    /**
     * Add or update budget
     *
     * @param view
     */
    public void addOrUpdateBudget(View view) {

    }
}