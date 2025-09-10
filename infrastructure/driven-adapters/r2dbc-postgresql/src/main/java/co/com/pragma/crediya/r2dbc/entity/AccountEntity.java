package co.com.pragma.crediya.r2dbc.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "accounts", schema = "auth")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountEntity {

    @Id
    private UUID id;

    @Column("profile_id")
    private UUID profileId;

    @Column("username")
    private String username;

    @Column("hashed_password")
    private String hashedPassword;

}
