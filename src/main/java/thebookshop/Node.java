package thebookshop;

import org.apache.commons.codec.digest.DigestUtils;

public class Node{
    protected thebookshop.Book book;
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
    public Node(thebookshop.Book book) {
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

    boolean hasOneorZeroChildren(){
        return right == null || left == null;
    }

    boolean isLeftChild(){
        return this == parent.left;
    };

    boolean isRightChild(){
        return this == parent.right;
    };

    boolean isLeftChildOf(Node parent){
        return this == parent.left;
    };

    boolean isRightChildOf(Node parent){
        return this == parent.right;
    };
    Node getGrandParent(){
        return parent.getParent();
    }

    Node getUncle(){
        return parent.getSibling();
    }

    Node getLeftMostNode(){
        if(left == null){
            return this;
        }else{
            return getLeftMostNode();
        }
    }

    /**
     * Replace child node with new node
     * @param oldChild The old child node
     * @param newChild The new child node
     */
   void replaceChild(Node oldChild, Node newChild){
       if (oldChild.isLeftChildOf(this)) {
          left = newChild;
       } else if (oldChild.isRightChildOf(this)) {
          right = newChild;
       } else {
           throw new IllegalStateException("Node is not a child of its parent");
       }
    }

    boolean isBlack(){
        return color.equals("BLACK");
    }
    Node getSibling(){
        if (this == parent.left) {
            return parent.right;
        } else if (this == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }
    void switchColor(){
        if(color.equals("RED")){
            color = "BLACK";
        }else{
            color = "RED";
        }
    }

    boolean hasLeftChild(){
       return left != null;
    }

    boolean hasRightChild() {
        return right != null;
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
