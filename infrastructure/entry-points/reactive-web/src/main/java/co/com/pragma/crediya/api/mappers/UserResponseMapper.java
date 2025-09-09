package co.com.pragma.crediya.api.mappers;

import co.com.pragma.crediya.api.model.response.UserResponse;
import co.com.pragma.crediya.model.user.User;

public class UserResponseMapper {

    UserResponseMapper() {
    }

    public static UserResponse fromDomain(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .documentId(user.getDocumentId())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .roles(user.getRoles())
                .baseSalary(user.getBaseSalary())
                .build();
    }

    public static UserResponse fromDomainToClient(User user) {
        return UserResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .documentId(user.getDocumentId())
                .birthDate(user.getBirthDate())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .baseSalary(user.getBaseSalary())
                .build();
    }

}
