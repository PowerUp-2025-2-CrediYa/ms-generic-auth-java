package co.com.pragma.crediya.model.user.exception;

public class InvalidBaseSalaryRangeException extends RuntimeException {

    public InvalidBaseSalaryRangeException(String baseSalary) {
        super("El salario base debe estar entre 0 y 15.000.000: ".concat(baseSalary));
    }

}
