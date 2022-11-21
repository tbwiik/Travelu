package travelu.client;

import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import travelu.core.DestinationList;
import travelu.core.Destination;

/**
 * Handle API-requests to server.
 */
public class Client {

    /**
     * API address for http-requests.
     */
    private static final String API_ADRESS = "/api/v1/entries/";

    /**
     * Handle generic http-request.
     */
    private final Requests httpRequests;

    /**
     * Initialize client used by ui for server communications.
     *
     * @param serverUrl  URL where server is hosted
     * @param serverPort Port where server is hosted
     */
    public Client(final String serverUrl, final int serverPort) {
        httpRequests = new Requests(serverUrl, serverPort);
    }

    /**
     * Get a {@link DestinationList} from the server.
     *
     * @return {@link DestinationList} object from server - empty if none
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public DestinationList getDestinationList()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADRESS + "destinationlist");

        Gson gson = new Gson();

        DestinationList result = gson.fromJson(response.body(), DestinationList.class);

        return result;
    }

    /**
     * Get a {@link Destination} from the server.
     * <p>
     * Formats space as %20
     *
     * @param destinationName identifier for wanted destination
     * @return wanted {@link Destination} object
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public Destination getDestination(final String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        String fixedDestinationName = destinationName.replace(" ", "%20");
        HttpResponse<String> response = httpRequests.get(API_ADRESS + fixedDestinationName);

        Gson gson = new Gson();

        Destination result = gson.fromJson(response.body(), Destination.class);

        return result;
    }

    /**
     * Get current chosen {@link Destination} from the server.
     * <p>
     * Then use {@link #getDestination(String)} to get Destination
     *
     * @return current chosen {@link Destination}
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public Destination getCurrentDestination()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        Destination destination = getDestination(getCurrentDestinationName());

        return destination;
    }

    /**
     * Get name of chosen {@link Destination} from the server.
     * <p>
     * Formats space as %20
     *
     * @return name of chosen {@link Destination}
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public String getCurrentDestinationName()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADRESS + "currentDestination");
        String result = response.body().replace("%20", " ");

        return result;
    }

    /**
     * Add new {@link Destination} through server.
     *
     * @param destination to add
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void addDestination(final Destination destination)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        Gson gson = new Gson();

        String destinationJSON = gson.toJson(destination);

        httpRequests.post(API_ADRESS + "add", destinationJSON);
    }

    /**
     * Store name of chosen destination to file through server.
     * <p>
     * Used for accessing correct destination when switching views
     * <p>
     * Formats space as %20
     *
     * @param destinationName name of destination
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void storeCurrentDestinationName(final String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        String fixedDestinationName = destinationName.replace(" ", "%20");
        httpRequests.put(API_ADRESS + "storeCurrent", fixedDestinationName);
    }

    /**
     * Remove {@link Destination} through server by name.
     *
     * @param destinationName of destination to remove
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void removeDestination(final String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        httpRequests.delete(API_ADRESS + "delete/" + destinationName.replaceAll(" ", "%20"));
    }

    /**
     * Add activity for chosen destination through server.
     *
     * @param activity to add
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void addActivity(final String activity)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.post(API_ADRESS + "addActivity", activity);
    }

    /**
     * Remove activity for chosen destination through server.
     * <p>
     * Formats space as %20
     *
     * @param activity to remove
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void removeActivity(final String activity)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.delete(API_ADRESS + "removeActivity/" + activity.replaceAll(" ", "%20"));
    }

    /**
     * Save rating for chosen destination through server.
     *
     * @param starNumber number of rating
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void setRating(final int starNumber)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        String starStr = String.valueOf(starNumber);
        httpRequests.put(API_ADRESS + "setRating", starStr);
    }

    /**
     * Save arrival date for chosen destination through server.
     *
     * @param arrivalDate
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void setArrivalDate(final String arrivalDate)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.put(API_ADRESS + "setArrivalDate", arrivalDate);
    }

    /**
     * Save departure date for chosen destination through server.
     *
     * @param departureDate
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void setDepartureDate(final String departureDate)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.put(API_ADRESS + "setDepartureDate", departureDate);
    }

    /**
     * Save updated comment for chosen destination through server.
     *
     * @param comment to set
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void updateComment(final String comment)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        httpRequests.put(API_ADRESS + "updateComment", comment);
    }

}
