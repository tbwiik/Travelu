package travelu.client;

import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import travelu.core.DestinationList;
import travelu.core.Destination;

/**
 * Handle api-requests to server
 */
public class Client {

    private final static String API_ADDRESS = "/api/v1/entries/";

    /**
     * Handle generic Http-request
     */
    private final Requests httpRequests;

    /**
     * Initialize client used by ui for server communications
     * 
     * @param serverUrl  URL where server is hosted
     * @param serverPort Port where server is hosted
     */
    public Client(final String serverUrl, final int serverPort) {
        httpRequests = new Requests(serverUrl, serverPort);
    }

    /**
     * Get a {@link DestinationList} from the server
     * 
     * @return {@link DestinationList} object from server
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public DestinationList getDestinationList()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "destinationlist");

        Gson gson = new Gson();

        DestinationList result = gson.fromJson(response.body(), DestinationList.class);

        return result;
    }

    /**
     * Get a {@link Destination} from the server
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
        HttpResponse<String> response = httpRequests.get(API_ADDRESS + fixedDestinationName);

        Gson gson = new Gson();

        Destination result = gson.fromJson(response.body(), Destination.class);

        return result;
    }

    /**
     * Get current chosen {@link Destination} from the server
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
     * Get name of chosen {@link Destination} from the server
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

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "currentDestination");
        String result = response.body().replace("%20", " ");

        return result;
    }

    /**
     * Add new {@link Destination} through server
     * 
     * @param destination
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void addDestination(Destination destination)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        Gson gson = new Gson();

        String destinationJSON = gson.toJson(destination);

        httpRequests.post(API_ADDRESS + "add", destinationJSON);
    }

    /**
     * Store name of chosen destination to file through server
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
        httpRequests.put(API_ADDRESS + "storeCurrent", fixedDestinationName);
    }

    /**
     * Remove {@link Destination} through server by name
     * <p>
     * Use a post request
     * 
     * @param destination to remove
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void removeDestination(String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        httpRequests.delete(API_ADDRESS + "delete/" + destinationName.replaceAll(" ", "%20"));
    }

    /**
     * Add activity for chosen destination through server
     * 
     * @param activity
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void addActivity(String activity)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.post(API_ADDRESS + "addActivity", activity);
    }

    /**
     * Remove activity for chosen destination through server
     * 
     * @param activity
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void removeActivity(final String activity)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.delete(API_ADDRESS + "removeActivity/" + activity.replaceAll(" ", "%20"));
    }

    /**
     * Save rating for chosen destination through server
     * 
     * @param starNumber
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void setRating(int starNumber)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        String starStr = String.valueOf(starNumber);
        httpRequests.put(API_ADDRESS + "setRating", starStr);
    }

    /**
     * Save arrival date for chosen destination through server
     * 
     * @param arrivalDate
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void setArrivalDate(String arrivalDate)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.put(API_ADDRESS + "setArrivalDate", arrivalDate);
    }

    /**
     * Save departure date for chosen destination through server
     * 
     * @param departureDate
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void setDepartureDate(String departureDate)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.put(API_ADDRESS + "setDepartureDate", departureDate);
    }

    /**
     * Save updated comment for chosen destination through server
     * 
     * @param comment
     * @throws URISyntaxException   if invalid URI
     * @throws InterruptedException if interruption occurs during retrival of
     *                              response
     * @throws ExecutionException   if completing task, but with an exception
     * @throws ServerException      if http request not successfull (not 200)
     */
    public void updateComment(String comment)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        httpRequests.put(API_ADDRESS + "updateComment", comment);
    }

}