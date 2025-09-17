/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HP
 */
public class StaffTableQueryBuilder {

    private String baseQuery = "SELECT * FROM staff s "
            + "INNER JOIN staff_role sr ON s.staff_role_id = sr.id "
            + "INNER JOIN staff_status ss ON s.staff_status_id = ss.id ";
    private String where = "WHERE 1=1 ";
    private String orderBy = "";

    public StaffTableQueryBuilder search(String search) {
        if (search != null && !search.isEmpty()) {
            where += "AND (s.nic LIKE '" + search + "%' OR s.first_name LIKE '%" + search + "%' OR s.last_name LIKE '" + search + "%') ";
        }
        return this;
    }

    public StaffTableQueryBuilder role(String role) {
        if (!"All".equals(role)) {
            where += "AND sr.role = '" + role + "' ";
        }
        return this;
    }

    public StaffTableQueryBuilder status(String status) {
        if (!"All".equals(status)) {
            where += "AND ss.status = '" + status + "' ";
        }
        return this;
    }

    public StaffTableQueryBuilder hospitalId(String hospitalId) {
        where += "AND s.hospital_id = '" + hospitalId + "' ";
        return this;
    }

    public StaffTableQueryBuilder order(int sortBySelectedIndex) {
        String sortByColumn = "";
        String sortByType = "";

        if (sortBySelectedIndex == 0) {
            sortByColumn = "s.added_date_time";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 1) {
            sortByColumn = "s.added_date_time";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 2) {
            sortByColumn = "s.first_name";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 3) {
            sortByColumn = "s.first_name";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 4) {
            sortByColumn = "s.last_name";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 5) {
            sortByColumn = "s.last_name";
            sortByType = "DESC";
        }

        orderBy = "ORDER BY " + sortByColumn + " " + sortByType;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }

}
