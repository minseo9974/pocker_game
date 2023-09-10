package com.nhnacademy.java.pocker.pockerRule;

import com.nhnacademy.java.pocker.cardPack.*;
import com.nhnacademy.java.pocker.player.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 포커 게임의 승리 규칙 클래스.
 * 
 */
public class PockerRule {

    /**
     * 유저 카드 2장과 뱅커의 카드 3장을 5장으로 만들기 위해 합치는 메서드.
     * 
     * @param user
     * @param banker
     * @return
     */
    public static List<Card> fiveCards(User user, Banker banker) {
        List<Card> fiveCardPack = new ArrayList<>(5);

        try {
            List<Card> u = user.getCardPack();
            List<Card> b = banker.getCardPack();

            for (int i = 0; i < u.size(); i++) {
                fiveCardPack.add(u.get(i));
            }
            for (int i = 0; i < b.size(); i++) {
                fiveCardPack.add(b.get(i));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return fiveCardPack;
    }

    /**
     * 카드 조합을 반환하는 메서드.
     * 
     * 원페어,투페어,트리플,풀하우스,포카드의 조합이 있다면 그것을 반환합니다.
     * 
     * @param user
     * @param banker
     * @return
     */
    public static List<Card> getCardsCombination(User user, Banker banker) {
        int result = 0;

        List<Card> fiveCardPack = fiveCards(user, banker);

        Map<Integer, Integer> numCnt = new HashMap<Integer, Integer>();

        // 카드 다섯장을 꺼내서 각각 같은 숫자가 몇장씩 있는지 map에 저장.
        for (Card card : fiveCardPack) {
            CardNum cardNum = card.getCardNum();
            Integer freqNum = numCnt.get(cardNum.getValue());

            numCnt.put(cardNum.getValue(), freqNum == null ? 1 : freqNum + 1);
        }

        List<Card> pairCards = new ArrayList<>();
        Iterator<Integer> keys = numCnt.keySet().iterator();
        while (keys.hasNext()) {
            Integer key = keys.next();
            Integer value = numCnt.get(key);
            if (value == 2) {
                for (Card card : fiveCardPack) {
                    if (card.getCardNum().getValue() == key) {
                        pairCards.add(card);
                    }
                }
            } else if (value == 3) {
                for (Card card : fiveCardPack) {
                    if (card.getCardNum().getValue() == key) {
                        pairCards.add(card);
                    }
                }
            } else if (value == 4) {
                for (Card card : fiveCardPack) {
                    if (card.getCardNum().getValue() == key) {
                        pairCards.add(card);
                    }
                }
            }
        }
        return pairCards;
    }
}

