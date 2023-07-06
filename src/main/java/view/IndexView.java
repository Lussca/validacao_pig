/*
 * Created by JFormDesigner on Sun Jun 25 01:13:19 BRT 2023
 */

package view;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfWriter;
import controller.ChamadoController;
import model.dto.ChamadosTabelaDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndexView extends JFrame {

    private Long idUser;

    private List<ChamadosTabelaDto> listaChamadosAll = new ArrayList<>();
    private List<ChamadosTabelaDto> listaChamadosAbertos = new ArrayList<>();
    private List<ChamadosTabelaDto> listaChamadosAtendidos = new ArrayList<>();

    public IndexView(Long idUser) {
        this.idUser = idUser;
        initComponents();
        carregarChamados();
        CarregarOpensChamadosForMe();
        CarregarAttentedChamadosForMe();
    }

    private void CarregarAttentedChamadosForMe() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listaChamadosAtendidos = chamadoController.carregarChamadosAtendidosPorMim(idUser);
            DefaultTableModel model =  (DefaultTableModel) tabChamadosAttended.getModel();
            for (ChamadosTabelaDto chamado : listaChamadosAtendidos) {
                model.addRow(new Object[]{
                        chamado.getCodChamado(), chamado.getDataHoraChamado(), chamado.getTitulo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
        }
    }

    private void CarregarOpensChamadosForMe() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listaChamadosAbertos = chamadoController.carregarChamadosAbertosPorMim(idUser);
            DefaultTableModel model =  (DefaultTableModel) tabChamadosOpens.getModel();
            for (ChamadosTabelaDto chamado : listaChamadosAbertos) {
                model.addRow(new Object[]{
                        chamado.getCodChamado(), chamado.getDataHoraChamado(), chamado.getTitulo()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
        }
    }

    private void carregarChamados() {
        ChamadoController chamadoController = new ChamadoController();
        try {
            listaChamadosAll = chamadoController.carregarChamadosAbertos();
            DefaultTableModel model =  (DefaultTableModel) tabChamados.getModel();
            for (ChamadosTabelaDto chamado : listaChamadosAll) {
                model.addRow(new Object[]{
                        chamado.getCodChamado(), chamado.getDataHoraChamado(), chamado.getTitulo()
                });
            }
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            tabChamados.setRowSorter(sorter);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
        }
    }

    private void modalCadastrarChamado(MouseEvent e) {
        ChamadoView chamadoView = new ChamadoView(null, idUser, null);
        chamadoView.setVisible(true);
        chamadoView.setLocationRelativeTo(null);
        dispose();
    }

    private void abrirModalCadastroChamado(MouseEvent e) {
        // TODO add your code here
    }

    private void button1MouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void abrirCadastrarChamado(MouseEvent e) {
        // TODO add your code here
    }

    private void carregarTodosChamdos(MouseEvent e) {
        boolean selecionado = radioAllChamados.isSelected();
        ChamadoController chamadoController = new ChamadoController();
        if(selecionado){
            try {
                listaChamadosAll = chamadoController.carregarChamados();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
            }
        } else{
            try {
                listaChamadosAll = chamadoController.carregarChamadosAbertos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os chamados!");
            }
        }

        atualizaTabela(tabChamados, listaChamadosAll);
    }

    private void editDeleteChamado(MouseEvent e) {
        int btnClicked = e.getButton();
        if(btnClicked == 1) {
            int i = tabChamados.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAll.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUser, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if(btnClicked == 2) {
            int i = tabChamados.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAll.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUser);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if(btnClicked == 3){
            int i = tabChamados.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o chamado: " + listaChamadosAll.get(i).getTitulo() + " ?",
                    "Deseja Remover?", JOptionPane.YES_NO_OPTION);
            if(confirmacao == 0){
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean removeChamado = chamadoController.removerChamadoPorId(listaChamadosAll.get(i).getIdChamado());
                    if(removeChamado){
                        JOptionPane.showMessageDialog(this, "Chamado removido com sucesso!");
                        ChamadosTabelaDto chamadosTabelaDto = listaChamadosAll.get(i);
                        listaChamadosAll.remove(chamadosTabelaDto);
                        listaChamadosAbertos.remove(chamadosTabelaDto);
                        listaChamadosAtendidos.remove(chamadosTabelaDto);
                        atualizaTabela(tabChamados, listaChamadosAll);
                        atualizaTabela(tabChamadosOpens, listaChamadosAbertos);
                        atualizaTabela(tabChamadosAttended, listaChamadosAtendidos);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void atualizaTabela(JTable tabChamados, List<ChamadosTabelaDto> listaChamadosAll) {
        DefaultTableModel model = (DefaultTableModel) tabChamados.getModel();
        model.getDataVector().removeAllElements();
        for (ChamadosTabelaDto chamado : listaChamadosAll) {
            model.addRow(new Object[]{
                    chamado.getCodChamado(), chamado.getDataHoraChamado(), chamado.getTitulo()
            });
        }
        model.fireTableDataChanged();
    }

    private void aditDeleteChamadosAbertosPorMim(MouseEvent e) {
        int btnClicked = e.getButton();
        if(btnClicked == 1) {
            int i = tabChamadosOpens.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAbertos.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUser, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if(btnClicked == 2) {
            int i = tabChamadosOpens.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAbertos.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUser);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if(btnClicked == 3){
            int i = tabChamadosOpens.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o chamado: " + listaChamadosAbertos.get(i).getTitulo() + " ?",
                    "Deseja Remover?", JOptionPane.YES_NO_OPTION);
            if(confirmacao == 0){
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean removeChamado = chamadoController.removerChamadoPorId(listaChamadosAbertos.get(i).getIdChamado());
                    if(removeChamado){
                        JOptionPane.showMessageDialog(this, "Chamado removido com sucesso!");
                        ChamadosTabelaDto chamadosTabelaDto = listaChamadosAbertos.get(i);
                        listaChamadosAll.remove(chamadosTabelaDto);
                        listaChamadosAbertos.remove(chamadosTabelaDto);
                        listaChamadosAtendidos.remove(chamadosTabelaDto);
                        atualizaTabela(tabChamados, listaChamadosAll);
                        atualizaTabela(tabChamadosOpens, listaChamadosAbertos);
                        atualizaTabela(tabChamadosAttended, listaChamadosAtendidos);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void editDeleteChamadosAtendidosPorMim(MouseEvent e) {
        int btnClicked = e.getButton();
        if(btnClicked == 1) {
            int i = tabChamadosAttended.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAtendidos.get(i).getIdChamado();
            ChamadoView chamadoView = new ChamadoView(null, idUser, idChamado);
            chamadoView.setVisible(true);
            chamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if(btnClicked == 2) {
            int i = tabChamadosAttended.rowAtPoint(e.getPoint());
            Long idChamado = listaChamadosAtendidos.get(i).getIdChamado();
            AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUser);
            atendimentoChamadoView.setVisible(true);
            atendimentoChamadoView.setLocationRelativeTo(null);
            dispose();
        }

        if(btnClicked == 3){
            int i = tabChamadosAttended.rowAtPoint(e.getPoint());
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja apagar o chamado: " + listaChamadosAtendidos.get(i).getTitulo() + " ?",
                    "Deseja Remover?", JOptionPane.YES_NO_OPTION);
            if(confirmacao == 0){
                ChamadoController chamadoController = new ChamadoController();
                try {
                    boolean removeChamado = chamadoController.removerChamadoPorId(listaChamadosAtendidos.get(i).getIdChamado());
                    if(removeChamado){
                        JOptionPane.showMessageDialog(this, "Chamado removido com sucesso!");
                        ChamadosTabelaDto chamadosTabelaDto = listaChamadosAtendidos.get(i);
                        listaChamadosAll.remove(chamadosTabelaDto);
                        listaChamadosAbertos.remove(chamadosTabelaDto);
                        listaChamadosAtendidos.remove(chamadosTabelaDto);
                        atualizaTabela(tabChamados, listaChamadosAll);
                        atualizaTabela(tabChamadosOpens, listaChamadosAbertos);
                        atualizaTabela(tabChamadosAttended, listaChamadosAtendidos);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void gerarRelatorio(MouseEvent e) {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(f.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            String path = f.getSelectedFile().getPath();
            ChamadoController chamadoController = new ChamadoController();
            try{
                Document relatorio = new Document();
                PdfWriter.getInstance(relatorio, new FileOutputStream(path+"\\relatorio.pdf"));
                relatorio.open();
                Font font = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dataHoje = simpleDateFormat.format(new Date());
                Chunk titulo = new Chunk("Relatório - "+dataHoje, font);
                Chunk infos = new Chunk("\nCÓDIGO       DATA DE ABERTURA        TÍTULO\n");

                int rowCount = tabChamados.getModel().getRowCount();
                int columnCount = tabChamados.getModel().getColumnCount();
                List<Integer> listaIndices = new ArrayList<>();
                for (int i = 0; i < rowCount; i++){
                    listaIndices.add(tabChamados.getRowSorter().convertRowIndexToView(i));
                }
                for (Integer i :listaIndices){
                    String chamadoInfo = "";
                    for (int k = 0; k < columnCount; k++){
                        String valueAt = (String) tabChamados.getModel().getValueAt(i, k);
                        chamadoInfo += valueAt +"              ";
                    }
                    chamadoInfo += "\n";
                    infos.append(chamadoInfo);
                }

                Paragraph p1 = new Paragraph(titulo);
                Paragraph p2 = new Paragraph(infos);
                relatorio.add(p1);
                relatorio.add(p2);
                relatorio.close();

                JOptionPane.showMessageDialog(null, "Relatório gerado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erro ao gerar o relatório! " + ex);
            }
        }
    }

    private void cadastrarUsuarioModal(MouseEvent e) {
        CadastrarUsuarioView cadastrarUsuarioView = new CadastrarUsuarioView(idUser, null);
        cadastrarUsuarioView.setVisible(true);
        cadastrarUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void alterarSenha(MouseEvent e) {
        CadastrarUsuarioView cadastrarUsuarioView = new CadastrarUsuarioView(idUser, idUser);
        cadastrarUsuarioView.setVisible(true);
        cadastrarUsuarioView.setLocationRelativeTo(null);
        dispose();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        content = new JPanel();
        panel2 = new JPanel();
        radioAllChamados = new JRadioButton();
        hSpacer1 = new JPanel(null);
        button2 = new JButton();
        cadastrarUsuario = new JButton();
        gerarRelatorio = new JButton();
        button1 = new JButton();
        panel1 = new JPanel();
        scrollPane1 = new JScrollPane();
        tabChamados = new JTable();
        desktopPane1 = new JDesktopPane();
        frameChamadosAbetos = new JInternalFrame();
        scrollPane2 = new JScrollPane();
        tabChamadosOpens = new JTable();
        desktopPane2 = new JDesktopPane();
        internalFrame2 = new JInternalFrame();
        scrollPane3 = new JScrollPane();
        tabChamadosAttended = new JTable();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(1, 1));

        //======== content ========
        {
            content.setLayout(new BorderLayout());

            //======== panel2 ========
            {
                panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

                //---- radioAllChamados ----
                radioAllChamados.setText("Todos chamados");
                radioAllChamados.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        carregarTodosChamdos(e);
                    }
                });
                panel2.add(radioAllChamados);
                panel2.add(hSpacer1);

                //---- button2 ----
                button2.setText("ALTERAR SENHA");
                button2.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        alterarSenha(e);
                    }
                });
                panel2.add(button2);

                //---- cadastrarUsuario ----
                cadastrarUsuario.setText("CADASTRAR USU\u00c1RIO");
                cadastrarUsuario.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastrarUsuarioModal(e);
                    }
                });
                panel2.add(cadastrarUsuario);

                //---- gerarRelatorio ----
                gerarRelatorio.setText("GERAR RELAT\u00d3RIO");
                gerarRelatorio.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        gerarRelatorio(e);
                    }
                });
                panel2.add(gerarRelatorio);

                //---- button1 ----
                button1.setText("CADASTRAR CHAMADO");
                button1.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        modalCadastrarChamado(e);
                        button1MouseClicked(e);
                        abrirCadastrarChamado(e);
                        abrirModalCadastroChamado(e);
                    }
                });
                panel2.add(button1);
            }
            content.add(panel2, BorderLayout.SOUTH);

            //======== panel1 ========
            {
                panel1.setLayout(new GridBagLayout());
                ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

                //======== scrollPane1 ========
                {

                    //---- tabChamados ----
                    tabChamados.setModel(new DefaultTableModel(
                        new Object[][] {
                        },
                        new String[] {
                            "C\u00d3DIGO", "DATA DE ABERTURA", "T\u00cdTULO"
                        }
                    ) {
                        boolean[] columnEditable = new boolean[] {
                            false, false, false
                        };
                        @Override
                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                            return columnEditable[columnIndex];
                        }
                    });
                    tabChamados.setEnabled(false);
                    tabChamados.setColumnSelectionAllowed(true);
                    tabChamados.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            editDeleteChamado(e);
                        }
                    });
                    scrollPane1.setViewportView(tabChamados);
                }
                panel1.add(scrollPane1, new GridBagConstraints(0, 0, 1, 2, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //======== desktopPane1 ========
                {

                    //======== frameChamadosAbetos ========
                    {
                        frameChamadosAbetos.setVisible(true);
                        frameChamadosAbetos.setIconifiable(true);
                        frameChamadosAbetos.setResizable(true);
                        frameChamadosAbetos.setTitle("Chamados Abertos Por Mim");
                        frameChamadosAbetos.setMaximizable(true);
                        frameChamadosAbetos.setClosable(true);
                        var frameChamadosAbetosContentPane = frameChamadosAbetos.getContentPane();
                        frameChamadosAbetosContentPane.setLayout(new GridBagLayout());
                        ((GridBagLayout)frameChamadosAbetosContentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)frameChamadosAbetosContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                        ((GridBagLayout)frameChamadosAbetosContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                        ((GridBagLayout)frameChamadosAbetosContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

                        //======== scrollPane2 ========
                        {

                            //---- tabChamadosOpens ----
                            tabChamadosOpens.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "C\u00d3DIGO", "DATA ABERTURA", "T\u00cdTULO"
                                }
                            ) {
                                boolean[] columnEditable = new boolean[] {
                                    false, true, true
                                };
                                @Override
                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return columnEditable[columnIndex];
                                }
                            });
                            tabChamadosOpens.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    aditDeleteChamadosAbertosPorMim(e);
                                }
                            });
                            scrollPane2.setViewportView(tabChamadosOpens);
                        }
                        frameChamadosAbetosContentPane.add(scrollPane2, new GridBagConstraints(0, 0, 2, 3, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    desktopPane1.add(frameChamadosAbetos, JLayeredPane.DEFAULT_LAYER);
                    frameChamadosAbetos.setBounds(0, 0, 370, 210);
                }
                panel1.add(desktopPane1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== desktopPane2 ========
                {

                    //======== internalFrame2 ========
                    {
                        internalFrame2.setVisible(true);
                        internalFrame2.setTitle("Chamados Atendidos Por Mim");
                        internalFrame2.setMaximizable(true);
                        internalFrame2.setIconifiable(true);
                        internalFrame2.setResizable(true);
                        internalFrame2.setClosable(true);
                        var internalFrame2ContentPane = internalFrame2.getContentPane();
                        internalFrame2ContentPane.setLayout(new GridBagLayout());
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                        ((GridBagLayout)internalFrame2ContentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

                        //======== scrollPane3 ========
                        {

                            //---- tabChamadosAttended ----
                            tabChamadosAttended.setModel(new DefaultTableModel(
                                new Object[][] {
                                },
                                new String[] {
                                    "C\u00d3DIGO", "DATA ABERTURA", "T\u00cdTULO"
                                }
                            ) {
                                boolean[] columnEditable = new boolean[] {
                                    true, true, false
                                };
                                @Override
                                public boolean isCellEditable(int rowIndex, int columnIndex) {
                                    return columnEditable[columnIndex];
                                }
                            });
                            tabChamadosAttended.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    editDeleteChamadosAtendidosPorMim(e);
                                }
                            });
                            scrollPane3.setViewportView(tabChamadosAttended);
                        }
                        internalFrame2ContentPane.add(scrollPane3, new GridBagConstraints(0, 0, 2, 3, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    desktopPane2.add(internalFrame2, JLayeredPane.DEFAULT_LAYER);
                    internalFrame2.setBounds(0, 0, 370, 210);
                }
                panel1.add(desktopPane2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            content.add(panel1, BorderLayout.CENTER);
        }
        contentPane.add(content);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel content;
    private JPanel panel2;
    private JRadioButton radioAllChamados;
    private JPanel hSpacer1;
    private JButton button2;
    private JButton cadastrarUsuario;
    private JButton gerarRelatorio;
    private JButton button1;
    private JPanel panel1;
    private JScrollPane scrollPane1;
    private JTable tabChamados;
    private JDesktopPane desktopPane1;
    private JInternalFrame frameChamadosAbetos;
    private JScrollPane scrollPane2;
    private JTable tabChamadosOpens;
    private JDesktopPane desktopPane2;
    private JInternalFrame internalFrame2;
    private JScrollPane scrollPane3;
    private JTable tabChamadosAttended;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
