package com.mycompany.mavenproject5;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author Zuzanna Raszka
 */

public class Main extends Application {

    // Statyczna instancja BookManager
    private static final BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Manager");
        
        // Tworzenie kontrolek GUI
        Label label = new Label("Book Manager");
        
        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        
        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        
        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");
        
        TextField yearField = new TextField();
        yearField.setPromptText("Year");
        
        Button addButton = new Button("Add Book");
        Button removeButton = new Button("Remove Book");
        Button updateButton = new Button("Update Book");
        Button showBooksButton = new Button("List Books");
        
        //Sample książek
        initializeSampleBooks();
        // Tabela do wyświetlania książek
        TableView<Book> tableView = new TableView<>();
        createTableColumns(tableView);
        
         // Dodanie obsługi zaznaczenia rekordu w tabeli aby potem go edytować
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                titleField.setText(newSelection.getTitle());
                authorField.setText(newSelection.getAuthor());
                isbnField.setText(newSelection.getIsbn());
                yearField.setText(String.valueOf(newSelection.getYear()));
            }
        });
        
        // Dodawanie książki
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            int year = Integer.parseInt(yearField.getText());
            Book book = new Book(title, author, isbn, year);
            bookManager.addBook(book);
            clearFields(titleField, authorField, isbnField, yearField);
            updateTableView(tableView);
        });

         // Usuwanie książki
        removeButton.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookManager.removeBook(selectedBook);
                updateTableView(tableView);
            }
        });

        // Aktualizacja książki
        updateButton.setOnAction(e -> {
            Book selectedBook = tableView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                int year = Integer.parseInt(yearField.getText());
                Book newBook = new Book(title, author, isbn, year);
                bookManager.updateBook(selectedBook, newBook);
                updateTableView(tableView);
                clearFields(titleField, authorField, isbnField, yearField);
            }
        });

        showBooksButton.setOnAction(e -> {
            StringBuilder booksList = new StringBuilder();
            for (Book book : bookManager.getBooks()) {
                booksList.append(book.getTitle()).append(" - ").append(book.getAuthor()).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Book List");
            alert.setHeaderText(null);
            alert.setContentText(booksList.toString());
            alert.showAndWait();
        });

        
        // Layout
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, titleField, authorField, isbnField, yearField, addButton, removeButton, updateButton, showBooksButton, tableView);

        // Scena
        Scene scene = new Scene(layout, 345, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Pomocnicza metoda do czyszczenia pól tekstowych
    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }
    // Metoda do tworzenia przykładowej listy książek
    private void initializeSampleBooks() {
        bookManager.addBook(new Book("The Catcher in the Rye", "J.D. Salinger", "aaaaaa", 1951));
        bookManager.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "aaaaaa", 1960));
        bookManager.addBook(new Book("1984", "George Orwell", "aaaaaa", 1949));
        bookManager.addBook(new Book("Moby-Dick", "Herman Melville", "aaaaaa", 1851));
        bookManager.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "aaaaaa", 1925));
        bookManager.addBook(new Book("War and Peace", "Leo Tolstoy", "aaaaaa", 1869));
        bookManager.addBook(new Book("Ulysses", "James Joyce", "aaaaaa", 1922));
    }
    
    
    
    private void createTableColumns(TableView<Book> tableView) {
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));

        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(authorColumn);
        tableView.getColumns().add(isbnColumn);
        tableView.getColumns().add(yearColumn);

        
        
        // Aktualizacja tabeli przy uruchomieniu
        updateTableView(tableView);
    }
    
    // Metoda do aktualizacji TableView
    private void updateTableView(TableView<Book> tableView) {
        ObservableList<Book> bookList = FXCollections.observableArrayList(bookManager.getBooks());
        tableView.setItems(bookList);
    }
}
