package com.arukione.curriculum_design.utils;

public class PermissionException extends Exception{
    public PermissionException(){
        super(Message.USER_PERMISSION_ERROR);
    }
}
