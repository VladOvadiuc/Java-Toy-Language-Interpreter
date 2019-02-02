package utilsTables;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class fileTableView {
    private SimpleIntegerProperty id;
    private SimpleStringProperty fileName;

    public fileTableView(Integer id, String fileName) {
        this.id =  new SimpleIntegerProperty(id);
        this.fileName =  new SimpleStringProperty(fileName);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public String getFileName() {
        return fileName.get();
    }

    public SimpleStringProperty fileNameProperty() {
        return fileName;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }
}