package controller;

import model.dao.ChamadoDao;
import model.dto.ChamadoDto;
import model.dto.ChamadoEditarDto;
import model.dto.ChamadosTabelaDto;

import java.sql.SQLException;
import java.util.List;

public class ChamadoController {

    public boolean cadastrarChamado(Long idUser, String titel_chamado, String description_chamado ) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.cadastrarChamado(idUser, titel_chamado, description_chamado);
    }

    public List<ChamadosTabelaDto> carregarChamadosAbertos() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamadosAbertos();
    }

    public List<ChamadosTabelaDto> carregarChamados() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamados();
    }

    public boolean removerChamadoPorId(Long idChamado) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.removerChamadoPorId(idChamado);
    }

    public ChamadoEditarDto buscarChamadoPorId(Long idChamado) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return  chamadoDao.buscarChamadoPorId(idChamado);
    }

    public boolean atualizarChamado(Long idUser, String titulo, String descricao) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.atualizarChamado(idUser, titulo, descricao);
    }

    public ChamadoDto carregarChamadosAtendimentoPorId(Long idChamado) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamadoAtendimentoPorId(idChamado);
    }

    public boolean receberChamado(Long idChamado, Long idUsuario) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.receberChamado(idChamado, idUsuario);
    }

    public boolean resolverChamado(Long idChamado, String solucao) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.resolverChamado(idChamado, solucao);
    }

    public List<ChamadosTabelaDto> carregarChamadosAbertosPorMim(Long idUser) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamadosAbertosPorMim(idUser);
    }

    public List<ChamadosTabelaDto> carregarChamadosAtendidosPorMim(Long idUser) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.carregarChamadosAtendidosPorMim(idUser);
    }

    public int buscarTotalChamdosAbertos() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroSistemaChamado();
    }

    public int buscarTotalChamdosAtendidos() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosEmAtendimento();
    }

    public int buscarTotalChamdosResolvidos() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosResolvidos();
    }

    public int buscarTotalChamdosResolvidosHoje() throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosResolvidosHoje();
    }

    public int buscarTotalChamdosAbertosPeloUsuario(Long idUser) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosAbertosPeloUsuario(idUser);
    }

    public int buscarTotalChamdosAtendidosPeloUsuario(Long idUser) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosAntendidosPeloUsuario(idUser);
    }

    public int buscarTotalChamdosResolvidosPeloUsuario(Long idUser) throws SQLException {
        ChamadoDao chamadoDao = new ChamadoDao();
        return chamadoDao.numeroChamadosResolvidosPeloUsuario(idUser);
    }
}
