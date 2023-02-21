package com.example.patientsmvc;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.patientsmvc.entities.Patient;
import com.example.patientsmvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientsMvcApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
		return args -> {
			patientRepository.save(new Patient(null,"Amine",new Date(),false,10));
			patientRepository.save(new Patient(null,"Amine1",new Date(),true,11));
			patientRepository.save(new Patient(null,"Amine2",new Date(),false,12));
			patientRepository.save(new Patient(null,"Amine3",new Date(),true,13));

			patientRepository.findAll().forEach(p-> {
				System.out.println(p.getNom());
			});
		};
	}
}
