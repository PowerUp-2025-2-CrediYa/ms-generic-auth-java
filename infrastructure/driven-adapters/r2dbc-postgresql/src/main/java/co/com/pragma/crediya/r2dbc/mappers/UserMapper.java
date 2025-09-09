package co.com.pragma.crediya.r2dbc.mappers;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.r2dbc.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public final class UserMapper {

    public static User toDomain(UserEntity userEntity, List<String> codes) {
        return User.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .documentId(userEntity.getDocumentId())
                .birthDate(userEntity.getBirthDate())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .baseSalary(userEntity.getBaseSalary())
                .roles(codes)
                .build();
    }

    public static UserEntity toEntity(User u) {

        var userEntity = new UserEntity();
        userEntity.setId(u.getId() != null ? u.getId() : UUID.randomUUID());
        userEntity.setFirstName(u.getFirstName());
        userEntity.setLastName(u.getLastName());
        userEntity.setEmail(u.getEmail());
        userEntity.setDocumentId(u.getDocumentId());
        userEntity.setBirthDate(u.getBirthDate());
        userEntity.setAddress(u.getAddress());
        userEntity.setPhoneNumber(u.getPhoneNumber());
        userEntity.setBaseSalary(u.getBaseSalary());
        return userEntity;
    }
}
