package eci.edu.zwing.modules.auth.application.port.in;


import eci.edu.zwing.modules.auth.infraestructure.web.dto.response.AuthResponse;

public interface AuthenticateWithGoogleUseCase {
    AuthResponse authenticate(String idToken);
}