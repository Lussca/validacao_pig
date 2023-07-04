package model.dto;

public class UserEditDto {

    private Long IdUser;

    private String codUser;

    private String login;

    private String email;

    private String nome;

    private String password;

    public Long getIdUser() {
        return IdUser;
    }

    public void setIdUser(Long idUser) {
        IdUser = idUser;
    }

    public String getCodUser() {
        return codUser;
    }

    public void setCodUser(String codUser) {
        this.codUser = codUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
