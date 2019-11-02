package com.asu.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection="patientDetails")
public class Patient {

	@Id
	private String patientId;
	private String patientName;
	private String diagonisedWith;
	private String admissionDate;
}
