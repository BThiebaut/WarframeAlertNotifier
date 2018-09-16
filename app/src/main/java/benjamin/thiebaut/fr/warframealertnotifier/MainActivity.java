package benjamin.thiebaut.fr.warframealertnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtCycle;
    TextView txtTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCycle = findViewById(R.id.cetus_current_cycle);
        txtTimer = findViewById(R.id.cetus_current_time);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_pref :
                intentPreferencies();
                break;
        }
        return true;
    }

    public void intentPreferencies(){
        Intent intent = new Intent(this, PreferenciesActivity.class);
        startActivity(intent);
    }

}
