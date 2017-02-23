package com.pano.panoserver.repository;

import com.pano.panoserver.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by wangboquan on 17/2/22.
 */
public interface PasswordResetRespository extends JpaRepository<PasswordReset, Integer> {
    PasswordReset findByEmail(String email);
}
