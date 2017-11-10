/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author haykv
 */
public class Program {

    public static void save(Serializable object, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(Library.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Object load(File file) {
        try {
            Object result;
            FileInputStream in = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(in);
            result = is.readObject();
            is.close();
            return result;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void addUsersFromCSV(File file, Library library) {
        try {
            Files.lines(file.toPath(), Charset.forName("Cp1252"))
                    .filter(line -> line.split(",").length == 4 && line.contains("@"))
                    .map(line -> line.trim())
                    .forEach(line -> library.addUser(line.split(",")[0], line.split(",")[1], line.split(",")[2], Integer.parseInt(line.split(",")[3])));
        } catch (IOException ex) {
            System.err.println("-E-");
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void addBooksFromCSV(File file, Library library) {
        try {
            Files.lines(file.toPath(), Charset.forName("Cp1252"))
                    .skip(1)
                    .filter(line -> line.split(",").length == 7)
                    .forEach(line -> library.addBook(line.split(",")[1],
                    line.split(",")[0],
                    line.split(",")[2],
                    line.split(",")[3],
                    Integer.parseInt(line.split(",")[4]),
                    Integer.parseInt(line.split(",")[5]),
                    Integer.parseInt(line.split(",")[6]))
                    );
        } catch (IOException ex) {
            System.err.println("-E-");
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here                        
        File fo = new File("c:\\temp\\out.txt");
        File usersCSV = new File("c:\\temp\\users.csv");
        File booksCSV = new File("c:\\temp\\books.csv");
        Library myLibrary = new Library();
        addUsersFromCSV(usersCSV, myLibrary);
        addBooksFromCSV(booksCSV, myLibrary);

        myLibrary.addBook("Feiwel & Friends", "From the Notebooks of a Middle School Princess", "Children", "1250066026", 2015, 0, 1);
        myLibrary.addBook("Feiwel & Friends", "Royal Wedding Disaster", "Children", "1250066042", 2016, 0, 5);

        myLibrary.addUser("John", "Doe", "john@gmail.com", 11122333);
        //myLibrary.giveBookToUser(0, "11122333", 1);
        ///myLibrary.takeBookFromUser(0, 10);
        save(myLibrary, fo);
        Library loadedLibrary = (Library) load(fo);
        loadedLibrary.printBooks();
        loadedLibrary.printUsers();

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Welcome to library");
            System.out.println("0) Add new user");
            System.out.println("1) Add new book");
            System.out.println("2) Search book by genre");
            System.out.println("3) Search book by string");
            System.out.println("4) Take book from user");
            System.out.println("5) Display users");
            System.out.println("6) Display books");
            System.out.println("7) Exit");

            String cmd = scanner.nextLine();
            String input;
            int userID;
            int days;
            int bookID, damage;
            switch (Integer.parseInt(cmd)) {
                case 0:
                    System.out.println("Enter user details");
                    System.out.println("FirstName LastName e-mail ID");

                    input = scanner.nextLine();

                    String firstName = input.split(" ")[0];
                    String lastName = input.split(" ")[1];
                    String email = input.split(" ")[2];
                    int id = Integer.parseInt(input.split(" ")[3]);

                    myLibrary.addUser(firstName, lastName, email, id);
                    break;
                case 1:
                    System.out.println("Enter book details");
                    System.out.println("FirstName LastName e-mail ID");

                    input = scanner.nextLine();

                    String authorString = input.split(" ")[0];
                    String bookNameString = input.split(" ")[1];
                    String genreString = input.split(" ")[2];
                    String isbnString = input.split(" ")[3];
                    int year = Integer.parseInt(input.split(" ")[4]);
                    damage = Integer.parseInt(input.split(" ")[5]);
                    int numberOfBooks = Integer.parseInt(input.split(" ")[6]);

                    myLibrary.addBook(authorString, bookNameString, genreString, isbnString, year, damage, numberOfBooks);

                    break;
                case 2:
                    System.out.println("Give book to user - enter details");

                    ArrayList<String> genres = myLibrary.getAvailableGenres();
                    myLibrary.printGenres();
                    input = scanner.nextLine();
                    int genreIndex = Integer.parseInt(input);
                    myLibrary.printGenreBooks(genres.get(genreIndex));

                    System.out.println("bookID userID days");
                    input = scanner.nextLine();
                    bookID = Integer.parseInt(input.split(" ")[0]);
                    userID = Integer.parseInt(input.split(" ")[1]);
                    days = Integer.parseInt(input.split(" ")[2]);
                    myLibrary.giveBookToUser(bookID, userID, days);

                    break;
                case 3:
                    System.out.println("Give book to user - enter details");

                    input = scanner.nextLine();
                    myLibrary.printBooksByString(input);

                    System.out.println("bookID userID days");
                    input = scanner.nextLine();
                    bookID = Integer.parseInt(input.split(" ")[0]);
                    userID = Integer.parseInt(input.split(" ")[1]);
                    days = Integer.parseInt(input.split(" ")[2]);
                    myLibrary.giveBookToUser(bookID, userID, days);

                    break;
                case 4:
                    System.out.println("Take book from user - enter details");
                    System.out.println("bookID damage");

                    input = scanner.nextLine();

                    bookID = Integer.parseInt(input.split(" ")[0]);
                    damage = Integer.parseInt(input.split(" ")[1]);

                    myLibrary.takeBookFromUser(bookID, damage);
                    break;
                case 5:
                    myLibrary.printUsers();
                    break;
                case 6:
                    myLibrary.printBooks();
                    break;
                case 7:
                    System.exit(0);
                default:
                    System.err.println("-E- Wrong choice");
                    break;
            }
            save(myLibrary, fo);
        }

    }

}
