package co.axelrod.rpi.meteo.bot.util;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;

public class TelegramUtil {
    private TelegramUtil() {
        // Utility class
    }

    public static SendMessage prepareMessage(Long chatId, String text, ReplyKeyboard keyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(keyboard);
        return message;
    }
}
