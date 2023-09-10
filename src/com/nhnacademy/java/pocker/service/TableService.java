package com.nhnacademy.java.pocker.service;

import java.util.List;
import java.util.Scanner;
import com.nhnacademy.java.pocker.cardPack.*;
import com.nhnacademy.java.pocker.player.*;
import com.nhnacademy.java.pocker.pockerRule.PockerRule;

public class TableService {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 6;

    /**
     * 포커 게임이 진행되는 메서드.
     */
    public static void gameStart() {
        List<Card> cardPack = getCards();

        System.out.print("몇명이서 게임 하시겠습니까? (2 ~ 6명) --> ");
        int N = getPlayers();

        User users[] = makeUser(cardPack, N);
        Banker banker[] = makeBanker(cardPack, N);

        displayGame(users, banker);
        resultGame(users, banker);
    }

    /**
     * 게임 플레이어 수를 정하는 메서드.
     * 
     * @return
     */
    public static int getPlayers() {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        if (N > MAX_PLAYERS || N < MIN_PLAYERS) {
            throw new IllegalArgumentException("Invalid number of players. Please choose between "
                    + MIN_PLAYERS + " and " + MAX_PLAYERS);
        }
        return N;
    }

    /**
     * 게임에서 높은 조합별로 점수를 얻는다.
     * 
     * 등급은 포카드 > 풀하우스 > 트리플 > 투페어 > 투페어 > 논페어 순이며.
     * 
     * 같은 등급일때는 해당 등급의 숫자가 더 높은 조합이 이기게 되며 그 숫자까지 똑같을시에는.
     * 
     * 그아래의 조합이 있는지 비교하며 그래도 같을 경우 논페어까지 내려가며 비교하게된다.
     * 
     * 즉 결국엔 가장 높은 점수를 받는 사람이 우승자이다.
     * 
     * @param user
     * @param banker
     * @return
     */
    public static int getPoint(User user, Banker banker) {
        int point = 0;
        List<Card> card = PockerRule.getCardsCombination(user, banker);
        // 원페어
        if (card.size() == 2) {
            point += (card.get(1).getCardNum().getValue() * 20);
        } // 트리플
        else if (card.size() == 3) {
            point += (card.get(1).getCardNum().getValue() * 4000);
        } else if (card.size() == 4) {
            // 포카드
            if (card.get(1).getCardNum().getValue() == card.get(3).getCardNum().getValue()) {
                point += (card.get(1).getCardNum().getValue() * 7000000);
            } // 투페어
            else {
                point += (Math.max(card.get(1).getCardNum().getValue(),
                        card.get(3).getCardNum().getValue()) * 300);
                point += (Math.min(card.get(1).getCardNum().getValue(),
                        card.get(3).getCardNum().getValue()) * 20);
            }
        } // 풀 하우스
        else if (card.size() == 5) {
            point += (card.get(1).getCardNum().getValue() * 50000);
            point += (card.get(3).getCardNum().getValue() * 600000);
        }
        point += intMaxNonePair(PockerRule.fiveCards(user, banker));
        return point;
    }

    /**
     * getPoint메서드로부터 각각의 유저의 점수를 가져오고 가장 높은 점수를 기록하여.
     * 
     * 가장 높은 점수의 유저를 불러온다.
     * 
     * @param users
     * @param banker
     */
    public static void resultGame(User users[], Banker banker[]) {
        int[] point = new int[users.length];
        int maxPoint = 0;
        int index = 0;
        for (int i = 0; i < point.length; i++) {
            point[i] = getPoint(users[i], banker[i]);
            if (maxPoint < point[i]) {
                maxPoint = point[i];
                index = i;
            }
        }

        for (int i = 0; i < point.length; i++) {
            System.out.println(users[i].getName() + "플레이어 점수 : [" + point[i] + "]점 입니다.");
        }
        System.out.println("\n" + users[index].getName() + " 플레이어님이 " + maxPoint + "점으로 우승 하였습니다.");
    }

    /**
     * 유저와 뱅커 카드를 조합해서 5장의 카드를 보여줍니다.
     * 
     * @param users
     * @param bankers
     */
    public static void displayGame(User[] users, Banker[] bankers) {
        int cnt = users.length + 1;
        System.out.println("\n플레이어들의 카드를 보여줍니다.");
        for (User user : users) {
            user.showUserCard();
        }
        System.out.println("\n딜러의 카드를 보여줍니다.");
        for (Banker banker : bankers) {
            banker.showBankerCard();
        }

        System.out.println("\n조합한 카드를 보여드립니다.");
        for (int i = 0; i < cnt; i++) {
            if (i < cnt - 1) {
                System.out.printf("%6s 플레이어 5장 조합 카드 : ", users[i].getName());
                List<Card> card = PockerRule.fiveCards(users[i], bankers[i]);
                System.out.print(" | ");
                for (Card cards : card) {
                    System.out.printf("%14s", cards);
                    System.out.print(" | ");
                }
                showCombination(users[i], bankers[i]);
            }
            System.out.println();
        }
    }

    /**
     * 각 유저의 조합을 가져와서 어떤 결과인지 출력하는 메서드.
     * 
     * @param user
     * @param banker
     */
    public static void showCombination(User user, Banker banker) {
        List<Card> card = PockerRule.getCardsCombination(user, banker);
        System.out.printf("\n\n%6s 플레이어 : ", user.getName());
        if (card.size() == 2) {
            System.out.print("원 페어 ");
            printCombi(card);
        } else if (card.size() == 3) {
            System.out.print("트리플 ");
            printCombi(card);
        } else if (card.size() == 4) {
            if (card.get(1).getCardNum().getValue() == card.get(3).getCardNum().getValue()) {
                System.out.print("포 카드 ");
                printCombi(card);
            } else {
                System.out.print("투 페어 ");
                printCombi(card);
            }
        } else if (card.size() == 5) {
            System.out.println("풀 하우스 ");
            printCombi(card);
        } else {
            System.out.print("논 페어 ");
            printNonePair(PockerRule.fiveCards(user, banker));
        }
    }

    public static void printCombi(List<Card> card) {
        for (Card c : card) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * 논페어 카드중 가장 높은 카드를 보여준다.
     * 
     * @param card
     */
    public static void printNonePair(List<Card> card) {
        int maxValue = intMaxNonePair(card);
        for (Card c : card) {
            if (c.getCardNum().getValue() == maxValue) {
                System.out.println(c);
            }
        }
    }

    /**
     * 보유하고있는 논페어 카드중 가장 높은 값을 반환.
     * 
     * @param card
     * @return
     */
    public static int intMaxNonePair(List<Card> card) {
        int maxValue = 0;
        for (Card c : card) {
            int cValue = c.getCardNum().getValue();
            maxValue = maxValue < cValue ? cValue : maxValue;
        }
        return maxValue;
    }

    /**
     * 52장의 카드팩을 만들어서 전달하는 메서드.
     * 
     * @return
     */
    public static List<Card> getCards() {
        return Card.makeCardPack();
    }

    /**
     * 플레이어를 입력 하고 각각 두장의 카드를 나눠준다.
     * 
     * @param cardPack
     * @param N
     */
    public static User[] makeUser(List<Card> cardPack, int N) {
        Scanner sc = new Scanner(System.in);
        User[] users = new User[N];
        System.out.println("\n\n플레이어 이름을 차례대로 입력해주세요.");
        for (int i = 0; i < N; i++) {
            System.out.print((i + 1) + "번째 Player - ");
            String name = sc.next();
            users[i] = new User(name);
            users[i].add(cardPack.remove(i));
            users[i].add(cardPack.remove(i));
        }
        return users;
    }

    /**
     * 딜러에게 카드 세장을 줍니다.
     * 
     * @param cardPack
     * @param N
     * @return
     */
    public static Banker[] makeBanker(List<Card> cardPack, int N) {
        Banker[] banker = new Banker[N];
        for (int i = 0; i < N; i++) {
            banker[i] = new Banker();
            banker[i].add(cardPack.remove(i));
            banker[i].add(cardPack.remove(i));
            banker[i].add(cardPack.remove(i));
        }
        return banker;
    }
}
