package com.FMCSULconferencehandler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FmcsUlConferenceHandlerApplication {

	public static void main(String[] args) {


		SpringApplication.run(FmcsUlConferenceHandlerApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(ParticipantRepository participantRepository,AdminRepository adminRepository)
	{
		return args->{

			//adminRepository.save(new Admin("Marek","Nowak"));
		};
	}

}
