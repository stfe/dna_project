package ru.sf.finances.moneylog.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import ru.sf.finances.moneylog.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Activity with the login, password fields
 */
public class AuthActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth);

        //checkAuthAndRedirect();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);    //To change body of overridden methods use File | Settings | File Templates.
        //checkAuthAndRedirect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkAuthAndRedirect();
    }

    /**
     * Check login and password and redirect to the MainActivity if auth is correct
     */
    private void checkAuthAndRedirect() {
        Map<String, String> loginAncPassword = getLoginAndPass();
        if (checkAuth(loginAncPassword.get(getString(R.string.login_key)), loginAncPassword.get(getString(R.string.password_key)))) {
            redirectToMainActivity();
        }
    }

    /**
     * Enter button listener
     *
     * @param view enter button reference
     */
    public void enterButtonListener(View view) {

        String login = ((EditText) findViewById(R.id.login)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();

        if (!checkAuth(login, password)) {
            return; // TODO wrong cred message
        }

        if (checkRememberMe()) {
            savePasswordAndLoginToSettings(login, password);
        } else {
            clearPasswordAndLoginToSettings();
        }

        redirectToMainActivity();
    }

    private void redirectToMainActivity() {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Check auth params. Now it is fake check.
     * TODO normal rights check
     *
     * @param login
     * @param password
     * @return
     */
    private boolean checkAuth(String login, String password) {
        return "1".equals(login) && "1".equals(password);
    }

    /**
     * Check if Remember me check box checked
     *
     * @return
     */
    private boolean checkRememberMe() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.remember_me_check_box);
        return checkBox.isChecked();
    }

    /**
     * Save login and password to shared preferences
     *
     * @param login
     * @param password
     */
    private void savePasswordAndLoginToSettings(String login, String password) {

        saveToSharedPref(true, login, password);
    }

    /**
     * Clear login and password from shared preferences
     */
    private void clearPasswordAndLoginToSettings() {
        saveToSharedPref(false, "", "");
    }

    private void saveToSharedPref(boolean isRememberMeChecked, String login, String password) {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_prop_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.remember_me_key), isRememberMeChecked);
        editor.putString(getString(R.string.login_key), login);
        editor.putString(getString(R.string.password_key), password);
        editor.commit();
    }


    /**
     * Get login and password map from shared file
     *
     * @return
     */
    public Map<String, String> getLoginAndPass() {
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.shared_prop_key), Context.MODE_PRIVATE);
        boolean isRemember = sharedPref.getBoolean(getString(R.string.remember_me_key), false);
        Map<String, String> loginAndPass = new HashMap<String, String>();
        if (isRemember) {
            loginAndPass.put(getString(R.string.login_key), sharedPref.getString(getString(R.string.login_key), ""));
            loginAndPass.put(getString(R.string.password_key), sharedPref.getString(getString(R.string.password_key), ""));
        }

        return loginAndPass;
    }
}
