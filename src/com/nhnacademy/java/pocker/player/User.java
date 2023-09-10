package com.nhnacademy.java.pocker.player;

import com.nhnacademy.java.pocker.cardPack.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 카드 클래스.
 */
public class User {
    private List<Card> userPack = new ArrayList<>();
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void add(Card card) {
        if (userPack.size() > 2) {
            throw new IllegalArgumentException("Banker can not have more than 3 cards!");
        }
        userPack.add(card);
    }

    public void showUserCard() {
        System.out.printf("%7s : ", this.name);

        for (int i = 0; i < userPack.size(); i++) {
            if (i > 0)
                System.out.print(" | ");
            System.out.printf("%14s", userPack.get(i));
        }

        System.out.println();
    }

    public List<Card> getCardPack() {
        if (userPack.size() == 0) {
            throw new IllegalArgumentException("User card is empty");
        }
        return userPack;
    }
}
