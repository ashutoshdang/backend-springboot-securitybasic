package com.asu.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asu.document.Patient;

public interface PatientRepository extends MongoRepository<Patient, String> {

	List<Patient> findByPatientName(String patientName);

	List<Patient> findByDiagonisedWith(String diseaseName);

}
