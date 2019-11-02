package com.asu.service;

import java.util.List;
import java.util.Map;

import com.asu.document.Patient;

public interface EntryServiceI {

	void createPatient();

	Map<String, Patient> getPatientByName(String name);

	void savePatient(Patient p);

	 Map<String,List<Patient>> getPatientByDisease(String disease);

	int updatediagonisedWith(String patientName, String diagonisedWith);
}
