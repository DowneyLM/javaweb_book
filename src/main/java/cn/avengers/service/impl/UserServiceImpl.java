package cn.avengers.service.impl;

import cn.avengers.dao.UserDao;
import cn.avengers.dao.impl.UserDaoImpl;
import cn.avengers.pojo.User;
import cn.avengers.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public void registUser(User user) {
        userDao.saveUser(user);
    }


    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {

        if(userDao.queryUserByUsername(username) == null){
            return false;
        }

        return true;
    }
}
