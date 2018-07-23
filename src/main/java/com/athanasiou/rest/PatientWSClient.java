package com.athanasiou.rest;

import com.athanasiou.rest.model.Patient;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;

/*
create patient
update patient
delete patient
through rest call to spring boot app
*/





public class PatientWSClient {

	private static final String PATIENTS = "/patients";
	private static final String PATIENT_SERVICE_URL = "http://localhost:8080/restWSApp/services/patientservice";

	public static void main(String[] args) {

		Client client = ClientBuilder.newClient();


		Patient newPatient = new Patient();
		newPatient.setName("Constantinos Athanasiou");
		WebTarget postTarget = client.target(PATIENT_SERVICE_URL).path(PATIENTS);
		Patient createdPatient = postTarget.request().post(Entity.entity(newPatient, MediaType.APPLICATION_XML),
				Patient.class);
		System.out.println("Created Patient ID " + createdPatient.getId());



		WebTarget target = client.target(PATIENT_SERVICE_URL).path(PATIENTS).path("/{id}").resolveTemplate("id", 160);
		Invocation.Builder request = target.request();
		Patient patient = request.get(Patient.class);
		System.out.println(patient.getId());
		System.out.println(patient.getName());
		patient.setName("Michail Konstantina!");
		WebTarget putTarget = client.target(PATIENT_SERVICE_URL).path(PATIENTS);
		Response updateResponse = putTarget.request().put(Entity.entity(patient, MediaType.APPLICATION_XML));
		System.out.println(updateResponse.getStatus());
		updateResponse.close();


		WebTarget targetDelete = client.target(PATIENT_SERVICE_URL).path(PATIENTS).path("/{id}").resolveTemplate("id", 158);
		Invocation.Builder requestDelete = targetDelete.request();
		Patient patientDelete = requestDelete.get(Patient.class);
		System.out.println(patientDelete.getId());
		System.out.println(patientDelete.getName());
		Response deleteResponse = targetDelete.request().delete();
		System.out.println(deleteResponse.getStatus());
		deleteResponse.close();

		client.close();

	}

}
