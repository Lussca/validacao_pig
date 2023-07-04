package controller;

import model.dao.UserDao;
import model.dto.UserEditDto;

import java.sql.SQLException;

public class UserController {

    public Long logar(String login_user, String password_user) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.logar(login_user, password_user);
    }

    public boolean cadastarUsuario(String codUser, String user, String hashSenha, String email, String name) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.cadastarUsuario(codUser, user, hashSenha, email, name);
    }

    public UserEditDto carregarUserPorId(Long idUserEdit) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.carregarUserPorId(idUserEdit);
    }

    public boolean atualizarSenha(Long idUserEdit, String hashSenha) throws SQLException {
        UserDao userDao = new UserDao();
        return userDao.atualizarSenhaPorId(idUserEdit, hashSenha);
    }
}
