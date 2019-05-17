package co.axelrod.rpi.weather.station.ui;

import co.axelrod.rpi.weather.station.pi.sensor.co2.CO2Sensor;
import co.axelrod.rpi.weather.station.pi.sensor.weather.WeatherSensor;
import co.axelrod.rpi.weather.station.util.TelegramUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Collections;

@Slf4j
@Profile("telegram")
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Autowired
    private WeatherSensor weatherSensor;

    @Autowired
    private CO2Sensor co2Sensor;

    private ReplyKeyboard getKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Температура, влажность и CO₂");
        replyKeyboard.setKeyboard(Collections.singletonList(keyboardRow));
        return replyKeyboard;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if ((update.hasMessage() && update.getMessage().hasText())) {
                Long telegramChatId = update.getMessage().getChatId();
                log.info("New request from Telegram bot with id: " + telegramChatId);

                execute(TelegramUtil.prepareMessage(telegramChatId,
                        weatherSensor.getResult() + co2Sensor.getResult(),
                        getKeyboard()));
            }
        } catch (TelegramApiException ex) {
            log.error("Telegram Bot is unable to process update!", ex);
        }
    }

    @Override
    public String getBotUsername() {
        return "Мой дом";
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }
}
