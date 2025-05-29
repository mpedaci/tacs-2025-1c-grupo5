package utn.tacs.grupo5.telegrambot.command.offer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.dto.offer.OfferInputDto;
import utn.tacs.grupo5.telegrambot.dto.offer.OfferOutputDto;
import utn.tacs.grupo5.telegrambot.dto.offer.OfferedCardInputDto;
import utn.tacs.grupo5.telegrambot.service.IOfferService;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.ArrayList;
import java.util.List;

import static utn.tacs.grupo5.telegrambot.telegram.UserState.CHOOSING_OPTIONS;

@Component
public class SaveOfferCommand implements StateCommand {
    private final IOfferService offerService;
    public SaveOfferCommand(IOfferService offerService) {
        this.offerService = offerService;
    }
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        List<OfferedCardInputDto> offeredCards = new ArrayList<>();
        for (int i = 0; i < chatData.getSelectedCardsIds().size(); i++) {
            OfferedCardInputDto offeredCard = new OfferedCardInputDto(
                    chatData.getSelectedCardsIds().get(i),
                    chatData.getSelectedCardsStates().get(i),
                    ""
            );
            offeredCards.add(offeredCard);
        }
        OfferInputDto offerInput = new OfferInputDto(
                chatData.getEstimatedValue() != null? chatData.getEstimatedValue() : null,
                chatData.getPublicationId(),
                chatData.getUserId(),
                offeredCards
        );
        OfferOutputDto offerOutput = offerService.createOffer(chatData.getToken(), offerInput);
        handler.reply(chatId, "✅ Oferta guardada exitosamente.", null);
        handler.reply(chatId, "ID de la oferta: " + offerOutput.getId(), null);
        handler.reply(chatId, "Volviendo al menú principal...", KeyboardFactory.getCardsOption(), CHOOSING_OPTIONS);
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        execute(chatId, null, handler);
    }
}