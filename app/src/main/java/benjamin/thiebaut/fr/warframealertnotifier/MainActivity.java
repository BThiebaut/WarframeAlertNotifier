package benjamin.thiebaut.fr.warframealertnotifier;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import benjamin.thiebaut.fr.warframealertnotifier.Adapters.AlertAdapter;
import benjamin.thiebaut.fr.warframealertnotifier.BO.Alert;
import benjamin.thiebaut.fr.warframealertnotifier.services.ApiService;

public class MainActivity extends AppCompatActivity implements ApiService.ApiServiceInterface {

    private static final String CETUS_REFRESH_ID = "cetusrefreshid";
    private static final String ALERTS_ID = "alertid";
    private Thread Timerthread;

    TextView txtCycle;
    TextView txtTimer;
    ListView listAlert;
    List<Alert> alerts;
    AlertAdapter alertAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCycle = findViewById(R.id.cetus_current_cycle);
        txtTimer = findViewById(R.id.cetus_current_time);
        listAlert = findViewById(R.id.list_alert);
        alerts = new ArrayList<>();

        SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(() -> {
            callCetusRefresh();
            refreshAlertList();
            pullToRefresh.setRefreshing(false);
        });

    }



    @Override
    protected void onResume() {
        super.onResume();
        callCetusRefresh();
        refreshAlertList();
        startCetusTimer();
    }

    public void refreshCetusTimer(View view) {
        callCetusRefresh();
    }

    public void callCetusRefresh(){
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
    public void apiResultAction(JSONArray result, String callbackId) {
        switch (callbackId){
            case ALERTS_ID :
                refreshAlertAction(result);
                break;
        }
    }

    @Override
    public void apiErrorAction(String message, String callbackId) {
        this.runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    public void showCetusInfos(JSONObject result){
        try {
            boolean isDay = result.getBoolean("isDay");
            if (isDay){
                this.runOnUiThread(() -> txtCycle.setText(R.string.day));

            }else {
                this.runOnUiThread(() -> txtCycle.setText(R.string.night));
            }

            String timeLeft = result.getString("timeLeft");
            this.runOnUiThread(() -> txtTimer.setText(getString(R.string.timeleft) + timeLeft));

            // notif
            // TODO

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void refreshAlertList(){
        ApiService.getInstance().callApi("alerts", this, ALERTS_ID);
    }

    public void refreshAlertAction(JSONArray result){
        alerts.clear();
        for (int i = 0; i < result.length(); i++){
            try {
                JSONObject object = result.getJSONObject(i);


                Alert alert = new Alert(
                        object.getString(Alert.Fields.ID.toString()),
                        object.getString(Alert.Fields.ACTIVATION.toString()),
                        object.getString(Alert.Fields.EXPIRY.toString()),
                        object.getBoolean(Alert.Fields.EXPIRED.toString()),
                        object.getString(Alert.Fields.ETA.toString())
                );

                JSONObject mission = object.getJSONObject(Alert.Fields.MISSION.toString());
                JSONObject rewards = mission.getJSONObject(Alert.Fields.REWARDS.toString());
                JSONArray items = rewards.getJSONArray(Alert.Fields.ITEMS.toString());
                if (items.length() > 0){
                    ArrayList<String> listitems = new ArrayList<>();
                    for(int j = 0; j < items.length(); j++){
                        String item = items.getString(j);
                        listitems.add(item);
                    }
                    alert.setRewards(listitems);
                }
                String credits = rewards.getString(Alert.Fields.CREDITS.toString());
                alert.setCredits(credits);
                alert.setNightMare(mission.getBoolean(Alert.Fields.NIGHTMARE.toString()));
                alert.setNode(mission.getString(Alert.Fields.NODE.toString()));
                alert.setMaxLevel(mission.getString(Alert.Fields.LEVEL.toString()));
                alert.setType(mission.getString(Alert.Fields.TYPE.toString()));

                alerts.add(alert);

            } catch (Exception e) {
                apiErrorAction(e.getMessage(), "refreshAlertAction");
            }
        }
        this.runOnUiThread(() -> {
            if (alerts != null && alertAdapter == null){
                alertAdapter = new AlertAdapter(MainActivity.this, alerts, getApplication());
                listAlert.setAdapter(alertAdapter);
            }
            alertAdapter.notifyDataSetChanged();
        });
    }

    public void startCetusTimer(){
        if (Timerthread == null){
            Timerthread = new Thread(() -> {
                callCetusRefresh();
                try {
                    Thread.sleep(1 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        if (Timerthread.isAlive()){
            Timerthread.interrupt();
        }
        Timerthread.start();

    }

}
