package travelu.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Destination object for use in travel-journal
 */
public class Destination {

    private String name;
    private HashMap<Date, Date> date = new HashMap<>();
    private Integer ranking;
    private List<String> activities = new ArrayList<>();
    private String comment;

    /**
     * Constructs destination object
     * 
     * @param name     of destination
     * @param date      visit from-to
     * @param ranking   on a scale from 1-5
     * @param activities you did during your visit
     * @param comment   with other relevant info
     */
    public Destination(String name, HashMap<Date, Date> date, Integer ranking, List<String> activities,
            String comment) {
        this.name = name;
        this.date = date;
        this.ranking = ranking;
        this.activities = activities;
        this.comment = comment;
    }

    /**
     * Constructs destination object from already existing destination object, creating a copy
     * @param destination
     */
    public Destination(Destination destination){
        this.name = destination.getName();
        this.date = destination.getDate();
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
     * @return copy for date field
     */
    public HashMap<Date, Date> getDate() {
        // date can be null
        if(date != null){
            return new HashMap<>(date);
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
    public void setRanking(Integer ranking){
        if(ranking < 1 || ranking > 5){
            throw new IllegalArgumentException("Ranking must be between 1 and 5");
        }
        this.ranking = ranking;
    }

    /**
     * @return null if activities is null, else return copy of activities
     */
    public List<String> getActivities() {
        return (activities == null) ? null : new ArrayList<String>(activities);
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

}
