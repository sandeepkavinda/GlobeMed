
package controller.report;

import controller.report.ReportResposeBean;
import java.util.Date;
import model.MySQL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Vector;

interface ReportElement {
    ReportResposeBean accept(ReportVisitor visitor);
}

class PatientReport implements ReportElement {

    private String patientNic;
    
    public PatientReport(String nic) {        
        this.patientNic = nic;
    }

    public String getNic() {        
        return patientNic;        
    }
    
    @Override
    public ReportResposeBean accept(ReportVisitor visitor) {
        return visitor.visit(this);
    }
}

class DiagnosticReport implements ReportElement {

    private String reportId;

    public DiagnosticReport(String id) {
        this.reportId = id;
    }

    public String getReportId() {
        return reportId;
    }
    
    @Override
    public ReportResposeBean accept(ReportVisitor visitor) {
        return visitor.visit(this);
    }
}

class FinancialReport implements ReportElement {

    @Override
    public ReportResposeBean accept(ReportVisitor visitor) {
        return visitor.visit(this);
    }
}

interface ReportVisitor {
    ReportResposeBean visit(PatientReport patientReport);
    ReportResposeBean visit(DiagnosticReport diagnosticReport);
    ReportResposeBean visit(FinancialReport financialReport);
}

class PatientReportVisitor implements ReportVisitor {

    @Override
    public ReportResposeBean visit(PatientReport patientReport) {
        ReportResposeBean reportResposeBean = new ReportResposeBean();
        
        try {
            ResultSet resultSet = MySQL.execute("SELECT *, "
                    + "TIMESTAMPDIFF(YEAR, p.date_of_birth, CURDATE()) AS age "
                    + "FROM patient p "
                    + "INNER JOIN gender g ON p.gender_id = g.id "
                    + "WHERE p.nic = '" + patientReport.getNic() + "'");
            
            if (resultSet.next()) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateTime = dateFormat.format(date);
                
                reportResposeBean.setFullName(resultSet.getString("p.full_name"));
                reportResposeBean.setAge(resultSet.getString("age") + " Years");
                reportResposeBean.setDateTime(currentDateTime);
                
                ResultSet medicalHistoryRs = MySQL.execute("SELECT * "
                        + "FROM medical_history m "
                        + "INNER JOIN treatment_plans t ON m.id = t.medical_history_id "
                        + "WHERE m.patient_nic = '" + patientReport.getNic() + "'");
                
                ArrayList<Vector> arrayList = new ArrayList<>();
                int rowNumber = 0;
                
                while (medicalHistoryRs.next()) {
                    
                    Vector v = new Vector();
                    v.add(++rowNumber);
                    v.add(medicalHistoryRs.getString("m.medical_note"));
                    v.add(medicalHistoryRs.getString("t.plan"));
                    v.add(medicalHistoryRs.getString("m.date_time"));
                    arrayList.add(v);
                    
                }
                reportResposeBean.setTableContainsList(arrayList);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Generating Patient Medical Report for NIC: " + patientReport.getNic());
        return reportResposeBean;
    }
    
    @Override
    public ReportResposeBean visit(DiagnosticReport diagnosticReport) {
        return null;
    }

    @Override
    public ReportResposeBean visit(FinancialReport financialReport) {
        return null;
    }
}

class DiagnosticReportVisitor implements ReportVisitor {

    @Override
    public ReportResposeBean visit(DiagnosticReport diagnosticReport) {
        System.out.println("Generating Diagnostic Report ID: " + diagnosticReport.getReportId());
        return null;
    }
    
    @Override
    public ReportResposeBean visit(PatientReport patientReport) {
        return null;
    }

    @Override
    public ReportResposeBean visit(FinancialReport financialReport) {
        return null;
    }
}

class FinancialReportVisitor implements ReportVisitor {

    @Override
    public ReportResposeBean visit(FinancialReport financialReport) {
        System.out.println("Generating Financial Report...");
        return null;
    }
    
    @Override
    public ReportResposeBean visit(PatientReport patientReport) {
        return null;
    }

    @Override
    public ReportResposeBean visit(DiagnosticReport diagnosticReport) {
        return null;
    }
}

// 5. Demo
public class Report {

    public static void main(String[] args) {
        ReportElement patientReport = new PatientReport("123456789V");
        ReportElement diagnosticReport = new DiagnosticReport("RPT-001");
        ReportElement financialReport = new FinancialReport();

        // Visitors
        ReportVisitor patientVisitor = new PatientReportVisitor();
        ReportVisitor diagnosticVisitor = new DiagnosticReportVisitor();
        ReportVisitor financialVisitor = new FinancialReportVisitor();

        // Generate each report
        patientReport.accept(patientVisitor);
        diagnosticReport.accept(diagnosticVisitor);
        financialReport.accept(financialVisitor);
    }
}
