package co.com.pragma.crediya.r2dbc.mappers;

import co.com.pragma.crediya.model.user.UserProfile;
import co.com.pragma.crediya.r2dbc.entity.ProfileEntity;

public final class ProfileEntityMapper {

    private ProfileEntityMapper(){}

    public static ProfileEntity toData(UserProfile u) {

        ProfileEntity d = new ProfileEntity();
        d.setId(u.getId());
        d.setFirstName(u.getFirstName());
        d.setLastName(u.getLastName());
        d.setEmail(u.getEmail());
        d.setDocumentId(u.getDocumentId());
        d.setBirthDate(u.getBirthDate());
        d.setAddress(u.getAddress());
        d.setPhoneNumber(u.getPhoneNumber());
        d.setBaseSalary(u.getBaseSalary());
        return d;
    }

    public static UserProfile toDomain(ProfileEntity d) {
        return UserProfile.builder()
                .id(d.getId())
                .firstName(d.getFirstName())
                .lastName(d.getLastName())
                .email(d.getEmail())
                .documentId(d.getDocumentId())
                .birthDate(d.getBirthDate())
                .address(d.getAddress())
                .phoneNumber(d.getPhoneNumber())
                .baseSalary(d.getBaseSalary())
                .build();
    }
}
