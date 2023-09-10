package com.nhnacademy.java.pocker;

import java.util.List;
import com.nhnacademy.java.pocker.cardPack.*;
import com.nhnacademy.java.pocker.player.*;
import com.nhnacademy.java.pocker.service.TableService;


public class TableShell {

    public static void main(String[] args) {
        try {
            TableService pockerGame = new TableService();
            pockerGame.gameStart();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
