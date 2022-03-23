package com.thebookshop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TheBookShop extends JFrame {
    private JPanel mainPanel;
    private JPanel mainScreen;
    private JPanel bookView;
    private JPanel book;
    private JPanel controls;
    private JPanel titleBar;
    private JButton purchaseButton;
    private JButton viewExitButton;
    private JLabel bookPrice;
    private JLabel theBookShopTitle;
    private JLabel bookTitle;
    private JLabel bookAuthor;
    private JPanel topBar;
    private JPanel center;
    private JPanel bottomBar;
    private JLabel theBookShopLabel;
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
    private Books books;
    CardLayout mainLayout = new CardLayout();
    private ArrayList<Book> rentedBooks = new ArrayList<>(5);
    private Book foundBook;

    public TheBookShop(Books books){
        this.books = books;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int)(screenSize.height * 0.75) ;
        int width = (int) (screenSize.width * 0.75);

        setSize(width, height);
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
    }

    void initComponents(){
        mainPanel = new JPanel();
        mainPanel.setLayout(mainLayout);
        initMainScreen();
        initBookView();
    }

    void initMainScreen(){
       resultPanel.setVisible(true);

        searchButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        viewBookButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               mainLayout.show(mainPanel, "bookView");
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
        //Add panel to main panel
        mainPanel.add(mainScreen, "mainScreen");
    }

    void initBookView(){
        purchaseButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (rentedBooks.size() < 6) {
                    rentedBooks.add(foundBook);
                    String message =
                            "You have rented " + foundBook.getTitle() + ". Written by: " + foundBook.getAuthor();
                    JOptionPane.showMessageDialog(bookView, message, "Book Rented", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    String message =
                            "Cannot rent " + foundBook.getTitle() +". You have rented the maximum number of books!" +
                                    " \n Return a book to continue renting books.";
                    JOptionPane.showMessageDialog(bookView, message, "Book Rent Limit Reached",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        //Listen for button click
        viewExitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Change screen
                mainLayout.show(mainPanel, "mainScreen");
            }
        });
        //Add panel to main panel
        mainPanel.add(bookView, "bookView");
    }

    void search() {
        String _bookTitle = bookTitleField.getText();
        String _author = authorField.getText();
        String _bookYear = publishedYearField.getText();

        Book bookFound = books.findBook(_bookTitle, _author, _bookYear);

        if (bookFound != null) {
            resultPanel.setVisible(true);
            //resultPanel.add(foundBook);
           // resultBookCover.add(foundBook);
            foundBook = bookFound;
        }else{
            String message = "We couldn't find " + _bookTitle + ", written by " + _author + " in year " + _bookYear;
            JOptionPane.showMessageDialog(mainPanel, message, "Book " +
                    "Not " +
                    "Found!", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
