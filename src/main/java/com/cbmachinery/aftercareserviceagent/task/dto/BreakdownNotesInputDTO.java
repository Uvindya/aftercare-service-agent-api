package com.cbmachinery.aftercareserviceagent.task.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class BreakdownNotesInputDTO extends NotesInputDTO {

	private final String rootCause;
	private final String solution;

	@JsonCreator
	public BreakdownNotesInputDTO(@JsonProperty("completionNote") String completionNote,
			@JsonProperty("additionalNote") String additionalNote, @JsonProperty("rootCause") String rootCause,
			@JsonProperty("solution") String solution) {
		super(completionNote, additionalNote);
		this.rootCause = rootCause;
		this.solution = solution;
	}

}
