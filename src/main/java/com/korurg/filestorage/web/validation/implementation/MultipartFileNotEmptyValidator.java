package com.korurg.filestorage.web.validation.implementation;

import com.korurg.filestorage.web.validation.constraints.MultipartFileNotEmpty;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MultipartFileNotEmptyValidator implements ConstraintValidator<MultipartFileNotEmpty, MultipartFile> {
    @Override
    public void initialize(MultipartFileNotEmpty constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return !value.isEmpty();
    }
}
