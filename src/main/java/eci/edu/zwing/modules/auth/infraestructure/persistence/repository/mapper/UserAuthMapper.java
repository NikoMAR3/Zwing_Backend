package eci.edu.zwing.modules.auth.infraestructure.persistence.repository.mapper;


import eci.edu.zwing.modules.auth.domain.model.User;
import eci.edu.zwing.modules.auth.infraestructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserAuthMapper {
    public UserEntity toEntity(User user){
        UserEntity entity = new UserEntity();

        entity.setUserId(user.getUserId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        return entity;
    }

    public User toDomain(UserEntity entity){
        return new User(
                entity.getUserId(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
