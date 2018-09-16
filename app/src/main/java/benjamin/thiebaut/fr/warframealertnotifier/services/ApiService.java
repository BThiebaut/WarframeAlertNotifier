package benjamin.thiebaut.fr.warframealertnotifier.services;

import android.app.Service;
import android.arch.core.util.Function;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiService{

    private static final String API_BASE_URL = "https://api.warframestat.us";
    private static final String API_CETUS_URI = "/pc/cetusCycle";
    private String plateform;
    private static ApiService instance;

    private ApiService() {
    }

    public static ApiService getInstance(){
        if (instance == null){
            instance = new ApiService();
            instance.setPlateform(PreferenciesManager.Platforms.PC);
        }
        return instance;
    }

    public void setPlateform(PreferenciesManager.Platforms pf){
        this.plateform = pf.toString().toLowerCase();
    }

    public void callApi(final String path, final ApiServiceInterface callback, final String callbackId){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(API_BASE_URL + "/" + plateform + "/" + path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder stringBuilder = new StringBuilder();
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            stringBuilder.append(line).append("\n");
                        }
                        bufferedReader.close();

                        JSONObject object = (JSONObject) new JSONTokener(stringBuilder.toString()).nextValue();
                        callback.apiResultAction(object, callbackId);

                    }finally {
                        urlConnection.disconnect();
                    }


                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage(), e);
                    callback.apiErrorAction(e.getMessage(), callbackId);
                }
            }
        });
        thread.start();

    }

    public interface ApiServiceInterface{
        void apiResultAction(JSONObject result, String callbackId);
        void apiErrorAction(String message, String callbackId);
    }

}
