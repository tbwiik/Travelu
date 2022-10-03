package travelu.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Destination object for use in travel-journal
 */
public class Destination {

    private String place;
    private HashMap<Date, Date> date = new HashMap<>();
    private Integer ranking;
    private List<String> activites = new ArrayList<>();
    private String comment;

    /**
     * Constructs destination object
     * 
     * @param place     of destination
     * @param date      visit from-to
     * @param ranking   on a scale from 1-5
     * @param activites you did during your visit
     * @param comment   with other relevant info
     */
    public Destination(String place, HashMap<Date, Date> date, Integer ranking, List<String> activites,
            String comment) {
        this.place = place;
        this.date = date;
        this.ranking = ranking;
        this.activites = activites;
        this.comment = comment;
    }

    /**
     * @return name of destination
     */
    public String getName() {
        return place;
    }

    /**
     * @return tuple from-to for the visit
     */
    public HashMap<Date, Date> getDate() {
        return date;
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
     * @return activities done in the destination
     */
    public List<String> getActivites() {
        return activites;
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