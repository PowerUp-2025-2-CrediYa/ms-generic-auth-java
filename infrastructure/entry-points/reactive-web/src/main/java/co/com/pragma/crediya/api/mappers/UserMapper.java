package co.com.pragma.crediya.api.mappers;

import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.model.user.User;

public class UserMapper {

    UserMapper() {
    }

    public static User toDomain(UserRequest dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .documentId(dto.getDocumentId())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .baseSalary(dto.getBaseSalary())
                .roles(dto.getRoles())
                .build();
    }

}
