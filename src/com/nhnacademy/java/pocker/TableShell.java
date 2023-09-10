package com.nhnacademy.java.pocker;

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
