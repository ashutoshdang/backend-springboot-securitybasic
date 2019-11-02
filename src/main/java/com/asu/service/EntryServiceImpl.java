package com.asu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.asu.document.Patient;
import com.asu.repository.PatientRepository;
import com.mongodb.WriteResult;

@Service
public class EntryServiceImpl implements EntryServiceI {

	@Autowired
	private PatientRepository patientRepository;
	
	
	@Override
	public void createPatient() {
		
		Patient patient=new Patient();
		patient.setPatientId("P005");
		patient.setPatientName("aston");
		patient.setDiagonisedWith("broken knee");
		patient.setAdmissionDate("24-06-2016");
		patientRepository.save(patient);
	}
	
	public Map<String,Patient>/*List<Patient>*/ getPatientByName(String name){
		Map<String,Patient> patientsMap=new HashMap<>();
			/*ApplicationContext ctx = 
                new AnnotationConfigApplicationContext(Config.class);
			MongoOperations mongoOperation = 
                (MongoOperations) ctx.getBean("test");
			Query query = new Query();
			query.addCriteria(Criteria.where("name").is("ashutosh"));
			List<Patient> users = mongoOperation.find(query,Patient.class);*/
		
		
		List<Patient> patients=patientRepository.findByPatientName(name);
		  
			for(Patient p:patients) {
				System.out.println(p.getPatientId()+">>>"+p.getPatientName()+">>"+p.getAdmissionDate()+">>>"+p.getDiagonisedWith());
				patientsMap.put(name, p);
			}
			return patientsMap;
	}
	
	public Map<String,List<Patient>>/*List<Patient>*/ getPatientByDisease(String diseaseName){
		Map<String,List<Patient>> patientsMap=new HashMap<>();
		List<Patient> listOfPatients=new ArrayList<>();
			/*ApplicationContext ctx = 
                new AnnotationConfigApplicationContext(Config.class);
			MongoOperations mongoOperation = 
                (MongoOperations) ctx.getBean("test");
			Query query = new Query();
			query.addCriteria(Criteria.where("name").is("ashutosh"));
			List<Patient> users = mongoOperation.find(query,Patient.class);*/
		
		
		List<Patient> patients=patientRepository.findByDiagonisedWith(diseaseName);
		  
			for(Patient p:patients) {
				System.out.println(p.getPatientId()+">>>"+p.getPatientName()+">>"+p.getAdmissionDate()+">>>"+p.getDiagonisedWith());
				listOfPatients.add(p);
				patientsMap.put(diseaseName, listOfPatients);
			}
			return patientsMap;
	}

	@Override
	public void savePatient(Patient p) {
		patientRepository.save(p);
	}
	
	@Autowired
    MongoTemplate mongoTemplate;

    @Override
    public int updatediagonisedWith(String patientName, String diagonisedWith) {

        Query query = new Query(Criteria.where("patientName").is(patientName));
        Update update = new Update();
        update.set("diagonisedWith", diagonisedWith);

        WriteResult result = mongoTemplate.updateFirst(query, update, Patient.class);
        

        if(result!=null)
            return result.getN();
        else
            return 0;

    }
}
