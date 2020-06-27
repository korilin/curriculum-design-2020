package com.arukione.curriculum_design.exception;

import com.arukione.curriculum_design.utils.Message;

public class PermissionException extends Exception{
    public PermissionException(){
        super(Message.USER_PERMISSION_ERROR);
    }
}
