package com.thebookshop;

import org.apache.commons.codec.digest.DigestUtils;

public class Node{
    protected Book book;
    protected Node left;
    protected Node right;
    protected String color;
    protected String hash;
    protected Node parent;

    Node(){
        parent = null;
        book = null;
        left = null;
        right = null;
        hash = "";
    }
    public Node(Book book) {
        this.book = book;
        this.hash = DigestUtils.sha256Hex(book.indexer);
    }

    public Node(Node node) {
        this.book = node.book;
        this.color = node.color;
        this.left = node.left;
        this.right = node.right;
        this.hash = node.hash;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
