package com.project.sms;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class ImportExportController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private MarksRepository marksRepo;

    @Autowired
    private AttendanceRepository attendanceRepo;

    @PostMapping("/import/students")
    public ResponseEntity<?> importStudents(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty!");
        }

        String filename = file.getOriginalFilename();
        if (filename == null || (!filename.endsWith(".csv") && !filename.endsWith(".xlsx"))) {
            return ResponseEntity.badRequest().body("Only .csv or .xlsx files are supported!");
        }

        List<Map<String, Object>> results = new ArrayList<>();
        int successCount = 0;

        try {
            if (filename.endsWith(".csv")) {
             
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(file.getInputStream())
                );
                String line;
                boolean firstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (firstLine) { firstLine = false; continue; } // skip header
                    if (line.trim().isEmpty()) continue;

                    String[] cols = line.split(",");
                    if (cols.length < 4) continue;

                    String name     = cols[0].trim();
                    String email    = cols[1].trim().toLowerCase();
                    String phone    = cols[2].trim();
                    String password = cols[3].trim();

                    if (name.isEmpty() || email.isEmpty()) continue;

                   
                    Student s = new Student();
                    s.setName(name);
                    s.setEmail(email);
                    s.setPhone(phone);
                    s.setPassword(password);
                    studentRepo.save(s);

                    Map<String, Object> r = new HashMap<>();
                    r.put("name", name);
                    r.put("status", "SUCCESS");
                    results.add(r);
                    successCount++;
                }

            } else {
               
                Workbook workbook = new XSSFWorkbook(file.getInputStream());
                Sheet sheet = workbook.getSheetAt(0);

                for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;

                    String name     = getCellValue(row, 0);
                    String email    = getCellValue(row, 1).toLowerCase();
                    String phone    = getCellValue(row, 2);
                    String password = getCellValue(row, 3);

                    if (name.isEmpty() || email.isEmpty()) continue;

                 
                    Student s = new Student();
                    s.setName(name);
                    s.setEmail(email);
                    s.setPhone(phone);
                    s.setPassword(password);
                    studentRepo.save(s);

                    Map<String, Object> r = new HashMap<>();
                    r.put("name", name);
                    r.put("status", "SUCCESS");
                    results.add(r);
                    successCount++;
                }
                workbook.close();
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Import failed: " + e.getMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("successCount", successCount);
        response.put("skipCount", 0);
        response.put("details", results);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/export/students")
    public ResponseEntity<byte[]> exportStudents() throws IOException {
        List<Student> students = studentRepo.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Students");
        CellStyle headerStyle = createHeaderStyle(workbook);

        Row header = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Email", "Phone"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5000);
        }

        int rowNum = 1;
        for (Student s : students) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getName());
            row.createCell(2).setCellValue(s.getEmail());
            row.createCell(3).setCellValue(s.getPhone() != null ? s.getPhone() : "");
        }

        byte[] bytes = workbookToBytes(workbook);
        workbook.close();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=students.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }


    @GetMapping("/export/marks")
    public ResponseEntity<byte[]> exportMarks() throws IOException {
        List<Marks> marksList = marksRepo.findAll();
        List<Student> students = studentRepo.findAll();
        Map<Long, String> nameMap = new HashMap<>();
        students.forEach(s -> nameMap.put(s.getId(), s.getName()));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Marks");
        CellStyle headerStyle = createHeaderStyle(workbook);

        Row header = sheet.createRow(0);
        String[] headers = {"Student ID", "Student Name", "Subject", "Marks", "Grade"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5500);
        }

        int rowNum = 1;
        for (Marks m : marksList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(m.getStudentId());
            row.createCell(1).setCellValue(nameMap.getOrDefault(m.getStudentId(), "Unknown"));
            row.createCell(2).setCellValue(m.getSubject());
            row.createCell(3).setCellValue(m.getMarks());
            row.createCell(4).setCellValue(m.getMarks() >= 75 ? "Good" : m.getMarks() >= 40 ? "Average" : "Below Pass");
        }

        byte[] bytes = workbookToBytes(workbook);
        workbook.close();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=marks.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }


    @GetMapping("/export/attendance")
    public ResponseEntity<byte[]> exportAttendance() throws IOException {
        List<Attendance> records = attendanceRepo.findAll();
        List<Student> students = studentRepo.findAll();
        Map<Long, String> nameMap = new HashMap<>();
        students.forEach(s -> nameMap.put(s.getId(), s.getName()));

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance");
        CellStyle headerStyle = createHeaderStyle(workbook);

        Row header = sheet.createRow(0);
        String[] headers = {"Student ID", "Student Name", "Date", "Status"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
            sheet.setColumnWidth(i, 5500);
        }

        records.sort(Comparator.comparing(Attendance::getStudentId)
                .thenComparing(Attendance::getAttendanceDate));

        int rowNum = 1;
        for (Attendance a : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(a.getStudentId());
            row.createCell(1).setCellValue(nameMap.getOrDefault(a.getStudentId(), "Unknown"));
            row.createCell(2).setCellValue(a.getAttendanceDate().toString());
            row.createCell(3).setCellValue(a.getStatus());
        }

        byte[] bytes = workbookToBytes(workbook);
        workbook.close();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attendance.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }


    @GetMapping("/export/full-report")
    public ResponseEntity<byte[]> exportFullReport() throws IOException {
        List<Student> students = studentRepo.findAll();
        List<Marks> marksList = marksRepo.findAll();
        List<Attendance> records = attendanceRepo.findAll();
        Map<Long, String> nameMap = new HashMap<>();
        students.forEach(s -> nameMap.put(s.getId(), s.getName()));

        Workbook workbook = new XSSFWorkbook();
        CellStyle headerStyle = createHeaderStyle(workbook);

  
        Sheet s1 = workbook.createSheet("Students");
        createHeaderRow(s1, new String[]{"ID", "Name", "Email", "Phone"}, headerStyle);
        int r = 1;
        for (Student s : students) {
            Row row = s1.createRow(r++);
            row.createCell(0).setCellValue(s.getId());
            row.createCell(1).setCellValue(s.getName());
            row.createCell(2).setCellValue(s.getEmail());
            row.createCell(3).setCellValue(s.getPhone() != null ? s.getPhone() : "");
        }
        autoSizeColumns(s1, 4);


        Sheet s2 = workbook.createSheet("Marks");
        createHeaderRow(s2, new String[]{"Student ID", "Student Name", "Subject", "Marks", "Grade"}, headerStyle);
        r = 1;
        for (Marks m : marksList) {
            Row row = s2.createRow(r++);
            row.createCell(0).setCellValue(m.getStudentId());
            row.createCell(1).setCellValue(nameMap.getOrDefault(m.getStudentId(), "Unknown"));
            row.createCell(2).setCellValue(m.getSubject());
            row.createCell(3).setCellValue(m.getMarks());
            row.createCell(4).setCellValue(m.getMarks() >= 75 ? "Good" : m.getMarks() >= 40 ? "Average" : "Below Pass");
        }
        autoSizeColumns(s2, 5);

     
        Sheet s3 = workbook.createSheet("Attendance");
        createHeaderRow(s3, new String[]{"Student ID", "Student Name", "Date", "Status"}, headerStyle);
        records.sort(Comparator.comparing(Attendance::getStudentId)
                .thenComparing(Attendance::getAttendanceDate));
        r = 1;
        for (Attendance a : records) {
            Row row = s3.createRow(r++);
            row.createCell(0).setCellValue(a.getStudentId());
            row.createCell(1).setCellValue(nameMap.getOrDefault(a.getStudentId(), "Unknown"));
            row.createCell(2).setCellValue(a.getAttendanceDate().toString());
            row.createCell(3).setCellValue(a.getStatus());
        }
        autoSizeColumns(s3, 4);

        byte[] bytes = workbookToBytes(workbook);
        workbook.close();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=full-report.xlsx")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(bytes);
    }


    @GetMapping("/import/template")
    public ResponseEntity<byte[]> downloadTemplate() {
        String csv = "name,email,phone,password\n" +
                     "Suriya,suriya@gmail.com,9876543210,1234\n" +
                     "Suriya,suriya@gmail.com,9876543211,1234\n";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=student-import-template.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv.getBytes());
    }

   
    private String getCellValue(Row row, int col) {
        Cell cell = row.getCell(col);
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING  -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((long) cell.getNumericCellValue());
            default      -> "";
        };
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private void createHeaderRow(Sheet sheet, String[] headers, CellStyle style) {
        Row row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void autoSizeColumns(Sheet sheet, int count) {
        for (int i = 0; i < count; i++) sheet.setColumnWidth(i, 5500);
    }

    private byte[] workbookToBytes(Workbook workbook) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        return out.toByteArray();
    }
}
