package eci.edu.zwing.modules.auth.application.port.out;

import eci.edu.zwing.modules.auth.domain.model.User;

public interface TokenGeneratorPort {
    String generateToken(User user);
}
