package model.dao;

import model.dto.UserEditDto;

import java.sql.*;
import java.time.LocalDateTime;

public class UserDao {

    public Long logar(String login_user, String password_user) throws SQLException {
        Long idUser = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_user FROM usuario WHERE login_user LIKE ? AND password_user LIKE ?");
            pstmt.setString(1, login_user);
            pstmt.setString(2, password_user);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                idUser = rs.getLong("id_user");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return idUser;
    }

    public boolean cadastarUsuario(String codUser, String user, String hashSenha, String email, String name) throws SQLException {
        boolean userCadastro = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("INSERT INTO usuario (cod_user, name_user, email_user, login_user, password_user)"+
                    "VALUES(?,?,?,?,?)");
            pstmt.setString(1, codUser);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, user);
            pstmt.setString(5, hashSenha);
            pstmt.executeUpdate();
            userCadastro = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return userCadastro;
    }

    public UserEditDto carregarUserPorId(Long idUserEdit) throws SQLException {
        UserEditDto userEditDto = new UserEditDto();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_user, cod_user, name_user, login_user, email_user FROM usuario WHERE id_user = ?");
            pstmt.setLong(1, idUserEdit);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idUser = rs.getLong("id_user");
                String codUser = rs.getString("cod_user");
                String name = rs.getString("name_user");
                String login = rs.getString("login_user");
                String email = rs.getString("email_user");
                userEditDto.setIdUser(idUser);
                userEditDto.setCodUser(codUser);
                userEditDto.setNome(name);
                userEditDto.setLogin(login);
                userEditDto.setEmail(email);
                userEditDto.setPassword("");
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return userEditDto;
    }

    public boolean atualizarSenhaPorId(Long idUserEdit, String hashSenha) throws SQLException {
        boolean atualizou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE usuario SET password_user = ? WHERE id_user = ?");
            pstmt.setString(1, hashSenha);
            pstmt.setLong(2, idUserEdit);
            pstmt.executeUpdate();
            atualizou = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return atualizou;
    }
}
