/*
 * Created by JFormDesigner on Sat Jul 01 16:33:19 BRT 2023
 */

package view;

import controller.ChamadoController;
import model.dto.ChamadoEditarDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ChamadoView extends JDialog {

    private Long idUser;
    private Long idChamado;
    public ChamadoView(Window owner, Long idUser, Long idChamado) {
        super(owner);
        this.idUser = idUser;
        this.idChamado = idChamado;
        initComponents();
        if(idChamado != null){
            okButton.setText("EDITAR");
            ChamadoController chamadoController = new ChamadoController();
            try{
                ChamadoEditarDto chamado = chamadoController.buscarChamadoPorId(idChamado);
                textCodigo.setText(chamado.getCodChamado());
                textTitel.setText(chamado.getTitulo());
                textDescription.setText(chamado.getDescricao());
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    private void fecharView(MouseEvent e) {
        IndexView mainView = new IndexView(idUser);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    private void cadastrarNovoChamado(MouseEvent e) {
        String erros = "";

        if(textTitel.getText().isBlank()){
            erros = "Por favor, insira um título.\n";
        }

        if(textTitel.getText().length() > 45){
            erros = "Por favor, insira um título menor.\n";
        }

        if(textDescription.getText().isBlank()){
            erros = "Por favor, insira uma descrição.\n";
        }

        if(textDescription.getText().length() > 100){
            erros = "Por favor, insira uma descrição.\n";
        }

        if(!erros.isBlank()){
            JOptionPane.showMessageDialog(this, erros);
        } else{
            if(idChamado == null){
                try {
                    ChamadoController chamadoController = new ChamadoController();
                    boolean sucessCadastro = chamadoController.cadastrarChamado(idUser, textTitel.getText(), textDescription.getText());
                    if (sucessCadastro){
                        JOptionPane.showMessageDialog(this, "Chamado cadastrado com sucesso!");
                        IndexView mainView = new IndexView(idUser);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro no cadastro do chamado!");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            } else{
                try {
                    ChamadoController chamadoController = new ChamadoController();
                    boolean updateCadastro = chamadoController.atualizarChamado(idUser, textTitel.getText(), textDescription.getText());
                    if (updateCadastro){
                        JOptionPane.showMessageDialog(this, "Chamado atualizado com sucesso!");
                        IndexView mainView = new IndexView(idUser);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro na atualização do chamado!");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        buttonBar = new JPanel();
        okButton = new JButton();
        cancelButton = new JButton();
        panel2 = new JPanel();
        label1 = new JLabel();
        textCodigo = new JTextField();
        titulo = new JLabel();
        textTitel = new JTextField();
        descricao = new JLabel();
        scrollPane1 = new JScrollPane();
        textDescription = new JTextArea();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
            dialogPane.setLayout(new BorderLayout());

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new BoxLayout(buttonBar, BoxLayout.X_AXIS));

                //---- okButton ----
                okButton.setText("CADASTRAR");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastrarNovoChamado(e);
                    }
                });
                buttonBar.add(okButton);

                //---- cancelButton ----
                cancelButton.setText("CANCELAR");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        fecharView(e);
                    }
                });
                buttonBar.add(cancelButton);
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);

            //======== panel2 ========
            {
                panel2.setLayout(new GridBagLayout());
                ((GridBagLayout)panel2.getLayout()).columnWidths = new int[] {0, 0};
                ((GridBagLayout)panel2.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 170, 0};
                ((GridBagLayout)panel2.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                ((GridBagLayout)panel2.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00d3DIGO DO CHAMADO");
                panel2.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- textCodigo ----
                textCodigo.setEditable(false);
                panel2.add(textCodigo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- titulo ----
                titulo.setText("T\u00cdTULO");
                panel2.add(titulo, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));
                panel2.add(textTitel, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- descricao ----
                descricao.setText("DESCRI\u00c7\u00c3O");
                panel2.add(descricao, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //======== scrollPane1 ========
                {
                    scrollPane1.setViewportView(textDescription);
                }
                panel2.add(scrollPane1, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(panel2, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel panel2;
    private JLabel label1;
    private JTextField textCodigo;
    private JLabel titulo;
    private JTextField textTitel;
    private JLabel descricao;
    private JScrollPane scrollPane1;
    private JTextArea textDescription;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
