package gr2219.backend;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

public class Destination {

    private String name;
    private Pair<Date, Date> date;
    private Integer ranking;
    private List<String> activites = new ArrayList<>();
    private String comment;

    /**
     * Constructs destination object
     * 
     * @param name      of destination
     * @param date      visit from-to
     * @param ranking   on a scale from 1-5
     * @param activites you did during your visit
     * @param comment   with other relevant info
     */
    public Destination(String name, Pair<Date, Date> date, Integer ranking, List<String> activites, String comment) {
        this.name = name;
        this.date = date;
        this.ranking = ranking;
        this.activites = activites;
        this.comment = comment;
    }

    /**
     * @return name of destination
     */
    public String getName() {
        return name;
    }

    /**
     * @return tuple from-to for the visit
     */
    public Pair<Date, Date> getDate() {
        return date;
    }

    /**
     * @return the ranking of the destination
     */
    public Integer getRanking() {
        return ranking;
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

}
