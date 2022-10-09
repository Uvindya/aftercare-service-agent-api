package com.cbmachinery.aftercareserviceagent.task.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class NotesInputDTO {

	private final String completionNote;
	private final String additionalNote;

	@JsonCreator
	public NotesInputDTO(@JsonProperty("completionNote") String completionNote,
			@JsonProperty("additionalNote") String additionalNote) {
		super();
		this.completionNote = completionNote;
		this.additionalNote = additionalNote;
	}

}
