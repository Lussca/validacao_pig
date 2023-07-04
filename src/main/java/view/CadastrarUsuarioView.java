/*
 * Created by JFormDesigner on Tue Jul 04 00:13:31 BRT 2023
 */

package view;

import controller.UserController;
import model.dto.UserEditDto;
import util.HashPasswordUtils;

import java.awt.*;
import java.awt.event.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author 09luc
 */
public class CadastrarUsuarioView extends JFrame {

    private Long idUser;

    private Long idUserEdit;

    private void cancelarCadastro(MouseEvent e) {
        IndexView mainView = new IndexView(idUser);
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
        dispose();
    }

    CadastrarUsuarioView(Long idUser, Long idUserEdit) {
        this.idUser = idUser;
        this.idUserEdit = idUserEdit;
        initComponents();
        if (idUserEdit != null){
            UserController userController = new UserController();
            UserEditDto user = null;
            try {
                user = userController.carregarUserPorId(idUserEdit);
                textCodUsuario.setText(user.getCodUser());
                textCodUsuario.setEditable(false);
                textUsuario.setText(user.getLogin());
                textUsuario.setEditable(false);
                textEmail.setText(user.getEmail());
                textEmail.setEditable(false);
                textNome.setText(user.getNome());
                textNome.setEditable(false);
                okButton.setText("ATUALIZAR SENHA");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar usuário!");
            }
        }
    }

    private void cadastrarUsuario(MouseEvent e) {
        if(idUserEdit != null){
            String erros = "";

            if(Arrays.toString(fieldPassword.getPassword()).isBlank()){
                erros += "Informe uma senha para o cadastro!\n";
            }
            if(Arrays.toString(fieldPassword.getPassword()).length() > 50){
                erros += "Senha muito grande!\n";
            }
            if(!erros.isBlank()){
                JOptionPane.showMessageDialog(null, erros);
            } else{
                try{
                    UserController userController = new UserController();
                    String hashSenha = HashPasswordUtils.transformationToHash(Arrays.toString(fieldPassword.getPassword()));
                    boolean atualizou = userController.atualizarSenha(idUserEdit, hashSenha);
                    if (atualizou){
                        JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!");
                        IndexView mainView = new IndexView(idUser);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    }
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar a senha!");
                }
            }
        } else{
            String erros = "";

            if(textCodUsuario.getText().isBlank()){
                erros += "Informe um código de usuário para o cadastro!\n";
            }
            if(textCodUsuario.getText().length() > 6){
                erros += "Código de usuário muito grande!\n";
            }
            if(textUsuario.getText().isBlank()){
                erros += "Informe um usuário para o cadastro!\n";
            }
            if(textUsuario.getText().length() > 50){
                erros += "Usuário muito grande!\n";
            }
            if(Arrays.toString(fieldPassword.getPassword()).isBlank()){
                erros += "Informe uma senha para o cadastro!\n";
            }
            if(Arrays.toString(fieldPassword.getPassword()).length() > 50){
                erros += "Senha muito grande!\n";
            }
            if(textNome.getText().isBlank()){
                erros += "Informe um nome para o cadastro!\n";
            }
            if(textUsuario.getText().length() > 57){
                erros += "Nome muito grande!\n";
            }
            if(textEmail.getText().isBlank()){
                erros += "Informe um email para o cadastro!\n";
            }
            if(!validaEmail(textEmail.getText())){
                erros += "Informe um email válido para o cadastro!\n";
            }
            if (!erros.isBlank()){
                JOptionPane.showMessageDialog(null, erros);
            } else{
                try{
                    UserController userController = new UserController();
                    String hashSenha = HashPasswordUtils.transformationToHash(Arrays.toString(fieldPassword.getPassword()));
                    String codUser = textCodUsuario.getText();
                    String user = textUsuario.getText();
                    String email = textEmail.getText();
                    String name = textNome.getText();
                    boolean cadastrou = userController.cadastarUsuario(codUser, user, hashSenha, email, name);
                    if (cadastrou) {
                        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
                        IndexView mainView = new IndexView(idUser);
                        mainView.setVisible(true);
                        mainView.setLocationRelativeTo(null);
                        dispose();
                    }
                } catch (Exception ex) {
                    System.out.println("Erro: " + ex.getMessage());
                }
            }
        }
    }

    public static boolean validaEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        textCodUsuario = new JTextField();
        label2 = new JLabel();
        textUsuario = new JTextField();
        label3 = new JLabel();
        fieldPassword = new JPasswordField();
        label4 = new JLabel();
        textEmail = new JTextField();
        label5 = new JLabel();
        textNome = new JTextField();
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
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

                //---- label1 ----
                label1.setText("C\u00d3DIGO USU\u00c1RIO");
                contentPanel.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textCodUsuario, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));

                //---- label2 ----
                label2.setText("USU\u00c1RIO");
                contentPanel.add(label2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textUsuario, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label3 ----
                label3.setText("SENHA");
                contentPanel.add(label3, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(fieldPassword, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label4 ----
                label4.setText("E-MAIL");
                contentPanel.add(label4, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 5), 0, 0));
                contentPanel.add(textEmail, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 5, 0), 0, 0));

                //---- label5 ----
                label5.setText("NOME");
                contentPanel.add(label5, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));
                contentPanel.add(textNome, new GridBagConstraints(1, 6, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

                //---- okButton ----
                okButton.setText("CADASTRAR");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cadastrarUsuario(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- cancelButton ----
                cancelButton.setText("CANCELAR");
                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelarCadastro(e);
                    }
                });
                buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
    private JTextField textCodUsuario;
    private JLabel label2;
    private JTextField textUsuario;
    private JLabel label3;
    private JPasswordField fieldPassword;
    private JLabel label4;
    private JTextField textEmail;
    private JLabel label5;
    private JTextField textNome;
    private JPanel buttonBar;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
