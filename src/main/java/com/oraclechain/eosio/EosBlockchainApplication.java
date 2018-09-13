package com.oraclechain.eosio;

//import com.github.pagehelper.PageHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class EosBlockchainApplication extends SpringBootServletInitializer {

	public static void main(String[] args)
	{
		SpringApplication.run(EosBlockchainApplication.class, args);
	}


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EosBlockchainApplication.class);
	}

}

