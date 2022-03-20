package com.karg.userrsmanagement.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationStatusModel {
    private String operationName;
    private String operationResult;
}