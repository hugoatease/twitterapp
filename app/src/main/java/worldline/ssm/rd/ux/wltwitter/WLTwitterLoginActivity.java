package worldline.ssm.rd.ux.wltwitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class WLTwitterLoginActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.loginButton).setOnClickListener(this);

        SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
        String login = prefs.getString("login", "");
        String pwd = prefs.getString("pwd", "");

        if (!login.isEmpty() && !pwd.isEmpty()) {
            nextActivity(login);
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

        if(TextUtils.isEmpty(loginEdit.getText())){
            Toast.makeText(this, R.string.error_no_login, Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(pwdEdit.getText())){
            Toast.makeText(this, R.string.error_no_password, Toast.LENGTH_LONG).show();
        }
        else {
            SharedPreferences prefs = WLTwitterApplication.getContext().getSharedPreferences(getString(R.string.login_information), Context.MODE_PRIVATE);
            prefs.edit().putString("login", loginEdit.getText().toString()).commit();
            prefs.edit().putString("pwd", pwdEdit.getText().toString()).commit();
            nextActivity(loginEdit.getText().toString());
        }
    }

    private void nextActivity(String login) {
        Intent intent = new Intent(this, WLTwitterActivity.class);
        Bundle extras = new Bundle();
        extras.putString("login", login);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
