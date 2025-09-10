package co.com.pragma.crediya.api.mappers;

import co.com.pragma.crediya.api.model.response.AccountResponse;
import co.com.pragma.crediya.api.model.response.ProfileResponse;
import co.com.pragma.crediya.api.model.response.RegistrationResponse;
import co.com.pragma.crediya.model.user.UserAccount;
import co.com.pragma.crediya.model.user.UserProfile;
import org.springframework.stereotype.Component;

@Component
public class RegistrationResponseMapper {

    public RegistrationResponse toResponse(UserProfile profile, UserAccount account) {
        var profileRes = new ProfileResponse(
                profile.getFirstName(),
                profile.getLastName(),
                profile.getDocumentId(),
                profile.getBirthDate(),
                profile.getAddress(),
                profile.getPhoneNumber(),
                profile.getBaseSalary()
        );

        var accountRes = new AccountResponse(
                account.getUsername(),
                account.getRoles()
        );

        return new RegistrationResponse(profileRes, accountRes);
    }
}
