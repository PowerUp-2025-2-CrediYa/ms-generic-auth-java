package co.com.pragma.crediya.r2dbc.mappers;

import co.com.pragma.crediya.model.user.User;
import co.com.pragma.crediya.r2dbc.entity.UserEntity;

public class UserPersistenceMapper {

    public static UserEntity toEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setDocumentId(user.getDocumentId());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userEntity.setRoleId(user.getRoleId());
        userEntity.setBaseSalary(user.getBaseSalary());
        userEntity.setCreatedAt(user.getCreatedAt());
        userEntity.setUpdatedAt(user.getUpdatedAt());
        return userEntity;
    }

    public static User toDomain(UserEntity userEntity) {
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setDocumentId(userEntity.getDocumentId());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        user.setRoleId(userEntity.getRoleId());
        user.setBaseSalary(userEntity.getBaseSalary());
        user.setCreatedAt(userEntity.getCreatedAt());
        user.setUpdatedAt(userEntity.getUpdatedAt());
        return user;
    }
}
