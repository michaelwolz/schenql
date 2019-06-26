package de.unitrier.dbis.sqlquerybuilder.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class ConditionGroup extends Condition {
    private ArrayList<Condition> conditions = new ArrayList<>();
    public boolean or = false;

    public ConditionGroup(Condition... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ConditionGroup)) {
            return false;
        }

        if (!super.equals(o)) return false;

        ConditionGroup c = (ConditionGroup) o;
        // This has a really bad performance
        return c.conditions.size() == conditions.size() &&
                c.conditions.containsAll(conditions) && conditions.containsAll(c.conditions) &&
                c.or == or;
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
