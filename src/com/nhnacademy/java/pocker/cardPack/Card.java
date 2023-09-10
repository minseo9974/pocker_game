package com.nhnacademy.java.pocker.cardPack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    private final Pattern pattern;
    private final CardNum cardNum;

    /**
     * 무늬와 숫자가 조합이 되어 한장의 카드를 찍어내는 카드 클래스 생성자.
     * 
     * @param pattern
     * @param cardNum
     */
    public Card(Pattern pattern, CardNum cardNum) {
        this.pattern = pattern;
        this.cardNum = cardNum;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public CardNum getCardNum() {
        return cardNum;
    }

    @Override
    public String toString() {
        return pattern + " " + cardNum;
    }

    /**
     * 무늬와 숫자를 받아와 그것들을 조합해 52장의 포커게임 카드를 만드는 클래스.
     * 
     * @return
     */
    public static List<Card> makeCardPack() {
        List<Card> cardPack = new ArrayList<>();

        for (Pattern p : Pattern.values()) {
            for (CardNum c : CardNum.values()) {
                cardPack.add(new Card(p, c));
            }
        }
        Collections.shuffle(cardPack);
        return cardPack;
    }

}
