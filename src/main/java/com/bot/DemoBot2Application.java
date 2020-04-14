package com.bot;

import com.microsoft.bot.integration.AdapterWithErrorHandler;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.integration.spring.BotController;
import com.microsoft.bot.integration.spring.BotDependencyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication

@Import({BotController.class})
public class DemoBot2Application extends BotDependencyConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(DemoBot2Application.class, args);
	}

	@Override
	public BotFrameworkHttpAdapter getBotFrameworkHttpAdaptor(Configuration configuration) {
		return new AdapterWithErrorHandler(configuration);
	}
}
