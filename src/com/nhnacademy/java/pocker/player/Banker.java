package com.nhnacademy.java.pocker.player;

import com.nhnacademy.java.pocker.cardPack.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 뱅커 카드 클래스.
 */
public class Banker {
    private List<Card> bankerPack = new ArrayList<>();

    public void add(Card card) {
        if (bankerPack.size() > 3) {
            throw new IllegalArgumentException("Banker can not have more than 3 cards!");
        }
        bankerPack.add(card);
    }

    public void showBankerCard() {
        System.out.print(" Banker : ");

        for (int i = 0; i < bankerPack.size(); i++) {
            if (i > 0)
                System.out.print(" | ");
            System.out.printf("%14s", bankerPack.get(i));
        }

        System.out.println();
    }


    public List<Card> getCardPack() {
        if (bankerPack.size() == 0) {
            throw new IllegalArgumentException("Banker card is empty");
        }
        return bankerPack;
    }
}
