package donaciones.service.impl;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import donaciones.service.DashboardService;
import donaciones.service.ReportService;
import donaciones.dto.response.DashboardStatsResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.Row;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final DashboardService dashboardService;

    @Override
    public byte[] generateDashboardPdfReport() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            
            document.open();
            
            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Sistema de Donaciones-Reporte", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            // Datos
            DashboardStatsResponse stats = dashboardService.getDashboardStats();
            
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Total de Donaciones: " + stats.getTotalDonations()));
            document.add(new Paragraph("Campañas Activas: " + stats.getActiveCampaigns()));
            document.add(new Paragraph("Total de Voluntarios: " + stats.getTotalVolunteers()));
            document.add(new Paragraph("Organizaciones ayudadas: " + stats.getOrganizationsHelped()));
            
            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el reporte PDF", e);
        }
    }

    @Override
    public byte[] generateDashboardExcelReport() {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             XSSFWorkbook workbook = new XSSFWorkbook()) {
            
            XSSFSheet sheet = workbook.createSheet("Sistema de Donaciones-Reporte");
            
            // Encabezados
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Metric");
            headerRow.createCell(1).setCellValue("Value");
            
            // Datos
            DashboardStatsResponse stats = dashboardService.getDashboardStats();
            
            Row dataRow1 = sheet.createRow(1);
            dataRow1.createCell(0).setCellValue("Total de donaciones");
            dataRow1.createCell(1).setCellValue(stats.getTotalDonations().doubleValue());
            
            Row dataRow2 = sheet.createRow(2);
            dataRow2.createCell(0).setCellValue("Campañas activas");
            dataRow2.createCell(1).setCellValue(stats.getActiveCampaigns());
            // Row dataRow3 = sheet.createRow(3);
            Row dataRow3 = sheet.createRow(3);  
            dataRow3.createCell(0).setCellValue("Total de voluntarios");
            dataRow3.createCell(1).setCellValue(stats.getTotalVolunteers());
            Row dataRow4 = sheet.createRow(4);
            dataRow4.createCell(0).setCellValue("Organizaciones ayudadas");
            dataRow4.createCell(1).setCellValue(stats.getOrganizationsHelped());
            // ... agregar más filas según sea necesario
            
            workbook.write(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating Excel report", e);
        }
    }
}