package com.cbmachinery.aftercareserviceagent.report.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.report.service.ReportService;
import com.cbmachinery.aftercareserviceagent.report.util.ReportDataUtil;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.service.BreakdownService;

@Service
public class ReportServiceImpl implements ReportService {

	private final BreakdownService breakdownService;

	public ReportServiceImpl(final BreakdownService breakdownService) {
		super();
		this.breakdownService = breakdownService;
	}

	@Override
	public byte[] breakdownReport(List<BreakdownKeys> keys, LocalDate from, LocalDate to) {
		Path path = createTempFile();
		List<Breakdown> breakdowns = this.breakdownService.findByReportedAt(from, to);
		try (BufferedWriter writer = Files.newBufferedWriter(path);

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
						.withHeader(ReportDataUtil.mapBreakdownKeysToHeaders(keys).toArray(String[]::new)));) {

			for (int i = 0; i < breakdowns.size(); i++) {
				Map<BreakdownKeys, String> b = ReportDataUtil.mapBreakdown(breakdowns.get(i));
				csvPrinter.printRecord(keys.stream().map(k -> b.get(k)).collect(Collectors.toList()));
				i++;
			}

			csvPrinter.flush();

			return Files.readAllBytes(path);
		} catch (IOException e) {

		}
		return null;
	}

	private Path createTempFile() {
		try {
			return Files.createTempFile("test", ".csv");
		} catch (IOException e) {
			throw new IllegalArgumentException("");
		}
	}

}
