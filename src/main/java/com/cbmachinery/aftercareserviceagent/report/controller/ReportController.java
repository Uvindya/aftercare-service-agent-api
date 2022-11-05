package com.cbmachinery.aftercareserviceagent.report.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.DashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.MaintainanceKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.ReporKeysOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.WorksheetKeys;
import com.cbmachinery.aftercareserviceagent.report.service.ReportService;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

	private final ReportService reportService;

	public ReportController(final ReportService reportService) {
		super();
		this.reportService = reportService;
	}

	@PostMapping("/breakdown")
	public ResponseEntity<byte[]> breakdownReport(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to, @RequestBody List<BreakdownKeys> keys) {
		byte[] fileContent = reportService.breakdownReport(keys, from, to);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(fileContent.length);
		headers.setContentDispositionFormData("attachment", "test.csv");
		return new ResponseEntity<>(fileContent, headers, HttpStatus.CREATED);
	}

	@PostMapping("/maintainance")
	public ResponseEntity<byte[]> maintainanceReport(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
			@RequestBody List<MaintainanceKeys> keys) {
		byte[] fileContent = reportService.maintainanceReport(keys, from, to);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(fileContent.length);
		headers.setContentDispositionFormData("attachment", "test.csv");
		return new ResponseEntity<>(fileContent, headers, HttpStatus.CREATED);
	}

	@PostMapping("/maintainance/upcomming")
	public ResponseEntity<byte[]> upcommingMaintainanceReport(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
			@RequestBody List<MaintainanceKeys> keys) {
		byte[] fileContent = reportService.upcommingMaintainanceReport(keys, from, to);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(fileContent.length);
		headers.setContentDispositionFormData("attachment", "test.csv");
		return new ResponseEntity<>(fileContent, headers, HttpStatus.CREATED);
	}

	@PostMapping("/worksheet")
	public ResponseEntity<byte[]> worksheetReport(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate to,
			@RequestParam(required = false, defaultValue = "0") long technicianId,
			@RequestBody List<WorksheetKeys> keys) {
		byte[] fileContent = reportService.worksheetReport(keys, from.atStartOfDay(), to.atTime(LocalTime.MAX),
				technicianId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(fileContent.length);
		headers.setContentDispositionFormData("attachment", "test.csv");
		return new ResponseEntity<>(fileContent, headers, HttpStatus.CREATED);
	}

	@GetMapping("/keys")
	public ResponseEntity<ReporKeysOutputDTO> getReportKeys() {
		return ResponseEntity.ok(this.reportService.getReportKeys());
	}

	@GetMapping("/dashboard")
	public ResponseEntity<DashboardOutputDTO> getDashboardSummary() {
		return ResponseEntity.ok(this.reportService.dashboardSummary());
	}
}
