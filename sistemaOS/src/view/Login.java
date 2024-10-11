package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import model.DAO;

public class Login extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private JPanel contentPane;
	private JPasswordField txtSenha;
	private JTextField txtLogin;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/Logo.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				status();

			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login");
		setResizable(false);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 5, 0));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 429, 824);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Login.class.getResource("/img/logo (1).png")));
		lblLogo.setBounds(91, 243, 258, 231);
		panel.add(lblLogo);
		
		JLabel lblGradiente = new JLabel("New label");
		lblGradiente.setBounds(-31, -16, 473, 934);
		panel.add(lblGradiente);
		lblGradiente.setIcon(new ImageIcon(Login.class.getResource("/img/sois.png")));

		JLabel lblLogin = new JLabel("LOGIN:");
		lblLogin.setBounds(509, 194, 417, 32);
		contentPane.add(lblLogin);
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 28));

		txtLogin = new JTextField();
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 26));
		txtLogin.setToolTipText("Coloque aqui o login que você cadastrou");
		txtLogin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtLogin.setBounds(509, 237, 417, 59);
		contentPane.add(txtLogin);
		txtLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtLogin.getText().equals("username")) {
					txtLogin.setText(null);
				}
			}
		});
		txtLogin.setForeground(Color.BLACK);
		txtLogin.setBackground(Color.WHITE);
		txtLogin.setColumns(10);

		txtSenha = new JPasswordField();
		txtSenha.setFont(new Font("Arial", Font.PLAIN, 26));
		txtSenha.setToolTipText("Coloque sua senha do login");
		txtSenha.setBounds(509, 389, 417, 59);
		contentPane.add(txtSenha);
		txtSenha.setBackground(Color.WHITE);
		txtSenha.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));

		JLabel lblSenha = new JLabel("SENHA: ");
		lblSenha.setBounds(509, 346, 417, 32);
		contentPane.add(lblSenha);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 28));

		btnAcessar = new JButton("LOGAR");
		btnAcessar.setFont(new Font("Arial", Font.BOLD, 30));
		btnAcessar.setFocusPainted(false);
		btnAcessar.setToolTipText("Login");
		btnAcessar.setForeground(Color.WHITE);
		btnAcessar.setBackground(new Color(30, 144, 255));
		btnAcessar.setBounds(509, 513, 417, 95);
		contentPane.add(btnAcessar);
		btnAcessar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcessar.setIcon(null);
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});

		getRootPane().setDefaultButton(btnAcessar);

		JLabel lblNewLabel_1 = new JLabel("FAÇA SEU LOGIN");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 38));
		lblNewLabel_1.setBounds(509, 50, 417, 79);
		contentPane.add(lblNewLabel_1);

	}

	/**
	 * Metodo responsavel por: fazer o login.
	 */
	Principal principal = new Principal();
	Usuarios usuarios = new Usuarios();
	private JButton btnAcessar;

	private void logar() {

		String capturarSenha = new String(txtSenha.getPassword());

		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário!");
			txtLogin.requestFocus();
		} else if (capturarSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a Senha do Usuário!");
			txtSenha.requestFocus();
		} else {
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				pst.setString(2, capturarSenha);
				rs = pst.executeQuery();
				if (rs.next()) {

					String perfil = rs.getString(5);
					if (perfil.equals("Admin")) {
						principal.setVisible(true);
						principal.lblNomeUsuario.setText(rs.getString(2).toUpperCase());
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						principal.lblNomeUsuario.setForeground(Color.YELLOW);
						principal.lblNomeUsuario.setFont(new Font("Arial", Font.BOLD, 20));
						principal.lblNivelAcesso.setForeground(Color.YELLOW);
						principal.lblNivelAcesso.setFont(new Font("Arial", Font.BOLD, 20));
						principal.lblNivelAcesso.setText(rs.getString(5).toUpperCase());
						principal.lblWelcome.setText(rs.getString(2).toUpperCase());
						

						this.dispose();
					} else {

						principal.setVisible(true);
						// ele vai alterar o nome de usuario para a string 2 do banco de dados, que é o
						// nome de usuario
						principal.lblNomeUsuario.setText(rs.getString(2).toUpperCase());
						principal.lblNomeUsuario.setFont(new Font("Arial", Font.BOLD, 20));
						principal.lblNivelAcesso.setText(rs.getString(5).toUpperCase());
						principal.lblNivelAcesso.setFont(new Font("Arial", Font.BOLD, 20));
						principal.lblWelcome.setText(rs.getString(2).toUpperCase());

						// fechar atela de login após acessar
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha inválidos");
					txtLogin.setText(null);
					txtSenha.setText(null);
					txtLogin.requestFocus(true);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Metodo responsavel por: mostrar o status do banco de dados.
	 */
	private void status() {
		try {

			con = dao.conectar();
			if (con == null) {

				btnAcessar.setBackground(Color.red);
				btnAcessar.setText("OFFLINE");
				
				
			} else {

			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}
}
