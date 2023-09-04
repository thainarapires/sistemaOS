package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;

public class Login extends JFrame {
	// objetos JDBC
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// objeto tela principal
	Principal principal = new Principal();

	private JPanel contentPane;
	private JPasswordField txtSenha;
	private JTextField txtLogin;
	private JLabel lblStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/entrar.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				// ativação da janela
				status();

			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login");
		setResizable(false);
		setBounds(100, 100, 383, 283);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 18));
		lblLogin.setBounds(27, 39, 73, 22);
		contentPane.add(lblLogin);

		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 18));
		lblSenha.setBounds(27, 105, 73, 22);
		contentPane.add(lblSenha);

		txtSenha = new JPasswordField();
		txtSenha.setBounds(97, 101, 222, 26);
		contentPane.add(txtSenha);

		JButton btnAcessar = new JButton("");
		btnAcessar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcessar.setBorderPainted(false);
		btnAcessar.setContentAreaFilled(false);
		btnAcessar.setIcon(new ImageIcon(Login.class.getResource("/img/entrar.png")));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});

		btnAcessar.setBounds(159, 151, 48, 48);
		contentPane.add(btnAcessar);

		getRootPane().setDefaultButton(btnAcessar);

		txtLogin = new JTextField();
		txtLogin.setBounds(97, 38, 222, 25);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dataoff.png")));
		lblStatus.setBounds(309, 185, 48, 48);
		contentPane.add(lblStatus);
	}// fim do construtor

	private void logar() {
		// Criar váriavel/objeto para capturar a senha
		String capturarSenha = new String(txtSenha.getPassword());
		// validação
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturarSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {
			// logica principal 
			
			String read = "select * from usuarios where login =? and senha=md5(?)";
			try {
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturarSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					//capturar perfil do usuario :D
					//System.out.println(rs.getString(5));
					//tratamento do perfil
					String perfil = rs.getString(5);
					if(perfil.equals("admin")) {
					principal.setVisible(true);
					// ele vai alterar o nome de usuario para a string 2 do banco de dados, que é o
					// nome de usuario
					principal.lblNomeUsuario.setText(rs.getString(2));
					//habilitar os botoes
					principal.btnRelatorios.setEnabled(true);
					principal.btnUsuarios.setEnabled(true);
					principal.panelRodape.setBackground(Color.RED);
					
					// fechar atela de login após acessar
					this.dispose();
				} else {
					
					principal.setVisible(true);
					// ele vai alterar o nome de usuario para a string 2 do banco de dados, que é o
					// nome de usuario
					principal.lblNomeUsuario.setText(rs.getString(2));
					// fechar atela de login após acessar
					this.dispose();
				}
				
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dataoff.png")));
			} else {
				// System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dataon.png")));
			}
			// Nunca esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}
}
