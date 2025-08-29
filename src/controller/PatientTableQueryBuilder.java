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
            where += "AND (p.full_name LIKE '%" + search + "%' OR p.address LIKE '%" + search + "%' OR p.nic LIKE '" + search + "%') ";
        }
        return this;
    }

    public PatientTableQueryBuilder gender(String gender) {
        if (!"All".equals(gender)) {
            where += "AND g.gender = '" + gender + "' ";
        }
        return this;
    }

    public PatientTableQueryBuilder order(int sortBySelectedIndex) {
        String sortByColumn = "";
        String sortByType = "";

        if (sortBySelectedIndex == 0) {
            sortByColumn = "p.registered_date";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 1) {
            sortByColumn = "p.registered_date";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 2) {
            sortByColumn = "p.full_name";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 3) {
            sortByColumn = "p.full_name";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 4) {
            sortByColumn = "age";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 5) {
            sortByColumn = "age";
            sortByType = "ASC";
        }

        orderBy = "ORDER BY " + sortByColumn + " " + sortByType;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }
}
