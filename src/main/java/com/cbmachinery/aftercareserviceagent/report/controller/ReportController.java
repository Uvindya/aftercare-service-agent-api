package com.cbmachinery.aftercareserviceagent.report.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
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
	public ResponseEntity<byte[]> breakdownReport(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate from,
			@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate to, @RequestBody List<BreakdownKeys> keys) {
		byte[] fileContent = reportService.breakdownReport(keys, from, to);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentLength(fileContent.length);
		headers.setContentDispositionFormData("attachment", "test.csv");
		return new ResponseEntity<>(fileContent, headers, HttpStatus.CREATED);
	}
}
