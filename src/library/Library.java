/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author haykv
 */
public class Library implements Serializable {

    private ArrayList<Book> books;
    private ArrayList<User> users;
    private int bookID = 0;
    private int userLimit = 10;
    private float damageFine = 10;
    private float dateFine = 5;

    public Library() {
        this.books = new ArrayList<Book>();
        this.users = new ArrayList<User>();
    }

    public void addBook(String author, String name, String genre, String isbn, int year, int damage, int numberOfBooks) {
        for (int i = 0; i < numberOfBooks; i++) {
            Book newBook = new Book(author, name, genre, isbn, year, damage, this.bookID++);
            books.add(newBook);
        }
    }

    public void addUser(String firstName, String lastName, String email, int id) {
        User newUser = new User(firstName, lastName, email, id, userLimit);
        users.add(newUser);
    }

    public Book findBook(int bookID) {
        return books.stream()
                .filter(book -> book.getBookID() == bookID)
                .findAny()
                .orElse(null);
    }

    public ArrayList<String> getAvailableGenres() {
        return (ArrayList< String>) books.stream()
                .map(book -> book.getGenre())
                .sorted()
                .distinct()
                .collect(Collectors.toList());
    }

    public void printGenres() {
        ArrayList<String> genres = getAvailableGenres();

        for (int i = 0; i < genres.size(); i++) {
            System.out.println(i + ") " + genres.get(i));
        }
    }

    public User findUser(int userID) {
        for(User user : users) {
            if (user.getId()==userID)
                return user;
        }
        return null;
        /*return users.stream()
                .filter(user -> user.getId().equals(userIDString))
                .findAny()
                .orElse(null);
*/
    }

    private ArrayList<Book> uniqueBookList(ArrayList<Book> booksList) {
        HashMap<String, Book> resultTable = new HashMap<String, Book>();
        for (Book book : booksList) {
            resultTable.put(book.getName(), book);
        }
        ArrayList< Book> result = new ArrayList< Book>();

        for (Map.Entry<String, Book> entry : resultTable.entrySet()) {
            result.add(entry.getValue());
        }

        return (ArrayList< Book>) result;
    }

    public ArrayList<Book> getAvailableBooksByString(String string) {
        ArrayList< Book> availableBooks = (ArrayList< Book>) books.stream()
                .filter(book -> book.getTakenBy() == null && book.getName().matches("(?i:.*" + string + ".*)"))
                .collect(Collectors.toList());
        return uniqueBookList(availableBooks);
    }

    public ArrayList<Book> getAvailableBooksByGenre(String genre) {

        ArrayList< Book> availableBooks = (ArrayList< Book>) books.stream()
                .filter(book -> book.getTakenBy() == null && book.getGenre().equals(genre))
                .collect(Collectors.toList());
        return uniqueBookList(availableBooks);
    }

    public void printBooksByString(String string) {
        ArrayList<Book> books = getAvailableBooksByString(string);
        for (Book book : books) {
            System.out.println("ID: " + book.getBookID() + " = " + book.getName());
        }
    }

    public void printGenreBooks(String genre) {
        ArrayList<Book> books = getAvailableBooksByGenre(genre);
        for (Book book : books) {
            System.out.println("ID: " + book.getBookID() + " = " + book.getName());
        }
    }

    public void giveBookToUser(int bookID, int userID, int days) {
        Book book = findBook(bookID);
        User user = findUser(userID);

        if (user == null) {
            System.err.println("-E- User not found");
            return;
        }
        if (book == null) {
            System.err.println("-E- Book not found");
            return;
        }
        if (user.getLimit() <= 0) {
            System.out.println("-W- Sorry limit reached" + user.getFirstName() + " " + user.getLastName());
            return;
        }

        book.setTakenBy(user);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        book.setReturnDate(calendar.getTime());
        user.addBook(book);

    }

    public void takeBookFromUser(int bookID, int damage) {
        Date current = new Date();
        Book book = findBook(bookID);

        if (book == null) {
            return;
        }

        User user = book.getTakenBy();

        if (user == null) {
            return;
        }

        long diffDays = (current.getTime() - book.getReturnDate().getTime()) / (1000 * 60 * 60 * 24);
        user.addFine(diffDays * dateFine);
        int currentDamage = damage - book.getDamage();
        user.addFine(Integer.max(0, currentDamage) * damageFine);
        // update damage
        book.setDamage(damage);
        book.setTakenBy(null);
        user.deleteBook(book);

    }

    public void printBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    public void printUsers() {
        for (User user : users) {
            System.out.println(user);
        }
    }

}
