package g_raffes.blackdragon.implement;

import dk.acto.blackdragon.service.DataFetcher;
import io.vavr.control.Try;
import okhttp3.*;

import java.net.URL;

public class DataFetcherImpl implements DataFetcher {
    private String takeResponse(Response response) throws Exception {
        if (!response.isSuccessful()) throw new Exception("Request could not be made");
        if (response.body() == null) throw new Exception("Response body is empty");
        return response.body().string();
    }

    @Override
    public String fetchData(URL url) {
        Request request = new Request.Builder()
            .url(url).get()
            .build();
        OkHttpClient client = new OkHttpClient.Builder()
            .build();

        // Request error -> return null
        return Try.withResources(() -> client.newCall(request).execute())
            .of(this::takeResponse)
            .getOrNull();
    }
}
