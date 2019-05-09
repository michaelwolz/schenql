package de.unitrier.dbis.schenql.compiler;

import java.util.*;

public class Helper {
    public static String sqlStringComparison(String compareString) {
        if (Schenql.EXACT_MATCH_STRINGS) {
            return "= \"" + compareString + "\"";
        } else {
            return "LIKE \"" + compareString + "%\"";
        }
    }

    public static String addLimitations(List<QueryLimitation> queryLimitations) {
        // Processing joins and limitations
        ArrayList<Join> joins = new ArrayList<>();
        ArrayList<String> limitations = new ArrayList<>();
        Set<String> groupBy = new HashSet<>();

        queryLimitations.forEach(
                ql -> {
                    if (ql.getJoins() != null)
                        joins.addAll(Arrays.asList(ql.getJoins()));
                    if (ql.getGroupBy() != null)
                        groupBy.addAll(ql.getGroupBy());
                    limitations.add(ql.getLimitation());
                }
        );

        // Generate join statements
        StringBuilder stmt = new StringBuilder();
        joins.forEach(
                join -> {
                    stmt.append(" INNER JOIN ");
                    stmt.append(join.getTableName());
                    stmt.append(" ON ");
                    stmt.append(join.getTableName()).append(".").append(join.getKey());
                    stmt.append(" = ");
                    stmt.append(join.getJoinKey());
                }
        );

        // Adding WHERE clause
        if (limitations.size() > 0) {
            stmt.append(" WHERE ");
            stmt.append(String.join(" AND ", limitations));
        }

        if (groupBy.size() > 0) {
            stmt.append(" GROUP BY ");
            stmt.append(String.join(", ", groupBy));
        }

        return stmt.toString();
    }
}
