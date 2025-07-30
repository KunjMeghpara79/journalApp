package com.cse.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement//this annotaion means it will treat every db op like one db op on the methods which  are annotated by Transactional
public class JournalApplication {

	public static void main(String[] args) {

		SpringApplication.run(JournalApplication.class, args);

	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){ // dbFactory lets you build connection between your mongoDb
		return new MongoTransactionManager(dbFactory);    //implementation of PlatfromTransactionManager
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
// platformtransactionmanager interface is responsible for managing the transactions.
// and this interface is implemented by mongotransactionmanager  class


}
