package utn.tacs.grupo5.telegrambot.command;

import org.springframework.stereotype.Component;
import utn.tacs.grupo5.telegrambot.UserState;
import utn.tacs.grupo5.telegrambot.command.card.*;
import utn.tacs.grupo5.telegrambot.command.offer.*;
import utn.tacs.grupo5.telegrambot.command.post.*;
import utn.tacs.grupo5.telegrambot.command.post.ChoosingPhotoCollectionCommand;
import utn.tacs.grupo5.telegrambot.command.session.AwaitingSessionCommand;
import utn.tacs.grupo5.telegrambot.command.session.LoginCommand;
import utn.tacs.grupo5.telegrambot.command.session.RegisterCommand;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.HashMap;
import java.util.Map;

import static utn.tacs.grupo5.telegrambot.UserState.*;

/**
 * Factory for creating state commands based on the user state
 */
@Component
public class StateCommandFactory {
    private final Map<UserState, StateCommand> commands = new HashMap<>();

    // Inject all command implementations
    public StateCommandFactory(
            AwaitingSessionCommand awaitingSessionCommand,
            LoginCommand loginCommand,
            RegisterCommand registerCommand,
            ChoosingOptionsCommand choosingOptionsCommand,
            ChoosingGameCommand choosingGameCommand,
            ChoosingCardCommand choosingCardCommand,
            ChoosingConditionCommand choosingConditionCommand,
            ChoosingPhotoCommand choosingPhotoCommand,
            ChoosingPhotoCollectionCommand choosingPhotoCollectionCommand,
            ChoosingValueTypeCommand choosingValueTypeCommand,
            ChoosingValueCommand choosingValueCommand,
            ChoosingDescriptionCommand choosingDescriptionCommand,
            SavePostCommand savePostCommand,
            SelectingCardCommand selectingCardCommand,
            ChoosingWantedCardsCommand choosingWantedCardsCommand,
            ChoosingPostFiltersCommand choosingPostFiltersCommand,
            SelectingPostCommand selectingPostCommand,
            ShowPostFiltersCommand showPostFiltersCommand,
            SaveOfferCommand saveOfferCommand) {

        commands.put(AWAITING_SESSION, awaitingSessionCommand);
        commands.put(LOGIN_IN, loginCommand);
        commands.put(REGISTERING, registerCommand);
        commands.put(CHOOSING_OPTIONS, choosingOptionsCommand);
        commands.put(CHOOSING_GAME, choosingGameCommand);
        commands.put(CHOOSING_CARD, choosingCardCommand);
        commands.put(CHOOSING_CONDITION, choosingConditionCommand);
        commands.put(CHOOSING_PHOTO_OPTION, choosingPhotoCommand);
        commands.put(CHOOSING_PHOTO, choosingPhotoCollectionCommand);
        commands.put(CHOOSING_VALUE_TYPE, choosingValueTypeCommand);
        commands.put(CHOOSING_VALUE, choosingValueCommand);
        commands.put(CHOOSING_DESCRIPTION, choosingDescriptionCommand);
        commands.put(CREATING_POST, savePostCommand);
        commands.put(SELECTING_CARD, selectingCardCommand);
        commands.put(SELECTING_OFFERED_CARD, selectingCardCommand);
        commands.put(SELECTING_WANTED_CARDS, choosingWantedCardsCommand);
        commands.put(CHOOSING_POST_FILTERS, choosingPostFiltersCommand);
        commands.put(SELECTING_POST, selectingPostCommand);
        commands.put(SHOWING_POSTS_FILTERS_RESULTS, showPostFiltersCommand);
        commands.put(CREATING_OFFER, saveOfferCommand);
    }
    
    
    /**
     * Get the command for the given state
     *
     * @param state The user state
     * @return The command for the state
     */
    public StateCommand getCommand(UserState state) {
        StateCommand command = commands.get(state);
        if (command == null) {
            // Return a default no-op command if state not found
            return new NoOpCommand();
        }
        return command;
    }

    /**
     * Default command that does nothing
     */
    private static class NoOpCommand implements StateCommand {
        @Override
        public void execute(long chatId, org.telegram.telegrambots.meta.api.objects.Message message,
                            ResponseHandler handler) {
            // No-op
        }

        @Override
        public void onEnter(long chatId, ResponseHandler handler) {
            // No-op
        }
    }
}