package travelu.core;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Destination object for use in travel-journal.
 */
public class Destination {

    /**
     * Interval for rating.
     */
    private final static int[] RATING_INTERVAL = { 1, 5 };

    /**
     * Name of destination.
     */
    private String name;
    /**
     * Date interval of stay in destination.
     * Contains arrival- and departuredate on
     * format dd/MM/yyyy.
     */
    private DateInterval dateInterval;
    /**
     * Rating, between 0-5.
     */
    private int rating;

    /**
     * List of activities done in destination.
     */
    private List<String> activities;
    /**
     * Extra comment for destination.
     */
    private String comment;

    /**
     * Constructs destination object.
     *
     * @param destName         of destination
     * @param destDateInterval visit from-to
     * @param destRating       on a scale from 1-5
     * @param destActivities   you did during your visit
     * @param destComment      with other relevant info
     */
    public Destination(final String destName, final DateInterval destDateInterval,
            final int destRating,
            final List<String> destActivities,
            final String destComment) {
        this.name = destName;

        // DateInterval is not allowed to be null
        this.dateInterval = destDateInterval == null ? new DateInterval()
                : new DateInterval(destDateInterval);
        this.rating = destRating;

        // If activities are null, create new list. Otherwise create copy of old
        this.activities = destActivities == null ? new ArrayList<String>()
                : new ArrayList<String>(destActivities);

        this.comment = destComment;
    }

    /**
     * Constructs destination object from already existing destination object.
     * <p>
     * Create a copy.
     *
     * @param destination
     * @throws IllegalArgumentException if destination is null
     */
    public Destination(final Destination destination) throws IllegalArgumentException {
        if (destination == null) {
            throw new IllegalArgumentException("Destination cannot be null");
        }

        this.name = destination.getName();
        this.dateInterval = destination.getDateInterval();
        this.rating = destination.getRating();
        this.activities = destination.getActivities();
        this.comment = destination.getComment();
    }

    /**
     * @return name of destination
     */
    public String getName() {
        return name;
    }

    /**
     * Returns copy of DateInterval. If DateInterval is null, return new
     * DateInterval.
     *
     * @return dateInterval from-to
     */
    public DateInterval getDateInterval() {
        if (dateInterval != null) {
            return new DateInterval(dateInterval);
        }
        return new DateInterval();
    }

    /**
     * Set arrival date for destination.
     *
     * @param arrivalDate - string in format dd/MM/yyyy
     * @throws IllegalArgumentException
     */
    public void setArrivalDate(final String arrivalDate) throws IllegalArgumentException {
        dateInterval.setArrivalDate(arrivalDate);
    }

    /**
     * Set departure date for destination.
     *
     * @param departureDate - string in format dd/MM/yyyy
     * @throws IllegalArgumentException
     */
    public void setDepartureDate(final String departureDate) throws IllegalArgumentException {
        dateInterval.setDepartureDate(departureDate);
    }

    /**
     * @return the rating of the destination.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Set rating of destination.
     *
     * @param newRating on a scale of 1-5
     * @throws IllegalArgumentException if rating is outside of range 1-5
     */
    public void setRating(final int newRating) throws IllegalArgumentException {
        if (newRating < RATING_INTERVAL[0] || newRating > RATING_INTERVAL[1]) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = newRating;
    }

    /**
     * @return copy of activities-list
     */
    public List<String> getActivities() {
        return new ArrayList<String>(activities);
    }

    /**
     * Add activity to destination.
     *
     * @param activity a string explaining the activity
     * @throws IllegalArgumentException if the input is null, blank or already in
     *                                  list
     */
    public void addActivity(final String activity) throws IllegalArgumentException {

        if (activity == null || activity.isBlank() || getActivities().contains(activity)) {
            throw new IllegalArgumentException("Invalid activity");
        }

        activities.add(activity);
    }

    /**
     * Remove activity from destination.
     *
     * @param activity the activity we want to remove
     * @throws NoSuchElementException if activity is not in list
     */
    public void removeActivity(final String activity) throws NoSuchElementException {
        if (!getActivities().contains(activity)) {
            throw new NoSuchElementException("Activity " + activity + " is not in activity list");
        }

        activities.remove(activity);
    }

    /**
     * @return comment about the visit
     */
    public String getComment() {
        return comment;
    }

    /**
     * Overwrites comment with new info.
     *
     * @param newComment new comment
     */
    public void setComment(final String newComment) {
        this.comment = newComment;
    }

}
