package travelu.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Destination object for use in travel-journal
 */
public class Destination {

    private String name;
    private DateInterval dateInterval;
    private Integer ranking;
    private List<String> activities;
    private String comment;

    /**
     * Constructs destination object
     * 
     * @param name       of destination
     * @param date       visit from-to
     * @param ranking    on a scale from 1-5
     * @param activities you did during your visit
     * @param comment    with other relevant info
     */
    public Destination(String name, DateInterval dateInterval, Integer ranking, List<String> activities,
            String comment) {
        this.name = name;

        // dateinterval is allowed to be null, but constructor should not take in null
        // as input
        this.dateInterval = dateInterval == null ? null : new DateInterval(dateInterval);
        this.ranking = ranking;

        // if activities are null, create new list. Otherwise create copy of old
        this.activities = activities == null ? new ArrayList<String>() : new ArrayList<String>(activities);

        this.comment = comment;
    }

    /**
     * Constructs destination object from already existing destination object,
     * creating a copy
     * 
     * @param destination
     * @throws IllegalArgumentException if destination is null
     */
    public Destination(Destination destination) throws IllegalArgumentException {
        if (destination == null)
            throw new IllegalArgumentException("Destination cannot be null");

        this.name = destination.getName();
        this.dateInterval = destination.getDateInterval();
        this.ranking = destination.getRanking();
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
     * @return dateInterval from-to
     */
    public DateInterval getDateInterval() {
        if (dateInterval != null) {
            return new DateInterval(dateInterval);
        }
        return null;
    }

    /**
     * @return the ranking of the destination
     */
    public Integer getRanking() {
        return ranking;
    }

    /**
     * @param ranking on a scale of 1-5
     */
    public void setRanking(Integer ranking) {
        if (ranking < 1 || ranking > 5) {
            throw new IllegalArgumentException("Ranking must be between 1 and 5");
        }
        this.ranking = ranking;
    }

    /**
     * @return copy of activities
     */
    public List<String> getActivities() {
        return new ArrayList<String>(activities);
    }

    /**
     * 
     * @param activity a string explaining the activity
     * @throws IllegalArgumentException if the input is blank
     */
    public void addActivity(String activity) throws IllegalArgumentException {

        if (activity.isBlank() || getActivities().contains(activity))
            throw new IllegalArgumentException("Invalid activity");

        activities.add(activity);
    }

    /**
     * @return additional comment about the visit
     */
    public String getComment() {
        return comment;
    }

    /**
     * Overwrites comment with new info
     * 
     * @param comment with other relevant info
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Appends further comment
     * <p>
     * Starts on a new line
     * 
     * @param addComment comment to append
     */
    public void addComment(String addComment) {
        this.comment = this.comment + "\n" + addComment;
    }

    /**
     * Return true if names are equal
     * <p>
     * This is satisfactory because there will never be more than one object per
     * destination
     * <p>
     * Note: auto-generated stub
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Destination other = (Destination) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    /**
     * Hashes Destination according to name
     * <p>
     * Implemented to ensure safe use where hashing is needed
     * <p>
     * Note: Auto generated stub
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

}
