package by.zadziarnouski.workoutlog.model;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    private final String displayValue;

    Gender(String displayValue){
        this.displayValue = displayValue;
    }

    public String getDisplayValue(){
        return displayValue;
    }
}
