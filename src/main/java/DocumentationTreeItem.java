import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.lang.reflect.Field;

public class DocumentationTreeItem extends TreeItem{
    Tooltip tooltip;

    public DocumentationTreeItem(String value){
        super(value);
    }

    public DocumentationTreeItem(String value, String returnType, boolean isReadOnly){
        super(value);
        tooltip = new Tooltip(getReadOnlyText(isReadOnly) + "\nReturn Type: " + returnType);
        tooltip.setFont(new Font(12));
        tooltip.setWrapText(true);
        hackTooltipStartTiming(tooltip);
    }

    public DocumentationTreeItem(String value, String returnType){
        super(value);
        tooltip = new Tooltip("Function\nReturn Type: " + returnType);
        tooltip.setFont(new Font(12));
        tooltip.setWrapText(true);
        hackTooltipStartTiming(tooltip);
    }

    private String getReadOnlyText(boolean isReadOnly){
        if(isReadOnly)
            return "Read-Only Property";
        return "Editable Property";
    }

    private void hackTooltipStartTiming(Tooltip tooltip) {
        try {
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(0)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
