package controller;

public class ReportTableQueryBuilder {

    private String baseQuery = "SELECT * FROM report r "
            + "INNER JOIN report_type rt ON r.report_type_id = rt.id "
            + "INNER JOIN patient p ON r.patient_nic = p.nic ";
    private String where = "WHERE 1=1 ";
    private String orderBy = "";

    public ReportTableQueryBuilder search(String search) {
        if (search != null && !search.isEmpty()) {
            where += "AND (r.id LIKE '" + search + "%' OR p.nic LIKE '" + search + "%' OR r.title LIKE '%" + search + "%') ";
        }
        return this;
    }

    public ReportTableQueryBuilder setType(String selectedType) {

        if (!selectedType.equals("All")) {
            where += "AND rt.type = '" + selectedType + "' ";
        }
        return this;
    }

    public ReportTableQueryBuilder setOrder(int sortBySelectedIndex) {
        String sortByColumn = "";
        String sortByType = "";

        if (sortBySelectedIndex == 0) {
            sortByColumn = "r.date_time";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 1) {
            sortByColumn = "r.date_time";
            sortByType = "ASC";
        }

        orderBy = "ORDER BY " + sortByColumn + " " + sortByType;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }
}
