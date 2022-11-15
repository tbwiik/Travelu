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
import travelu.core.Destination;

public class Client {

    private final static String API_ADDRESS = "/api/v1/entries/";

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
    private HttpResponse<String> get(String endpoint)
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

        HttpResponse<String> response = this.get(API_ADDRESS + "destinationlist");

        Gson gson = new Gson();

        DestinationList result = gson.fromJson(response.body(), DestinationList.class);

        return result;
    }

    /**
     * Get a {@link Destination} from the server
     * 
     * @param destinationName identifier for wanted destination
     * @return wanted {@link Destination} object
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Destination getDestination(String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException {

        HttpResponse<String> response = this.get(API_ADDRESS + destinationName);

        Gson gson = new Gson();

        Destination result = gson.fromJson(response.body(), Destination.class);

        return result;
    }

    /**
     * Get current chosen {@link Destination} from the server
     * <p>
     * First send a get-request to get name
     * <p>
     * Then use {@link #getDestination(String)} to get Destination
     * 
     * @return current chosen {@link Destination}
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Destination getDestination() throws URISyntaxException, InterruptedException, ExecutionException {

        HttpResponse<String> response = this.get(API_ADDRESS + "currentDestination");

        Destination destination = getDestination(response.body());

        return destination;
    }

    /**
     * Asynchronous post request
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
     */
    private HttpResponse<String> post(String endpoint, String payload)
            throws URISyntaxException, InterruptedException, ExecutionException {

        HttpResponse<String> response = this.postAsync(endpoint, payload).get();

        return response;
    }

    /**
     * Add new {@link Destination} through server
     * 
     * @param destination
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void addDestination(Destination destination)
            throws URISyntaxException, InterruptedException, ExecutionException {

        Gson gson = new Gson();

        String destinationJSON = gson.toJson(destination);

        this.post(API_ADDRESS + "add", destinationJSON);
    }

    /**
     * Store chosen destination to file through server
     * <p>
     * Used for accessing correct destination when switching views
     * 
     * @param destinationName
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void storeCurrentDestination(String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException {

        Gson gson = new Gson();

        String destinationJSON = gson.toJson(destinationName);

        this.post(API_ADDRESS + "storeCurrent", destinationJSON);
    }

    /**
     * Remove {@link Destination} through server by name
     * <p>
     * Use a post request
     * 
     * @param destination to remove
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void removeDestination(String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException {

        Gson gson = new Gson();
        String destinationJSON = gson.toJson(destinationName);
        this.post(API_ADDRESS + "remove", destinationJSON);
    }

    /**
     * Add activity for chosen destination through server
     * 
     * @param activity
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void addActivity(String activity) throws URISyntaxException, InterruptedException, ExecutionException {
        this.post(API_ADDRESS + "addActivity", activity);
    }

    /**
     * Remove activity for chosen destination through server
     * 
     * @param activity
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void removeActivity(String activity) throws URISyntaxException, InterruptedException, ExecutionException {
        this.post(API_ADDRESS + "removeActivity", activity);
    }

    /**
     * Save rating for chosen destination through server
     * 
     * @param starNumber
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void setRating(int starNumber) throws URISyntaxException, InterruptedException, ExecutionException {
        String starStr = String.valueOf(starNumber);
        this.post(API_ADDRESS + "setRating", starStr);
    }

    /**
     * Save arrival date for chosen destination through server
     * 
     * @param arrivalDate
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void setArrivalDate(String arrivalDate) throws URISyntaxException, InterruptedException, ExecutionException {
        this.post(API_ADDRESS + "setArrivalDate", arrivalDate);
    }

    /**
     * Save departure date for chosen destination through server
     * 
     * @param departureDate
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void setDepartureDate(String departureDate)
            throws URISyntaxException, InterruptedException, ExecutionException {
        this.post(API_ADDRESS + "setDepartureDate", departureDate);
    }

    /**
     * Save updated comment for chosen destination through server
     * 
     * @param comment
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public void updateComment(String comment) throws URISyntaxException, InterruptedException, ExecutionException {
        this.post(API_ADDRESS + "updateComment", comment);
    }

}