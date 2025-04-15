package utn.tacs.grupo5.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.offer.OfferInputDto;
import utn.tacs.grupo5.dto.offeredCard.OfferedCardInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.offer.Offer;
import utn.tacs.grupo5.entity.offer.OfferStatus;
import utn.tacs.grupo5.entity.offer.OfferedCard;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.entity.post.Post;
import utn.tacs.grupo5.repository.OfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryOfferRepository;
import utn.tacs.grupo5.repository.impl.InMemoryOfferedCardRepository;
import utn.tacs.grupo5.repository.impl.InMemoryPostRepository;
import utn.tacs.grupo5.repository.impl.InMemoryUserRepository;
import utn.tacs.grupo5.service.impl.OfferService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @InjectMocks
    private OfferService offerService;

    @Mock
    private OfferRepository offerRepository;
    @Mock
    private InMemoryPostRepository inMemoryPostRepository;
    @Mock
    private InMemoryOfferRepository inMemoryOfferRepository;
    @Mock
    private InMemoryUserRepository inMemoryUserRepository;
    @Mock
    private InMemoryOfferedCardRepository inMemoryOfferedCardRepository;

    @Test
    void getOfferByIdOk() {
        Offer offer = new Offer();
        Post post = new Post();
        post.setId(1L);
        offer.setPost(post);

        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(post));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.of(offer));

        Offer result = offerService.getById(1L, 2L);
        assertEquals(post, result.getPost());
    }

    @Test
    void getOfferByIdPostNotFound() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.getById(1L, 2L));
    }

    @Test
    void getOfferByIdOfferNotFound() {
        Post post = new Post();
        post.setId(1L);
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(post));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.getById(1L, 2L));
    }

    @Test
    void getOfferByIdMismatch() {
        Offer offer = new Offer();
        Post offerPost = new Post();
        offerPost.setId(2L);
        offer.setPost(offerPost);

        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.of(offer));

        assertThrows(NotFoundException.class, () -> offerService.getById(1L, 2L));
    }

    @Test
    void getAllOffers() {
        offerService.getAll();
        verify(offerRepository).findAll();
    }

    @Test
    void getAllByPublicationIdOk() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        offerService.getAllByPublicationId(1L);
        verify(offerRepository).findAllByPublicationId(1L);
    }

    @Test
    void getAllByPublicationIdPostNotFound() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> offerService.getAllByPublicationId(1L));
    }

    @Test
    void saveOfferOk() {
        OfferInputDto dto = new OfferInputDto();
        dto.setPostId(1L);
        dto.setOffererId(1L);
        dto.setMoney(100.0F);

        List<OfferedCardInputDto> cards = new ArrayList<>();
        OfferedCardInputDto cardDto = new OfferedCardInputDto();
        cardDto.setImage("img.png");
        cardDto.setConservationStatus("GOOD");
        cards.add(cardDto);
        dto.setOfferedCards(cards);

        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(inMemoryUserRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(offerRepository.save(any())).thenReturn(new Offer());

        Offer saved = offerService.save(dto);
        assertNotNull(saved);
    }

    @Test
    void saveOfferPostNotFound() {
        OfferInputDto dto = new OfferInputDto();
        dto.setPostId(1L);

        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.save(dto));
    }

    @Test
    void saveOfferUserNotFound() {
        OfferInputDto dto = new OfferInputDto();
        dto.setPostId(1L);
        dto.setOffererId(1L);

        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(inMemoryUserRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.save(dto));
    }

    @Test
    void patchOfferOk() {
        Post post = new Post();
        post.setId(1L);

        Offer offer = new Offer();
        offer.setPost(post);

        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(post));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.of(offer));

        offerService.patch(2L, 1L, OfferStatus.ACCEPTED);
        assertEquals(OfferStatus.ACCEPTED, offer.getOfferStatus());
    }

    @Test
    void patchOfferPostNotFound() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> offerService.patch(2L, 1L, OfferStatus.REJECTED));
    }

    @Test
    void patchOfferNotFound() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> offerService.patch(2L, 1L, OfferStatus.REJECTED));
    }

    @Test
    void deleteById() {
        offerService.delete(1L);
        verify(inMemoryOfferRepository).deleteById(1L);
    }

    @Test
    void deleteByPostAndIdOk() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.of(new Offer()));

        offerService.delete(1L, 2L);
        verify(inMemoryOfferRepository).deleteById(2L);
    }

    @Test
    void deleteByPostAndIdPostNotFound() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> offerService.delete(1L, 2L));
    }

    @Test
    void deleteByPostAndIdOfferNotFound() {
        when(inMemoryPostRepository.findById(1L)).thenReturn(Optional.of(new Post()));
        when(inMemoryOfferRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> offerService.delete(1L, 2L));
    }
}
