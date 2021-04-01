package web.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.Role;
import web.model.User;
import web.repository.RoleRepository;
import web.repository.UserDao;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleRepository roleRepository;

    public void addUser(User user) {
        if (userDao.loadUserByUsername(user.getLogin()) == null) {
            for (Role role : user.getRoles()) {
                roleRepository.save(role);
            }
            userDao.addUser(user);
        }
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void updateUser(Long id, User user) {
        userDao.updateUser(id, user);
    }

    public void deleteUser(Long id) {
        User user = userDao.getUser(id);
        for (Role role : user.getRoles()
        ) {
            roleRepository.delete(role);
        }
        userDao.deleteUser(id);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }
}
