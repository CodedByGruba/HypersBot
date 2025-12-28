package de.codedbygruba.services.implementation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.codedbygruba.models.ApiResult;
import de.codedbygruba.services.ApiService;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ApiServiceImpl implements ApiService {
    private final Gson gson;
    private final HttpClient client;

    public ApiServiceImpl() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        client = HttpClient.newBuilder().build();
    }

    public <T, T2> CompletableFuture<ApiResult<T>> sendPostRequest(String url, Class<T> responseType, T2 requestBody) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(requestBody)))
                .build();

        return sendRequest(request, responseType);
    }


    @Override
    public <T> CompletableFuture<ApiResult<T>> sendGetRequest(String url, Class<T> responseType) {
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        return sendRequest(request, responseType);
    }

    private <T> CompletableFuture<ApiResult<T>> sendRequest(HttpRequest request, Class<T> responseType) {
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(result -> {
                    if (result.statusCode() / 100 != 2) {
                        return new ApiResult<T>(result.statusCode(), null);
                    }
                    return new ApiResult<>(
                            result.statusCode(),
                            gson.fromJson(result.body(), responseType)
                    );
                }).exceptionally(ex -> {
                    System.err.println(ex.getMessage());
                    ex.printStackTrace();
                    return new ApiResult<>(HttpURLConnection.HTTP_INTERNAL_ERROR, null);
                });
    }
}
