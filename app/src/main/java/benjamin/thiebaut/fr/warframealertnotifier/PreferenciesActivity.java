package benjamin.thiebaut.fr.warframealertnotifier;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import benjamin.thiebaut.fr.warframealertnotifier.services.PreferenciesManager;

public class PreferenciesActivity extends AppCompatActivity {

    Spinner platform;
    CheckBox chkCetus;
    EditText edtCetus;
    CheckBox chkNitain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencies);

        platform = findViewById(R.id.spin_platform);
        chkCetus = findViewById(R.id.check_cetus);
        edtCetus = findViewById(R.id.cetustimer);
        chkNitain = findViewById(R.id.check_nitain);
        ArrayAdapter<PreferenciesManager.Platforms> adapter = new ArrayAdapter<PreferenciesManager.Platforms>(this, android.R.layout.simple_spinner_item, PreferenciesManager.Platforms.values());
        platform.setAdapter(adapter);

        PreferenciesManager.Platforms memoryPlat = PreferenciesManager.getInstance(this).getPlateform();
        boolean isCetusCheck = PreferenciesManager.getInstance(this).isCetusEnabled();
        int edtTimer = PreferenciesManager.getInstance(this).getCetusTimer();

        platform.setSelection(adapter.getPosition(memoryPlat));
        chkCetus.setChecked(isCetusCheck);
        edtCetus.setText(""+ edtTimer);
        chkNitain.setChecked(PreferenciesManager.getInstance(this).getAlertEnabled(PreferenciesManager.PREF_ALERT_NITAIN));


    }

    public void savePref(View view) {
        if (!"".equals(platform.getSelectedItem().toString())){
            PreferenciesManager.Platforms plat = PreferenciesManager.Platforms.valueOf(platform.getSelectedItem().toString());
            PreferenciesManager.getInstance(this).savePlateform(plat);
        }

        boolean enabledcetus = chkCetus.isChecked();
        PreferenciesManager.getInstance(this).saveCetusEnabled(enabledcetus);

        try {
            int timerCetus = Integer.parseInt(edtCetus.getText().toString());
            PreferenciesManager.getInstance(this).saveCetusTimer(timerCetus);
        }catch (Exception e){
            // TODO format error
        }

        boolean enableNitain = chkNitain.isChecked();
        PreferenciesManager.getInstance(this).saveAlert(PreferenciesManager.PREF_ALERT_NITAIN, enableNitain);

        finish();
    }
}
