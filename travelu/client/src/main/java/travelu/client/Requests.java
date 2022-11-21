package travelu.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Serves generic Http-requests
 */
public class Requests {

    private final static String HTTP_STATUS_OK = "[2][0-9]*";

    private final String serverUrl;
    private final int serverPort;

    public Requests(final String serverUrl, final int serverPort) {
        this.serverUrl = serverUrl;
        this.serverPort = serverPort;
    }

    /**
     * Asynchronous get request
     * <p>
     * Method written per {@link HttpRequest} documentation
     * 
     * @param endpoint where the request is sent to
     * @return the HTTP async response
     * @throws URISyntaxException if invalid URI
     */
    private CompletableFuture<HttpResponse<String>> getAsync(String endpoint) throws URISyntaxException {
        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(new URI(this.serverUrl + ":" + this.serverPort + endpoint))
                .build();

        return client.sendAsync(request, BodyHandlers.ofString());
    }

    /**
     * Synchronous get request
     * <p>
     * Makes use of {@link #getAsync(String)}
     * 
     * @param endpoint where the request is sent to
     * @return the HTTP response
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrieval of
     *                              response
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public HttpResponse<String> get(String endpoint)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> httpResponse = this.getAsync(endpoint).get();

        if (!Pattern.matches(HTTP_STATUS_OK,
                String.valueOf(httpResponse.statusCode())))
            throw new ServerException(httpResponse.body(),
                    httpResponse.statusCode());

        return httpResponse;
    }

    /**
     * Asynchronous post request
     * <p>
     * Method written per {@link HttpRequest} documentation
     * 
     * @param endpoint where the request is sent to
     * @return the HTTP async response
     * @throws URISyntaxException if invalid URI
     */
    private CompletableFuture<HttpResponse<String>> postAsync(String endpoint, String payload)
            throws URISyntaxException {

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .uri(new URI(this.serverUrl + ":" + this.serverPort + endpoint))
                .build();

        return client.sendAsync(request, BodyHandlers.ofString());
    }

    /**
     * Synchronous post request
     * <p>
     * Makes use of {@link #postAsync(String)}
     * 
     * @param endpoint where the request is sent to
     * @return the HTTP response
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrieval of
     *                              response
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public HttpResponse<String> post(String endpoint, String payload)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> httpResponse = this.postAsync(endpoint, payload).get();

        if (!Pattern.matches(HTTP_STATUS_OK,
                String.valueOf(httpResponse.statusCode())))
            throw new ServerException(httpResponse.body(),
                    httpResponse.statusCode());

        return httpResponse;
    }

    /**
     * Asynchronous delete request
     * <p>
     * Method written per {@link HttpRequest} documentation
     * 
     * @param endpoint where the request is sent to
     * @return the HTTP async response
     * @throws URISyntaxException if invalid URI
     */
    private CompletableFuture<HttpResponse<String>> deleteAsync(String endpoint)
            throws URISyntaxException {

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .DELETE()
                .uri(new URI(this.serverUrl + ":" + this.serverPort + endpoint))
                .build();

        return client.sendAsync(request, BodyHandlers.ofString());
    }

    /**
     * Synchronous delete request
     * <p>
     * Makes use of {@link #deleteAsync(String)}
     * 
     * @param endpoint where the request is sent to
     * @return the HTTP response
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrieval of
     *                              response
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public HttpResponse<String> delete(String endpoint)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> httpResponse = this.deleteAsync(endpoint).get();

        if (!Pattern.matches(HTTP_STATUS_OK,
                String.valueOf(httpResponse.statusCode())))
            throw new ServerException(httpResponse.body(),
                    httpResponse.statusCode());

        System.out.println(httpResponse.body());
        return httpResponse;
    }

    /**
     * Asynchronous put request
     * <p>
     * Method written per {@link HttpRequest} documentation
     * 
     * @param endpoint where the request is sent to
     * @param payload  what to put
     * @return the HTTP async response
     * @throws URISyntaxException if invalid URI
     */
    private CompletableFuture<HttpResponse<String>> putAsync(String endpoint, String payload)
            throws URISyntaxException {

        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(payload))
                .uri(new URI(this.serverUrl + ":" + this.serverPort + endpoint))
                .build();

        return client.sendAsync(request, BodyHandlers.ofString());
    }

    /**
     * Synchronous put request
     * <p>
     * Makes use of {@link #putAsync(String)}
     * 
     * @param endpoint where the request is sent to
     * @param payload  what to put
     * @return the HTTP response
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrieval of
     *                              response
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public HttpResponse<String> put(String endpoint, String payload)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> httpResponse = this.putAsync(endpoint, payload).get();

        if (!Pattern.matches(HTTP_STATUS_OK,
                String.valueOf(httpResponse.statusCode())))
            throw new ServerException(httpResponse.body(),
                    httpResponse.statusCode());

        System.out.println(httpResponse.body());
        return httpResponse;
    }

}