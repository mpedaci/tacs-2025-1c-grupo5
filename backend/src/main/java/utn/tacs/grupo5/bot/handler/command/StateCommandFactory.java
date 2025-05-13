package utn.tacs.grupo5.bot.handler.command;

import org.springframework.stereotype.Component;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.command.post.*;
import utn.tacs.grupo5.bot.handler.command.session.AwaitingSessionCommand;
import utn.tacs.grupo5.bot.handler.command.session.LoginCommand;
import utn.tacs.grupo5.bot.handler.command.session.RegisterCommand;

import java.util.HashMap;
import java.util.Map;

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
            ChoosingPhotoPublicationCommand choosingPhotoPublicationCommand,
            ChoosingValueTypeCommand choosingValueTypeCommand,
            ChoosingValueCommand choosingValueCommand,
            ChoosingDescriptionCommand choosingDescriptionCommand) {

        commands.put(UserState.AWAITING_SESSION, awaitingSessionCommand);
        commands.put(UserState.LOGIN_IN, loginCommand);
        commands.put(UserState.REGISTERING, registerCommand);
        commands.put(UserState.CHOOSING_OPTIONS, choosingOptionsCommand);
        commands.put(UserState.CHOOSING_GAME, choosingGameCommand);
        commands.put(UserState.CHOOSING_CARD, choosingCardCommand);
        commands.put(UserState.CHOOSING_CONDITION, choosingConditionCommand);
        commands.put(UserState.CHOOSING_PHOTO_PUBLICATION, choosingPhotoPublicationCommand);
        commands.put(UserState.CHOOSING_VALUE_TYPE, choosingValueTypeCommand);
        commands.put(UserState.CHOOSING_VALUE, choosingValueCommand);
        commands.put(UserState.CHOOSING_DESCRIPTION, choosingDescriptionCommand);
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
                            utn.tacs.grupo5.bot.handler.ResponseHandler handler) {
            // No-op
        }
    }
}