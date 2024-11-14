package com.bufigol.universidad.validacion;

import com.bufigol.universidad.utils.Comprobadores;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RutValidator implements ConstraintValidator<RutValido, String> {

    @Override
    public void initialize(RutValido constraintAnnotation) {
    }

    @Override
    public boolean isValid(String rut, ConstraintValidatorContext context) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }
        return Comprobadores.isValidRUT(rut);
    }
}