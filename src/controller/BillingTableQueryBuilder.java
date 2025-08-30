package controller;

public class BillingTableQueryBuilder {

    private String baseQuery = "SELECT * FROM bill b "
            + "INNER JOIN appointment a ON b.appointment_id = a.id "
            + "INNER JOIN service s ON a.service_id = s.id "
            + "INNER JOIN payment_method p ON b.payment_method_id = p.id ";
    private String where = "WHERE 1=1 ";
    private String orderBy = "";

    public BillingTableQueryBuilder search(String search) {
        if (search != null && !search.isEmpty()) {
            where += "AND (b.id LIKE '" + search + "%' OR s.id LIKE '" + search + "%' OR a.id LIKE '" + search + "%') ";
        }
        return this;
    }

    public BillingTableQueryBuilder order(int sortBySelectedIndex) {
        String sortByColumn = "";
        String sortByType = "";

        if (sortBySelectedIndex == 0) {
            sortByColumn = "b.date_time";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 1) {
            sortByColumn = "b.date_time";
            sortByType = "ASC";
        } 
        orderBy = "ORDER BY " + sortByColumn + " " + sortByType;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }
}
