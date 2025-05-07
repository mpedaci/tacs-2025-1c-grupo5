package utn.tacs.grupo5.bot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class KeyboardFactory {
    public static ReplyKeyboard getCardsOption() {
        KeyboardRow row = new KeyboardRow();
        row.add("Publicar Carta");
        row.add("Hacer Una Oferta");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getStartOption() {
        KeyboardRow row = new KeyboardRow();
        row.add("Log In");
        row.add("Registrarse");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getYesOrNo() {
        KeyboardRow row = new KeyboardRow();
        row.add("Si");
        row.add("No");
        return new ReplyKeyboardMarkup(List.of(row));
    }
}