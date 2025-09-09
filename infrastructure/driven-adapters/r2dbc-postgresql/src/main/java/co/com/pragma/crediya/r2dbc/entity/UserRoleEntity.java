package co.com.pragma.crediya.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("auth.roles")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserRoleEntity {

    @Id
    @Column("id_rol")
    private Integer id;

    @Column("codigo")
    private String code;

    @Column("nombre")
    private String name;

    @Column("descripcion")
    private String description;
}
