package travelu.client;

import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
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
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
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
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
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
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
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
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public String getCurrentDestinationName()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "currentDestination");
        String result = response.body().replace("%20", " ");

        return result;
    }

    /**
     * Get names of stored Destinations
     * 
     * @return destination names
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public List<String> getDestinationNames()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "destinationNames");

        String[] names = new Gson().fromJson(response.body(), String[].class);

        return Arrays.asList(names);
    }

    /**
     * Check if name is already present in list
     * 
     * @return true if in list
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public boolean containsDestination(final String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        String destinationNameJSON = destinationName.replace(" ", "%20");
        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "contains/" + destinationNameJSON);

        Boolean contains = new Gson().fromJson(response.body(), Boolean.class);

        return contains;
    }

    /**
     * Add new {@link Destination} through server
     * 
     * @param destination
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
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
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
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
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void removeDestination(String destinationName)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        httpRequests.delete(API_ADDRESS + "delete/" + destinationName);
    }

    /**
     * Add activity for chosen destination through server
     * 
     * @param activity
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void addActivity(String activity)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.post(API_ADDRESS + "addActivity", activity);
    }

    /**
     * Remove activity for chosen destination through server
     * 
     * @param activity
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void removeActivity(String activity)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.delete(API_ADDRESS + "removeActivity/" + activity);
    }

    public List<String> getActivites()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        Gson gson = new Gson();

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "getActivites");

        String[] activities = gson.fromJson(response.body(), String[].class);

        return Arrays.asList(activities);
    }

    /**
     * Save rating for chosen destination through server
     * 
     * @param starNumber
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void setRating(int starNumber)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        String starStr = String.valueOf(starNumber);
        httpRequests.put(API_ADDRESS + "setRating", starStr);
    }

    /**
     * Get rating for chosen destination through server
     * 
     * @return int - rating
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException
     */
    public int getRating() throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "getRating");

        int rating = Integer.parseInt(response.body());

        return rating;
    }

    /**
     * Save arrival date for chosen destination through server
     * 
     * @param arrivalDate
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void setArrivalDate(String arrivalDate)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.put(API_ADDRESS + "setArrivalDate", arrivalDate);
    }

    /**
     * Get arrival date for chosen destination through server
     * 
     * @return arrival date
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException
     */
    public String getArrivalDate()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "getArrivalDate");

        return response.body();
    }

    /**
     * Save departure date for chosen destination through server
     * 
     * @param departureDate
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void setDepartureDate(String departureDate)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {
        httpRequests.put(API_ADDRESS + "setDepartureDate", departureDate);
    }

    /**
     * Get departure date for chosen destination through server
     * 
     * @return departure date
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException
     */
    public String getDepartureDate()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "getDepartureDate");

        return response.body();
    }

    /**
     * Save updated comment for chosen destination through server
     * 
     * @param comment
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException      if http request not successfull
     */
    public void updateComment(String comment)
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        httpRequests.put(API_ADDRESS + "updateComment", comment);
    }

    /**
     * Get comment for chosen destination through server
     * 
     * @return comment
     * @throws URISyntaxException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws ServerException
     */
    public String getComment()
            throws URISyntaxException, InterruptedException, ExecutionException, ServerException {

        HttpResponse<String> response = httpRequests.get(API_ADDRESS + "getComment");

        return response.body();
    }

}