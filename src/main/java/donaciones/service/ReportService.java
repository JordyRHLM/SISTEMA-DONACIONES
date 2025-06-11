package donaciones.service;
public interface ReportService {
    byte[] generateDashboardPdfReport();
    byte[] generateDashboardExcelReport();
}