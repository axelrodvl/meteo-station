package co.axelrod.rpi.weather.station.config;

import co.axelrod.rpi.weather.station.ui.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Configuration
@Profile("telegram")
public class TelegramConfig {
    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot();
    }

    @PostConstruct
    public void registerTelegramBot() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(telegramBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
