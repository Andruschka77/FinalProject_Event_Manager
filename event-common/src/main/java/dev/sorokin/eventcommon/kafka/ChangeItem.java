package dev.sorokin.eventcommon.kafka;

public class ChangeItem {

    private String fieldName = "test";

    public ChangeItem() {}

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
