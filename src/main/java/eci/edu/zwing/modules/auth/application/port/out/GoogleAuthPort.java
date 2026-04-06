package eci.edu.zwing.modules.auth.application.port.out;


import eci.edu.zwing.modules.auth.domain.model.GoogleUserData;

public interface GoogleAuthPort {
    GoogleUserData verifyToken(String googleToken);
}
