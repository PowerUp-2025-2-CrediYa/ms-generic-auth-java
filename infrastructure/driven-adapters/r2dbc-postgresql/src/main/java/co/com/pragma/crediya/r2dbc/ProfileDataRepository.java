package co.com.pragma.crediya.r2dbc;

import co.com.pragma.crediya.r2dbc.entity.ProfileEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ProfileDataRepository extends ReactiveCrudRepository<ProfileEntity, UUID> {}