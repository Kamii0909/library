package hust.kien.project.gui.pages.main.components.authors.components.author;

import hust.kien.project.gui.pages.ImmutableNode;
import hust.kien.project.gui.pages.InterfaceComponent;
import javafx.scene.layout.Region;

public class AuthorComponent implements InterfaceComponent {

    private final ImmutableNode region;

    private final AuthorController authorController;

    public AuthorComponent(Region region, AuthorController authorController) {
        this.region = new ImmutableNode(region);
        this.authorController = authorController;
    }

    @Override
    public ImmutableNode element() {
        return region;
    }

}
