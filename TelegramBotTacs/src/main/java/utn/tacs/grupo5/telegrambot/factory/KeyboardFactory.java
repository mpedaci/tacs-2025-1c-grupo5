package utn.tacs.grupo5.telegrambot;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import utn.tacs.grupo5.entity.card.Game;
import utn.tacs.grupo5.entity.post.ConservationStatus;

import java.util.List;

public class KeyboardFactory {

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

    public static ReplyKeyboard getCardsOption() {
        KeyboardRow row = new KeyboardRow();
        row.add("Publicar Carta");
        row.add("Hacer Una Oferta");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getGameOption() {
        KeyboardRow row = new KeyboardRow();
        for (Game.Name gameName : Game.Name.values()) {
            row.add(gameName.toString().toLowerCase());
        }
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getCardConditionOption() {
        KeyboardRow row = new KeyboardRow();
        for (ConservationStatus status : ConservationStatus.values()) {
            row.add(status.toString().toLowerCase());
        }
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getCardValueOption() {
        KeyboardRow row = new KeyboardRow();
        row.add("Dinero");
        row.add("Cartas");
        row.add("Ambos");
        return new ReplyKeyboardMarkup(List.of(row));
    }

}