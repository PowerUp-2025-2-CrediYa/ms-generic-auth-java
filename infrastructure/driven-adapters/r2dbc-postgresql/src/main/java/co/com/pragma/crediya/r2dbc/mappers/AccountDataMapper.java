package co.com.pragma.crediya.r2dbc.mappers;

import co.com.pragma.crediya.model.user.UserAccount;
import co.com.pragma.crediya.r2dbc.entity.AccountEntity;

public class AccountDataMapper {

    private AccountDataMapper(){}

    public static AccountEntity toData(UserAccount a) {
        var d = new AccountEntity();
        d.setId(a.getId());
        d.setProfileId(a.getProfileId());
        d.setUsername(a.getUsername());
        d.setHashedPassword(a.getHashedPassword());
        return d;
    }

    public static UserAccount toDomain(AccountEntity d) {
        return UserAccount.builder()
                .id(d.getId())
                .profileId(d.getProfileId())
                .username(d.getUsername())
                .hashedPassword(d.getHashedPassword())
                .roles(null) // los roles se cargan aparte desde account_roles
                .build();
    }

}
