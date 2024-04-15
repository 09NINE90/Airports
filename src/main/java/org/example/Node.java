package org.example;

class Node {
    String data;
    int lineNumber;
    Node left, right;

    public Node(String data, int lineNumber) {
        this.data = data;
        this.lineNumber = lineNumber;
        left = right = null;
    }
}
