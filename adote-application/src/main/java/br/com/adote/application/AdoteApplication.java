package br.com.adote.application;

import br.com.adote.application.config.AdoteApplicationConfig;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class AdoteApplication {
    private static final Logger logger = LoggerFactory.getLogger(AdoteApplication.class);

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext app = SpringApplication.run(AdoteApplicationConfig.class, args);

        String applicationName = app.getEnvironment().getProperty("spring.application.name");
        String port = app.getEnvironment().getProperty("server.port");
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String currentTime = LocalDateTime.now().toString();

        logger.info(
            "|\n" +
            "|------------------------------------------------------------\n" +
            "|   Application " + applicationName + " is running! Access URLs:\n" +
            "|   Local:      http://127.0.0.1:" + port + "\n" +
            "|   External:   http://" + hostAddress + ":" + port + "\n" +
            "|   Current:    " + currentTime + "\n" +
            "|------------------------------------------------------------"
        );


    }

}
