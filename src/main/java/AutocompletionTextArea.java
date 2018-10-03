import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.fxmisc.richtext.InlineCssTextArea;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class AutocompletionTextArea extends InlineCssTextArea {
    //Local variables
    //entries to autocomplete
    private final SortedSet<String> entries;
    //popup GUI
    protected ContextMenu entriesPopup;

    public AutocompletionTextArea() {
        super();
        this.entries = new TreeSet<>();
        this.entriesPopup = new ContextMenu();

        setListener();
    }

    private void setListener() {
        //Add "suggestions" by changing text
        textProperty().addListener((observable, oldValue, newValue) -> {
            String enteredText = getText();
            //always hide suggestion if nothing has been entered (only "spacebars" are dissalowed in TextFieldWithLengthLimit)
            if (enteredText == null || enteredText.isEmpty()) {
                entriesPopup.hide();
            } else {
                //filter all possible suggestions depends on "Text", case insensitive
                String word = enteredText.substring(0, getCaretPosition());
                int lastIndex = Math.max(word.lastIndexOf(' '), word.lastIndexOf('\n'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('\t'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('.'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf(','));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('('));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('{'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf(':'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('+'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('-'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('*'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('/'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('&'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('='));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('|'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('!'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf('^'));
                lastIndex = Math.max(lastIndex, word.lastIndexOf(';'));
                word = word.substring(lastIndex + 1);
                if(!word.equals("")) {
                    String finalWord = word;
                    List<String> filteredEntries = entries.stream()
                            .filter(e -> e.length() >= finalWord.length() && !finalWord.equals(e) && e.substring(0, finalWord.length()).equals(finalWord))
                            .collect(Collectors.toList());
                    //some suggestions are found
                    if (!filteredEntries.isEmpty() && Main.enableAutoComplete.isSelected()) {
                        //build popup - list of "CustomMenuItem"
                        populatePopup(filteredEntries, word);
                        //robot.keyPress(KeyEvent.VK_DOWN);
                        //robot.keyRelease(KeyEvent.VK_DOWN);
                        entriesPopup.show(this, getCaretBounds().get().getMaxX(), getCaretBounds().get().getMaxY()); //position of popup
                        // entriesPopup.show(this, MouseInfo.getPointerInfo().getLocation().x + 10, MouseInfo.getPointerInfo().getLocation().y); //position of popup
                        //no suggestions -> hide
                    } else {
                        entriesPopup.hide();
                    }
                }else{
                    entriesPopup.hide();
                }
            }
        });

        //Hide always by focus-in (optional) and out
        focusedProperty().addListener((observableValue, oldValue, newValue) -> {
            entriesPopup.hide();
        });
    }             


    /**
    * Populate the entry set with the given search results. Display is limited to 10 entries, for performance.
    * 
    * @param searchResult The set of matching strings.
    */
    private void populatePopup(List<String> searchResult, String searchReauest) {
        //List of "suggestions"
        List<CustomMenuItem> menuItems = new LinkedList<>();
        //List size - 10 or founded suggestions count
        int maxEntries = 10;
        int count = Math.min(searchResult.size(), maxEntries);
        //Build list as set of labels
        for (int i = 0; i < count; i++) {
          final String result = searchResult.get(i);
          //label with graphic (text flow) to highlight founded subtext in suggestions
          Label entryLabel = new Label();
          entryLabel.setGraphic(buildTextFlow(result, searchReauest));
          entryLabel.setPrefHeight(10);  //don't sure why it's changed with "graphic"
          CustomMenuItem item = new CustomMenuItem(entryLabel, true);
          menuItems.add(item);

          //if any suggestion is select set it into text and close popup
          item.setOnAction(actionEvent -> {
              int delta = result.length() - searchReauest.length();
              int pos = getCaretPosition();
              insertText(getCaretPosition(), result.substring(searchReauest.length()));
              moveTo(pos + delta);
              entriesPopup.hide();
          });
        }

        //"Refresh" context menu
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    public static TextFlow buildTextFlow(String text, String filter) {
        int filterIndex = text.toLowerCase().indexOf(filter.toLowerCase());
        Text textBefore = new Text(text.substring(0, filterIndex));
        Text textAfter = new Text(text.substring(filterIndex + filter.length()));
        Text textFilter = new Text(text.substring(filterIndex,  filterIndex + filter.length())); //instead of "filter" to keep all "case sensitive"
        textFilter.setFill(Color.ORANGE);
        textFilter.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
        return new TextFlow(textBefore, textFilter, textAfter);
    }
    /**
    * Get the existing set of autocomplete entries.
    * 
    * @return The existing autocomplete entries.
    */
    public SortedSet<String> getEntries() { return entries; }
}