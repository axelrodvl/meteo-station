package co.axelrod.rpi.meteo.bot.ui;

import co.axelrod.rpi.meteo.bot.pi.MeteoSensor;
import co.axelrod.rpi.meteo.bot.util.TelegramUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by Vadim Axelrod (vadim@axelrod.co) on 27.02.2018.
 */
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    @Autowired
    private MeteoSensor meteoSensor;

    private ReplyKeyboard getKeyboard() {
        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add("Температура и влажность");
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
                        meteoSensor.getTemperatureInCelsius(),
                        getKeyboard()));
            }
        } catch (TelegramApiException ex) {
            log.error("Telegram Bot is unable to process update!", ex);
        } catch (InterruptedException | IOException e) {
            log.error("Not running on Pi or Pi is not working");
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
