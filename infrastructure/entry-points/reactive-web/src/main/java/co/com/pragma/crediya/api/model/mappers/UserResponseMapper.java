package co.com.pragma.crediya.api.model.mappers;

import co.com.pragma.crediya.api.model.response.UserResponseDTO;
import co.com.pragma.crediya.model.user.User;

public class UserResponseMapper {

    public static UserResponseDTO toDTO(User user) {
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setFirstName(user.getFirstName());
        responseDTO.setLastName(user.getLastName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setDocumentId(user.getDocumentId());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        responseDTO.setRoleId(user.getRoleId());
        responseDTO.setBaseSalary(user.getBaseSalary());
        responseDTO.setCreatedAt(user.getCreatedAt());
        responseDTO.setUpdatedAt(user.getUpdatedAt());
        return responseDTO;
    }
}
