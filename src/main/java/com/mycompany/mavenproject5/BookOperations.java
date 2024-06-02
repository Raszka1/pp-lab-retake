package com.mycompany.mavenproject5;
import java.util.List;
/**
 *
 * @author Zuzanna Raszka
 */

public interface BookOperations {
    // Dodaj książkę
    void addBook(Book book);

    // Usuń książkę
    void removeBook(Book book);

    // Aktualizuj informacje o książce
    void updateBook(Book oldBook, Book newBook);

    // Pobierz listę książek
    List<Book> getBooks();
}
