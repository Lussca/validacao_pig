/*
 * Created by JFormDesigner on Sun Jun 25 00:44:29 BRT 2023
 */

package view;

import controller.UserController;
import util.HashPasswordUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class LoginView extends JFrame {
    public LoginView() {
        initComponents();
    }

    public static void initialize () {
        LoginView mainView = new LoginView();
        mainView.setVisible(true);
        mainView.setLocationRelativeTo(null);
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        this.dispose();
    }

    private void okButtonMouseClicked(MouseEvent e) {
        boolean erro = false;

        //erro do usuário
        if(textUser.getText().isBlank()){
            messageUser.setText("Por favor informe um usuário válido!");
            erro = true;
        }

        //erro da senha
        if(textPassword.getPassword().length == 0){
            messageUser.setText("Por favor insira a senha correta!");
            erro = true;
        }

        if(!erro){
            try {
                String password = Arrays.toString(textPassword.getPassword());
                String hash = HashPasswordUtils.transformationToHash(password);
                String login = textUser.getText();
                UserController userController = new UserController();
                Long idUser = userController.logar(login, hash);
                if (idUser != null){
                    IndexView mainView = new IndexView(idUser);
                    mainView.setVisible(true);
                    mainView.setLocationRelativeTo(null);
                    dispose();
                } else {
                    messageUser.setText("Usuário/Senha incorretos!");
                    messagePassword.setText("");
                }
            }catch (Exception ex){
                System.out.println("Erro ao tentar acessar o sistema!");
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        buttonBar = new JPanel();
        label1 = new JLabel();
        hSpacer1 = new JPanel(null);
        textUser = new JTextField();
        label2 = new JLabel();
        textPassword = new JPasswordField();
        messagePassword = new JLabel();
        messageUser = new JLabel();
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
                contentPanel.setLayout(new GridLayout(1, 1));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
        }
        contentPane.add(dialogPane, BorderLayout.NORTH);

        //======== buttonBar ========
        {
            buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
            buttonBar.setLayout(new GridBagLayout());
            ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 0, 85, 85, 0, 0};
            ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 0.0, 0.0};

            //---- label1 ----
            label1.setText("USU\u00c1RIO");
            buttonBar.add(label1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            buttonBar.add(hSpacer1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            buttonBar.add(textUser, new GridBagConstraints(1, 1, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- label2 ----
            label2.setText("SENHA");
            buttonBar.add(label2, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            buttonBar.add(textPassword, new GridBagConstraints(1, 3, 4, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            buttonBar.add(messagePassword, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));
            buttonBar.add(messageUser, new GridBagConstraints(2, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

            //---- okButton ----
            okButton.setText("CONECTAR");
            okButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    okButtonMouseClicked(e);
                }
            });
            buttonBar.add(okButton, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

            //---- cancelButton ----
            cancelButton.setText("SAIR");
            cancelButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    cancelButtonMouseClicked(e);
                }
            });
            buttonBar.add(cancelButton, new GridBagConstraints(3, 7, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));
        }
        contentPane.add(buttonBar, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JPanel buttonBar;
    private JLabel label1;
    private JPanel hSpacer1;
    private JTextField textUser;
    private JLabel label2;
    private JPasswordField textPassword;
    private JLabel messagePassword;
    private JLabel messageUser;
    private JButton okButton;
    private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
