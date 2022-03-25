import thebookshop.Books;
import thebookshop.Shop;

public class Main {

    public static void main(String[] args) {
       Books books = new Books();

        Shop bookShop = new Shop(books);
    }
}
