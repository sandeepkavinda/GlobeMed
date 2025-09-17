/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author HP
 */
public class ServiceTableQueryBuilder {

    private String baseQuery = "SELECT *,CONCAT(stf.first_name, ' ', stf.last_name) AS doctor_name "
            + "FROM service ser "
            + "INNER JOIN staff stf ON ser.doctor_nic = stf.nic "
            + "INNER JOIN staff_role stfr ON stf.staff_role_id = stfr.id "
            + "INNER JOIN service_type sert ON ser.service_type_id = sert.id ";

    private String where = "WHERE 1=1 ";
    private String orderBy = "";

    public ServiceTableQueryBuilder search(String search) {
        if (search != null && !search.isEmpty()) {
            where += "AND (ser.id LIKE '%" + search + "%' OR ser.service_name LIKE '%" + search + "%' OR CONCAT(stf.first_name, ' ', stf.last_name) LIKE '%" + search + "%') ";
        }
        return this;
    }

    public ServiceTableQueryBuilder serviceType(String serviceType) {
        if (!"All".equals(serviceType)) {
            where += "AND sert.type = '" + serviceType + "' ";
        }
        return this;
    }


    public ServiceTableQueryBuilder hospitalId(String hospitalId) {
        where += "AND stf.hospital_id = '" + hospitalId + "' ";
        return this;
    }

    public ServiceTableQueryBuilder order(int sortBySelectedIndex) {
        String sortByColumn = "";
        String sortByType = "";

        if (sortBySelectedIndex == 0) {
            sortByColumn = "ser.id";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 1) {
            sortByColumn = "ser.id";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 2) {
            sortByColumn = "ser.service_name";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 3) {
            sortByColumn = "ser.service_name";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 4) {
            sortByColumn = "ser.price";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 5) {
            sortByColumn = "ser.price";
            sortByType = "ASC";
        }

        orderBy = "ORDER BY " + sortByColumn + " " + sortByType;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }

}
