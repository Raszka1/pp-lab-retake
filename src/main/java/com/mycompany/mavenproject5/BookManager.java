package com.mycompany.mavenproject5;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Zuzanna Raszka
 */

public class BookManager implements BookOperations {
    private List<Book> books;

    // Konstruktor
    public BookManager() {
        this.books = new ArrayList<>();
    }

    public BookManager(List<Book> books) {
        this.books = books;
    }

    // Implementacja metody addBook
    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    // Implementacja metody removeBook
    @Override
    public void removeBook(Book book) {
        books.removeIf(b -> Objects.equals(b, book));
    }

    // Implementacja metody updateBook
    @Override
    public void updateBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
        }
    }

    // Implementacja metody getBooks
    @Override
    public List<Book> getBooks() {
        return new ArrayList<>(books); // Zwraca kopię listy książek
    }
}
