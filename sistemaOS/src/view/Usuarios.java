package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;

import model.DAO;
import utils.Validador;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JPasswordField passwordSenha;
	private JTextField txtID;
	private JTextField txtLogin;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnPesquisar;
	@SuppressWarnings("rawtypes")
	private JList listUsers;
	private JScrollPane scrollPaneUsers;
	private JLabel lblPerfil;
	@SuppressWarnings("rawtypes")
	private JComboBox cboPerfil;
	private JCheckBox checkSenha;
	private JPanel panelWhite;
	public JButton btnListar;
	private JLabel lblNewLabel_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método responsável por limpar os campos
	 */
	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtLogin.setText(null);
		passwordSenha.setText(null);
		passwordSenha.setBackground(Color.gray);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPaneUsers.setVisible(false);
		cboPerfil.setSelectedItem("");
		checkSenha.setSelected(false);
		cboPerfil.setSelectedItem(null);
		passwordSenha.setEnabled(false);

	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/Logo.png")));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneUsers.setVisible(false);
			}
		});

		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setTitle("Usuarios");
		setModal(true);
		setBounds(100, 100, 800, 405);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Arial", Font.PLAIN, 11));
		txtNome.setBackground(SystemColor.control);
		txtNome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				listarUsuarios();

			}
		});

		scrollPaneUsers = new JScrollPane();
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(297, 204, 418, 32);
		getContentPane().add(scrollPaneUsers);

		listUsers = new JList();
		scrollPaneUsers.setViewportView(listUsers);
		listUsers.setFont(new Font("Arial", Font.PLAIN, 11));
		listUsers.setBackground(SystemColor.control);
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		listUsers.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.setBounds(297, 182, 418, 32);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		JLabel lblSenha = new JLabel("SENHA:");
		lblSenha.setBounds(297, 243, 98, 14);
		getContentPane().add(lblSenha);

		passwordSenha = new JPasswordField();
		passwordSenha.setEnabled(false);
		passwordSenha.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		passwordSenha.setFont(new Font("Arial", Font.PLAIN, 11));
		passwordSenha.setBackground(Color.GRAY);
		passwordSenha.setBounds(297, 260, 236, 32);
		getContentPane().add(passwordSenha);
		passwordSenha.setDocument(new Validador(250));

		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(543, 243, 29, 14);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtID.setBackground(Color.GRAY);
		txtID.setEditable(false);
		txtID.setBounds(543, 259, 81, 32);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNome = new JLabel("NOME DE USUÁRIO:");
		lblNome.setBounds(297, 167, 205, 14);
		getContentPane().add(lblNome);

		txtLogin = new JTextField();
		txtLogin.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 11));
		txtLogin.setBackground(SystemColor.control);
		txtLogin.setBounds(297, 108, 377, 32);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(15));

		btnPesquisar = new JButton("");
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBounds(684, 108, 32, 32);
		getContentPane().add(btnPesquisar);
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnPesquisar.setSelected(true);
		btnPesquisar.setToolTipText("Buscar pelo login");
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/zoom.png")));
		getRootPane().setDefaultButton(btnPesquisar);

		cboPerfil = new JComboBox();
		cboPerfil.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		cboPerfil.setBackground(SystemColor.control);
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {"", "Admin", "User"}));
		cboPerfil.setBounds(634, 259, 82, 32);
		getContentPane().add(cboPerfil);

		lblPerfil = new JLabel("PERFIL:");
		lblPerfil.setBounds(634, 243, 82, 14);
		getContentPane().add(lblPerfil);

		checkSenha = new JCheckBox("Alterar senha");
		checkSenha.setBackground(Color.WHITE);
		checkSenha.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		checkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkSenha.isSelected()) {
					passwordSenha.setText(null);
					passwordSenha.setEnabled(true);
					passwordSenha.setBackground(Color.RED);

				} else {
					passwordSenha.setBackground(Color.gray);
					passwordSenha.setEnabled(false);
					passwordSenha.setText(null);

				}

			}
		});
		checkSenha.setBounds(298, 303, 236, 24);
		getContentPane().add(checkSenha);

		JLabel lblLogin = new JLabel("LOGIN:");
		lblLogin.setBounds(297, 94, 419, 14);
		getContentPane().add(lblLogin);
		
		JPanel panelAzul = new JPanel();
		panelAzul.setLayout(null);
		panelAzul.setBackground(new Color(0, 51, 255));
		panelAzul.setBounds(-2, -1, 229, 386);
		getContentPane().add(panelAzul);
		
				btnAdicionar = new JButton("ADICIONAR");
				btnAdicionar.setFocusPainted(false);
				btnAdicionar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
				btnAdicionar.setFont(new Font("Dialog", Font.BOLD, 12));
				btnAdicionar.setForeground(Color.WHITE);
				btnAdicionar.setHorizontalAlignment(SwingConstants.LEFT);
				btnAdicionar.setBounds(10, 66, 209, 39);
				panelAzul.add(btnAdicionar);
				btnAdicionar.setEnabled(false);
				btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnAdicionar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						adicionar();
					}
				});
				btnAdicionar.setContentAreaFilled(false);
				btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/adicionarusuario.png")));
				btnAdicionar.setToolTipText("Adicionar novo usuario");
				
						btnEditar = new JButton("EDITAR");
						btnEditar.setFocusPainted(false);
						btnEditar.setHorizontalAlignment(SwingConstants.LEFT);
						btnEditar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
						btnEditar.setForeground(Color.WHITE);
						btnEditar.setFont(new Font("Dialog", Font.BOLD, 12));
						btnEditar.setBounds(10, 147, 209, 39);
						panelAzul.add(btnEditar);
						btnEditar.setEnabled(false);
						btnEditar.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								if (checkSenha.isSelected()) {
									editarUsuario();
								} else {

									editarUsuarioExcetoSenha();
								}
							}
						});
						btnEditar.setContentAreaFilled(false);
						btnEditar.setToolTipText("Editar usuario");
						btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/editarusuario.png")));
						btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						
								btnExcluir = new JButton("APAGAR ");
								btnExcluir.setFocusPainted(false);
								btnExcluir.setHorizontalAlignment(SwingConstants.LEFT);
								btnExcluir.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
								btnExcluir.setForeground(Color.WHITE);
								btnExcluir.setFont(new Font("Dialog", Font.BOLD, 12));
								btnExcluir.setBounds(10, 228, 209, 39);
								panelAzul.add(btnExcluir);
								btnExcluir.setToolTipText("Apagar usuario");
								btnExcluir.setEnabled(false);
								btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								btnExcluir.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent e) {
										excluirUsuario();
									}

								});
								btnExcluir.setAlignmentX(Component.RIGHT_ALIGNMENT);
								btnExcluir.setContentAreaFilled(false);
								btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/deletarusuario.png")));
								
										JButton btnApagar = new JButton("LIMPAR OS CAMPOS      ");
										btnApagar.setFocusPainted(false);
										btnApagar.setHorizontalAlignment(SwingConstants.LEFT);
										btnApagar.setForeground(Color.WHITE);
										btnApagar.setFont(new Font("Dialog", Font.BOLD, 12));
										btnApagar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
										btnApagar.setBounds(10, 309, 209, 39);
										panelAzul.add(btnApagar);
										btnApagar.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent e) {
												limparCampos();
											}
										});
										btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
										btnApagar.setToolTipText("Limpar todos os campos");
										btnApagar.setContentAreaFilled(false);
										btnApagar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/limparcamposusuario.png")));
										
										panelWhite = new JPanel();
										panelWhite.setBounds(0, 43, 239, 4);
										panelAzul.add(panelWhite);
										
										JLabel lblNewLabel = new JLabel("Bem vindo(a) Admin!");
										lblNewLabel.setForeground(Color.WHITE);
										lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 14));
										lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
										lblNewLabel.setBounds(15, 16, 204, 14);
										panelAzul.add(lblNewLabel);
										
										lblNewLabel_1 = new JLabel("New label");
										lblNewLabel_1.setIcon(new ImageIcon(Usuarios.class.getResource("/img/sois.png")));
										lblNewLabel_1.setBounds(0, -63, 244, 895);
										panelAzul.add(lblNewLabel_1);
										
										JLabel lblCadastro = new JLabel("CADASTRO");
										lblCadastro.setFont(new Font("Arial", Font.PLAIN, 28));
										lblCadastro.setHorizontalAlignment(SwingConstants.CENTER);
										lblCadastro.setBounds(394, 23, 241, 48);
										getContentPane().add(lblCadastro);
		

		
	}

	/**
	 * Metodo responsavel por: buscar usuario.
	 */

	private void buscar() {
		if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'login'");
			txtLogin.requestFocus();
		} else {

			String read = "select * from usuarios where login = ?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogin.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					passwordSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Usuario inexistente");
					btnAdicionar.setEnabled(true);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Metodo responsavel por: adicionar um usuario.
	 */
	private void adicionar() {
		String capturarSenha = new String(passwordSenha.getPassword());
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (cboPerfil.getSelectedIndex() == 0) {
		    JOptionPane.showMessageDialog(null, "Selecione um perfil.");
		    cboPerfil.requestFocus();
		} else if (capturarSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			passwordSenha.requestFocus();
		} else {

			String create = "insert into usuarios(nome,login,senha, perfil) values (?,?,md5(?),?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Usuario adicionado");

				limparCampos();

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	/**
	 * Metodo responsavel por: editar um usuario.
	 */
	@SuppressWarnings("deprecation")
	private void editarUsuario() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			txtNome.requestFocus();

		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuario");
			txtLogin.requestFocus();

		} else if (passwordSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha do usuario");
			passwordSenha.requestFocus();

		} else if (cboPerfil.getSelectedItem().equals(" ")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do usuario");
			cboPerfil.requestFocus();

		} else {

			String update = "update usuarios set nome =?, login=?, senha=md5(?), perfil=? where id=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, passwordSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");

				limparCampos();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Metodo responsavel por: editar um usuario exceto senha.
	 */
	private void editarUsuarioExcetoSenha() {
		String capturarSenha = new String(passwordSenha.getPassword());

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			txtNome.requestFocus();

		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuario");
			txtLogin.requestFocus();

		} else if (capturarSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a senha do usuario");
			passwordSenha.requestFocus();

		} else if (cboPerfil.getSelectedItem().equals(" ")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil do usuario");
			cboPerfil.requestFocus();

		} else {

			String update2 = "update usuarios set nome =?, login=?, perfil=? where id=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");

				limparCampos();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Metodo responsavel por: excluir um usuario.
	 */
	private void excluirUsuario() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuario?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {

			String delete = "delete from usuarios where id=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Usuario excluido");

				limparCampos();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Metodo responsavel por: listar usuario pelo nome.
	 */
	@SuppressWarnings("unchecked")
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();

		listUsers.setModel(modelo);

		String readLista = "select * from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				modelo.addElement(rs.getString(2));
				scrollPaneUsers.setVisible(true);

				if (txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsavel por: buscar usuario pelo nome.
	 */
	private void buscarUsuarioLista() {

		int linha = listUsers.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select *from usuarios where nome like '" + txtNome.getText() + "%'"

					+ "order by nome limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneUsers.setVisible(false);

					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					passwordSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

				} else {

					JOptionPane.showMessageDialog(null, "Usuario inexistente");

				}

				con.close();

			} catch (Exception e) {

			}

		} else {

			scrollPaneUsers.setVisible(false);

		}
	}
}
