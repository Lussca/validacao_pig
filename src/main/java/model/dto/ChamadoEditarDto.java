package model.dto;

public class ChamadoEditarDto {

    private Long idChamaddo;

    private String codChamado;

    private String titulo;

    private String descricao;

    public ChamadoEditarDto(Long idChamaddo, String codChamado, String titulo, String descricao) {
        this.idChamaddo = idChamaddo;
        this.codChamado = codChamado;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Long getIdChamaddo() {
        return idChamaddo;
    }

    public void setIdChamaddo(Long idChamaddo) {
        this.idChamaddo = idChamaddo;
    }

    public String getCodChamado() {
        return codChamado;
    }

    public void setCodChamado(String codChamado) {
        this.codChamado = codChamado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
