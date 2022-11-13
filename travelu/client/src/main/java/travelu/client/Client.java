package travelu.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import travelu.core.DestinationList;

public class Client {

    private final String serverUrl;
    private final int serverPort;

    /**
     * Initialize client used by ui for server communications
     * 
     * @param serverUrl  URL where server is hosted
     * @param serverPort Port where server is hosted
     */
    public Client(String serverUrl, int serverPort) {
        this.serverUrl = serverUrl;
        this.serverPort = serverPort;
    }

    /**
     * Asynchronous get request
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
     */
    private HttpResponse<String> get(final String endpoint)
            throws URISyntaxException, InterruptedException, ExecutionException {

        HttpResponse<String> response = this.getAsync(endpoint).get();

        return response;
    }

    /**
     * Get a {@link DestinationList} from the server
     * 
     * @return {@link DestinationList} object from server
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException
     */
    public DestinationList getDestinationList() throws URISyntaxException, InterruptedException, ExecutionException {

        HttpResponse<String> response = this.get("/api/v1/entries/" + "destinationlist");

        Gson gson = new Gson();

        DestinationList result = gson.fromJson(response.body(), DestinationList.class);

        return result;
    }

}