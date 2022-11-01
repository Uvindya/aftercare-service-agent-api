package com.cbmachinery.aftercareserviceagent.report.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.MaintainanceKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.ReporKeysOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.exception.ReportProcessingException;
import com.cbmachinery.aftercareserviceagent.report.service.ReportService;
import com.cbmachinery.aftercareserviceagent.report.util.ReportDataUtil;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.service.BreakdownService;
import com.cbmachinery.aftercareserviceagent.task.service.MaintainanceService;

@Service
public class ReportServiceImpl implements ReportService {

	private final BreakdownService breakdownService;
	private final MaintainanceService maintainanceService;

	public ReportServiceImpl(final BreakdownService breakdownService, final MaintainanceService maintainanceService) {
		super();
		this.breakdownService = breakdownService;
		this.maintainanceService = maintainanceService;
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
			throw new ReportProcessingException(e.getLocalizedMessage());
		}
	}

	private Path createTempFile() {
		try {
			return Files.createTempFile(UUID.randomUUID().toString(), ".csv");
		} catch (IOException e) {
			throw new ReportProcessingException(e.getLocalizedMessage());
		}
	}

	@Override
	public ReporKeysOutputDTO getReportKeys() {
		return new ReporKeysOutputDTO(Arrays.asList(BreakdownKeys.values()), Arrays.asList(MaintainanceKeys.values()));
	}

	@Override
	public byte[] maintainanceReport(List<MaintainanceKeys> keys, LocalDate from, LocalDate to) {
		Path path = createTempFile();
		List<Maintainance> maintainances = this.maintainanceService.findByReportedAt(from, to);
		try (BufferedWriter writer = Files.newBufferedWriter(path);

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
						.withHeader(ReportDataUtil.mapMaintainanceKeysToHeaders(keys).toArray(String[]::new)));) {

			for (int i = 0; i < maintainances.size(); i++) {
				Map<MaintainanceKeys, String> b = ReportDataUtil.mapMaintainance(maintainances.get(i));
				csvPrinter.printRecord(keys.stream().map(k -> b.get(k)).collect(Collectors.toList()));
				i++;
			}

			csvPrinter.flush();

			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new ReportProcessingException(e.getLocalizedMessage());
		}
	}

}
