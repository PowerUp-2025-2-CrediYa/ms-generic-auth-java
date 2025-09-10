package co.com.pragma.crediya.api.mappers;

import co.com.pragma.crediya.api.model.request.AccountRequest;
import co.com.pragma.crediya.api.model.request.ProfileRequest;
import co.com.pragma.crediya.model.user.UserAccount;
import co.com.pragma.crediya.model.user.UserProfile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationMapper {
    public UserProfile toProfile(ProfileRequest dto, String email) {
        return UserProfile.builder()
                .id(null)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(email)
                .documentId(dto.getDocumentId())
                .birthDate(dto.getBirthDate())
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .baseSalary(dto.getBaseSalary())
                .build();
    }

    public UserAccount toAccount(AccountRequest dto, UUID profileId, String hashedPassword) {
        return UserAccount.builder()
                .id(null)
                .profileId(profileId)
                .username(dto.getUsername())
                .hashedPassword(hashedPassword)
                .roles(dto.getRoles())
                .build();
    }

}
