package com.babysitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class Controller {

    private final int START_TO_BED_TIME_PAY_RATE = 12;
    private final int BED_TIME_TO_MIDNIGHT_PAY_RATE = 8;
    private final int MID_NIGHT_TO_END_RATE = 16;

    private final int BED_TIME = 9;

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
    private Label startToBedTimeInputResult, bedTimeToMidnightInputResult, midnightToShiftEndInputResult, totalResult;

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
                DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Your start entry should be 5PM or after!" + " You enter: " + hr + ":" + mins + ampm);
                entryCheck = false;
            }

        } else if (ampm.equalsIgnoreCase("AM") && flag.equalsIgnoreCase("AM")) {
            if (inputHr > 4) {

                    DialogBox.showDialog(Alert.AlertType.ERROR, window, "Error!", "Your leave entry should be before 4AM!" + " You enter: " + hr + ":" + mins + ampm);
                    entryCheck = false;
            }
        }
        return entryCheck;
    }

    private void calculatePayment(String startHrTime, String startMinuteTime, String endHrTime, String endMinuteTime) {
        int startHrTimeValue = Integer.parseInt(startHrTime);
        int startMinsTimeValue = Integer.parseInt(startMinuteTime);
        int endHrTimeValue = Integer.parseInt(endHrTime);
        int endMinsTimeValue = Integer.parseInt(endMinuteTime);

        System.out.println(startHrTimeValue);
        System.out.println(endHrTimeValue);
        int startToBedTime = (BED_TIME - startHrTimeValue) * START_TO_BED_TIME_PAY_RATE;
        startToBedTimeInputResult.setText("$" + startToBedTime);

        int bedTimeToMidnight = (12 - BED_TIME) * BED_TIME_TO_MIDNIGHT_PAY_RATE;
        bedTimeToMidnightInputResult.setText("$" + bedTimeToMidnight);

        int midnightToShiftEnd = endHrTimeValue * MID_NIGHT_TO_END_RATE;
        midnightToShiftEndInputResult.setText("$" + midnightToShiftEnd);

        int total = startToBedTime + bedTimeToMidnight + midnightToShiftEnd;
        totalResult.setText("$" + total);


    }

    public void handleResetButtonAction(ActionEvent actionEvent) {
        startTimeHourList.setValue(null);
        startTimeMinuteList.setValue(null);
        startTimeAmPmList.setValue(null);

        endTimeHourList.setValue(null);
        endTimeMinuteList.setValue(null);
        endTimeAmPmList.setValue(null);
        startToBedTimeInputResult.setText(null);
        bedTimeToMidnightInputResult.setText(null);
        midnightToShiftEndInputResult.setText(null);
        totalResult.setText(null);
    }


}
