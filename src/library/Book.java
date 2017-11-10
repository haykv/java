/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author haykv
 */
public class Book implements Serializable {

    private String author;
    private String name;
    private String genre;
    private String isbn;
    private int damage;
    private int bookID;
    private int year;
    private Date returnDate;

    public Date getReturnDate() {
        return returnDate;
    }

    public User getTakenBy() {
        return takenBy;
    }
    private User takenBy;

    public Book(String author, String name, String genre, String isbn, int year, int damage, int bookID) {
        this.author = author;
        this.name = name;
        this.isbn = isbn;
        this.damage = damage;
        this.bookID = bookID;
        this.year = year;
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getDamage() {
        return damage;
    }

    public int getBookID() {
        return bookID;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Book{" + "author=" + author + ", name=" + name + ", genre=" + genre + ", isbn=" + isbn + ", damage=" + damage + ", bookID=" + bookID + ", year=" + year + ", returnDate=" + returnDate + ", takenBy=" + takenBy + '}';
    }
 

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTakenBy(User takenBy) {
        this.takenBy = takenBy;
    }

}
