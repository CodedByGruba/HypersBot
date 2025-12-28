package de.codedbygruba.services;

import de.codedbygruba.models.ApiResult;

import java.util.concurrent.CompletableFuture;

public interface ApiService {
    <T, T2> CompletableFuture<ApiResult<T>> sendPostRequest(String url, Class<T> responseType, T2 requestBody);
    <T> CompletableFuture<ApiResult<T>> sendGetRequest(String url, Class<T> responseType);
}
