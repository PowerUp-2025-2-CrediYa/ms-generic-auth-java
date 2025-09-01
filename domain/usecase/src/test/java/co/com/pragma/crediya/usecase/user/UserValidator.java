package co.com.pragma.crediya.usecase.user;

import co.com.pragma.crediya.model.user.User;

import java.util.UUID;

final class UserValidator {

    static User valid() {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName("Jon");
        user.setLastName("Doe");
        user.setEmail("ok@dom.com");
        user.setDocumentId("123456789");
        user.setPhoneNumber("1234568910");
        user.setRoleId(1);
        user.setBaseSalary(Double.valueOf("1000000"));
        return user;

    }

    static User withBaseSalary(String value) {
        var u = valid();
        u.setBaseSalary(Double.valueOf(value));
        return u;
    }
}
