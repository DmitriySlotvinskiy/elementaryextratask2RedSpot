package com.slotvinskiy;

//1. Есть схема:
//dl.dropboxusercontent.com/s/6fnasg6j3o2chdn/crystalcell.png
//На ней 8 точек и 2 красных маркера. Каждый ход каждый из маркеров прыгает на случайную из соседних точек.
//Игра заканчивается когда оба красных маркера оказывается в одной точке.
//Вывести вероятность того что игра закончится на 1, 2...N ходу.

import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static final int ITERATIONS = 100000;

    public static void main(String[] args) {

//Создание узлов
        Field.MyNode node1A = new Field.MyNode("1A");
        Field.MyNode node1B = new Field.MyNode("1B");
        Field.MyNode node1C = new Field.MyNode("1C");

        Field.MyNode node2A = new Field.MyNode("2A");
        Field.MyNode node2B = new Field.MyNode("2B");

        Field.MyNode node3A = new Field.MyNode("3A");
        Field.MyNode node3B = new Field.MyNode("3B");
        Field.MyNode node3C = new Field.MyNode("3C");

        //Создание связи между узлами
        //      1A   1B   1C
        //        \ /  \ /
        //         2A   2B
        //       /  \  /  \
        //      3A   3B   3C
        node1A.addNeighbor(node2A);
        node1B.addNeighbor(node2A);
        node1B.addNeighbor(node2B);
        node1C.addNeighbor(node2B);
        node3A.addNeighbor(node2A);
        node3B.addNeighbor(node2A);
        node3B.addNeighbor(node2B);
        node3C.addNeighbor(node2B);

//Создание точек
        Field.RedPoint rp1 = new Field.RedPoint(node2A);
        Field.RedPoint rp2 = new Field.RedPoint(node2B);

//Определение вероятностей - см. постановку задачи
        testAndPrintStatistics(rp1, rp2);
    }

    private static void testAndPrintStatistics(Field.RedPoint rp1, Field.RedPoint rp2) {
        Map<Integer, Integer> statistic = new TreeMap<>();
        for (int i = 0; i < ITERATIONS; i++) {
            int steps = playGame(rp1, rp2);
            if (statistic.containsKey(steps)) {      //если статистика с таким числом ходов уже есть - ++
                int newValue = statistic.get(steps) + 1;
                statistic.put(steps, newValue);
            } else {                                //если статистики с таким числом ходов нет - начинаем отсчет
                statistic.put(steps, 1);
            }
        }
        showStatistics(statistic);
    }

    private static int playGame(Field.RedPoint rp1, Field.RedPoint rp2) {
        int steps = 0;
        while (rp1.getLocation() != rp2.getLocation()) {
            rp1.move();
            rp2.move();
            steps++;
        }
        rp1.resetLocation();
        rp2.resetLocation();
        return steps;
    }

    private static void showStatistics(Map<Integer, Integer> statistic) {
        for (Map.Entry<Integer, Integer> entry : statistic.entrySet()) {
            System.out.printf("Iterations number: %2d     probability: %.3f %%\n", entry.getKey(), (entry.getValue() * 100.0) / ITERATIONS);
        }
    }
}
