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
		setBounds(100, 100, 809, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(0, 0, 5, 0));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 343, 561);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Login.class.getResource("/img/logo (1).png")));
		lblLogo.setBounds(40, 170, 258, 231);
		panel.add(lblLogo);
		
		JLabel lblGradiente = new JLabel("New label");
		lblGradiente.setBounds(-32, -17, 473, 578);
		panel.add(lblGradiente);
		lblGradiente.setIcon(new ImageIcon(Login.class.getResource("/img/sois.png")));

		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(424, 213, 73, 22);
		contentPane.add(lblLogin);
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 18));

		txtLogin = new JTextField();
		txtLogin.setToolTipText("Coloque aqui o login que você cadastrou");
		txtLogin.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtLogin.setBounds(424, 239, 310, 32);
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
		txtSenha.setToolTipText("Coloque sua senha do login");
		txtSenha.setBounds(424, 311, 310, 32);
		contentPane.add(txtSenha);
		txtSenha.setBackground(Color.WHITE);
		txtSenha.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));

		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setBounds(424, 282, 73, 22);
		contentPane.add(lblSenha);
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 18));

		JButton btnAcessar = new JButton("Login");
		btnAcessar.setFocusPainted(false);
		btnAcessar.setToolTipText("Login");
		btnAcessar.setBorder(new LineBorder(new Color(0, 120, 215), 5, true));
		btnAcessar.setForeground(Color.WHITE);
		btnAcessar.setBackground(new Color(30, 144, 255));
		btnAcessar.setBounds(505, 371, 161, 32);
		contentPane.add(btnAcessar);
		btnAcessar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAcessar.setIcon(null);
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});

		getRootPane().setDefaultButton(btnAcessar);

		JLabel lblNewLabel_1 = new JLabel("Faça seu login");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 26));
		lblNewLabel_1.setBounds(424, 104, 310, 39);
		contentPane.add(lblNewLabel_1);

		lblStatus = new JLabel("");
		lblStatus.setToolTipText("Conexão com o banco");
		lblStatus.setBounds(757, 535, 24, 24);
		contentPane.add(lblStatus);
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/dataoffline.png")));

	}

	/**
	 * Metodo responsavel por: fazer o login.
	 */
	Principal principal = new Principal();
	Usuarios usuarios = new Usuarios();

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
					if (perfil.equals("admin")) {
						principal.setVisible(true);
						principal.lblNomeUsuario.setText(rs.getString(2).toUpperCase());
						principal.btnRelatorios.setEnabled(true);
						principal.btnUsuarios.setEnabled(true);
						principal.lblNomeUsuario.setForeground(Color.YELLOW);
						principal.lblNomeUsuario.setFont(new Font("Arial", Font.BOLD, 12));
						principal.lblNivelAcesso.setForeground(Color.YELLOW);
						principal.lblNivelAcesso.setFont(new Font("Arial", Font.BOLD, 12));
						principal.lblNivelAcesso.setText(rs.getString(5).toUpperCase());
						principal.lblWelcome.setText(rs.getString(2).toUpperCase());
						

						this.dispose();
					} else {

						principal.setVisible(true);
						// ele vai alterar o nome de usuario para a string 2 do banco de dados, que é o
						// nome de usuario
						principal.lblNomeUsuario.setText(rs.getString(2).toUpperCase());
						principal.lblNomeUsuario.setFont(new Font("Arial", Font.BOLD, 12));
						principal.lblNivelAcesso.setText(rs.getString(5).toUpperCase());
						principal.lblNivelAcesso.setFont(new Font("Arial", Font.BOLD, 12));
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

				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dataoffline.png")));
			} else {

				lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dataonline.png")));
			}

			con.close();
		} catch (Exception e) {
			System.out.println(e);

		}

	}
}
