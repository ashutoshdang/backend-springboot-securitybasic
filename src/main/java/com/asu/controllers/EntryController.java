package com.asu.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asu.document.Patient;
import com.asu.service.EntryServiceI;

@RestController
@RequestMapping("/api")
public class EntryController {
	@Autowired
	private EntryServiceI entryService;

	@GetMapping("/createPatient")
	public ResponseEntity<String> create(){
		entryService.createPatient();
		return new ResponseEntity<String>("Ok",HttpStatus.I_AM_A_TEAPOT);
	}
	@GetMapping("/getPatientByName")
	public ResponseEntity<Map<String,Patient>> getPatientByName(@RequestParam("name") String name){
//		return entryService.getPatientByName(name);
		return new ResponseEntity<Map<String,Patient>>(entryService.getPatientByName(name),HttpStatus.I_AM_A_TEAPOT);
	}
	@GetMapping("/getPatientByDisease")
	public  ResponseEntity<Map<String,List<Patient>>> getPatientByDisease(@RequestParam("disease") String disease){
		return new ResponseEntity<Map<String,List<Patient>>>(entryService.getPatientByDisease(disease),HttpStatus.I_AM_A_TEAPOT);
//		return new ResponseEntity<String>("found Employee",HttpStatus.I_AM_A_TEAPOT);
	}
	@PostMapping("/newPatient")
	public ResponseEntity<String> createPatient(@RequestBody Patient p){
		entryService.savePatient(p);
		return new ResponseEntity<String>("New Patient Created",HttpStatus.I_AM_A_TEAPOT);
	}
	
	@PostMapping("/updateDiagonisedWith")
	public ResponseEntity<String> updatediagonisedWith(@RequestParam String patientName, @RequestParam String diagonisedWith){
		entryService.updatediagonisedWith(patientName,diagonisedWith);
		return new ResponseEntity<String>("Disease Updated",HttpStatus.I_AM_A_TEAPOT);
	}
	
}
