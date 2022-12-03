package com.example.potato.sic9.repository;

import com.example.potato.sic9.entity.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByName(String name);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
