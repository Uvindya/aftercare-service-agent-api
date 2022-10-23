package com.cbmachinery.aftercareserviceagent.report.util;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.cbmachinery.aftercareserviceagent.product.model.Product;
import com.cbmachinery.aftercareserviceagent.report.dto.BreakdownKeys;
import com.cbmachinery.aftercareserviceagent.task.model.Breakdown;
import com.cbmachinery.aftercareserviceagent.user.model.Client;
import com.cbmachinery.aftercareserviceagent.user.model.Technician;

public class ReportDataUtil {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static final Map<BreakdownKeys, String> breakdownHeaders = Map.ofEntries(Map.entry(BreakdownKeys.ID, "ID"),
			Map.entry(BreakdownKeys.DESCRIPTION, "Description"),
			Map.entry(BreakdownKeys.BREAKDOWN_TYPE, "Breakdown Type"),
			Map.entry(BreakdownKeys.RISK_LEVEL, "Risk Level"),
			Map.entry(BreakdownKeys.REPORTED_DATE, "Reported Date & Time"),
			Map.entry(BreakdownKeys.PRODUCT_ID, "Product ID"), Map.entry(BreakdownKeys.PRODUCT_NAME, "Product Name"),
			Map.entry(BreakdownKeys.CLIENT_ID, "Client ID"), Map.entry(BreakdownKeys.CLIENT_NAME, "Client Name"),
			Map.entry(BreakdownKeys.CLIENT_EMAIL, "Client Email"),
			Map.entry(BreakdownKeys.PRIORITY_LEVEL, "Priority Level"), Map.entry(BreakdownKeys.STATUS, "Status"),
			Map.entry(BreakdownKeys.TECH_ID, "Technician ID"), Map.entry(BreakdownKeys.TECH_NAME, "Technician Name"),
			Map.entry(BreakdownKeys.ROOT_CAUSE, "Root Cause"), Map.entry(BreakdownKeys.SOLUTION, "Solution"));

	public static List<String> mapBreakdownKeysToHeaders(List<BreakdownKeys> keys) {
		return keys.stream().map(breakdownHeaders::get).collect(Collectors.toList());
	}

	public static Map<BreakdownKeys, String> mapBreakdown(Breakdown breakdown) {
		Product p = breakdown.getProduct();
		Client c = p.getClient();
		Technician t = breakdown.getTechnician();
		return Map.ofEntries(Map.entry(BreakdownKeys.ID, String.valueOf(breakdown.getId())),
				Map.entry(BreakdownKeys.DESCRIPTION, escapeSpecialCharacters(breakdown.getDescription())),
				Map.entry(BreakdownKeys.BREAKDOWN_TYPE, breakdown.getBreakdownType().name()),
				Map.entry(BreakdownKeys.RISK_LEVEL, breakdown.getRiskLevel().name()),
				Map.entry(BreakdownKeys.REPORTED_DATE, breakdown.getCreatedAt().format(FORMATTER)),
				Map.entry(BreakdownKeys.PRODUCT_ID, p.getErpId()), Map.entry(BreakdownKeys.PRODUCT_NAME, p.getName()),
				Map.entry(BreakdownKeys.CLIENT_ID, c.getErpId()),
				Map.entry(BreakdownKeys.CLIENT_NAME, c.getFirstName() + " " + c.getLastName()),
				Map.entry(BreakdownKeys.CLIENT_EMAIL, c.getEmail()),
				Map.entry(BreakdownKeys.PRIORITY_LEVEL, breakdown.getPriorityLevel().name()),
				Map.entry(BreakdownKeys.STATUS, breakdown.getStatus().name()),
				Map.entry(BreakdownKeys.TECH_ID, Objects.nonNull(t) ? t.getErpId() : ""),
				Map.entry(BreakdownKeys.TECH_NAME, Objects.nonNull(t) ? t.getFirstName() + " " + t.getLastName() : ""),
				Map.entry(BreakdownKeys.ROOT_CAUSE, escapeSpecialCharacters(breakdown.getRootCause())),
				Map.entry(BreakdownKeys.SOLUTION, escapeSpecialCharacters(breakdown.getRootCause())));
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
