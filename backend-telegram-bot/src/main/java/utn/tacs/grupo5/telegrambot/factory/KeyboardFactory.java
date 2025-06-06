package utn.tacs.grupo5.telegrambot.factory;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;
import utn.tacs.grupo5.telegrambot.dto.game.GameOutputDto;

import java.util.ArrayList;
import java.util.List;

@Component
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

    public static ReplyKeyboard getGameOption(List<GameOutputDto> games) {
        KeyboardRow row = new KeyboardRow();
        for (String gameName : games.stream().map(GameOutputDto::getTitle).toList()) {
            row.add(gameName.toLowerCase());
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

    public static ReplyKeyboard getMore() {
        KeyboardRow row = new KeyboardRow();
        row.add("Mas");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getMoreOrFinish() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Mas"));
        row.add(new KeyboardButton("Finalizar"));
        keyboardRows.add(row);

        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }


    public static ReplyKeyboard getOtherOrFinish() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setSelective(true);
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(new KeyboardButton("Otra"));
        row.add(new KeyboardButton("Finalizar"));
        keyboardRows.add(row);

        keyboard.setKeyboard(keyboardRows);
        return keyboard;
    }

    public static ReplyKeyboard getNextOrNo() {
        KeyboardRow row = new KeyboardRow();
        row.add("Siguiente");
        row.add("No");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getNext() {
        KeyboardRow row = new KeyboardRow();
        row.add("Siguiente");
        return new ReplyKeyboardMarkup(List.of(row));
    }

    public static ReplyKeyboard getContinueOrCancel() {
        KeyboardRow row = new KeyboardRow();
        row.add("Continuar");
        row.add("/cancelar");
        return new ReplyKeyboardMarkup(List.of(row));
    }
}