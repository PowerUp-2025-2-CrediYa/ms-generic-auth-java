package co.com.pragma.crediya.api.config;

import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.model.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class UtilUserTesting {

    public static final String BASE_URL = "/api/v1/usuarios";

    public static User getUser() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse("01/01/1990", formatter);

        return User.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("ok@dom.com")
                .documentId("123456789")
                .birthDate(fechaNacimiento)
                .address("Calle XXX # YY-ZZ")
                .phoneNumber("+1234568910")
                .roleId(1)
                .baseSalary(5000000.0)
                .build();
    }


    public static UserRequest getUserRequest() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNacimiento = LocalDate.parse("01/01/1990", formatter);

        return UserRequest.builder()
                .firstName("Jon")
                .lastName("Doe")
                .email("ok@dom.com")
                .documentId("123456789")
                .birthDate(fechaNacimiento)
                .address("Calle XXX # YY-ZZ")
                .phoneNumber("+1234568910")
                .roleId(1)
                .baseSalary(5000000.0)
                .build();
    }
}
