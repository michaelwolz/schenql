package de.unitrier.dbis.schenql.compiler;

import java.util.ArrayList;

public class QueryLimitation {
    private Join[] joins;
    private String limitation;
    private ArrayList<String> groupBy = new ArrayList<>();


    public ArrayList<String> getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(ArrayList<String> groupBy) {
        this.groupBy = groupBy;
    }


    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    public Join[] getJoins() {
        return joins;
    }

    public void setJoins(Join[] joins) {
        this.joins = joins;
    }
}
