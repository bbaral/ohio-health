package com.babysitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class Controller {

    private final int regularPayRate = 12;
    private final int bedtimePayRate = 8;
    private final int midnightPayRate = 16;

    private final int bedTime = 9;

    @FXML
    private ComboBox<String> startTimeHourList;

    @FXML
    private ComboBox<String> startTimeMinuteList;

    @FXML
    private ComboBox<String> startTimeAmPmList;

    @FXML
    private ComboBox<String> endTimeHourList;

    @FXML
    private ComboBox<String> endTimeMinuteList;

    @FXML
    private ComboBox<String> endTimeAmPmList;

    @FXML
    private Button submitButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label result1, result2;

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        boolean checkStartTime = false;
        boolean checkEndTime = false;
        Window window = submitButton.getScene().getWindow();

        if (startTimeHourList.getValue() == null) {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Start hour time cannot be empty!");
        } else if (startTimeMinuteList.getValue() == null) {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Start minute time cannot be empty!");
        } else if (startTimeAmPmList.getValue() == null) {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "AM/PM for start time is required!");
        } else if (endTimeHourList.getValue() == null) {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "End hour time cannot be empty!");
        } else if (endTimeMinuteList.getValue() == null) {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "End minute time cannot be empty!");
        } else if (endTimeAmPmList.getValue() == null) {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "AM/PM for end time is required!");
        } else {
            if (startTimeHourList.getValue().length() > 0 && startTimeMinuteList.getValue().length() > 0 && startTimeAmPmList.getValue().length() > 0 && endTimeHourList.getValue().length() > 0 && endTimeMinuteList.getValue().length() > 0 && endTimeAmPmList.getValue().length() > 0) {
                checkStartTime = validateStartAndEndTime(startTimeHourList.getValue(), startTimeMinuteList.getValue(), startTimeAmPmList.getValue(), "PM");
                checkEndTime = validateStartAndEndTime(endTimeHourList.getValue(), endTimeMinuteList.getValue(), endTimeAmPmList.getValue(), "AM");
                if (checkStartTime && checkEndTime) {
                    calculatePayment(startTimeHourList.getValue(), startTimeMinuteList.getValue(), endTimeHourList.getValue(), endTimeMinuteList.getValue());
                }
            }
        }
    }

    private boolean validateStartAndEndTime(String hour, String minute, String ampm, String flag) {
        boolean isTimeEntryValid;
        Window window = submitButton.getScene().getWindow();

        if (ampm.equalsIgnoreCase(flag)) {
            isTimeEntryValid = checkTimeEntry(hour, minute, ampm, flag);
        } else {
            DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Your start time cannot be earlier than 5pm and leave time cannot be later than 4am");
            isTimeEntryValid = false;
        }

        return isTimeEntryValid;
    }

    private boolean checkTimeEntry(String hr, String mins, String ampm, String flag) {
        Window window = submitButton.getScene().getWindow();
        boolean entryCheck = true;
        int inputHr = Integer.parseInt(hr);
        int inputMins = Integer.parseInt(mins);
        if (ampm.equalsIgnoreCase("PM") && flag.equalsIgnoreCase("PM")) {
            if (inputHr < 5) {
                DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Your start entry should be 5PM or after!" + " Your start entry time: " + hr + ":" + mins + ampm);
                entryCheck = false;
            }

        } else if (ampm.equalsIgnoreCase("AM") && flag.equalsIgnoreCase("AM")) {
            if (inputHr >= 4) {
                if (inputMins > 0) {
                    DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Your leave entry should be before 4AM!" + " Your end entry time: " + hr + ":" + mins + ampm);
                    entryCheck = false;
                }
            }
        }
        return entryCheck;
    }

    private void calculatePayment(String startHrTime, String startMinuteTime, String endHrTime, String endMinuteTime) {
        int startHrTimeValue = Integer.parseInt(startHrTime);
        int startMinsTimeValue = Integer.parseInt(startMinuteTime);
        int endHrTimeValue = Integer.parseInt(endHrTime);
        int endMinsTimeValue = Integer.parseInt(endMinuteTime);

        int startToBedTime = calculateTimeDifference(startHrTimeValue, startMinsTimeValue, endHrTimeValue, endMinsTimeValue);


    }

    private int calculateTimeDifference(int startHrTimeValue, int startMinsTimeValue, int endHrTimeValue, int endMinsTimeValue) {
        System.out.println(startHrTimeValue);
        System.out.println(startMinsTimeValue);
        System.out.println(endHrTimeValue);
        System.out.println(endMinsTimeValue);
        return 1;
    }


    public void handleResetButtonAction(ActionEvent actionEvent) {
        startTimeHourList.setValue("");
        startTimeMinuteList.setValue("");
        startTimeAmPmList.setValue("");

        endTimeHourList.setValue("");
        endTimeMinuteList.setValue("");
        endTimeAmPmList.setValue("");
    }


}
