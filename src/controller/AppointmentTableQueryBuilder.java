package controller;

public class AppointmentTableQueryBuilder {

    private String baseQuery = "SELECT * FROM appointment a "
            + "INNER JOIN service s ON a.service_id = s.id ";
    private String where = "WHERE 1=1 ";
    private String orderBy = "";

    public AppointmentTableQueryBuilder search(String search) {
        if (search != null && !search.isEmpty()) {
            where += "AND (a.id LIKE '" + search + "%' OR a.service_id LIKE '" + search + "%' OR a.patient_nic LIKE '" + search + "%') ";
        }
        return this;
    }

    public AppointmentTableQueryBuilder setAppoitmentType(String selectedType) {
        
        if(selectedType.equals("Past Appointments")){
            where += "AND TIMESTAMP(a.appointment_date, s.time) <= NOW() ";
        }else if(selectedType.equals("Upcoming Appointments")){
            where += "AND TIMESTAMP(a.appointment_date, s.time) > NOW() ";
        }
        return this;
        
    }

    public AppointmentTableQueryBuilder setOrder(int sortBySelectedIndex) {
        String sortByColumn = "";
        String sortByType = "";

        if (sortBySelectedIndex == 0) {
            sortByColumn = "a.added_date_time";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 1) {
            sortByColumn = "a.added_date_time";
            sortByType = "ASC";
        } else if (sortBySelectedIndex == 2) {
            sortByColumn = "TIMESTAMP(a.appointment_date, s.time)";
            sortByType = "DESC";
        } else if (sortBySelectedIndex == 3) {
            sortByColumn = "TIMESTAMP(a.appointment_date, s.time)";
            sortByType = "ASC";
        }
        
        orderBy = "ORDER BY " + sortByColumn + " " + sortByType;
        return this;
    }

    public String build() {
        return baseQuery + where + orderBy;
    }
}
