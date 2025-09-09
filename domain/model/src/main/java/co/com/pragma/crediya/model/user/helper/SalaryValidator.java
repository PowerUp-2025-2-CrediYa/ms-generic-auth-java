package co.com.pragma.crediya.model.user.helper;

import co.com.pragma.crediya.model.user.exception.InvalidBaseSalaryRangeException;
import co.com.pragma.crediya.model.user.exception.InvalidUserException;

import java.math.BigDecimal;

public final class SalaryValidator {

    private static final double MAX_SALARY = 15_000_000;

    private SalaryValidator() {
    }

    public static void validate(Double salary) {
        if (salary == null) {
            throw new InvalidUserException("El salario base no puede ser nulo");
        }

        if (salary < 0 || salary > MAX_SALARY) {
            throw new InvalidBaseSalaryRangeException(
                    BigDecimal.valueOf(salary).stripTrailingZeros().toPlainString()
            );
        }
    }
}

