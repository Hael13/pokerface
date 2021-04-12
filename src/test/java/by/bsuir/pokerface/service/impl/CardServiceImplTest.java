package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.entity.card.*;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class CardServiceImplTest {
    private final CardService cardService = CardServiceImpl.getInstance();

    @Test
    public void testFindBestCombination() throws JsonProcessingException {

        List<Card> playerCards = List.of(new Card(Suit.CLUB, Value.ACE), new Card(Suit.CLUB, Value.QUEEN));
        List<Card> boardCards = List.of(new Card(Suit.DIAMOND, Value.JACK), new Card(Suit.HEART, Value.TEN),
                new Card(Suit.SPADE, Value.KING), new Card(Suit.CLUB, Value.KING), new Card(Suit.SPADE, Value.NINE));
        Combination best = findBestCombination(playerCards, boardCards);
        Combination straight = new Combination(CombinationType.STRAIGHT, Value.ACE.getStrength());
        assertEquals(straight.getType(), best.getType());
        assertEquals(straight.getStrength(), best.getStrength());

        playerCards = List.of(new Card(Suit.CLUB, Value.SIX), new Card(Suit.DIAMOND, Value.SIX));
        boardCards = List.of(new Card(Suit.DIAMOND, Value.JACK), new Card(Suit.HEART, Value.TEN),
                new Card(Suit.SPADE, Value.KING), new Card(Suit.CLUB, Value.KING), new Card(Suit.SPADE, Value.NINE));
        best = findBestCombination(playerCards, boardCards);
        Combination two_pair = new Combination(CombinationType.TWO_PAIR,
                (Value.SIX.getStrength() + Value.KING.getStrength()) * 2*CombinationConstant.COMBINATION_STRENGTH
                            + Value.JACK.getStrength());
        assertEquals(two_pair.getType(), best.getType());
        assertEquals(two_pair.getStrength(), best.getStrength());

        playerCards = List.of(new Card(Suit.HEART, Value.FIVE), new Card(Suit.DIAMOND, Value.TWO));
        boardCards = List.of(new Card(Suit.SPADE, Value.FIVE), new Card(Suit.HEART, Value.EIGHT),
                new Card(Suit.SPADE, Value.THREE), new Card(Suit.CLUB, Value.SIX), new Card(Suit.CLUB, Value.FIVE));
        best = findBestCombination(playerCards, boardCards);
        Combination three_of_kind = new Combination(CombinationType.THREE_OF_A_KIND,
                Value.FIVE.getStrength()*CombinationConstant.COMBINATION_STRENGTH
                        + Value.EIGHT.getStrength() + Value.SIX.getStrength());
        assertEquals(three_of_kind.getType(), best.getType());
        assertEquals(three_of_kind.getStrength(), best.getStrength());

    }

    Combination findBestCombination(List<Card> playerCards, List<Card> boardCards) {
        try {
            return cardService.findBestCombination(playerCards, boardCards);
        } catch (ServiceException exception) {
            return new Combination();
        }
    }


}