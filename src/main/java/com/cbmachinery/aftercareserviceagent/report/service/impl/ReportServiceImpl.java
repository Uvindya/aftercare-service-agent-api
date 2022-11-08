package com.cbmachinery.aftercareserviceagent.report.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import com.cbmachinery.aftercareserviceagent.product.dto.BasicProductOutputDTO;
import com.cbmachinery.aftercareserviceagent.product.service.ProductService;
import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.ClientDashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.DashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.MaintainanceKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.MonthlySummaryDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.ReporKeysOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.TechnicianDashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.WorksheetKeys;
import com.cbmachinery.aftercareserviceagent.report.exception.ReportProcessingException;
import com.cbmachinery.aftercareserviceagent.report.service.ReportService;
import com.cbmachinery.aftercareserviceagent.report.util.ReportDataUtil;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicBreakdownOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.dto.BasicMaintainanceOutputDTO;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.task.model.enums.BreakdownStatus;
import com.cbmachinery.aftercareserviceagent.task.model.enums.MaintainanceStatus;
import com.cbmachinery.aftercareserviceagent.task.service.BreakdownService;
import com.cbmachinery.aftercareserviceagent.task.service.MaintainanceService;
import com.cbmachinery.aftercareserviceagent.user.service.ClientService;
import com.cbmachinery.aftercareserviceagent.user.service.TechnicianService;

@Service
public class ReportServiceImpl implements ReportService {

	private final BreakdownService breakdownService;
	private final MaintainanceService maintainanceService;
	private final ProductService productService;
	private final TechnicianService technicianService;
	private final ClientService clientService;

	public ReportServiceImpl(final BreakdownService breakdownService, final MaintainanceService maintainanceService,
			final ProductService productService, final TechnicianService technicianService,
			final ClientService clientService) {
		super();
		this.breakdownService = breakdownService;
		this.maintainanceService = maintainanceService;
		this.productService = productService;
		this.technicianService = technicianService;
		this.clientService = clientService;
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
		return new ReporKeysOutputDTO(Arrays.asList(BreakdownKeys.values()), Arrays.asList(MaintainanceKeys.values()),
				Arrays.asList(WorksheetKeys.values()));
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
			}

			csvPrinter.flush();

			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new ReportProcessingException(e.getLocalizedMessage());
		}
	}

	@Override
	public byte[] worksheetReport(List<WorksheetKeys> keys, LocalDateTime from, LocalDateTime to, long technicianId) {
		Path path = createTempFile();

		List<Breakdown> breakdowns = technicianId == 0 ? this.breakdownService.findByAssignedAt(from, to)
				: this.breakdownService.findByAssignedAtForTechnician(from, to, technicianId);

		List<Maintainance> maintainances = technicianId == 0 ? this.maintainanceService.findByAssignedAt(from, to)
				: this.maintainanceService.findByAssignedAtForTechnician(from, to, technicianId);

		try (BufferedWriter writer = Files.newBufferedWriter(path);

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
						.withHeader(ReportDataUtil.mapWorksheetKeysToHeaders(keys).toArray(String[]::new)));) {

			for (int i = 0; i < breakdowns.size(); i++) {
				Map<WorksheetKeys, String> b = ReportDataUtil.mapBreakdownAsWorksheet(breakdowns.get(i));
				csvPrinter.printRecord(keys.stream().map(k -> b.get(k)).collect(Collectors.toList()));
			}

			for (int i = 0; i < maintainances.size(); i++) {
				Map<WorksheetKeys, String> b = ReportDataUtil.mapMaintainanceAsWorksheet(maintainances.get(i));
				csvPrinter.printRecord(keys.stream().map(k -> b.get(k)).collect(Collectors.toList()));
			}

			csvPrinter.flush();

			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new ReportProcessingException(e.getLocalizedMessage());
		}
	}

	@Override
	public byte[] upcommingMaintainanceReport(List<MaintainanceKeys> keys, LocalDate from, LocalDate to) {
		Path path = createTempFile();
		List<Maintainance> maintainances = this.maintainanceService.findUpcomming(from, to);
		try (BufferedWriter writer = Files.newBufferedWriter(path);

				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
						.withHeader(ReportDataUtil.mapMaintainanceKeysToHeaders(keys).toArray(String[]::new)));) {

			for (int i = 0; i < maintainances.size(); i++) {
				Map<MaintainanceKeys, String> b = ReportDataUtil.mapMaintainance(maintainances.get(i));
				csvPrinter.printRecord(keys.stream().map(k -> b.get(k)).collect(Collectors.toList()));
			}

			csvPrinter.flush();

			return Files.readAllBytes(path);
		} catch (IOException e) {
			throw new ReportProcessingException(e.getLocalizedMessage());
		}
	}

	@Override
	public DashboardOutputDTO dashboardSummary() {
		long breakdownCount = this.breakdownService.count();
		long maintainanceCount = this.maintainanceService.count();

		LocalDate nextMonth = LocalDate.now().plusMonths(1);
		long upCommingMaintainancesCount = this.maintainanceService.findUpcomming(nextMonth.withDayOfMonth(1),
				nextMonth.withDayOfMonth(nextMonth.getMonth().length(nextMonth.isLeapYear()))).size();

		long inProgressMaintainanceCount = this.maintainanceService.inProgressCount();

		long totalTasksCount = breakdownCount + maintainanceCount;

		long totalTasksAfterRemovingScheduled = totalTasksCount - this.maintainanceService.scheduledCount();

		double notStartedPercentage = ((this.breakdownService.notStartedCount()
				+ this.maintainanceService.notStartedCount()) / (double) totalTasksAfterRemovingScheduled) * 100;

		long inProgressBreakdownCount = this.breakdownService.inProgressCount();

		double inprogressPercentage = ((inProgressBreakdownCount + inProgressMaintainanceCount)
				/ (double) totalTasksAfterRemovingScheduled) * 100;

		double completedPercentage = ((this.maintainanceService.completedCount()
				+ this.breakdownService.completedCount()) / (double) totalTasksAfterRemovingScheduled) * 100;

		List<MonthlySummaryDTO> montlySummaries = new ArrayList<>();

		for (LocalDate date = LocalDate.now().minusMonths(12); date
				.isBefore(LocalDate.now()); date = date.plusMonths(1)) {
			long maintainancesCount = this.maintainanceService.findByScheduledAt(date.withDayOfMonth(1),
					date.withDayOfMonth(date.getMonth().length(date.isLeapYear()))).size();

			long breakdownsCount = this.breakdownService.findByReportedAt(date.withDayOfMonth(1),
					date.withDayOfMonth(date.getMonth().length(date.isLeapYear()))).size();

			montlySummaries.add(new MonthlySummaryDTO(date.getYear() + " " + date.getMonth().name(), maintainancesCount,
					breakdownsCount));
		}

		return new DashboardOutputDTO(this.productService.count(), this.clientService.count(),
				this.technicianService.count(), totalTasksCount, maintainanceCount, breakdownCount,
				inProgressBreakdownCount + inProgressMaintainanceCount, upCommingMaintainancesCount,
				this.breakdownService.pendingCount(), inProgressMaintainanceCount,
				this.breakdownService.pendingAcceptenceCount(), notStartedPercentage, inprogressPercentage,
				completedPercentage, montlySummaries);
	}

	@Override
	public ClientDashboardOutputDTO clientDashboardSummary(String username) {
		List<BasicProductOutputDTO> clientProducts = this.productService.findMyProducts(username);
		List<BasicMaintainanceOutputDTO> clientMaintainances = this.maintainanceService.findMyOwnership(username);
		List<BasicBreakdownOutputDTO> clientBreakdowns = this.breakdownService.findMyOwnership(username);

		LocalDate nextMonth = LocalDate.now().plusMonths(1);

		long activeBreakdowns = clientBreakdowns.stream()
				.filter(b -> Arrays.asList(BreakdownStatus.NEW, BreakdownStatus.TECH_ASSIGNED,
						BreakdownStatus.IN_PROGRESS, BreakdownStatus.NEEDS_CLIENTS_ACCEPTENCE).contains(b.getStatus()))
				.count();

		long upCommingMaintainances = clientMaintainances.stream()
				.filter(m -> Arrays
						.asList(MaintainanceStatus.CLIENT_ACKNOWLEDGED, MaintainanceStatus.SCHEDULED,
								MaintainanceStatus.TECH_ASSIGNED)
						.contains(m.getStatus()) && m.getScheduledDate().isAfter(nextMonth.withDayOfMonth(1))
						&& m.getScheduledDate().isBefore(
								nextMonth.withDayOfMonth(nextMonth.getMonth().length(nextMonth.isLeapYear()))))
				.count();

		long breakdownsForAcceptence = clientBreakdowns.stream()
				.filter(b -> Arrays.asList(BreakdownStatus.NEEDS_CLIENTS_ACCEPTENCE).contains(b.getStatus())).count();
		long maintainanceForAcceptence = clientMaintainances.stream()
				.filter(m -> Arrays.asList(MaintainanceStatus.NEEDS_CLIENTS_ACCEPTENCE).contains(m.getStatus()))
				.count();

		return new ClientDashboardOutputDTO(clientProducts.size(), activeBreakdowns,
				breakdownsForAcceptence + maintainanceForAcceptence, upCommingMaintainances);
	}

	@Override
	public TechnicianDashboardOutputDTO technicianDashboardSummary(String username) {

		List<BasicMaintainanceOutputDTO> technicianMaintainances = this.maintainanceService.findMyAssigns(username);
		List<BasicBreakdownOutputDTO> technicianBreakdowns = this.breakdownService.findMyAssigns(username);

		LocalDate nextMonth = LocalDate.now().plusMonths(1);

		long activeBreakdowns = technicianBreakdowns.stream().filter(b -> Arrays.asList(BreakdownStatus.TECH_ASSIGNED,
				BreakdownStatus.IN_PROGRESS, BreakdownStatus.NEEDS_CLIENTS_ACCEPTENCE).contains(b.getStatus())).count();

		long upCommingMaintainances = technicianMaintainances.stream().filter(
				m -> Arrays.asList(MaintainanceStatus.TECH_ASSIGNED).contains(m.getStatus()) && m.getScheduledDate()
						.isBefore(nextMonth.withDayOfMonth(nextMonth.getMonth().length(nextMonth.isLeapYear()))))
				.count();

		long activeMaintainances = technicianMaintainances.stream()
				.filter(m -> Arrays.asList(MaintainanceStatus.IN_PROGRESS, MaintainanceStatus.NEEDS_CLIENTS_ACCEPTENCE)
						.contains(m.getStatus()))
				.count();

		return new TechnicianDashboardOutputDTO(technicianMaintainances.size() + technicianBreakdowns.size(),
				activeBreakdowns, activeMaintainances, upCommingMaintainances);
	}

}
