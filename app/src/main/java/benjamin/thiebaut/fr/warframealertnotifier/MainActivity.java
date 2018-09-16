package benjamin.thiebaut.fr.warframealertnotifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import benjamin.thiebaut.fr.warframealertnotifier.services.ApiService;

public class MainActivity extends AppCompatActivity implements ApiService.ApiServiceInterface {

    private static final String CETUS_REFRESH_ID = "cetusrefreshid";

    TextView txtCycle;
    TextView txtTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCycle = findViewById(R.id.cetus_current_cycle);
        txtTimer = findViewById(R.id.cetus_current_time);
    }

    public void refreshCetusTimer(View view) {
        ApiService.getInstance().callApi("cetusCycle", this, CETUS_REFRESH_ID);
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

    @Override
    public void apiResultAction(JSONObject result, String callbackId) {
        switch (callbackId){
            case CETUS_REFRESH_ID :
                showCetusInfos(result);
                break;
        }
    }

    @Override
    public void apiErrorAction(String message, String callbackId) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showCetusInfos(JSONObject result){
        try {
            boolean isDay = result.getBoolean("isDay");
            if (isDay){
                this.runOnUiThread(() -> txtCycle.setText("Day"));

            }else {
                this.runOnUiThread(() -> txtCycle.setText("Night"));
            }

            String timeLeft = result.getString("timeLeft");
            this.runOnUiThread(() -> txtTimer.setText("Time left : " + timeLeft));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
