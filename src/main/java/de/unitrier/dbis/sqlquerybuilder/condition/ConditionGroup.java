package de.unitrier.dbis.sqlquerybuilder.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

public class ConditionGroup extends Condition {
    private ArrayList<Condition> conditions = new ArrayList<>();
    public boolean or = false;

    public ConditionGroup(Condition... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
    }

    public void addCondition(Condition condition) {
        conditions.add(condition);
    }

    @Override
    public String createStatement() {
        return "(" + createGroupedStatement(conditions) + ")";
    }

    public static String createGroupedStatement(ArrayList<Condition> conditions) {
        StringJoiner condString = new StringJoiner(" ");
        for (int i = 0; i < conditions.size(); i++) {
            if (i > 0) {
                Condition cond = conditions.get(i);
                condString.add(cond.or ? "OR" : "AND");
                if (cond.negate) {
                    condString.add("NOT");
                }
            }
            condString.add(conditions.get(i).createStatement());
        }
        return condString.toString();
    }
}
