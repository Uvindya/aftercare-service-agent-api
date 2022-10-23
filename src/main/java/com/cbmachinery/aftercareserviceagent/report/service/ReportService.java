package com.cbmachinery.aftercareserviceagent.report.service;

import java.time.LocalDate;
import java.util.List;

import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;

public interface ReportService {

	public byte[] breakdownReport(List<BreakdownKeys> keys, LocalDate from, LocalDate to);

}
