package travelu.client;

/**
 * Exception for server errors
 * Created to be able to easily send statuscode upwards
 */
public class ServerException extends Exception {

    /**
     * HTTP status for the error
     */
    private int statusCode;

    /**
     * Exception thrown when error with server
     * 
     * @param info       connected to exception
     * @param statusCode HTTP request status
     */
    public ServerException(final String info, final int statusCode) {
        super(info);
        this.statusCode = statusCode;
    }

    /**
     * @return HTTP status-code
     */
    public int getStatusCode() {
        return statusCode;
    }
}
