package com.cbmachinery.aftercareserviceagent.report.util;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.report.dto.MaintainanceKeys;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.task.model.Maintainance;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public class ReportDataUtil {

	private static final DateTimeFormatter DT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	private static final DateTimeFormatter D_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static final Map<BreakdownKeys, String> BREAKDOWN_HEADERS = Map.ofEntries(Map.entry(BreakdownKeys.ID, "ID"),
			Map.entry(BreakdownKeys.DESCRIPTION, "Description"),
			Map.entry(BreakdownKeys.BREAKDOWN_TYPE, "Breakdown Type"),
			Map.entry(BreakdownKeys.RISK_LEVEL, "Risk Level"),
			Map.entry(BreakdownKeys.REPORTED_DATE, "Reported Date & Time"),
			Map.entry(BreakdownKeys.PRODUCT_ID, "Product ID"), Map.entry(BreakdownKeys.PRODUCT_NAME, "Product Name"),
			Map.entry(BreakdownKeys.CLIENT_ID, "Client ID"), Map.entry(BreakdownKeys.CLIENT_NAME, "Client Name"),
			Map.entry(BreakdownKeys.CLIENT_EMAIL, "Client Email"),
			Map.entry(BreakdownKeys.PRIORITY_LEVEL, "Priority Level"), Map.entry(BreakdownKeys.STATUS, "Status"),
			Map.entry(BreakdownKeys.TECHNICIAN_ID, "Technician ID"),
			Map.entry(BreakdownKeys.TECHNICIAN_NAME, "Technician Name"),
			Map.entry(BreakdownKeys.ROOT_CAUSE, "Root Cause"), Map.entry(BreakdownKeys.SOLUTION, "Solution"));

	public static final Map<MaintainanceKeys, String> MAINTAINANCE_HEADERS = Map.ofEntries(
			Map.entry(MaintainanceKeys.ID, "ID"), Map.entry(MaintainanceKeys.DESCRIPTION, "Description"),
			Map.entry(MaintainanceKeys.MAINTAINANCE_TYPE, "Maintainance Type"),
			Map.entry(MaintainanceKeys.REPORTED_DATE, "Reported Date & Time"),
			Map.entry(MaintainanceKeys.PRODUCT_ID, "Product ID"),
			Map.entry(MaintainanceKeys.PRODUCT_NAME, "Product Name"),
			Map.entry(MaintainanceKeys.CLIENT_ID, "Client ID"), Map.entry(MaintainanceKeys.CLIENT_NAME, "Client Name"),
			Map.entry(MaintainanceKeys.CLIENT_EMAIL, "Client Email"), Map.entry(MaintainanceKeys.STATUS, "Status"),
			Map.entry(MaintainanceKeys.TECHNICIAN_ID, "Technician ID"),
			Map.entry(MaintainanceKeys.TECHNICIAN_NAME, "Technician Name"),
			Map.entry(MaintainanceKeys.SCHEDULE_DATE, "Schedule Date"));

	public static List<String> mapBreakdownKeysToHeaders(List<BreakdownKeys> keys) {
		return keys.stream().map(BREAKDOWN_HEADERS::get).collect(Collectors.toList());
	}

	public static List<String> mapMaintainanceKeysToHeaders(List<MaintainanceKeys> keys) {
		return keys.stream().map(MAINTAINANCE_HEADERS::get).collect(Collectors.toList());
	}

	public static Map<BreakdownKeys, String> mapBreakdown(Breakdown breakdown) {
		Product p = breakdown.getProduct();
		Client c = p.getClient();
		Technician t = breakdown.getTechnician();
		return Map.ofEntries(Map.entry(BreakdownKeys.ID, String.valueOf(breakdown.getId())),
				Map.entry(BreakdownKeys.DESCRIPTION, escapeSpecialCharacters(breakdown.getDescription())),
				Map.entry(BreakdownKeys.BREAKDOWN_TYPE, breakdown.getBreakdownType().name()),
				Map.entry(BreakdownKeys.RISK_LEVEL, breakdown.getRiskLevel().name()),
				Map.entry(BreakdownKeys.REPORTED_DATE, breakdown.getCreatedAt().format(DT_FORMATTER)),
				Map.entry(BreakdownKeys.PRODUCT_ID, p.getErpId()), Map.entry(BreakdownKeys.PRODUCT_NAME, p.getName()),
				Map.entry(BreakdownKeys.CLIENT_ID, c.getErpId()),
				Map.entry(BreakdownKeys.CLIENT_NAME, c.getFirstName() + " " + c.getLastName()),
				Map.entry(BreakdownKeys.CLIENT_EMAIL, c.getEmail()),
				Map.entry(BreakdownKeys.PRIORITY_LEVEL, breakdown.getPriorityLevel().name()),
				Map.entry(BreakdownKeys.STATUS, breakdown.getStatus().name()),
				Map.entry(BreakdownKeys.TECHNICIAN_ID, Objects.nonNull(t) ? t.getErpId() : ""),
				Map.entry(BreakdownKeys.TECHNICIAN_NAME,
						Objects.nonNull(t) ? t.getFirstName() + " " + t.getLastName() : ""),
				Map.entry(BreakdownKeys.ROOT_CAUSE, escapeSpecialCharacters(breakdown.getRootCause())),
				Map.entry(BreakdownKeys.SOLUTION, escapeSpecialCharacters(breakdown.getRootCause())));
	}

	public static Map<MaintainanceKeys, String> mapMaintainance(Maintainance maintainance) {
		Product p = maintainance.getProduct();
		Client c = p.getClient();
		Technician t = maintainance.getTechnician();
		String scheduleDate = Optional.ofNullable(maintainance.getScheduledDate()).map(d -> d.format(D_FORMATTER))
				.orElse("");
		return Map.ofEntries(Map.entry(MaintainanceKeys.ID, String.valueOf(maintainance.getId())),
				Map.entry(MaintainanceKeys.DESCRIPTION, escapeSpecialCharacters(maintainance.getDescription())),
				Map.entry(MaintainanceKeys.MAINTAINANCE_TYPE, maintainance.getMaintainanceType().name()),
				Map.entry(MaintainanceKeys.REPORTED_DATE, maintainance.getCreatedAt().format(DT_FORMATTER)),
				Map.entry(MaintainanceKeys.SCHEDULE_DATE, scheduleDate),
				Map.entry(MaintainanceKeys.PRODUCT_ID, p.getErpId()),
				Map.entry(MaintainanceKeys.PRODUCT_NAME, p.getName()),
				Map.entry(MaintainanceKeys.CLIENT_ID, c.getErpId()),
				Map.entry(MaintainanceKeys.CLIENT_NAME, c.getFirstName() + " " + c.getLastName()),
				Map.entry(MaintainanceKeys.CLIENT_EMAIL, c.getEmail()),
				Map.entry(MaintainanceKeys.STATUS, maintainance.getStatus().name()),
				Map.entry(MaintainanceKeys.TECHNICIAN_ID, Objects.nonNull(t) ? t.getErpId() : ""),
				Map.entry(MaintainanceKeys.TECHNICIAN_NAME,
						Objects.nonNull(t) ? t.getFirstName() + " " + t.getLastName() : ""));
	}

	public static String escapeSpecialCharacters(String data) {
		if (Objects.isNull(data)) {
			return "";
		}
		String escapedData = data.replaceAll("\\R", " ");
		if (data.contains(",") || data.contains("\"") || data.contains("'")) {
			data = data.replace("\"", "\"\"");
			escapedData = "\"" + data + "\"";
		}
		return escapedData;
	}

}
