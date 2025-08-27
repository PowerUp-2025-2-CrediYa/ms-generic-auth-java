package co.com.pragma.crediya.api.model.mappers;

import co.com.pragma.crediya.api.model.request.UserRequestDTO;
import co.com.pragma.crediya.model.user.User;

import java.time.ZonedDateTime;
import java.util.UUID;

public class UserRequestMapper {

    public static User toDomain(UserRequestDTO requestDTO) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setDocumentId(requestDTO.getDocumentId());
        user.setPhoneNumber(requestDTO.getPhoneNumber());
        user.setRoleId(requestDTO.getRoleId());
        user.setBaseSalary(requestDTO.getBaseSalary());
        user.setCreatedAt(ZonedDateTime.now());
        user.setUpdatedAt(ZonedDateTime.now());
        return user;
    }
}
