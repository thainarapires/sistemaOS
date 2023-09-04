package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.SystemColor;

@SuppressWarnings("unused")
public class Clientes extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtNome;
	private JTextField txtCPF;
	private JTextField txtRG;
	private JTextField txtCNPJ;
	private JTextField txtNumero;
	private JTextField txtBairro;
	private JTextField txtTelefone;
	private JTextField txtEmail;
	private JTextField txtID;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();
	
	private JButton btnBuscar;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnExcluir;
	private JTextField txtEndereco;
	private JTextField txtComplemento;
	private JTextField txtCEP;
	private JTextField txtCidade;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUF;
	private JScrollPane scrollPaneClientes;
	private JScrollPane scrollPaneClient;
	@SuppressWarnings("rawtypes")
	private JList listClient;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
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
	@SuppressWarnings("rawtypes")
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneClient.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/Clientee.png")));
		setTitle("Cadastro de Clientes");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);
		
		scrollPaneClient = new JScrollPane();
		scrollPaneClient.setVisible(false);
		scrollPaneClient.setBounds(20, 95, 416, 32);
		getContentPane().add(scrollPaneClient);
		
		listClient = new JList();
		listClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarCliente();
			}
		});
		scrollPaneClient.setViewportView(listClient);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 734, 37);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblCadastrodeClientes = new JLabel("CADASTRO DE CLIENTES");
		lblCadastrodeClientes.setBounds(236, 11, 203, 19);
		panel.add(lblCadastrodeClientes);
		lblCadastrodeClientes.setForeground(new Color(0, 128, 255));
		lblCadastrodeClientes.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblNome = new JLabel("Nome*");
		lblNome.setForeground(new Color(0, 0, 0));
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBounds(20, 48, 203, 19);
		getContentPane().add(lblNome);
		
		JLabel lblCPF = new JLabel("CPF*");
		lblCPF.setForeground(Color.BLACK);
		lblCPF.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCPF.setBounds(20, 108, 49, 19);
		getContentPane().add(lblCPF);
		
		txtNome = new JTextField();
		txtNome.setBackground(SystemColor.control);
		txtNome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBounds(20, 70, 416, 27);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));
		
		JLabel lblRg = new JLabel("RG*");
		lblRg.setForeground(Color.BLACK);
		lblRg.setFont(new Font("Arial", Font.PLAIN, 16));
		lblRg.setBounds(254, 108, 49, 19);
		getContentPane().add(lblRg);
		
		JLabel lblCnpj = new JLabel("CNPJ");
		lblCnpj.setForeground(Color.BLACK);
		lblCnpj.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCnpj.setBounds(434, 108, 49, 19);
		getContentPane().add(lblCnpj);
		
		txtCPF = new JTextField();
		txtCPF.setBackground(SystemColor.control);
		txtCPF.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCPF.setColumns(10);
		txtCPF.setBounds(20, 128, 223, 27);
		getContentPane().add(txtCPF);
		txtCPF.setDocument(new Validador(11));
		
		txtRG = new JTextField();
		txtRG.setBackground(SystemColor.control);
		txtRG.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtRG.setColumns(10);
		txtRG.setBounds(253, 128, 175, 27);
		getContentPane().add(txtRG);
		txtRG.setDocument(new Validador(9));
		
		txtCNPJ = new JTextField();
		txtCNPJ.setBackground(SystemColor.control);
		txtCNPJ.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCNPJ.setColumns(10);
		txtCNPJ.setBounds(434, 129, 234, 27);
		getContentPane().add(txtCNPJ);
		txtCNPJ.setDocument(new Validador(14));
		
		JLabel lblNumero = new JLabel("Numero");
		lblNumero.setForeground(Color.BLACK);
		lblNumero.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNumero.setBounds(20, 225, 83, 19);
		getContentPane().add(lblNumero);
		
		txtNumero = new JTextField();
		txtNumero.setBackground(SystemColor.control);
		txtNumero.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNumero.setColumns(10);
		txtNumero.setBounds(20, 246, 127, 27);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(10));
		
		JLabel lblComplemento = new JLabel("Complemento");
		lblComplemento.setForeground(Color.BLACK);
		lblComplemento.setFont(new Font("Arial", Font.PLAIN, 16));
		lblComplemento.setBounds(157, 225, 108, 19);
		getContentPane().add(lblComplemento);
		
		txtBairro = new JTextField();
		txtBairro.setBackground(SystemColor.control);
		txtBairro.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtBairro.setColumns(10);
		txtBairro.setBounds(252, 187, 175, 27);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(30));
		
		JLabel lblTelCel = new JLabel("Telefone/Celular*");
		lblTelCel.setForeground(Color.BLACK);
		lblTelCel.setFont(new Font("Arial", Font.PLAIN, 16));
		lblTelCel.setBounds(20, 284, 191, 19);
		getContentPane().add(lblTelCel);
		
		txtTelefone = new JTextField();
		txtTelefone.setBackground(SystemColor.control);
		txtTelefone.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(20, 305, 314, 27);
		getContentPane().add(txtTelefone);
		txtTelefone.setDocument(new Validador(12));
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEmail.setBounds(343, 285, 191, 19);
		getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBackground(SystemColor.control);
		txtEmail.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtEmail.setColumns(10);
		txtEmail.setBounds(343, 305, 325, 27);
		getContentPane().add(txtEmail);
		txtEmail.setDocument(new Validador(50));
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(0, 431, 708, 37);
		getContentPane().add(panel_1);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/EDITAR64X64.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar cliente");
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBounds(239, 355, 64, 64);
		getContentPane().add(btnEditar);
		
		
		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/ADICIONAR64X64.png")));
		btnAdicionar.setToolTipText("Adicionar cliente");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setBounds(313, 356, 64, 64);
		getContentPane().add(btnAdicionar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			excluir();
			}
			
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/TRASH64X64.png")));
		btnExcluir.setToolTipText("Excluir cliente");
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(387, 356, 64, 64);
		getContentPane().add(btnExcluir);
		
		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/2064480_education_eraser_learn_student_study_icon.png")));
		btnLimpar.setToolTipText("Limpar todos os campos");
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(668, 122, 48, 45);
		getContentPane().add(btnLimpar);
		
		JLabel lblID = new JLabel("ID:");
		lblID.setForeground(Color.BLACK);
		lblID.setFont(new Font("Arial", Font.PLAIN, 16));
		lblID.setBounds(446, 48, 203, 19);
		getContentPane().add(lblID);
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(446, 70, 222, 27);
		getContentPane().add(txtID);
		setBounds(100, 100, 732, 503);
		setLocationRelativeTo(null);
		
		JLabel lblEndereco = new JLabel("Endereço*");
		lblEndereco.setForeground(Color.BLACK);
		lblEndereco.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEndereco.setBounds(20, 166, 83, 19);
		getContentPane().add(lblEndereco);
		
		txtEndereco = new JTextField();
		txtEndereco.setBackground(SystemColor.control);
		txtEndereco.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(20, 187, 223, 27);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(50));
		
		JLabel lblBairro = new JLabel("Bairro*");
		lblBairro.setForeground(Color.BLACK);
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBairro.setBounds(252, 166, 83, 19);
		getContentPane().add(lblBairro);
		
		txtComplemento = new JTextField();
		txtComplemento.setBackground(SystemColor.control);
		txtComplemento.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(157, 246, 146, 27);
		getContentPane().add(txtComplemento);
		txtNome.setDocument(new Validador(20));
		
		txtCEP = new JTextField();
		txtCEP.setBackground(SystemColor.control);
		txtCEP.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		txtCEP.setColumns(10);
		txtCEP.setBounds(314, 246, 190, 27);
		getContentPane().add(txtCEP);
		txtCEP.setDocument(new Validador(10));

		
		JLabel lblCEP = new JLabel("CEP:");
		lblCEP.setForeground(Color.BLACK);
		lblCEP.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCEP.setBounds(313, 225, 108, 19);
		getContentPane().add(lblCEP);
		
		JLabel lblUF = new JLabel("UF");
		lblUF.setForeground(Color.BLACK);
		lblUF.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUF.setBounds(625, 226, 32, 19);
		getContentPane().add(lblUF);
		
		JLabel lblCidade = new JLabel("Cidade*");
		lblCidade.setForeground(Color.BLACK);
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblCidade.setBounds(434, 167, 83, 19);
		getContentPane().add(lblCidade);
		
		txtCidade = new JTextField();
		txtCidade.setBackground(SystemColor.control);
		txtCidade.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCidade.setColumns(10);
		txtCidade.setBounds(434, 188, 234, 27);
		getContentPane().add(txtCidade);
		
		cboUF = new JComboBox();
		cboUF.setModel(new DefaultComboBoxModel(new String[] {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"}));
		cboUF.setBounds(625, 248, 43, 27);
		getContentPane().add(cboUF);
		
		JButton btnPesqCEP = new JButton("Pesq. CEP");
		btnPesqCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnPesqCEP.setBounds(514, 248, 101, 23);
		getContentPane().add(btnPesqCEP);

	}
	private void limparCampos() {
		//teste
		//System.out.println("Teste");
		txtID.setText(null);
		txtNome.setText(null);
		txtCPF.setText(null);
		txtRG.setText(null);
		txtCNPJ.setText(null);
		txtNumero.setText(null);
		txtBairro.setText(null);
		txtTelefone.setText(null);
		txtEmail.setText(null);
		txtComplemento.setText(null);
		txtCidade.setText(null);
		txtEndereco.setText(null);
		txtCEP.setText(null);
		
		
		
	
	}
	
	private void adicionar() {
		
		// System.out.println("Teste");
		// validação de campos obrigatórios

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();
		} else if (txtRG.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o RG");
			txtRG.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero");
			txtNumero.requestFocus();
			
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro");
			txtBairro.requestFocus();
		
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtCidade.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone");
			txtTelefone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email");
			txtEmail.requestFocus();
		
		
			
		} else {
			// lógica principal
			// CRUD Create
			String create = "insert into clientes(nome,cpf,rg, cnpj, endereco, numero, complemento, bairro, cep , cidade, uf,  fone, email) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			// tratamento de exceções

			try {
				// abrir conexao
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCPF.getText());
				pst.setString(3, txtRG.getText());
				pst.setString(4, txtCNPJ.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCEP.getText());
				pst.setString(10, txtCidade.getText());
				pst.setString(11, cboUF.getSelectedItem().toString());
				pst.setString(12, txtTelefone.getText());
				pst.setString(13, txtEmail.getText());
				
				
				// executa a query(instrução sql, CRUD)
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Cliente adicionado");
				// limpar campos
				limparCampos();

				// fechar conection
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não adicionado.\nEste CPF, RG ou EMAIL já está sendo utilizado.");
				txtCPF.setText(null);
				txtCPF.requestFocus();
				txtRG.setText(null);
				txtRG.requestFocus();
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
		
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtCPF.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CPF");
			txtCPF.requestFocus();
		} else if (txtRG.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o RG");
			txtRG.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro");
			txtBairro.requestFocus();
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtCidade.requestFocus();
		} else if (txtTelefone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o telefone");
			txtTelefone.requestFocus();
		} else if (txtEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o email");
			txtEmail.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update = "update clientes set nome=?, cpf=?, rg=?, cnpj=?, endereco=?, numero=?, complemento=?, bairro=?, cep=?, cidade=?, uf=?, fone=?, email=? where idcli=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtCPF.getText());
				pst.setString(3, txtRG.getText());
				pst.setString(4, txtCNPJ.getText());
				pst.setString(5, txtEndereco.getText());
				pst.setString(6, txtNumero.getText());
				pst.setString(7, txtComplemento.getText());
				pst.setString(8, txtBairro.getText());
				pst.setString(9, txtCEP.getText());
				pst.setString(10, txtCidade.getText());
				pst.setString(11, cboUF.getSelectedItem().toString());
				pst.setString(12, txtTelefone.getText());
				pst.setString(13, txtEmail.getText());
				pst.setString(14, txtID.getText());
				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!");
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
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?", "Atenção", JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			// CRUD - Delete vai excluir o contato
			
	         
			String delete = "delete from clientes where idcli= ?";
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
				JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não excluido.\nEste cliente tem uma OS pendente.");
				txtID.setText(null);
				txtID.requestFocus();
				
			
			} catch (Exception e2) {
				System.out.println(e2);
		}
		}
		
	}//fim do excluir
	
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
						System.out.println("OK");
					//	lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
						} else {
							JOptionPane.showMessageDialog(null, "CEP não encontrado");
						}
					}
				}
				txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
				System.out.println(e);
		}
	}
	private void listarClientes() {

		// System.out.println("teste");

		 

		// linha abaixo cria um objeto usando como referencia um vetor dinamico , este

		// objeto ira temporariamente armazenar os nomes

		DefaultListModel<String> modelo = new DefaultListModel<>();

		// setar a lista -> modelo

		listClient.setModel(modelo);

		// Query (instrução sql)

		String readLista = "select *from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";

		try {

		con = dao.conectar();

		pst = con.prepareStatement(readLista);

		rs = pst.executeQuery();

		// uso do while para trazer os usuarios enquanto exix=stir

		while (rs.next()) {

		// mostrar a barra de rolagem(scrollpane)

		scrollPaneClient.setVisible(true);

		// adcionar os usuarios novertor -> lista

		modelo.addElement(rs.getString(2));

		 

		if (txtNome.getText().isEmpty()) {

		scrollPaneClient.setVisible(false);

		}

		 

		} // fechar a conexão

		con.close();

		} catch (Exception e) {

		System.out.println(e);

		}

		 

	}
	private void buscarCliente() {

		// System.out.println("teste");

		// variavel que captuar o indice da linha da lista

		int linha = listClient.getSelectedIndex();

		if (linha >= 0) {

		// String readBuscaLista=

		// Query (instrução sql)

		// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista

		String readBuscaLista = "select *from clientes where nome like '" + txtNome.getText() + "%'"

		+ "order by nome limit " + (linha) + " ,1";

		try {

		con = dao.conectar();

		pst = con.prepareStatement(readBuscaLista);

		rs = pst.executeQuery();

		if (rs.next()) {

		scrollPaneClient.setVisible(false);

		txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
		txtNome.setText(rs.getString(2)); // 2º Campo da Tabela ID
		txtCPF.setText(rs.getString(3)); // 3º Campo da Tabela ID
		txtRG.setText(rs.getString(4)); // 4º Campo da Tabela ID
		txtCNPJ.setText(rs.getString(5)); // 4º Campo da Tabela ID
		txtEndereco.setText(rs.getString(6)); // 4º Campo da Tabela ID
		txtNumero.setText(rs.getString(7)); // 4º Campo da Tabela ID
		txtComplemento.setText(rs.getString(8)); // 4º Campo da Tabela ID
		txtBairro.setText(rs.getString(9)); // 4º Campo da Tabela ID
		txtCEP.setText(rs.getString(10)); // 4º Campo da Tabela ID
		txtCidade.setText(rs.getString(11)); // 4º Campo da Tabela ID
		
		cboUF.setSelectedItem(rs.getString(12)); // 4º Campo da Tabela ID	
		
		txtTelefone.setText(rs.getString(13)); // 4º Campo da Tabela ID
		txtEmail.setText(rs.getString(14)); // 4º Campo da Tabela ID

		 

		} else {

		// System.out.println("Contatos não cadastrados");

		JOptionPane.showMessageDialog(null, "Usuario inexistente");

		 

		}

		con.close();

		} catch (Exception e) {

		 

		}

		 

		} else {

		scrollPaneClient.setVisible(false);

		 

		}
	}
	
}//fim do codigo
