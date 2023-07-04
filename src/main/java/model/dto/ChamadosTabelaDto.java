package model.dto;

import java.util.Objects;

public class ChamadosTabelaDto {
    private Long idChamado;

    private String codChamado;

    private String dataHoraChamado;

    private String titulo;

    public ChamadosTabelaDto(Long idChamado, String codChamado, String dataHoraChamado, String titulo) {
        this.idChamado = idChamado;
        this.codChamado = codChamado;
        this.dataHoraChamado = dataHoraChamado;
        this.titulo = titulo;
    }

    public Long getIdChamado() {
        return idChamado;
    }

    public void setIdChamado(Long idChamado) {
        this.idChamado = idChamado;
    }

    public String getCodChamado() {
        return codChamado;
    }

    public void setCodChamado(String codChamado) {
        this.codChamado = codChamado;
    }

    public String getDataHoraChamado() {
        return dataHoraChamado;
    }

    public void setDataHoraChamado(String dataHoraChamado) {
        this.dataHoraChamado = dataHoraChamado;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChamadosTabelaDto that = (ChamadosTabelaDto) o;
        return Objects.equals(idChamado, that.idChamado) && Objects.equals(codChamado, that.codChamado) && Objects.equals(dataHoraChamado, that.dataHoraChamado) && Objects.equals(titulo, that.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idChamado, codChamado, dataHoraChamado, titulo);
    }
}
