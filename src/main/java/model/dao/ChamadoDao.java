package model.dao;

import model.dto.ChamadoDto;
import model.dto.ChamadoEditarDto;
import model.dto.ChamadosTabelaDto;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChamadoDao {

    public boolean cadastrarChamado(Long idUser, String titel_chamado, String description_chamado) throws SQLException{
        boolean sucessCadastro = false;
        Integer chamado = numeroSistemaChamado();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("INSERT INTO chamados (cod_chamado, titel_chamado, description_chamado, data_abertura_chamado, id_usuario_requisitante)"+
                    "VALUES(?,?,?,?,?)");
            String cod_chamado = "C"+idUser.toString()+""+chamado;
            pstmt.setString(1, cod_chamado);
            pstmt.setString(2, titel_chamado);
            pstmt.setString(3, description_chamado);
            Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
            pstmt.setTimestamp(4, timestamp);
            pstmt.setLong(5, idUser);
            pstmt.executeUpdate();
            sucessCadastro = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return sucessCadastro;
    }

    public int numeroSistemaChamado() throws SQLException{
        int numsChamados = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) from chamados");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamados = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
                conn.close();
                pstmt.close();
        }

        return numsChamados;
    }

    public List<ChamadosTabelaDto> carregarChamadosAbertos() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadosTabelaDto> chamadosAbertos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, data_abertura_chamado, cod_chamado, titel_chamado from chamados WHERE data_fechamento_chamado IS NULL ORDER BY data_abertura_chamado ");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                Timestamp dataHoraAbretura = rs.getTimestamp(2);
                String dataFormatada = simpleDateFormat.format(dataHoraAbretura);
                String codChamado = rs.getString(3);
                String titulo = rs.getString(4);
                ChamadosTabelaDto chamado = new ChamadosTabelaDto(idChamado, codChamado,dataFormatada, titulo);
                chamadosAbertos.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return chamadosAbertos;
    }

    public List<ChamadosTabelaDto> carregarChamados() throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadosTabelaDto> chamados = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, data_abertura_chamado, cod_chamado, titel_chamado from chamados ORDER BY data_abertura_chamado");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                Timestamp dataHoraAbretura = rs.getTimestamp(2);
                String dataFormatada = simpleDateFormat.format(dataHoraAbretura);
                String codChamado = rs.getString(3);
                String titulo = rs.getString(4);
                ChamadosTabelaDto chamado = new ChamadosTabelaDto(idChamado, codChamado,dataFormatada, titulo);
                chamados.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return chamados;
    }

    public boolean removerChamadoPorId(Long idChamado) throws SQLException {
        boolean apagar = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("DELETE FROM chamados WHERE  id_chamado = ?");
            pstmt.setLong(1, idChamado);
            pstmt.executeUpdate();
            apagar = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return apagar;
    }

    public ChamadoEditarDto buscarChamadoPorId(Long idChamado) throws SQLException {
        ChamadoEditarDto chamado = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, titel_chamado, description_chamado FROM chamados WHERE  id_chamado = ?");
            pstmt.setLong(1, idChamado);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                Long id = rs.getLong(1);
                String codChamado = rs.getString(2);
                String titulo = rs.getString(3);
                String descricao = rs.getString(4);
                chamado = new ChamadoEditarDto(id, codChamado, titulo, descricao);
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return chamado;
    }

    public boolean atualizarChamado(Long idUser, String titulo, String descricao) throws SQLException {
        boolean atualizou = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamados SET titel_chamado = ?, description_chamado = ?,id_chamado =?");
            pstmt.setString(1, titulo);
            pstmt.setString(2, descricao);
            pstmt.setLong(3, idUser);
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

    public ChamadoDto carregarChamadoAtendimentoPorId(Long idChamado) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        ChamadoDto chamado = new ChamadoDto();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, cod_chamado, titel_chamado, description_chamado, data_abertura_chamado, name_user, description__soluction_chamado, data_fechamento_chamado, id_usuario_tecnico " +
                    "FROM chamados INNER JOIN usuario " +
                    "ON chamados.id_usuario_requisitante = usuario.id_user WHERE id_chamado = ?");
            pstmt.setLong(1, idChamado);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long id = rs.getLong(1);
                String codChamado = rs.getString(2);
                String titulo = rs.getString(3);
                String descricao = rs.getString(4);
                Timestamp dataHoraAbretura = rs.getTimestamp(5);
                String dataAberturaFormatada = simpleDateFormat.format(dataHoraAbretura);
                String nomeUsuario = rs.getString(6);
                String descricaoSolucao = rs.getString(7);
                Timestamp dataHoraFechamento = rs.getTimestamp(8);
                String dataFechamentoFormatada = "";
                if (dataHoraFechamento != null){
                    dataFechamentoFormatada = simpleDateFormat.format(dataHoraFechamento);
                }
                Long idUsuarioAtendente = rs.getLong(9);
                chamado.setIdChamado(id);
                chamado.setCodChamado(codChamado);
                chamado.setTitulo(titulo);
                chamado.setDescricao(descricao);
                chamado.setDataHoraAbertura(dataAberturaFormatada);
                chamado.setUsuarioRequisitante(nomeUsuario);
                chamado.setSolucao(descricaoSolucao);
                chamado.setDataHoraFechamento(dataFechamentoFormatada);
                chamado.setIdUsuarioAtendente(idUsuarioAtendente);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return chamado;
    }

    public boolean receberChamado(Long idChamado, Long idUsuario) throws SQLException {
        boolean recebido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamados SET id_usuario_tecnico = ? WHERE id_chamado =?");
            pstmt.setLong(1, idUsuario);
            pstmt.setLong(2, idChamado);
            pstmt.executeUpdate();
            recebido = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return recebido;
    }

    public boolean resolverChamado(Long idChamado, String solucao) throws SQLException {
        boolean resolvido = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("UPDATE chamados SET description__soluction_chamado = ?, data_fechamento_chamado = ? WHERE id_chamado =?");
            pstmt.setString(1, solucao);
            LocalDateTime now = LocalDateTime.now();
            Timestamp timestamp = Timestamp.valueOf(now);
            pstmt.setTimestamp(2, timestamp);
            pstmt.setLong(3, idChamado);
            pstmt.executeUpdate();
            resolvido = true;
        } catch (Exception e) {
            System.out.println("Erro: "+ e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return resolvido;
    }

    public List<ChamadosTabelaDto> carregarChamadosAbertosPorMim(Long idUser) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadosTabelaDto> chamadosAbertosPorMim = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, data_abertura_chamado, cod_chamado, titel_chamado from chamados WHERE id_usuario_requisitante = ? ORDER BY data_abertura_chamado");
            pstmt.setLong(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                Timestamp dataHoraAbretura = rs.getTimestamp(2);
                String dataFormatada = simpleDateFormat.format(dataHoraAbretura);
                String codChamado = rs.getString(3);
                String titulo = rs.getString(4);
                ChamadosTabelaDto chamado = new ChamadosTabelaDto(idChamado, codChamado,dataFormatada, titulo);
                chamadosAbertosPorMim.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return chamadosAbertosPorMim;
    }

    public List<ChamadosTabelaDto> carregarChamadosAtendidosPorMim(Long idUser) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        List<ChamadosTabelaDto> chamadosAtendidosPorMim = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT id_chamado, data_abertura_chamado, cod_chamado, titel_chamado from chamados WHERE id_usuario_tecnico = ? AND data_fechamento_chamado IS NULL ORDER BY data_abertura_chamado");
            pstmt.setLong(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Long idChamado = rs.getLong(1);
                Timestamp dataHoraAbretura = rs.getTimestamp(2);
                String dataFormatada = simpleDateFormat.format(dataHoraAbretura);
                String codChamado = rs.getString(3);
                String titulo = rs.getString(4);
                ChamadosTabelaDto chamado = new ChamadosTabelaDto(idChamado, codChamado,dataFormatada, titulo);
                chamadosAtendidosPorMim.add(chamado);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }
        return chamadosAtendidosPorMim;
    }

    public int numeroChamadosEmAtendimento() throws SQLException {
        int numsChamadosEmAtendimento = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE id_usuario_tecnico IS NOT NULL AND data_fechamento_chamado IS NULL");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamadosEmAtendimento = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numsChamadosEmAtendimento;
    }

    public int numeroChamadosResolvidos() throws SQLException {
        int numsChamadosResolvidos = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE  data_fechamento_chamado IS NOT NULL");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamadosResolvidos = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numsChamadosResolvidos;
    }

    public int numeroChamadosResolvidosHoje() throws SQLException {
        int numsChamadosResolvidosHoje = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE  data_fechamento_chamado IS NOT NULL AND DATE (data_fechamento_chamado) = CURDATE()");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamadosResolvidosHoje = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numsChamadosResolvidosHoje;
    }

    public int numeroChamadosAbertosPeloUsuario(Long idUser) throws SQLException {
        int numsChamadosAbertosPorMim = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE  id_usuario_requisitante = ?");
            pstmt.setLong(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamadosAbertosPorMim = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numsChamadosAbertosPorMim;
    }

    public int numeroChamadosAntendidosPeloUsuario(Long idUser) throws SQLException {
        int numsChamadosAtendidosPorMim = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE  id_usuario_tecnico = ? AND data_fechamento_chamado IS NULL");
            pstmt.setLong(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamadosAtendidosPorMim = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numsChamadosAtendidosPorMim;
    }

    public int numeroChamadosResolvidosPeloUsuario(Long idUser) throws SQLException {
        int numsChamadosAResolvidosPorMim = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/banco_val_desktop_pig", "root", "admin");
            pstmt = conn.prepareStatement("SELECT COUNT(id_chamado) FROM chamados WHERE  id_usuario_tecnico = ? AND data_fechamento_chamado IS NOT NULL");
            pstmt.setLong(1, idUser);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                numsChamadosAResolvidosPorMim = rs.getInt(1);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        } finally {
            conn.close();
            pstmt.close();
        }

        return numsChamadosAResolvidosPorMim;
    }
}
