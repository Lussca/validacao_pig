/*
 * Created by JFormDesigner on Sun Jul 02 01:33:23 BRT 2023
 */

package view;

import controller.ChamadoController;
import model.dto.ChamadoDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class AtendimentoChamadoView extends JFrame {

    private Long idChamado;
    private Long idUsuario;
    public AtendimentoChamadoView(Long idChamado, Long idUsuario) {
        this.idChamado = idChamado;
        this.idUsuario = idUsuario;
        initComponents();
        carregarChamado();
    }

    private void carregarChamado() {
        ChamadoController chamadoController = new ChamadoController();
        try{
            ChamadoDto chamado = chamadoController.carregarChamadosAtendimentoPorId(idChamado);
            textCodigo.setText(chamado.getCodChamado());
            textTitulo.setText(chamado.getTitulo());
            textUsuarioReq.setText(chamado.getUsuarioRequisitante());
            textAbertura.setText(chamado.getDataHoraAbertura());
            textDescricao.setText(chamado.getDescricao());
            if (chamado.getIdUsuarioAtendente() != 0) {
                textFechamento.setVisible(true);
                scrollSolucao.setVisible(true);
                labelFechamento.setVisible(true);
                labelSolucao.setVisible(true);
                okButton.setText("SOLUCIONAR");
                if(!chamado.getDataHoraFechamento().isBlank()) {
                    textSolucao.setEditable(false);
                    okButton.setEnabled(false);
                    textSolucao.setText(chamado.getSolucao());
                    labelFechamento.setVisible(true);
                    textFechamento.setVisible(true);
                    textFechamento.setText(chamado.getDataHoraFechamento());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void returnInicial(MouseEvent e) {
        IndexView mainView = new IndexView(idUsuario);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void atenderChamado(MouseEvent e) {
        if(okButton.getText().equals("SOLUCIONAR")) {
            if(textSolucao.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Insira uma descrição para a solução.");
            } else{
                ChamadoController chamadoController = new ChamadoController();
                try{
                    boolean resolvido = chamadoController.resolverChamado(idChamado, textSolucao.getText());
                    if (resolvido){
                        JOptionPane.showMessageDialog(this, "Chamado resolvido!");
                        AtendimentoChamadoView atendimentoChamadoView = new AtendimentoChamadoView(idChamado, idUsuario);
                        atendimentoChamadoView.setVisible(true);
                        atendimentoChamadoView.setLocationRelativeTo(null);
                        dispose();
                    }
                } catch (SQLException ex){
                    JOptionPane.showMessageDialog(this, "Problema ao resolver o chamado.");
                }
            }
        } else {
            ChamadoController chamadoController = new ChamadoController();
            try {
                boolean recebido = chamadoController.receberChamado(idChamado, idUsuario);
                if(recebido) {
                    textFechamento.setVisible(true);
                    scrollSolucao.setVisible(true);
                    labelFechamento.setVisible(true);
                    labelSolucao.setVisible(true);
                    okButton.setText("SOLUCIONAR");
                    textSolucao.setEditable(true);
                    textSolucao.setEnabled(true);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        textCodigo = new JTextField();
        hSpacer1 = new JPanel(null);
        textUsuarioReq = new JTextField();
        label3 = new JLabel();
        textTitulo = new JTextField();
        label4 = new JLabel();
        scrollPane1 = new JScrollPane();
        textDescricao = new JTextArea();
        label5 = new JLabel();
        textAbertura = new JTextField();
        labelSolucao = new JLabel();
        scrollSolucao = new JScrollPane();
        textSolucao = new JTextArea();
        labelFechamento = new JLabel();
        textFechamento = new JTextField();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 57, 0, 0, 60, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00d3DIGO DO CHAMADO");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("USU\u00c1RIO REQUISITANTE");
                contentPanel.add(label2, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textCodigo ----
                textCodigo.setEditable(false);
                contentPanel.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(hSpacer1, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textUsuarioReq ----
                textUsuarioReq.setEditable(false);
                contentPanel.add(textUsuarioReq, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label3 ----
                label3.setText("T\u00cdTULO");
                label3.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label3, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textTitulo ----
                textTitulo.setEditable(false);
                contentPanel.add(textTitulo, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label4 ----
                label4.setText("DESCRI\u00c7\u00c3O DO CHAMADO");
                label4.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollPane1 ========
                {

                    //---- textDescricao ----
                    textDescricao.setEditable(false);
                    scrollPane1.setViewportView(textDescricao);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label5 ----
                label5.setText("DATA DE ABERTURA");
                label5.setHorizontalAlignment(SwingConstants.RIGHT);
                contentPanel.add(label5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- textAbertura ----
                textAbertura.setEditable(false);
                contentPanel.add(textAbertura, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- labelSolucao ----
                labelSolucao.setText("DESCRI\u00c7\u00c3O DA SOLU\u00c7\u00c3O");
                labelSolucao.setHorizontalAlignment(SwingConstants.RIGHT);
                labelSolucao.setVisible(false);
                contentPanel.add(labelSolucao, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //======== scrollSolucao ========
                {
                    scrollSolucao.setVisible(false);
                    scrollSolucao.setViewportView(textSolucao);
                }
                contentPanel.add(scrollSolucao, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- labelFechamento ----
                labelFechamento.setText("DATA DE FECHAMENTO");
                labelFechamento.setEnabled(false);
                labelFechamento.setVisible(false);
                contentPanel.add(labelFechamento, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- textFechamento ----
                textFechamento.setEnabled(false);
                textFechamento.setVisible(false);
                contentPanel.add(textFechamento, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("ATENDER");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        atenderChamado(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("CANCELAR");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        returnInicial(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(1, 0, 3, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label1;
    private JLabel label2;
    private JTextField textCodigo;
    private JPanel hSpacer1;
    private JTextField textUsuarioReq;
    private JLabel label3;
    private JTextField textTitulo;
    private JLabel label4;
    private JScrollPane scrollPane1;
    private JTextArea textDescricao;
    private JLabel label5;
    private JTextField textAbertura;
    private JLabel labelSolucao;
    private JScrollPane scrollSolucao;
    private JTextArea textSolucao;
    private JLabel labelFechamento;
    private JTextField textFechamento;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
