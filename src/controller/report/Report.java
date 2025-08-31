/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.report;

// 1. Common interface
interface ReportElement {
    void accept(ReportVisitor visitor);
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
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}



class DiagnosticReport implements ReportElement {
    private String reportId;
    public DiagnosticReport(String id) { this.reportId = id; }
    public String getReportId() { return reportId; }

    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}

class FinancialReport implements ReportElement {
    @Override
    public void accept(ReportVisitor visitor) {
        visitor.visit(this);
    }
}

// 3. Visitor Interface
interface ReportVisitor {
    void visit(PatientReport patientReport);
    void visit(DiagnosticReport diagnosticReport);
    void visit(FinancialReport financialReport);
}

// 4. Concrete Visitors (each report type logic)
class PatientReportVisitor implements ReportVisitor {
    @Override
    public void visit(PatientReport patientReport) {
        // Your Patient Medical Report DB logic
        System.out.println("Generating Patient Medical Report for NIC: " + patientReport.getNic());
        // (insert your loadReportData() logic here)
    }

    @Override
    public void visit(DiagnosticReport diagnosticReport) {}
    @Override
    public void visit(FinancialReport financialReport) {}
}

class DiagnosticReportVisitor implements ReportVisitor {
    @Override
    public void visit(DiagnosticReport diagnosticReport) {
        // Your Diagnostic Report DB logic
        System.out.println("Generating Diagnostic Report ID: " + diagnosticReport.getReportId());
    }

    @Override
    public void visit(PatientReport patientReport) {}
    @Override
    public void visit(FinancialReport financialReport) {}
}

class FinancialReportVisitor implements ReportVisitor {
    @Override
    public void visit(FinancialReport financialReport) {
        // Your Financial Report DB logic
        System.out.println("Generating Financial Report...");
    }

    @Override
    public void visit(PatientReport patientReport) {}
    @Override
    public void visit(DiagnosticReport diagnosticReport) {}
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
