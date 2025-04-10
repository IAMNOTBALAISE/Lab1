package org.champsoft.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpErrorInfo {

    private int status;
    private String path;
    private String message;
}
