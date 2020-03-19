package hotel.utils;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public final class Utils {
    @FXML
    private TextField naamInput;

    public static boolean textFieldCheck(TextField input){
        boolean passed = false;
        String inputValue = input.getText();

        if (inputValue.trim().length() > 0){
            passed = true;
        }
        return passed;
    }

    public static void setTrimmedText(TextField input){
        String trimmed = input.getText().trim();
        input.setText(trimmed);
    }

    public static String capitalizeString(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
