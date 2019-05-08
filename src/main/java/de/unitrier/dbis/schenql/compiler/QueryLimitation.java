package de.unitrier.dbis.schenql.compiler;

public class QueryLimitation {
    private Join[] joins;

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    private String limitation;

    public Join[] getJoins() {
        return joins;
    }

    public void setJoins(Join[] joins) {
        this.joins = joins;
    }
}
