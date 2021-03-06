package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class WLTwitterLoginActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.loginButton).setOnClickListener(this);

        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        String remember = prefs.getString("remember", "");

        if (!remember.isEmpty()) {
            nextActivity(prefs.getString("login", ""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        EditText loginEdit = ((EditText) findViewById(R.id.loginEditText));
        EditText pwdEdit = ((EditText) findViewById(R.id.passwordEditText));

        boolean emptyLogin = TextUtils.isEmpty(loginEdit.getText());
        boolean emptyPwd = TextUtils.isEmpty(pwdEdit.getText());

        if (emptyLogin && emptyPwd) {
            Toast.makeText(this, R.string.error_no_login_and_password, Toast.LENGTH_LONG).show();
        } else if (emptyPwd) {
            Toast.makeText(this, R.string.error_no_password, Toast.LENGTH_LONG).show();
        } else if (emptyLogin) {
            Toast.makeText(this, R.string.error_no_login, Toast.LENGTH_LONG).show();
        } else {
            SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
            prefs.edit().putString("login", loginEdit.getText().toString()).commit();
            prefs.edit().putString("pwd", pwdEdit.getText().toString()).commit();

            final CheckBox checkBox = (CheckBox) findViewById(R.id.remember_me);
            if (checkBox.isChecked()) {
                prefs.edit().putString("remember", findViewById(R.id.remember_me).toString()).commit();
            }

            nextActivity(loginEdit.getText().toString());
        }
    }

    private void nextActivity(String login) {
        Intent intent = new Intent(this, WLTwitterActivity.class);
        Bundle extras = new Bundle();
        extras.putString("login", login);
        intent.putExtras(extras);
        startActivity(intent);
        overridePendingTransition(R.anim.down_in, R.anim.down_out);
    }
}
