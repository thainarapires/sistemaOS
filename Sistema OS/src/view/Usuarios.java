package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JCheckBox;

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
	private JList listUsers;
	private JScrollPane scrollPaneUsers;
	private JLabel lblPerfil;
	private JComboBox cboPerfil;
	private JCheckBox checkSenha;

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
		passwordSenha.setBackground(Color.WHITE);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPaneUsers.setVisible(false);
		cboPerfil.setSelectedItem("");
		checkSenha.setSelected(false);
		cboPerfil.setSelectedItem(null);
		
	}// FIM DO MÉTODO LIMPAR CAMPOS

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneUsers.setVisible(false);
			}
		});

		setResizable(false);
		getContentPane().setBackground(new Color(255, 255, 255));
		setTitle("Usuarios");
		setModal(true);
		setBounds(100, 100, 407, 327);
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
		scrollPaneUsers.setBounds(13, 126, 233, 37);
		getContentPane().add(scrollPaneUsers);

		listUsers = new JList();
		listUsers.setFont(new Font("Arial", Font.PLAIN, 11));
		listUsers.setBackground(SystemColor.control);
		listUsers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarUsuarioLista();
			}
		});
		listUsers.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPaneUsers.setViewportView(listUsers);
		txtNome.setBounds(12, 95, 236, 32);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		JLabel lblLogin = new JLabel("LOGIN:");
		lblLogin.setBounds(11, 11, 46, 14);
		getContentPane().add(lblLogin);

		JLabel lblSenha = new JLabel("SENHA:");
		lblSenha.setBounds(11, 138, 46, 14);
		getContentPane().add(lblSenha);

		passwordSenha = new JPasswordField();
		passwordSenha.setEnabled(false);
		passwordSenha.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		passwordSenha.setFont(new Font("Arial", Font.PLAIN, 11));
		passwordSenha.setBackground(SystemColor.activeCaptionBorder);
		passwordSenha.setBounds(11, 163, 236, 32);
		getContentPane().add(passwordSenha);
		passwordSenha.setDocument(new Validador(250));

		JLabel lblLock = new JLabel("");
		lblLock.setIcon(new ImageIcon(Usuarios.class.getResource("/img/key.png")));
		lblLock.setBounds(48, 136, 16, 16);
		getContentPane().add(lblLock);

		JLabel lblID = new JLabel("ID:");
		lblID.setBounds(318, 11, 46, 14);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(318, 27, 57, 32);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		JLabel lblNome = new JLabel("NOME DE USUÁRIO:");
		lblNome.setBounds(11, 76, 142, 14);
		getContentPane().add(lblNome);

		txtLogin = new JTextField();
		txtLogin.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLogin.setFont(new Font("Arial", Font.PLAIN, 11));
		txtLogin.setBackground(SystemColor.control);
		txtLogin.setBounds(13, 26, 162, 34);
		getContentPane().add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.setDocument(new Validador(15));

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.menu);
		panel.setBounds(0, 248, 455, 98);
		getContentPane().add(panel);
		panel.setLayout(null);

		btnPesquisar = new JButton("");
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.setBounds(183, 12, 48, 48);
		getContentPane().add(btnPesquisar);
		btnPesquisar.setBorderPainted(false);
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();

			}
		});
		btnPesquisar.setSelected(true);
		btnPesquisar.setToolTipText("Pesquisar usuario");
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Pesquisar48px.png")));

		// substituir o click pela tecla <ENTER> em um botão
		getRootPane().setDefaultButton(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(269, 88, 48, 48);
		getContentPane().add(btnAdicionar);
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Adicionar48px.png")));
		btnAdicionar.setToolTipText("Adicionar novo usuario");

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(327, 88, 48, 48);
		getContentPane().add(btnEditar);
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checkSenha.isSelected()) {
				editarUsuario();
				} else {
	
				editarUsuarioExcetoSenha();
				}
			}
		});
		btnEditar.setContentAreaFilled(false);
		btnEditar.setToolTipText("Editar contato");
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/edit.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnApagar = new JButton("");
		btnApagar.setBounds(269, 147, 48, 48);
		getContentPane().add(btnApagar);
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnApagar.setBorderPainted(false);
		btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApagar.setToolTipText("APAGAR TUDO!!!");
		btnApagar.setContentAreaFilled(false);
		btnApagar.setIcon(new ImageIcon(
				Usuarios.class.getResource("/img/2064480_education_eraser_learn_student_study_icon.png")));

		btnExcluir = new JButton("");
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
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Deletar48px.png")));
		btnExcluir.setBounds(326, 147, 48, 48);
		getContentPane().add(btnExcluir);
		
		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] {" ", "Admin", "User"}));
		cboPerfil.setBounds(241, 26, 67, 34);
		getContentPane().add(cboPerfil);
		
		lblPerfil = new JLabel("PERFIL:");
		lblPerfil.setBounds(241, 11, 46, 14);
		getContentPane().add(lblPerfil);
		
		checkSenha = new JCheckBox("Alterar senha");
		checkSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(checkSenha.isSelected()) {
				passwordSenha.setText(null);
				passwordSenha.setEnabled(true);
				passwordSenha.setBackground(Color.RED);
				
			} else {
				passwordSenha.setBackground(Color.WHITE);
				passwordSenha.setEnabled(false);
				
				
			}
			
			}
		});
		checkSenha.setBounds(13, 206, 122, 32);
		getContentPane().add(checkSenha);

	}

	private void buscar() {

		// System.out.println("Teste do botão buscar");

		// Criar uma variável com a query (instrução do banco)

		// Tratamento de exceções
		String read = "select * from usuarios where login = ?";
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execucão da query( instrução sql - CRUD Read)
			// o parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				// preencher as caixas de texto com as informações

				txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
				txtNome.setText(rs.getString(2)); // 2º Campo da Tabela ID
				txtLogin.setText(rs.getString(3)); // 3º Campo da Tabela ID
				passwordSenha.setText(rs.getString(4)); // 4º Campo da Tabela ID
				cboPerfil.setSelectedItem(rs.getString(5)); // 4º Campo da Tabela ID
				
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
	
			} else {
				// System.out.println("Contaos não cadastrados");
				JOptionPane.showMessageDialog(null, "Usuario inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(true);
				passwordSenha.setBackground(Color.GRAY);
				
			}
			con.close();
		
		
		
		} catch (Exception e) {
			System.out.println(e);

		}

	}

	@SuppressWarnings("deprecation")
	private void adicionar() {// Criar váriavel/objeto para capturar a senha
		String capturarSenha = new String(passwordSenha.getPassword());

		// System.out.println("Teste");
		// validação de campos obrigatórios

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogin.requestFocus();
		} else if (capturarSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			passwordSenha.requestFocus();
		} else if (capturarSenha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			passwordSenha.requestFocus();
		} else if (cboPerfil.equals(" ")) {
			JOptionPane.showMessageDialog(null, "Preencha o perfil");
			
		} else {
			// lógica principal
			// CRUD Create
			String create = "insert into usuarios(nome,login,senha, perfil) values (?,?,md5(?),?)";
			// tratamento de exceções

			try {
				// abrir conexao
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, capturarSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// executa a query(instrução sql, CRUD)
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Usuario adicionado");
				// limpar campos
				limparCampos();

				// fechar conection
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste login já está sendo utilizado.");
				txtLogin.setText(null);
				txtLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
		}
		}

	}// fim do método add

	private void editarUsuario() {
		
		// System.out.println("teste do botão editar");
		// validar campos obrigatorios
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
			// logica principal
			// CRUD - Update
			String update = "update usuarios set nome =?, login=?, senha=md5(?), perfil=? where id=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, passwordSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}// FIM DO MÉTODO EDITAR CONTATO
	
	private void editarUsuarioExcetoSenha() {
		String capturarSenha = new String(passwordSenha.getPassword());

		// System.out.println("teste do botão editar");
		// validar campos obrigatorios
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
			// logica principal
			// CRUD - Update
			String update2 = "update usuarios set nome =?, login=?, perfil=? where id=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update2);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	// Excluir contato
	private void excluirUsuario() {
		// System.out.println("teste");
		// validação de exclusao - a variável confirma captura a opção escolhida.
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuario?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete vai excluir o contato
			String delete = "delete from usuarios where id=?";
			// tratamento de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());
				// executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Usuario excluido");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}// Fim do método excluir contato

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
				
				if(txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		}

	private void buscarUsuarioLista() {

		// System.out.println("teste");

		// variavel que captuar o indice da linha da lista

		int linha = listUsers.getSelectedIndex();

		if (linha >= 0) {

			// String readBuscaLista=

			// Query (instrução sql)

			// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista

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
					cboPerfil.setSelectedItem(rs.getString(5)); // 4º Campo da Tabela ID


					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					
					
				} else {

					// System.out.println("Contatos não cadastrados");

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
