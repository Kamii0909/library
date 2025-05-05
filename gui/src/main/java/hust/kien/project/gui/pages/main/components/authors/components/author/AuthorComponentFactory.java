package hust.kien.project.gui.pages.main.components.authors.components.author;

import java.io.IOException;
import java.net.URL;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.AuthorService;
import hust.kien.project.gui.controller.utils.FxUtils;
import hust.kien.project.gui.pages.ComponentFactory;
import hust.kien.project.gui.view.event.AuthorDeletedEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class AuthorComponentFactory
        implements ComponentFactory<@NonNull State, @NonNull Interactions, @NonNull AuthorComponent, @NonNull Author> {

    private final URL authorCss;

    private final AuthorService authorService;

    private final Image defaultImage;

    public AuthorComponentFactory(
            @Value("classpath:gui/component/author/author.css") Resource authorCss,
            @Qualifier("defaultBookImage") Resource defaultImage,
            AuthorService authorService) {
        try {
            this.authorCss = authorCss.getURL();
            this.defaultImage = FxUtils.renderImage(new Image(defaultImage.getInputStream(),
                    0, 0, true, true), 1);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
        this.authorService = authorService;

    }

    @Override
    public AuthorComponent createComponent(Author author) {
        return createNewUIElement(defaultImage, author);
    }

    private AuthorComponent createNewUIElement(Image image, Author author) {
        StackPane imageContainer = imageContainer(image);
        AnchorPane.setTopAnchor(imageContainer, 5.0);
        AnchorPane.setLeftAnchor(imageContainer, 10.0);

        TextArea authorName = authorName();
        AnchorPane.setLeftAnchor(authorName, 80.0);
        AnchorPane.setRightAnchor(authorName, 120.0);
        AnchorPane.setTopAnchor(authorName, 20.0);

        TextField authorAgeNumber = ageNumber();

        TextFlow authorAge = authorAge(ageText(), authorAgeNumber);
        AnchorPane.setLeftAnchor(authorAge, 80.0);
        AnchorPane.setTopAnchor(authorAge, 4.0);
        AnchorPane.setRightAnchor(authorAge, 140.0);

        Button delete = deleteButton();
        ToggleButton edit = editButton();

        VBox actions = actionContainer(delete, edit);
        AnchorPane.setRightAnchor(actions, 15.0);
        AnchorPane.setTopAnchor(actions, 5.0);

        BooleanProperty isNameValid = new SimpleBooleanProperty(true);
        BooleanProperty isAgeValid = new SimpleBooleanProperty(true);

        authorName.editableProperty().bind(edit.selectedProperty());
        authorAgeNumber.editableProperty().bind(edit.selectedProperty());

        State state = new State(authorName.textProperty(), authorAgeNumber.textProperty(),
                () -> authorName.fireEvent(new AuthorDeletedEvent(author)),
                edit.selectedProperty(),
                isNameValid,
                isAgeValid);

        AuthorController controller = new AuthorController(authorService, state, author);
        authorName.setOnKeyTyped(_ -> controller.validate());
        authorAgeNumber.setOnKeyTyped(_ -> controller.validate());

        delete.setOnMouseClicked(_ -> controller.handleDeleteAuthor());
        edit.setOnMouseClicked(_ -> controller.handleModifyAuthor());

        StringBinding nameStyles = Bindings.createStringBinding(() -> validationStyles(isNameValid.get()), isNameValid);
        StringBinding ageStyles = Bindings.createStringBinding(() -> validationStyles(isAgeValid.get()), isAgeValid);

        authorName.styleProperty().bind(nameStyles);
        authorAgeNumber.styleProperty().bind(ageStyles);

        AnchorPane root = new AnchorPane();
        root.setCursor(Cursor.CROSSHAIR);
        root.setStyle("-fx-background-color: white; -fx-background-radius: 20;");
        root.getStylesheets().add(authorCss.toString());
        root.getChildren().addAll(imageContainer, authorName, authorAge, actions);

        return new AuthorComponent(root, controller);
    }

    private VBox actionContainer(Button delete, ToggleButton edit) {
        VBox actions = new VBox(delete, edit);
        actions.setAlignment(Pos.CENTER);
        actions.setLayoutX(260.0);
        actions.setLayoutY(15.0);
        return actions;
    }

    private ToggleButton editButton() {
        ToggleButton edit = new ToggleButton("Sua thong tin");
        edit.setMnemonicParsing(false);
        edit.getStyleClass().addAll("modify-button", "alatsi");
        return edit;
    }

    private Button deleteButton() {
        Button delete = new Button("Xóa tác giả");
        delete.setMnemonicParsing(false);
        delete.setPrefHeight(30.0);
        delete.setPrefWidth(120.0);
        delete.getStyleClass().addAll("delete-button", "alatsi");
        return delete;
    }

    private TextFlow authorAge(Text ageText, TextField authorAgeNumber) {
        TextFlow authorAge = new TextFlow(ageText, authorAgeNumber);
        authorAge.setLayoutX(80.0);
        authorAge.setLayoutY(4.0);
        authorAge.setTextAlignment(TextAlignment.CENTER);
        return authorAge;
    }

    private TextField ageNumber() {
        TextField authorAgeNumber = new TextField();
        authorAgeNumber.setAlignment(Pos.TOP_LEFT);
        authorAgeNumber.setEditable(false);
        authorAgeNumber.setPrefHeight(20.0);
        return authorAgeNumber;
    }

    private Text ageText() {
        Text ageText = new Text("Tuổi: ");
        ageText.setStrokeType(StrokeType.OUTSIDE);
        ageText.setStrokeWidth(0.0);
        ageText.setTextAlignment(TextAlignment.CENTER);
        ageText.getStyleClass().addAll("goto-regular", "author-age");
        return ageText;
    }

    private TextArea authorName() {
        TextArea authorName = new TextArea();
        authorName.setEditable(false);
        authorName.setMaxHeight(45);
        authorName.setPrefHeight(45);
        authorName.setPrefWidth(140);
        authorName.setWrapText(true);
        authorName.setFont(new Font(18));
        authorName.getStyleClass().addAll("alatsi", "author-name");
        return authorName;
    }

    private StackPane imageContainer(Image image) {
        ImageView imageView = new ImageView(image);

        Circle circle = new Circle(40, 35, 30);
        imageView.setClip(circle);

        StackPane imageContainer = new StackPane(imageView);
        imageContainer.setLayoutX(10.0);
        imageContainer.setLayoutY(5.0);

        return imageContainer;
    }

    private String validationStyles(boolean isValid) {
        return isValid ? "-fx-boder-color: none" : "-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5";
    }
}
