package thebookshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.ArrayList;

public class Shop extends JFrame {
    private JPanel mainPanel;
    private JPanel mainScreen;
    private JPanel bookView;
    private JPanel bookCoverImage;
    private JPanel controls;
    private JPanel titleBar;
    private JButton rentBookButton;
    private JButton viewExitButton;
    private JLabel bookPrice;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JPanel topBar;
    private JPanel center;
    private JPanel bottomBar;
    private JLabel search;
    private JPanel searchBox;
    private JTextField bookTitleField;
    private JTextField authorField;
    private JButton searchButton;
    private JButton viewBookButton;
    private JPanel resultPanel;
    private JPanel resultBookCover;
    private JTextField publishedYearField;
    private JLabel publishedYearLabel;
    private JLabel bookTitleLabel;
    private JLabel authorLabel;
    private JButton leaveButton;
    private JScrollPane allBooksScroll;
    private JPanel results_list;
    private JPanel allBooksPanel;
    private JPanel bookFoundPanel;
    private JButton cancelSearchButton;
    private JPanel returnBooks;
    private JPanel returnTopBar;
    private JScrollPane rentedBooksScroll;
    private JPanel returnBtmBar;
    private JButton returnExitBtn;
    private JButton returnAllBtn;
    private JPanel rentedBooksPanel;
    private JButton returnBooksBtn;
    private Books books;
    CardLayout mainLayout = new CardLayout();
    private ArrayList<Book> rentedBooks = new ArrayList<>();
    private Book foundBook;
    private int bookCount = 0;

    public Shop(Books books){
        this.books = books;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)(screenSize.height * 0.90) ;
        int width = (int) (screenSize.width * 0.75);

        setMinimumSize(new Dimension(width, height));
        setVisible(true);
        setEnabled(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("The Book Shop");
        setLayout(new BorderLayout());

        initComponents();
        add(mainPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
        pack();
    }

    void initComponents(){
        //ainPanel = new JPanel();
        //mainPanel.setLayout(mainLayout);
        initMainScreen();
        initBookView();
        initReturnBooks();

        mainPanel.add(mainScreen, "mainScreen");
        mainPanel.add(bookView, "bookView");
        mainPanel.add(returnBooks, "returnBooks");
    }

    private JPanel _bookListItem(Book book){
        JPanel panel = new JPanel(new GridLayout(1, 3, 10, 0));
        panel.setSize(300, 50);
        panel.setVisible(true);
        panel.setEnabled(true);
        panel.add(new JLabel(book.getTitle()));
        panel.add(new JLabel(book.getAuthor()));
        panel.add(new JLabel(book.getPublishedYear()));
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                foundBook = book;
                bookCoverImage.removeAll();
                bookCoverImage.add(book.getCover());
                bookTitle.setText(book.getTitle());
                bookAuthor.setText(book.getAuthor());
                bookPrice.setText("$ "+ Math.round(foundBook.getPrice()));
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "bookView");
                super.mouseClicked(e);
            }
        });
        return panel;
    }

    public void InOrder(Node node) {
        if (node != null) {
            InOrder(node.getLeft());
            allBooksPanel.add(_bookListItem(node.book));
            InOrder(node.getRight());
        }
    }

    void addToBooksScrollPane(){
        allBooksPanel.setLayout(new GridLayout(books.getSize(), 1, 0, 10));
        allBooksPanel.removeAll();
        InOrder(books.getRoot());
    }

    void initMainScreen(){
        resultPanel = new JPanel();
        addToBooksScrollPane();
        allBooksScroll.getVerticalScrollBar().setUnitIncrement(50);
        JPanel header = new JPanel(new GridLayout(0, 3));
        header.add(new JLabel("Title"));
        header.add(new JLabel("Author"));
        header.add(new JLabel("Year"));

        allBooksScroll.setColumnHeaderView(header);

        publishedYearField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) && (c != '-')) {
                    e.consume();  // if it's not a number, ignore the event
                }
                if(c == '-' && publishedYearField.getText().length() != 0){
                    e.consume();
                }
            }
        });

        searchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        viewBookButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookCoverImage.add(foundBook.getCover());
                bookTitle.setText(foundBook.getTitle());
                bookAuthor.setText(foundBook.getAuthor());
                bookPrice.setText("$ "+ Math.round(foundBook.getPrice()));
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "bookView");
            }
        });

        leaveButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to exit?", "Confirm Exit",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(option == 0) {
                    dispose();
                }
            }
        });

        cancelSearchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publishedYearField.setText("");
                authorField.setText("");
                bookTitleField.setText("");
                ((CardLayout) results_list.getLayout()).show(results_list, "allBooksCard");
            }
        });

        returnBooksBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "returnBooks");
                showRentedBooks();
            }
        });
        //Add panel to main panel
        mainPanel.add(mainScreen, "mainScreen");
    }

    void initBookView() {
        bookView.setSize(400, 550);

        final int RENT_LIMIT = 5;

        rentBookButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rentedBooks.size() != RENT_LIMIT) {

                    if (books.rentBook(foundBook) != null) {
                        rentedBooks.add(foundBook);
                        addToBooksScrollPane();

                        String message =
                                "You have rented \"" + foundBook.getTitle() + "\", written by: \"" + foundBook.getAuthor()+"\"";
                        JOptionPane.showMessageDialog(bookView, message, "Book Rented", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        String message =
                                "Requested book has already been rented.";
                        JOptionPane.showMessageDialog(bookView, message, "Book Not Found!",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    String message =
                            "Cannot rent " + foundBook.getTitle() + ". You have rented the maximum number of books!" +
                                    " \n Return a book to continue renting books.";
                    JOptionPane.showMessageDialog(bookView, message, "Book Rent Limit Reached",
                            JOptionPane.ERROR_MESSAGE);
                }
                //Change screen
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "mainScreen");
            }
        });

        //Listen for button click
        viewExitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookCoverImage.removeAll();
                bookTitle.setText("");
                bookAuthor.setText("");
                bookPrice.setText("");
                //Change screen
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "mainScreen");
            }
        });

        //Add panel to main panel
        mainPanel.add(bookView, "bookView");
    }

    void initReturnBooks(){
        rentedBooksPanel.setSize(rentedBooks.size() * 350, 650);
        rentedBooksPanel.setLayout(new GridLayout(1,rentedBooks.size()));
        rentedBooksScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        rentedBooksScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        showRentedBooks();

        returnAllBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!rentedBooks.isEmpty()){
                    rentedBooks.forEach(book->{
                        books.returnBook(book);

                    });
                    rentedBooks = new ArrayList<>();
                    showRentedBooks();
                }
            }
        });

        returnExitBtn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) mainPanel.getLayout()).show(mainPanel, "mainScreen");
            }
        });


    }

    void showRentedBooks() {
        rentedBooksPanel.removeAll();

        if (!rentedBooks.isEmpty()) {
            rentedBooks.forEach(book -> {
                JPanel rentedBook = new JPanel();
                rentedBook.setSize(350, 400);
                rentedBook.setLayout(new BoxLayout(rentedBook, BoxLayout.Y_AXIS));
                //
                JButton returnBookBtn = new JButton();
                returnBookBtn.setText("Return");
                returnBookBtn.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        books.returnBook(book);
                        rentedBooks.remove(book);
                        showRentedBooks();
                        revalidate();
                        repaint();

                        JOptionPane.showMessageDialog(rentedBooksPanel,
                                "You have successfully returned \"" + book.getTitle() + "\", written by \"" + book.getAuthor() + "\"", "Book Returned", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                //
                rentedBook.add(book.getCover());
                rentedBook.add(new JLabel("Title:  " + book.getTitle()));
                rentedBook.add(new JLabel("Author:  " + book.getAuthor()));
                // rentedBook.setOpaque(false);
                rentedBook.add(returnBookBtn);
                rentedBooksPanel.add(rentedBook);
            });
        }
    }

    void search() {
        String _bookTitle = bookTitleField.getText().trim();
        String _author = authorField.getText().trim();

        String _bookYear = publishedYearField.getText();

        Book book = books.findBook(_bookTitle.toLowerCase(), _author.toLowerCase(), _bookYear.toLowerCase());

        if (book != null) {
            foundBook = book;
            resultPanel.setVisible(true);
            bookPrice.setText("$ " + foundBook.getPrice());
            bookFoundPanel.add(foundBook.getCover());

            ((CardLayout) results_list.getLayout()).show(results_list, "resultCard");

        }else{
            String message =
                    "We couldn't find \"" + _bookTitle + "\" , written by \"" + _author + "\" in year \"" + _bookYear + "\"";
            JOptionPane.showMessageDialog(mainPanel, message, "Book " +
                    "Not " +
                    "Found!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}