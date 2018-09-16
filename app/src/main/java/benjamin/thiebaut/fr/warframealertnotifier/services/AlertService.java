package benjamin.thiebaut.fr.warframealertnotifier.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import benjamin.thiebaut.fr.warframealertnotifier.R;

public class AlertService extends Service {

    private static final String CHANNEL_ID = "warframealert";
    private static AlertService instance;
    private Context context;

    public static AlertService getInstance(Context context){
        if (instance == null){
            instance = new AlertService(context);
        }
        return instance;
    }

    public AlertService() {
    }

    public AlertService(Context context) {
        this.context = context;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void notifyCetusCycle(String cycle){
        boolean isEnabled = PreferenciesManager.getInstance(context).isCetusEnabled();
        if (isEnabled){
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID);
            mBuilder.setContentTitle("Cetus : " + cycle);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
