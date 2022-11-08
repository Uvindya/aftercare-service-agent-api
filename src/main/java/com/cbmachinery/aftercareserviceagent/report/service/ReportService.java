package com.cbmachinery.aftercareserviceagent.report.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.ClientDashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.DashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.MaintainanceKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.ReporKeysOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.TechnicianDashboardOutputDTO;
import com.cbmachinery.aftercareserviceagent.report.dto.WorksheetKeys;

public interface ReportService {

	public byte[] breakdownReport(List<BreakdownKeys> keys, LocalDate from, LocalDate to);

	public byte[] maintainanceReport(List<MaintainanceKeys> keys, LocalDate from, LocalDate to);

	public byte[] upcommingMaintainanceReport(List<MaintainanceKeys> keys, LocalDate from, LocalDate to);

	public byte[] worksheetReport(List<WorksheetKeys> keys, LocalDateTime from, LocalDateTime to, long technicianId);

	public ReporKeysOutputDTO getReportKeys();

	public DashboardOutputDTO dashboardSummary();

	public ClientDashboardOutputDTO clientDashboardSummary(String username);

	public TechnicianDashboardOutputDTO technicianDashboardSummary(String username);

}
