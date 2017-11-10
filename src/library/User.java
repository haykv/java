/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author haykv
 */
public class User implements Serializable {

    private String firstName, lastName;
    private String email;
    private int id;
    ArrayList<Book> books;
    private int limit;
    private float fine;

    public User(String firstName, String lastName, String email, int id, int limit) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.books = new ArrayList<>();
        this.limit = limit;        
        this.fine = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimit() {
        return limit;
    }

    public void addBook(Book b) {        
        this.books.add(b);
        this.limit--;
    }

    public void deleteBook(Book b) {
        this.books.remove(b);
        this.limit++;
    }

    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", id=" + id + ", books=" + books.size() + ", limit=" + limit + ", fine=" + fine + '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void addFine(float fine) {
        this.fine += fine;
    }        
    

}
