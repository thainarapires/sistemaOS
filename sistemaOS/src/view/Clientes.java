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
import javax.swing.border.LineBorder;

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
	private JButton btnExcluir;

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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Clientes() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneClient.setVisible(false);
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/logo (1).png")));
		setTitle("Cadastro de Clientes");
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		scrollPaneClient = new JScrollPane();
		scrollPaneClient.setVisible(false);
		scrollPaneClient.setBounds(20, 102, 473, 70);
		getContentPane().add(scrollPaneClient);

		listClient = new JList();
		listClient.setFont(new Font("Arial", Font.PLAIN, 26));
		listClient.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarCliente();
			}
		});
		scrollPaneClient.setViewportView(listClient);

		JPanel panel = new JPanel();
		panel.setBounds(-14, -1, 1035, 33);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblCadastrodeClientes = new JLabel("CADASTRO DE CLIENTES");
		lblCadastrodeClientes.setBounds(383, 0, 412, 38);
		panel.add(lblCadastrodeClientes);
		lblCadastrodeClientes.setForeground(new Color(0, 128, 255));
		lblCadastrodeClientes.setFont(new Font("Arial", Font.BOLD, 20));

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setForeground(new Color(0, 0, 0));
		lblNome.setFont(new Font("Arial", Font.PLAIN, 24));
		lblNome.setBounds(20, 36, 473, 28);
		getContentPane().add(lblNome);

		JLabel lblCPF = new JLabel("CPF:");
		lblCPF.setForeground(Color.BLACK);
		lblCPF.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCPF.setBounds(516, 36, 223, 28);
		getContentPane().add(lblCPF);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Arial", Font.PLAIN, 26));
		txtNome.setBackground(Color.LIGHT_GRAY);
		txtNome.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBounds(20, 66, 473, 42);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(150));

		JLabel lblRg = new JLabel("RG:");
		lblRg.setForeground(Color.BLACK);
		lblRg.setFont(new Font("Arial", Font.PLAIN, 24));
		lblRg.setBounds(814, 37, 181, 27);
		getContentPane().add(lblRg);

		JLabel lblCnpj = new JLabel("CNPJ:");
		lblCnpj.setForeground(Color.BLACK);
		lblCnpj.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCnpj.setBounds(20, 119, 307, 28);
		getContentPane().add(lblCnpj);

		txtCPF = new JTextField();
		txtCPF.setFont(new Font("Arial", Font.PLAIN, 26));
		txtCPF.setBackground(Color.LIGHT_GRAY);
		txtCPF.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCPF.setColumns(10);
		txtCPF.setBounds(516, 66, 283, 42);
		getContentPane().add(txtCPF);
		txtCPF.setDocument(new Validador(11));

		txtRG = new JTextField();
		txtRG.setFont(new Font("Arial", Font.PLAIN, 26));
		txtRG.setBackground(Color.LIGHT_GRAY);
		txtRG.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtRG.setColumns(10);
		txtRG.setBounds(814, 66, 181, 42);
		getContentPane().add(txtRG);
		txtRG.setDocument(new Validador(9));

		txtCNPJ = new JTextField();
		txtCNPJ.setFont(new Font("Arial", Font.PLAIN, 26));
		txtCNPJ.setBackground(Color.LIGHT_GRAY);
		txtCNPJ.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCNPJ.setColumns(10);
		txtCNPJ.setBounds(20, 146, 473, 42);
		getContentPane().add(txtCNPJ);
		txtCNPJ.setDocument(new Validador(14));

		JLabel lblNumero = new JLabel("Número:");
		lblNumero.setForeground(Color.BLACK);
		lblNumero.setFont(new Font("Arial", Font.PLAIN, 24));
		lblNumero.setBounds(20, 289, 127, 39);
		getContentPane().add(lblNumero);

		txtNumero = new JTextField();
		txtNumero.setFont(new Font("Arial", Font.PLAIN, 26));
		txtNumero.setBackground(Color.LIGHT_GRAY);
		txtNumero.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtNumero.setColumns(10);
		txtNumero.setBounds(20, 327, 127, 42);
		getContentPane().add(txtNumero);
		txtNumero.setDocument(new Validador(10));

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setForeground(Color.BLACK);
		lblComplemento.setFont(new Font("Arial", Font.PLAIN, 24));
		lblComplemento.setBounds(157, 289, 336, 39);
		getContentPane().add(lblComplemento);

		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Arial", Font.PLAIN, 26));
		txtBairro.setBackground(Color.LIGHT_GRAY);
		txtBairro.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtBairro.setColumns(10);
		txtBairro.setBounds(516, 236, 481, 42);
		getContentPane().add(txtBairro);
		txtBairro.setDocument(new Validador(30));

		JLabel lblTelCel = new JLabel("Telefone/Celular:");
		lblTelCel.setForeground(Color.BLACK);
		lblTelCel.setFont(new Font("Arial", Font.PLAIN, 24));
		lblTelCel.setBounds(18, 380, 473, 39);
		getContentPane().add(lblTelCel);

		txtTelefone = new JTextField();
		txtTelefone.setFont(new Font("Arial", Font.PLAIN, 26));
		txtTelefone.setBackground(Color.LIGHT_GRAY);
		txtTelefone.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtTelefone.setColumns(10);
		txtTelefone.setBounds(18, 419, 473, 42);
		getContentPane().add(txtTelefone);
		txtTelefone.setDocument(new Validador(12));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(Color.BLACK);
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 24));
		lblEmail.setBounds(516, 380, 191, 39);
		getContentPane().add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 26));
		txtEmail.setBackground(Color.LIGHT_GRAY);
		txtEmail.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtEmail.setColumns(10);
		txtEmail.setBounds(514, 419, 481, 42);
		getContentPane().add(txtEmail);
		txtEmail.setDocument(new Validador(50));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.textHighlight);
		panel_1.setLayout(null);
		panel_1.setBounds(-24, 639, 1021, 42);
		getContentPane().add(panel_1);

		btnEditar = new JButton("EDITAR CLIENTE");
		btnEditar.setBackground(Color.LIGHT_GRAY);
		btnEditar.setFont(new Font("Arial", Font.BOLD, 24));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setToolTipText("Editar cliente");
		btnEditar.setBounds(265, 554, 286, 53);
		getContentPane().add(btnEditar);

		btnAdicionar = new JButton("ADICIONAR CLIENTE");
		btnAdicionar.setForeground(new Color(0, 100, 0));
		btnAdicionar.setBackground(Color.LIGHT_GRAY);
		btnAdicionar.setFont(new Font("Arial", Font.BOLD, 24));
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar cliente");
		btnAdicionar.setBounds(265, 497, 286, 53);
		getContentPane().add(btnAdicionar);

		btnExcluir = new JButton("EXCLUIR CLIENTE");
		btnExcluir.setForeground(Color.RED);
		btnExcluir.setBackground(Color.LIGHT_GRAY);
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 24));
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}

		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir cliente");
		btnExcluir.setBounds(561, 554, 378, 53);
		getContentPane().add(btnExcluir);

		JButton btnLimpar = new JButton("LIMPAR TODOS OS CAMPOS");
		btnLimpar.setBackground(Color.LIGHT_GRAY);
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 24));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar todos os campos");
		btnLimpar.setBounds(561, 497, 378, 53);
		getContentPane().add(btnLimpar);

		JLabel lblID = new JLabel("ID do Cliente:");
		lblID.setForeground(Color.BLACK);
		lblID.setFont(new Font("Arial", Font.PLAIN, 24));
		lblID.setBounds(20, 472, 168, 19);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setFont(new Font("Arial", Font.PLAIN, 26));
		txtID.setBackground(Color.LIGHT_GRAY);
		txtID.setEditable(false);
		txtID.setColumns(10);
		txtID.setBounds(20, 502, 150, 42);
		getContentPane().add(txtID);
		setBounds(100, 100, 1024, 700);
		setLocationRelativeTo(null);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setForeground(Color.BLACK);
		lblEndereco.setFont(new Font("Arial", Font.PLAIN, 24));
		lblEndereco.setBounds(20, 202, 473, 35);
		getContentPane().add(lblEndereco);

		txtEndereco = new JTextField();
		txtEndereco.setFont(new Font("Arial", Font.PLAIN, 26));
		txtEndereco.setBackground(Color.LIGHT_GRAY);
		txtEndereco.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtEndereco.setColumns(10);
		txtEndereco.setBounds(20, 236, 473, 42);
		getContentPane().add(txtEndereco);
		txtEndereco.setDocument(new Validador(100));

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setForeground(Color.BLACK);
		lblBairro.setFont(new Font("Arial", Font.PLAIN, 24));
		lblBairro.setBounds(516, 199, 481, 39);
		getContentPane().add(lblBairro);

		txtComplemento = new JTextField();
		txtComplemento.setFont(new Font("Arial", Font.PLAIN, 26));
		txtComplemento.setBackground(Color.LIGHT_GRAY);
		txtComplemento.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtComplemento.setColumns(10);
		txtComplemento.setBounds(157, 327, 336, 42);
		getContentPane().add(txtComplemento);
		txtComplemento.setDocument(new Validador(20));

		txtCEP = new JTextField();
		txtCEP.setFont(new Font("Arial", Font.PLAIN, 26));
		txtCEP.setBackground(Color.LIGHT_GRAY);
		txtCEP.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtCEP.setColumns(10);
		txtCEP.setBounds(516, 146, 283, 42);
		getContentPane().add(txtCEP);
		txtCEP.setDocument(new Validador(8));

		JLabel lblCEP = new JLabel("CEP:");
		lblCEP.setForeground(Color.BLACK);
		lblCEP.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCEP.setBounds(516, 120, 283, 27);
		getContentPane().add(lblCEP);

		JLabel lblUF = new JLabel("UF:");
		lblUF.setForeground(Color.BLACK);
		lblUF.setFont(new Font("Arial", Font.PLAIN, 24));
		lblUF.setBounds(869, 289, 105, 39);
		getContentPane().add(lblUF);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setForeground(Color.BLACK);
		lblCidade.setFont(new Font("Arial", Font.PLAIN, 24));
		lblCidade.setBounds(516, 289, 334, 39);
		getContentPane().add(lblCidade);

		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Arial", Font.PLAIN, 26));
		txtCidade.setBackground(Color.LIGHT_GRAY);
		txtCidade.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtCidade.setColumns(10);
		txtCidade.setBounds(516, 327, 334, 42);
		getContentPane().add(txtCidade);

		cboUF = new JComboBox();
		cboUF.setFont(new Font("Arial", Font.PLAIN, 20));
		cboUF.setBackground(Color.LIGHT_GRAY);
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUF.setBounds(869, 327, 105, 42);
		getContentPane().add(cboUF);

		JButton btnPesqCEP = new JButton("PESQ. CEP");
		btnPesqCEP.setFont(new Font("Arial", Font.BOLD, 19));
		btnPesqCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnPesqCEP.setBounds(814, 146, 185, 42);
		getContentPane().add(btnPesqCEP);

	}

	/**
	 * Metodo responsavel por: limpar os campos.
	 */
	private void limparCampos() {

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
		cboUF.setSelectedIndex(0);

	}

	/**
	 * Metodo responsavel por: adicionar cliente.
	 */

	private void adicionar() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro");
			txtBairro.requestFocus();

		} else {

			String create = "insert into clientes(nome,cpf,rg, cnpj, endereco, numero, complemento, bairro, cep , cidade, uf,  fone, email) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";

			try {

				con = dao.conectar();

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

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Cliente adicionado");

				limparCampos();

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				System.out.println(e1);

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	/**
	 * Metodo responsavel por: editar cliente.
	 */

	private void editar() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();

		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o endereço");
			txtEndereco.requestFocus();
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o numero");
			txtNumero.requestFocus();
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o bairro");
			txtBairro.requestFocus();

		} else {

			String update = "update clientes set nome=?, cpf=?, rg=?, cnpj=?, endereco=?, numero=?, complemento=?, bairro=?, cep=?, cidade=?, uf=?, fone=?, email=? where idcli=?";

			try {

				con = dao.conectar();

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

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do cliente editados com sucesso!");

				limparCampos();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Metodo responsavel por: excluir um cliente.
	 */

	private void excluir() {
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o Cliente a ser excluido!");
			txtNome.requestFocus();
		} else {
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste cliente?", "Atenção",
					JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {

				String delete = "delete from clientes where idcli= ?";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(delete);
					pst.setString(1, txtID.getText());

					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");
					limparCampos();
					con.close();

				} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
					JOptionPane.showMessageDialog(null, "Cliente não excluido.\nEste cliente tem uma OS pendente.");

				} catch (Exception e2) {
					System.out.println(e2);
				}
			}
		}
	}

	/**
	 * Metodo responsavel por: buscar o endereço pelo CEP.
	 */

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
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsável por: listar cliente pelo nome.
	 */

	@SuppressWarnings("unchecked")
	private void listarClientes() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		listClient.setModel(modelo);

		String readLista = "select *from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";

		try {

			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			while (rs.next()) {

				scrollPaneClient.setVisible(true);

				modelo.addElement(rs.getString(2));

				if (txtNome.getText().isEmpty()) {

					scrollPaneClient.setVisible(false);

				}

			}

			con.close();

		} catch (Exception e) {

			System.out.println(e);

		}

	}

	/**
	 * Metodo responsavel por: fazer a busca do cliente pelo nome.
	 */

	private void buscarCliente() {

		int linha = listClient.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select *from clientes where nome like '" + txtNome.getText() + "%'"

					+ "order by nome limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneClient.setVisible(false);

					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtCPF.setText(rs.getString(3));
					txtRG.setText(rs.getString(4));
					txtCNPJ.setText(rs.getString(5));
					txtEndereco.setText(rs.getString(6));
					txtNumero.setText(rs.getString(7));
					txtComplemento.setText(rs.getString(8));
					txtBairro.setText(rs.getString(9));
					txtCEP.setText(rs.getString(10));
					txtCidade.setText(rs.getString(11));

					cboUF.setSelectedItem(rs.getString(12));

					txtTelefone.setText(rs.getString(13));
					txtEmail.setText(rs.getString(14));

				} else {

					JOptionPane.showMessageDialog(null, "Cliente inexistente");

				}

				con.close();

			} catch (Exception e) {

			}

		} else {

			scrollPaneClient.setVisible(false);

		}
	}

}
