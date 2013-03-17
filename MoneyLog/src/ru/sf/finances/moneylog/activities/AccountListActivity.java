package ru.sf.finances.moneylog.activities;

import android.app.Activity;
import android.os.Bundle;
import ru.sf.finances.moneylog.R;


/**
 * Account list activity
 */
public class AccountListActivity extends Activity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_list);
    }


    /**
     * TODO
     */
//    public void addACCOUNTButtonListener(View view) {
//        Intent intent = new Intent();
//        intent.setClass(this, AddAccountActivity.class);
//        startActivity(intent);
//    }
}