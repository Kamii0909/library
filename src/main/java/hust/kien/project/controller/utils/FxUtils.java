package hust.kien.project.controller.utils;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class FxUtils {
	private FxUtils() {}

	public static void setAnchorPoint(Number top, Number bot, Number left, Number right,
		Node child) {
		if (top != null) {
			AnchorPane.setTopAnchor(child, top.doubleValue());
		}
		if (bot != null) {
			AnchorPane.setBottomAnchor(child, bot.doubleValue());
		}
		if (left != null) {
			AnchorPane.setLeftAnchor(child, left.doubleValue());
		}
		if (right != null) {
			AnchorPane.setRightAnchor(child, right.doubleValue());
		}
	}

	public static void setWidth(Number min, Number pref, Number max, Region region) {
		if (min != null) {
			region.setMinWidth(min.doubleValue());
		}
		if (pref != null) {
			region.setPrefWidth(pref.doubleValue());
		}
		if (max != null) {
			region.setMaxWidth(max.doubleValue());
		}
	}

	public static void setHeigth(Number min, Number pref, Number max, Region region) {
		if (min != null) {
			region.setMinHeight(min.doubleValue());
		}
		if (pref != null) {
			region.setPrefHeight(pref.doubleValue());
		}
		if (max != null) {
			region.setMaxHeight(max.doubleValue());
		}
	}

	public static void setEditableAndFocusTraversable(TextInputControl editableText,
		boolean editable, boolean focusTraversable) {

		editableText.setEditable(editable);
		editableText.setFocusTraversable(focusTraversable);
	}

	public static Text createAnchoredText(String string, Number top, Number bot, Number left,
		Number right, String... styles) {
		Text text = new Text(string);
		setAnchorPoint(top, bot, left, right, text);
		text.getStyleClass().setAll(styles);
		return text;
	}

	public static TextField createAnchoredTextField(String text, boolean editable,
		boolean focusTraversable, Number top, Number bot, Number left, Number right,
		String... styles) {

		TextField textField = new TextField(text);
		setEditableAndFocusTraversable(textField, editable, focusTraversable);
		setAnchorPoint(top, bot, left, right, textField);
		textField.getStyleClass().setAll(styles);

		return textField;
	}

	public static TextArea createAnchoredTextArea(String text, boolean editable,
		boolean focusTraversable, boolean wrapText, Number top, Number bot, Number left,
		Number right, String... styles) {

		TextArea textArea = new TextArea(text);
		textArea.setWrapText(wrapText);
		setEditableAndFocusTraversable(textArea, editable, focusTraversable);
		setAnchorPoint(top, bot, left, right, textArea);
		textArea.getStyleClass().setAll(styles);

		return textArea;
	}

	public static Button createAnchoredButton(String text, Number top, Number bot, Number left,
		Number right, String... styles) {
			
		Button button = new Button(text);
		setAnchorPoint(top, bot, left, right, button);
		button.getStyleClass().setAll(styles);

		return button;
	}

	public static void clipChildren(Node region, double arc, double width, double height) {

		final Rectangle outputClip = new Rectangle(width, height);

		outputClip.setArcWidth(arc);
		outputClip.setArcHeight(arc);

		region.setClip(outputClip);
	}

    public static Image renderImage(Image image, double scale) {
    	double width = image.getWidth();
    	double height = image.getHeight();
    
    	ImageView imageView = new ImageView();
    	imageView.setPreserveRatio(true);
    
    	int newHeigth = (int) height;
    	int newWidth = (int) width;
    	int x = 0;
    	int y = 0;
    
    	if (height / width > scale) {
    		newHeigth = (int) (width * scale);
    		y = (int) ((newHeigth - height) / 2);
    	} else {
    		newWidth = (int) (height / scale);
    		x = (int) ((width - newWidth) / 2);
    	}
    
    	return new WritableImage(image.getPixelReader(), x, y, newWidth, newHeigth);
    }

}
