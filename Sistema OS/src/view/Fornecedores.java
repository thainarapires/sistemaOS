package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;

import javax.swing.ImageIcon;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class Fornecedores extends JDialog {
	private JTextField txtRazao;
	private JTextField txtID;
	private JTextField txtCNPJ;
	private JTextField txtLogradouro;
	private JTextField txtNumero;
	private JTextField txtFantasia;
	private JTextField txtTel;
	private JTextField txtComplemento;
	private JTextField txtCidade;
	private JTextField txtCEP;
	private JTextField txtBairro;
	private JTextField txtReferencia;
	private JTextField txtCel;
	private JTextField txtEmail;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();
	private JComboBox cboUF;
	private JScrollPane scrollPaneFor;
	private JList listFor;
	private JTextField txtSite;
	private JTextField txtIE;
	private JTextField txtVendedor;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fornecedores dialog = new Fornecedores();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("unchecked")
	public Fornecedores() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneFor.setVisible(false);
			}
		});
		getContentPane().setBackground(SystemColor.text);
		setBounds(100, 100, 839, 522);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setVisible(false);
		scrollPaneFor.setBounds(20, 102, 321, 23);
		getContentPane().add(scrollPaneFor);
		
		listFor = new JList();
		listFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedorLista();
			}
		});
		scrollPaneFor.setViewportView(listFor);

		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 823, 38);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblTitulo = new JLabel("ÁREA DO FORNECEDOR");
		lblTitulo.setBorder(null);
		lblTitulo.setForeground(new Color(0, 128, 255));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(305, 0, 204, 38);
		panel.add(lblTitulo);
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 445, 823, 38);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblRazao = new JLabel("Razão Social*");
		lblRazao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRazao.setBounds(20, 49, 136, 23);
		getContentPane().add(lblRazao);
		
		JLabel lblID = new JLabel("ID Fornecedor:");
		lblID.setFont(new Font("Arial", Font.PLAIN, 16));
		lblID.setBounds(634, 246, 173, 22);
		getContentPane().add(lblID);
		
		txtRazao = new JTextField();
		txtRazao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtRazao.setBackground(SystemColor.control);
		txtRazao.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtRazao.setFont(new Font("Arial", Font.PLAIN, 12));
		txtRazao.setBounds(20, 74, 321, 29);
		getContentPane().add(txtRazao);
		txtRazao.setColumns(10);
		txtRazao.setDocument(new Validador(50));

		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setDisabledTextColor(SystemColor.textInactiveText);
		txtID.setBackground(SystemColor.activeCaptionBorder);
		txtID.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtID.setFont(new Font("Arial", Font.PLAIN, 12));
		txtID.setColumns(10);
		txtID.setBounds(631, 268, 116, 29);
		getContentPane().add(txtID);
		
		JLabel lblCnpj = new JLabel("CNPJ*");
		lblCnpj.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnpj.setBounds(600, 49, 74, 23);
		getContentPane().add(lblCnpj);
		
		txtCNPJ = new JTextField();
		txtCNPJ.setBackground(SystemColor.control);
		txtCNPJ.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCNPJ.setFont(new Font("Arial", Font.PLAIN, 12));
		txtCNPJ.setColumns(10);
		txtCNPJ.setBounds(600, 74, 207, 29);
		getContentPane().add(txtCNPJ);
		txtCNPJ.setDocument(new Validador(14));

		
		txtLogradouro = new JTextField();
		txtLogradouro.setBackground(SystemColor.control);
		txtLogradouro.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLogradouro.setFont(new Font("Arial", Font.PLAIN, 12));
		txtLogradouro.setColumns(10);
		txtLogradouro.setBounds(20, 139, 237, 29);
		getContentPane().add(txtLogradouro);
		txtLogradouro.setDocument(new Validador(35));

		
		JLabel lblLogradouro = new JLabel("Logradouro*");
		lblLogradouro.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLogradouro.setBounds(20, 114, 207, 23);
		getContentPane().add(lblLogradouro);
		
		txtNumero = new JTextField();
		
		txtNumero.setBackground(SystemColor.control);
		txtNumero.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNumero.setFont(new Font("Arial", Font.PLAIN, 12));
		txtNumero.setColumns(10);
		txtNumero.setBounds(267, 139, 74, 29);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(10));

		
		JLabel lblCnpj_1_1 = new JLabel("Número*");
		lblCnpj_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnpj_1_1.setBounds(267, 114, 74, 23);
		getContentPane().add(lblCnpj_1_1);
		
		txtFantasia = new JTextField();
		txtFantasia.setBackground(SystemColor.control);
		txtFantasia.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFantasia.setFont(new Font("Arial", Font.PLAIN, 12));
		txtFantasia.setColumns(10);
		txtFantasia.setBounds(354, 74, 236, 29);
		getContentPane().add(txtFantasia);
		txtFantasia.setDocument(new Validador(50));

		
		JLabel lblFantasia = new JLabel("Nome fantasia:");
		lblFantasia.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFantasia.setBounds(352, 53, 204, 14);
		getContentPane().add(lblFantasia);
		
		JLabel lblTelefone = new JLabel("Telefone*");
		lblTelefone.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTelefone.setBounds(20, 242, 135, 29);
		getContentPane().add(lblTelefone);
		
		txtTel = new JTextField();
		txtTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

				e.consume();
				}
			}
		});
		txtTel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtTel.setBackground(SystemColor.control);
		txtTel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtTel.setFont(new Font("Arial", Font.PLAIN, 12));
		txtTel.setColumns(10);
		txtTel.setBounds(20, 268, 166, 29);
		getContentPane().add(txtTel);
		txtTel.setDocument(new Validador(12));
		
		JLabel lblCnpj_1_1_1 = new JLabel("Complemento:");
		lblCnpj_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnpj_1_1_1.setBounds(352, 114, 154, 23);
		getContentPane().add(lblCnpj_1_1_1);
		
		txtComplemento = new JTextField();
		txtComplemento.setFont(new Font("Arial", Font.PLAIN, 12));
		txtComplemento.setColumns(10);
		txtComplemento.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtComplemento.setBackground(SystemColor.menu);
		txtComplemento.setBounds(351, 139, 155, 29);
		getContentPane().add(txtComplemento);
		txtComplemento.setDocument(new Validador(20));

		
		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Arial", Font.PLAIN, 12));
		txtCidade.setColumns(10);
		txtCidade.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCidade.setBackground(SystemColor.menu);
		txtCidade.setBounds(516, 204, 207, 29);
		getContentPane().add(txtCidade);
		txtCidade.setDocument(new Validador(30));

		
		JLabel lblCidade = new JLabel("Cidade*");
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCidade.setBounds(516, 179, 74, 23);
		getContentPane().add(lblCidade);
		
		JLabel lblUF = new JLabel("UF:");
		lblUF.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUF.setBounds(733, 179, 74, 23);
		getContentPane().add(lblUF);
		
		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(new String[] {" ", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUF.setBounds(733, 204, 74, 29);
		getContentPane().add(cboUF);
		
		JLabel lblCep = new JLabel("CEP*");
		lblCep.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCep.setBounds(516, 114, 207, 23);
		getContentPane().add(lblCep);
		
		txtCEP = new JTextField();
		txtCEP.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

				e.consume();
				}
		
			}
		});
		txtCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtCEP.setFont(new Font("Arial", Font.PLAIN, 12));
		txtCEP.setColumns(10);
		txtCEP.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCEP.setBackground(SystemColor.menu);
		txtCEP.setBounds(516, 139, 175, 29);
		getContentPane().add(txtCEP);
		txtCEP.setDocument(new Validador(20));

		
		JButton btnPesqCEP = new JButton("Pesq. CEP");
		btnPesqCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnPesqCEP.setBounds(701, 142, 106, 23);
		getContentPane().add(btnPesqCEP);
		
		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Arial", Font.PLAIN, 12));
		txtBairro.setColumns(10);
		txtBairro.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtBairro.setBackground(SystemColor.menu);
		txtBairro.setBounds(20, 204, 237, 29);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(50));

		
		JLabel lblBairro = new JLabel("Bairro*");
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBairro.setBounds(20, 179, 207, 23);
		getContentPane().add(lblBairro);
		
		JLabel lblReferencia = new JLabel("Referência:");
		lblReferencia.setFont(new Font("Arial", Font.PLAIN, 16));
		lblReferencia.setBounds(267, 179, 207, 23);
		getContentPane().add(lblReferencia);
		
		txtReferencia = new JTextField();
		txtReferencia.setFont(new Font("Arial", Font.PLAIN, 12));
		txtReferencia.setColumns(10);
		txtReferencia.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtReferencia.setBackground(SystemColor.menu);
		txtReferencia.setBounds(267, 204, 237, 29);
		getContentPane().add(txtReferencia);
		txtReferencia.setDocument(new Validador(50));

		
		JLabel lblCelular = new JLabel("Celular:");
		lblCelular.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCelular.setBounds(196, 241, 165, 29);
		getContentPane().add(lblCelular);
		
		txtCel = new JTextField();
		txtCel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789";

					if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
					}
			}
		});
		txtCel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		txtCel.setFont(new Font("Arial", Font.PLAIN, 12));
		txtCel.setColumns(10);
		txtCel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCel.setBackground(SystemColor.menu);
		txtCel.setBounds(196, 267, 183, 29);
		getContentPane().add(txtCel);
		txtCel.setDocument(new Validador(12));

		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 12));
		txtEmail.setColumns(10);
		txtEmail.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtEmail.setBackground(SystemColor.menu);
		txtEmail.setBounds(389, 268, 235, 29);
		getContentPane().add(txtEmail);
		txtEmail.setDocument(new Validador(50));

		
		JLabel lblEmail = new JLabel("Email*");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmail.setBounds(389, 242, 135, 29);
		getContentPane().add(lblEmail);
		
		JButton btnAdicionar = new JButton("");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			adicionar();
			}
		});
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/ADICIONAR64X64.png")));
		btnAdicionar.setBounds(383, 364, 68, 68);
		getContentPane().add(btnAdicionar);
		
		JButton btnApagar = new JButton("");
		btnApagar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			excluir();
			}
		});
		btnApagar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/TRASH64X64.png")));
		btnApagar.setContentAreaFilled(false);
		btnApagar.setBorderPainted(false);
		btnApagar.setBounds(461, 364, 68, 68);
		getContentPane().add(btnApagar);
		
		JButton btnEditar = new JButton("");
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/EDITAR64X64.png")));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(304, 364, 68, 68);
		getContentPane().add(btnEditar);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/ERASES64X64.png")));
		btnNewButton.setBounds(753, 257, 60, 48);
		getContentPane().add(btnNewButton);
		
		txtSite = new JTextField();
		txtSite.setFont(new Font("Arial", Font.PLAIN, 12));
		txtSite.setColumns(10);
		txtSite.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtSite.setBackground(SystemColor.menu);
		txtSite.setBounds(20, 324, 269, 29);
		getContentPane().add(txtSite);
		
		JLabel lblSite = new JLabel("Site:");
		lblSite.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSite.setBounds(20, 298, 135, 29);
		getContentPane().add(lblSite);
		
		JLabel lblIE = new JLabel("IE:");
		lblIE.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIE.setBounds(299, 298, 165, 29);
		getContentPane().add(lblIE);
		
		txtIE = new JTextField();
		txtIE.setFont(new Font("Arial", Font.PLAIN, 12));
		txtIE.setColumns(10);
		txtIE.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtIE.setBackground(SystemColor.menu);
		txtIE.setBounds(299, 324, 197, 29);
		getContentPane().add(txtIE);
		
		JLabel lblVendedor = new JLabel("Vendedor:");
		lblVendedor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblVendedor.setBounds(511, 298, 140, 29);
		getContentPane().add(lblVendedor);
		
		txtVendedor = new JTextField();
		txtVendedor.setFont(new Font("Arial", Font.PLAIN, 12));
		txtVendedor.setColumns(10);
		txtVendedor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtVendedor.setBackground(SystemColor.menu);
		txtVendedor.setBounds(512, 324, 295, 29);
		getContentPane().add(txtVendedor);

	}
	
	
	private void adicionar() {
		
		// System.out.println("Teste");
		// validação de campos obrigatórios

		if (txtRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Razão Social'. ");
			txtRazao.requestFocus();
		} else if (txtCNPJ.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'CNPJ'.");
			txtCNPJ.requestFocus();
		} else if (txtLogradouro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Logradouro'.");
			txtLogradouro.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Número'.");
			txtNumero.requestFocus();
		} else if (txtCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'CEP'.");
			txtCEP.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Bairro'.");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Endereço'.");
			txtCidade.requestFocus();
		} else if (txtTel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Telefone'.");
			txtTel.requestFocus();
		} else if (txtCel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Celular'.");
			txtCel.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Email'.");
			txtEmail.requestFocus();
		} else if (cboUF.equals(" ")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF");
			cboUF.requestFocus();
		
		
			
		} else {
			// lógica principal
			// CRUD Create
			String create = "insert into fornecedores (razaosocial,nomefantasia, cnpj, logradouro, numero, complemento, cep, bairro, referencia, cidade, uf,  telefone, celular , email, site, vendedor, ie) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções

			try {
				// abrir conexao
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(create);
				pst.setString(1, txtRazao.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtCNPJ.getText());
				pst.setString(4, txtLogradouro.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtCEP.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtReferencia.getText());
				pst.setString(10, txtCidade.getText());
				pst.setString(11, cboUF.getSelectedItem().toString());
				pst.setString(12, txtTel.getText());
				pst.setString(13, txtCel.getText());
				pst.setString(14, txtEmail.getText());
				pst.setString(15, txtSite.getText());
				pst.setString(16, txtVendedor.getText());
				pst.setString(17, txtIE.getText());
				
				
				
				// executa a query(instrução sql, CRUD)
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Fornecedor adicionado");
				// limpar campos
				limparCampos();

				// fechar conection
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado.\nEste CNPJ ou EMAIL já está sendo utilizado.");
				txtCNPJ.setText(null);
				txtCNPJ.requestFocus();
				txtEmail.setText(null);
				txtEmail.requestFocus();
				
			
			} catch (Exception e2) {
				System.out.println(e2);
		}
		}
		
	}
private void editar() {
		
		// System.out.println("teste do botão editar");
		// validar campos obrigatorios
		
		if (txtRazao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Razão Social'. ");
			txtRazao.requestFocus();
		} else if (txtCNPJ.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'CNPJ'.");
			txtCNPJ.requestFocus();
		} else if (txtLogradouro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Logradouro'.");
			txtLogradouro.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Número'.");
			txtNumero.requestFocus();
		} else if (txtCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'CEP'.");
			txtCEP.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Bairro'.");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Cidade'.");
			txtCidade.requestFocus();
		} else if (txtTel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Telefone'.");
			txtTel.requestFocus();
		} else if (txtCel.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Celular'.");
			txtCel.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Email'.");
			txtEmail.requestFocus();
		} else if (cboUF.equals(" ")) {
			JOptionPane.showMessageDialog(null, "Preencha a UF");
			cboUF.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update = "update fornecedores set razaosocial=?, nomefantasia=?, cnpj=?, logradouro=?, numero=?, complemento=?, cep=?, bairro=?, referencia=?, cidade=?, uf=?, telefone=?, celular=?, email=?, site=?, vendedor=?, ie=? where idfornecedor=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtRazao.getText());
				pst.setString(2, txtFantasia.getText());
				pst.setString(3, txtCNPJ.getText());
				pst.setString(4, txtLogradouro.getText());
				pst.setString(5, txtNumero.getText());
				pst.setString(6, txtComplemento.getText());
				pst.setString(7, txtCEP.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtReferencia.getText());
				pst.setString(10, txtCidade.getText());
				pst.setString(11, cboUF.getSelectedItem().toString());
				pst.setString(12, txtTel.getText());
				pst.setString(13, txtCel.getText());
				pst.setString(14, txtEmail.getText());
				pst.setString(15, txtSite.getText());
				pst.setString(16, txtVendedor.getText());
				pst.setString(17, txtIE.getText());
				pst.setString(18, txtID.getText());
				
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do fornecedor editados com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}
private void excluir() {
	//System.out.println("teste");
	//validação de exclusao - a variável confirma captura a opção escolhida.
	int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor?", "Atenção", JOptionPane.YES_NO_OPTION);
	if (confirma == JOptionPane.YES_OPTION) {
		// CRUD - Delete vai excluir o contato
		
         
		String delete = "delete from fornecedores where idfornecedor= ?";
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
			JOptionPane.showMessageDialog(null, "Fornecedor excluido com sucesso!");
			// limpar campos
			limparCampos();
			// fechar conexao
			con.close();

		} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
			JOptionPane.showMessageDialog(null, "Fornecedor não excluido.\nEste fornecedor tem peças no seu estoque.");
			txtID.setText(null);
			txtID.requestFocus();
			
		
		} catch (Exception e2) {
			System.out.println(e2);
	}
	}
	
}

private void buscarCep() {
	String logradouro = "";
	String tipoLogradouro = "";
	String resultado = null;
	String cep = txtCEP.getText();
	try {
		URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
		SAXReader xml = new SAXReader();
		Document documento = xml.read(url);
		Element root = documento.getRootElement();
		for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
			Element element = it.next();
			if (element.getQualifiedName().equals("cidade")) {
				txtCidade.setText(element.getText());
			}
			if (element.getQualifiedName().equals("bairro")) {
				txtBairro.setText(element.getText());
			}
			if (element.getQualifiedName().equals("uf")) {
				cboUF.setSelectedItem(element.getText());
			}
			if (element.getQualifiedName().equals("tipo_logradouro")) {
				tipoLogradouro = element.getText();
			}
			if (element.getQualifiedName().equals("logradouro")) {
				logradouro = element.getText();
			}
			if (element.getQualifiedName().equals("resultado")) {
				resultado = element.getText();
				if (resultado.equals("1")) {
				
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtLogradouro.setText(tipoLogradouro + " " + logradouro);
	} catch (Exception e) {
			System.out.println(e);
	}
}

private void listarFornecedor() {
	DefaultListModel<String> modelo = new DefaultListModel<>();

	listFor.setModel(modelo);

	String readLista = "select * from fornecedores where razaosocial like '" + txtRazao.getText() + "%'" + "order by razaosocial";
	try {
		con = dao.conectar();
		pst = con.prepareStatement(readLista);
		rs = pst.executeQuery();

		while (rs.next()) {
			modelo.addElement(rs.getString(2));
			scrollPaneFor.setVisible(true);

			if (txtRazao.getText().isEmpty()) {
				scrollPaneFor.setVisible(false);
			}
		}
	} catch (Exception e) {
		System.out.println(e);
	}
}

private void buscarFornecedorLista() {

	// System.out.println("teste");

	// variavel que captuar o indice da linha da lista

	int linha = listFor.getSelectedIndex();

	if (linha >= 0) {

		// String readBuscaLista=

		// Query (instrução sql)

		// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista

		String readBuscaLista = "select *from fornecedores where razaosocial like '" + txtRazao.getText() + "%'"

				+ "order by razaosocial limit " + (linha) + " ,1";

		try {

			con = dao.conectar();

			pst = con.prepareStatement(readBuscaLista);

			rs = pst.executeQuery();

			if (rs.next()) {

				scrollPaneFor.setVisible(false);

				txtID.setText(rs.getString(1));
				txtRazao.setText(rs.getString(2));
				txtFantasia.setText(rs.getString(3));
				txtCNPJ.setText(rs.getString(4));
				txtLogradouro.setText(rs.getString(5));
				txtNumero.setText(rs.getString(6));
				txtComplemento.setText(rs.getString(7));
				txtCEP.setText(rs.getString(8));
				txtBairro.setText(rs.getString(9));
				txtReferencia.setText(rs.getString(10));
				txtCidade.setText(rs.getString(11));
				cboUF.setSelectedItem(rs.getString(12)); // 4º Campo da Tabela ID	
				txtTel.setText(rs.getString(13));
				txtCel.setText(rs.getString(14));
				txtEmail.setText(rs.getString(15));
				txtSite.setText(rs.getString(16));
				txtVendedor.setText(rs.getString(17));
				txtIE.setText(rs.getString(18));
				
			} else {

				// System.out.println("Contatos não cadastrados");

				JOptionPane.showMessageDialog(null, "Fornecedor inexistente");

			}

			con.close();

		} catch (Exception e) {

		}

	} else {

		scrollPaneFor.setVisible(false);

	}
}




    private void limparCampos() {
	
	txtRazao.setText(null);
	txtFantasia.setText(null);
	txtCNPJ.setText(null);
	txtLogradouro.setText(null);
	txtNumero.setText(null);
	txtComplemento.setText(null);
	txtCEP.setText(null);
	txtBairro.setText(null);
	txtReferencia.setText(null);
	txtCidade.setText(null);
	txtTel.setText(null);
	txtCel.setText(null);
	txtEmail.setText(null);
	txtID.setText(null);
	txtSite.setText(null);
	txtVendedor.setText(null);
	txtIE.setText(null);

}
}
