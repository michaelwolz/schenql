package de.unitrier.dbis.schenql.compiler;

public class QueryLimitation {
    private String[] joins;

    public String getLimitation() {
        return limitation;
    }

    public void setLimitation(String limitation) {
        this.limitation = limitation;
    }

    private String limitation;

    public String[] getJoins() {
        return joins;
    }

    public void setJoins(String[] joins) {
        this.joins = joins;
    }
}
