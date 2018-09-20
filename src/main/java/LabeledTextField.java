import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class LabeledTextField extends HBox {
    public Label label;
    public TextField field;

    public LabeledTextField(String labelText, final String fieldText){
        super();

        label = new Label(labelText);
        field = new TextField(fieldText);
        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(label, field);
        field.setMaxWidth(100);

        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.trim().equals("")){
                field.setText(oldValue);
                return;
            }
            for(int i = 0; i < newValue.length(); i++){
                if(!Character.isLetter(newValue.charAt(i)) && newValue.charAt(i) != ' '){
                    field.setText(oldValue);
                    return;
                }
            }
        });
    }
}
