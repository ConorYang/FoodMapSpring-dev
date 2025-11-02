package Food.service.account;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Food.entity.account.UserType;
import Food.repository.account.UserTypeRepository;

@Service
public class UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    public Optional<UserType> getById(Long id) {
        return userTypeRepository.findById(id);
    }
}
