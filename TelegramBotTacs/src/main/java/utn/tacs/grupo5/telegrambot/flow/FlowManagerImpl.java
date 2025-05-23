package utn.tacs.grupo5.telegrambot.flow;

import org.springframework.stereotype.Component;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utn.tacs.grupo5.telegrambot.UserState.*;

@Component
public class FlowManagerImpl implements FlowManager {
    private final Map<String, FlowDefinition> flows = new HashMap<>();

    public FlowManagerImpl() {
        initializeFlows();
    }

    private void initializeFlows() {
        // Post creation flow with conditional logic
        FlowDefinition postFlow = new FlowDefinition(List.of(
                CHOOSING_OPTIONS,
                CHOOSING_GAME,
                CHOOSING_CARD,
                SELECTING_OFFERED_CARD,
                CHOOSING_CONDITION,
                CHOOSING_PHOTO_OPTION,
                CHOOSING_PHOTO,
                CHOOSING_VALUE_TYPE,
                CHOOSING_VALUE,
                SELECTING_WANTED_CARDS,
                CHOOSING_DESCRIPTION,
                CREATING_POST
        ))
                .addTransition(CHOOSING_CARD,
                        FlowTransition.conditional(
                                chatData -> chatData.getCurrentCards() != null && chatData.getCurrentCards().size() > 1,
                                SELECTING_OFFERED_CARD,
                                CHOOSING_CONDITION
                        ))
                .addTransition(SELECTING_OFFERED_CARD,
                        FlowTransition.conditional(
                                chatData -> chatData.needsMoreCardSelection(),
                                SELECTING_OFFERED_CARD,
                                CHOOSING_CONDITION
                        ))
                .addTransition(CHOOSING_PHOTO_OPTION,
                        FlowTransition.conditional(
                                chatData -> chatData.shouldCollectPhotos(),
                                CHOOSING_PHOTO,
                                CHOOSING_VALUE_TYPE
                        ))
                // Nueva transición desde CHOOSING_PHOTO
                .addTransition(CHOOSING_PHOTO,
                        FlowTransition.direct(CHOOSING_VALUE_TYPE)
                )
                .addTransition(CHOOSING_VALUE,
                        FlowTransition.conditional(
                                chatData -> "Cartas".equals(chatData.getHelpStringValue()) || "Ambos".equals(chatData.getHelpStringValue()),
                                SELECTING_WANTED_CARDS,
                                CHOOSING_DESCRIPTION
                        ))
                .addTransition(SELECTING_WANTED_CARDS,
                        FlowTransition.conditional(
                                chatData -> chatData.needsMoreCardSelection(),
                                SELECTING_WANTED_CARDS,
                                CHOOSING_DESCRIPTION
                        ));

        flows.put("post", postFlow);

        // Simple flows
        flows.put("register", new FlowDefinition(List.of(AWAITING_SESSION,REGISTERING)));
        flows.put("login", new FlowDefinition(List.of(AWAITING_SESSION,LOGIN_IN)));
    }

    @Override
    public FlowDefinition getFlow(String flowName) {
        return flows.get(flowName);
    }

    @Override
    public UserState getNextState(String flowName, UserState currentState, ChatData chatData) {
        FlowDefinition flow = flows.get(flowName);
        return flow != null ? flow.getNextState(currentState, chatData) : CHOOSING_OPTIONS;
    }

    @Override
    public void registerFlow(String flowName, FlowDefinition flowDefinition) {
        flows.put(flowName, flowDefinition);
    }

    @Override
    public UserState getfirstStateInFlow(String flowName) {
        FlowDefinition flow = flows.get(flowName);
        return flow != null ? flow.getFirstState() : null;
    }
}