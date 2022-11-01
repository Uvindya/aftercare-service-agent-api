package com.cbmachinery.aftercareserviceagent.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unable to process the requested report")
public class ReportProcessingException extends RuntimeException {

	public ReportProcessingException(String arg0) {
		super(arg0);
	}
}
