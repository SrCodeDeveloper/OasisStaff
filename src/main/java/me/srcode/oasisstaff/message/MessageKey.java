package me.srcode.oasisstaff.message;

public enum MessageKey {

    NO_PERMISSION("no-permission");

    private final String value;

    MessageKey(String value){
        this.value = value;
    }
    public String value(){
        return this.value;
    }
}
