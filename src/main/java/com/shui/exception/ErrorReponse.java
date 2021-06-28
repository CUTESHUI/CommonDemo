package com.shui.exception;

import lombok.*;

import java.util.Date;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReponse {
    private int code;
    private String method;
    private String message;
    private String path;
    private Date date;
    private String errorTypeName;
}
