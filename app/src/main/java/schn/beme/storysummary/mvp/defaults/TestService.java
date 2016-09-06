package schn.beme.storysummary.mvp.defaults;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Dorito on 05-09-16.
 */
public class TestService  {

    public void test(){

        final String API_URL = "http://192.168.0.2:8080/v1/api/sync/test";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.d("INFO",String.valueOf(response.code()));
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }
}
