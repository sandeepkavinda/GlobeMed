package controller;

public class PatientTableQueryBuilder {

    private String baseQuery = "SELECT p.*,g.gender AS gender_name,  "
            + "TIMESTAMPDIFF(YEAR, p.date_of_birth, CURDATE()) AS age "
            + "FROM patient p "
            + "INNER JOIN gender g ON p.gender_id = g.id ";
    private String where = "WHERE 1=1 ";
    private String orderBy = "";

    public PatientTableQueryBuilder search(String search) {
        if (search != null && !search.isEmpty()) {
            where += "AND (p.full_name LIKE '%" + search + "%' OR p.address LIKE '%" + search + "%') ";
        }
        return this;
    }

    public PatientTableQueryBuilder gender(String gender) {
        if (!"All".equals(gender)) {
            where += "AND g.gender = '" + gender + "' ";
        }
        return this;
    }

    public PatientTableQueryBuilder order(String column, String type) {
        orderBy = "ORDER BY " + column + " " + type;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }
}
