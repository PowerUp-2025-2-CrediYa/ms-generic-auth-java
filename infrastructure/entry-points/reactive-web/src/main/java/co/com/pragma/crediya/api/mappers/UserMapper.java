package co.com.pragma.crediya.api.mappers;

import co.com.pragma.crediya.api.model.request.UserRequest;
import co.com.pragma.crediya.model.user.User;


public class UserMapper {
    public static User toDomain(UserRequest dto) {
        return User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .documentId(dto.getDocumentId())
                .phoneNumber(dto.getPhoneNumber())
                .roleId(dto.getRoleId())
                .baseSalary(dto.getBaseSalary())
                .build();
    }

}
