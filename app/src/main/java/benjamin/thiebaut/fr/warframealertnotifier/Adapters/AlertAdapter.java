package benjamin.thiebaut.fr.warframealertnotifier.Adapters;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import benjamin.thiebaut.fr.warframealertnotifier.BO.Alert;
import benjamin.thiebaut.fr.warframealertnotifier.R;

public class AlertAdapter extends ArrayAdapter<Alert> {
    private Application application;

    public AlertAdapter(Context context, List<Alert> data, Application application) {
        super(context, R.layout.alert_list, data);
        this.application = application;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alert_list, parent, false);
        }

        Alert alert = getItem(position);
        TextView txtTitle = convertView.findViewById(R.id.txt_alert_title);
        TextView txtDesc = convertView.findViewById(R.id.txt_alert_desc);
        TextView txtReward = convertView.findViewById(R.id.txt_alert_reward);

        txtTitle.setText(alert.getNode() + " - " + alert.getType());
        txtDesc.setText(application.getString(R.string.end) + alert.getEta());
        StringBuilder sb = new StringBuilder();

        if (!"".equals(alert.getCredits())){
            sb.append(alert.getCredits() + " CR");
        }

        for(String reward : alert.getRewards()){
            if (sb.length() > 0){
                sb.append(", ");
            }
            sb.append(reward);
        }
        txtReward.setText(sb.toString());

        return convertView;
    }

}
