package com.github.rlewan.mailer.model;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelValidator {

    private ModelValidator() {
        // Utility class
    }

    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public static <T> Set<String> validate(T bean) {
        return getMessages(factory.getValidator().validate(bean));
    }

    private static <T> Set<String> getMessages(Set<ConstraintViolation<T>> response) {
        return response.stream()
            .map(ModelValidator::prepareMessage)
            .collect(Collectors.toSet());
    }

    private static String prepareMessage(ConstraintViolation property) {
        if (!StringUtils.isEmpty(property.getPropertyPath().toString())) {
            return property.getPropertyPath() + " : " + property.getMessage();
        }

        return property.getMessage();
    }

}
