package com.fruitshop.service;

import com.fruitshop.dao.UserDAO;
import com.fruitshop.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public User findById(int id) {
        return userDAO.findById(id);
    }

    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User findByPhone(String phone) {
        return userDAO.findByPhone(phone);
    }

    public boolean authenticate(String emailOrPhone, String password) {
        User user = null;

        // 尝试通过邮箱查找用户
        if (emailOrPhone.contains("@")) {
            user = userDAO.findByEmail(emailOrPhone);
        } else {
            // 否则尝试通过手机号查找用户
            user = userDAO.findByPhone(emailOrPhone);
        }

        if (user != null) {
            // 使用BCrypt验证密码
            return BCrypt.checkpw(password, user.getPasswordHash());
        }

        return false;
    }

    public User login(String emailOrPhone, String password) {
        User user = null;

        // 尝试通过邮箱查找用户
        if (emailOrPhone.contains("@")) {
            user = userDAO.findByEmail(emailOrPhone);
        } else {
            // 否则尝试通过手机号查找用户
            user = userDAO.findByPhone(emailOrPhone);
        }

        if (user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            return user;
        }

        return null;
    }

    public int register(User user, String password) {
        // 检查用户是否已存在
        if (user.getEmail() != null && userDAO.findByEmail(user.getEmail()) != null) {
            return -1; // 邮箱已被使用
        }

        if (user.getPhone() != null && userDAO.findByPhone(user.getPhone()) != null) {
            return -2; // 手机号已被使用
        }

        // 使用BCrypt哈希密码
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPasswordHash(hashedPassword);

        // 保存用户
        return userDAO.insert(user);
    }

    public boolean updateProfile(User user) {
        User existingUser = userDAO.findById(user.getId());

        if (existingUser == null) {
            return false;
        }

        // 检查邮箱是否已被其他用户使用
        if (user.getEmail() != null && !user.getEmail().equals(existingUser.getEmail())) {
            User emailUser = userDAO.findByEmail(user.getEmail());
            if (emailUser != null && emailUser.getId() != user.getId()) {
                return false; // 邮箱已被其他用户使用
            }
        }

        // 检查手机号是否已被其他用户使用
        if (user.getPhone() != null && !user.getPhone().equals(existingUser.getPhone())) {
            User phoneUser = userDAO.findByPhone(user.getPhone());
            if (phoneUser != null && phoneUser.getId() != user.getId()) {
                return false; // 手机号已被其他用户使用
            }
        }

        // 更新用户信息
        return userDAO.update(user);
    }

    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        User user = userDAO.findById(userId);

        if (user == null || !BCrypt.checkpw(oldPassword, user.getPasswordHash())) {
            return false;
        }

        // 使用BCrypt哈希新密码
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        user.setPasswordHash(hashedPassword);

        return userDAO.update(user);
    }
}
