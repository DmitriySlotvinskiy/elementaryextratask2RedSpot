package com.slotvinskiy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {

    public static final Random RANDOM = new Random();

    public static class MyNode {
        private String nodeId;
        private List<MyNode> neighborMyNodes = new ArrayList<>();

        public MyNode(String id) {
            this.nodeId = id;
        }

        public void addNeighbor(MyNode newNeighbor) {
            if (!this.neighborMyNodes.contains(newNeighbor)) {
                this.neighborMyNodes.add(newNeighbor);
                newNeighbor.addNeighbor(this);
            }
        }
    }

    public static class RedPoint {

        private MyNode currentNode;
        private final MyNode originLocation; //для возврата в исходное положение

        public RedPoint(MyNode node) {
            this.currentNode = node;
            this.originLocation = node;
        }

        public void move() {
            int numberOfNeighbors = this.currentNode.neighborMyNodes.size();
            int neighborIndex = RANDOM.nextInt(numberOfNeighbors);
            this.currentNode = this.currentNode.neighborMyNodes.get(neighborIndex);
        }

        public MyNode getLocation() {
            return this.currentNode;
        }

        public void resetLocation() {
            currentNode = originLocation;
        }
    }

    List<MyNode> myNodes = new ArrayList<>();

    public void addNodeToField(String id) {
        MyNode myNode = new MyNode(id);
        myNodes.add(myNode);
    }

}
