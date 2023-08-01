package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.net.URI;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.DAO;
import com.toedter.calendar.JDateChooser;

public class Produtos extends JDialog {
	private JTextField txtBarras;
	private JTextField txtID;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();
	
	private FileInputStream fis;
	
	private int tamanho;
	private JLabel lblFoto;
	private JTextField txtDesc;
	private JTextField txtEstoqueMin;
	private JTextField txtEstoque;
	private JTextField txtArmazem;
	private JTextField txtValor;
	private JComboBox cboMedida;
	private JTextField txtIDFor;
	private JList listProd;
	private JScrollPane scrollPaneProd;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnBorracha;
	private JTextField txtNome;
	private JScrollPane scrollPaneNome;
	private JList listNome;
	private JTextField txtLote;
	private JTextField txtFabricante;
	private JTextField txtGarantia;
	private JTextField txtLucro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Produtos dialog = new Produtos();
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
	public Produtos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneNome.setVisible(false);
				scrollPaneProd.setVisible(false);
			}
		});
		getContentPane().setBackground(SystemColor.text);
		getContentPane().setLayout(null);
		
		JButton btnFoto = new JButton("Carregar foto");
		btnFoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFoto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
				btnAdicionar.setEnabled(true);
			
				
				
			}
		});
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowActivated(WindowEvent e) {
					
				}
			});
		
		
		
		scrollPaneProd = new JScrollPane();
		scrollPaneProd.setVisible(false);
		
		scrollPaneNome = new JScrollPane();
		scrollPaneNome.setVisible(false);
		scrollPaneNome.setBounds(22, 101, 368, 23);
		getContentPane().add(scrollPaneNome);
		
		listNome = new JList();
		listNome.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarNome();
			}
		});
		listNome.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPaneNome.setViewportView(listNome);
		scrollPaneProd.setBounds(598, 101, 195, 23);
		getContentPane().add(scrollPaneProd);
		
		listProd = new JList();
		listProd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			buscarForLista();
			}
		});
		scrollPaneProd.setViewportView(listProd);
		btnFoto.setForeground(new Color(0, 128, 255));
		btnFoto.setFont(new Font("Arial", Font.BOLD, 16));
		btnFoto.setBounds(881, 407, 141, 23);
		getContentPane().add(btnFoto);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(0, 0, 1098, 40);
		getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel lblTitulo = new JLabel("ÁREA DE PRODUTOS:");
		lblTitulo.setForeground(new Color(0, 128, 255));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(451, 0, 251, 40);
		panel_1_1.add(lblTitulo);
		
		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBounds(0, 475, 1098, 40);
		getContentPane().add(panel_1_1_1);
		
		txtBarras = new JTextField();
		txtBarras.setFont(new Font("Arial", Font.PLAIN, 16));
		txtBarras.setColumns(10);
		txtBarras.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtBarras.setBackground(SystemColor.menu);
		txtBarras.setBounds(400, 76, 188, 29);
		getContentPane().add(txtBarras);
		
		JLabel lblBarCode = new JLabel("Código de barras*");
		lblBarCode.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBarCode.setBounds(400, 51, 156, 19);
		getContentPane().add(lblBarCode);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(803, 51, 285, 345);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		lblFoto = new JLabel("");
		lblFoto.setBounds(10, 89, 265, 256);
		panel.add(lblFoto);
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/bluecamera.png")));
		
		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(10, 34, 218, 29);
		panel.add(txtID);
		txtID.setFont(new Font("Arial", Font.PLAIN, 16));
		txtID.setColumns(10);
		txtID.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtID.setBackground(SystemColor.activeCaptionBorder);
		
		JLabel lblID = new JLabel("Código do produto:");
		lblID.setBounds(10, 11, 236, 19);
		panel.add(lblID);
		lblID.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JButton btnPesq = new JButton("");
		btnPesq.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesq.setContentAreaFilled(false);
		btnPesq.setBorderPainted(false);
		btnPesq.setIcon(new ImageIcon(Produtos.class.getResource("/img/zoom.png")));
		btnPesq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProduto();
			}
		});
		btnPesq.setBounds(238, 31, 32, 32);
		panel.add(btnPesq);
		
		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescricao.setBounds(22, 116, 156, 19);
		getContentPane().add(lblDescricao);
		
		txtDesc = new JTextField();
		txtDesc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDesc.setColumns(10);
		txtDesc.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtDesc.setBackground(SystemColor.menu);
		txtDesc.setBounds(22, 138, 368, 29);
		getContentPane().add(txtDesc);
		
		txtEstoqueMin = new JTextField();
		txtEstoqueMin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

				e.consume();
				}
			}
		});
		txtEstoqueMin.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEstoqueMin.setColumns(10);
		txtEstoqueMin.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtEstoqueMin.setBackground(SystemColor.menu);
		txtEstoqueMin.setBounds(546, 138, 136, 29);
		getContentPane().add(txtEstoqueMin);
		
		JLabel lblEstoque = new JLabel("Estoque*");
		lblEstoque.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque.setBounds(400, 113, 156, 19);
		getContentPane().add(lblEstoque);
		
		txtEstoque = new JTextField();
		txtEstoque.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

				e.consume();
				}
			
			}
		});
		txtEstoque.setFont(new Font("Arial", Font.PLAIN, 16));
		txtEstoque.setColumns(10);
		txtEstoque.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtEstoque.setBackground(SystemColor.menu);
		txtEstoque.setBounds(400, 138, 136, 29);
		getContentPane().add(txtEstoque);
		
		JLabel lblDescricao_1_1 = new JLabel("Estoque Mínimo*");
		lblDescricao_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescricao_1_1.setBounds(546, 113, 156, 19);
		getContentPane().add(lblDescricao_1_1);
		
		JLabel lblUnidades = new JLabel("Tipo unidade:");
		lblUnidades.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUnidades.setBounds(694, 113, 156, 19);
		getContentPane().add(lblUnidades);
		
		cboMedida = new JComboBox();
		cboMedida.setModel(new DefaultComboBoxModel(new String[] {"UN", "CX", "PC", "Kg", "m"}));
		cboMedida.setBounds(692, 138, 78, 29);
		getContentPane().add(cboMedida);
		
		JLabel lblLocalDeArmazenagem = new JLabel("Local de Armazenagem:");
		lblLocalDeArmazenagem.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLocalDeArmazenagem.setBounds(22, 178, 189, 19);
		getContentPane().add(lblLocalDeArmazenagem);
		
		txtArmazem = new JTextField();
		txtArmazem.setFont(new Font("Arial", Font.PLAIN, 16));
		txtArmazem.setColumns(10);
		txtArmazem.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtArmazem.setBackground(SystemColor.menu);
		txtArmazem.setBounds(22, 203, 261, 29);
		getContentPane().add(txtArmazem);
		
		JLabel lblEstoque_1_1 = new JLabel("Valor*");
		lblEstoque_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque_1_1.setBounds(22, 304, 156, 19);
		getContentPane().add(lblEstoque_1_1);
		
		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

				e.consume();
				}
			}
		});
		
		txtValor.setFont(new Font("Arial", Font.PLAIN, 16));
		txtValor.setColumns(10);
		txtValor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtValor.setBackground(SystemColor.menu);
		txtValor.setBounds(22, 329, 261, 29);
		getContentPane().add(txtValor);
		
		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setToolTipText("Adicionar");
		btnAdicionar.setContentAreaFilled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/ADICIONAR64X64.png")));
		btnAdicionar.setBounds(350, 392, 68, 68);
		getContentPane().add(btnAdicionar);
		
		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editprod.png")));
		btnEditar.setBounds(428, 392, 64, 64);
		getContentPane().add(btnEditar);
		
		btnExcluir = new JButton("");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluir();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/TRASH64X64.png")));
		btnExcluir.setContentAreaFilled(false);
		btnExcluir.setBorderPainted(false);
		btnExcluir.setBounds(502, 394, 64, 64);
		getContentPane().add(btnExcluir);
		
		JLabel lblIDFor = new JLabel("ID do fornecedor:");
		lblIDFor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIDFor.setBounds(598, 51, 195, 19);
		getContentPane().add(lblIDFor);
		
		txtIDFor = new JTextField();
		txtIDFor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();	
				}
		});
		txtIDFor.setFont(new Font("Arial", Font.PLAIN, 16));
		txtIDFor.setColumns(10);
		txtIDFor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtIDFor.setBackground(SystemColor.control);
		txtIDFor.setBounds(598, 76, 195, 29);
		getContentPane().add(txtIDFor);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNome.setBounds(22, 53, 156, 19);
		getContentPane().add(lblNome);
		
		btnBorracha = new JButton("");
		btnBorracha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnBorracha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBorracha.setBorderPainted(false);
		btnBorracha.setContentAreaFilled(false);
		btnBorracha.setIcon(new ImageIcon(Produtos.class.getResource("/img/ERASES64X64.png")));
		btnBorracha.setBounds(576, 396, 64, 64);
		getContentPane().add(btnBorracha);
		
		txtNome = new JTextField();
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				
				listarNome();
			}
		});
		txtNome.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNome.setColumns(10);
		txtNome.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtNome.setBackground(SystemColor.menu);
		txtNome.setBounds(22, 76, 368, 29);
		getContentPane().add(txtNome);
		
		txtLote = new JTextField();
		txtLote.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLote.setColumns(10);
		txtLote.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLote.setBackground(SystemColor.menu);
		txtLote.setBounds(293, 203, 197, 29);
		getContentPane().add(txtLote);
		
		JLabel lblLote = new JLabel("Lote*");
		lblLote.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLote.setBounds(293, 178, 189, 19);
		getContentPane().add(lblLote);
		
		txtFabricante = new JTextField();
		txtFabricante.setFont(new Font("Arial", Font.PLAIN, 16));
		txtFabricante.setColumns(10);
		txtFabricante.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFabricante.setBackground(SystemColor.menu);
		txtFabricante.setBounds(502, 203, 291, 29);
		getContentPane().add(txtFabricante);
		
		JLabel lblFabricante = new JLabel("Fabricante:");
		lblFabricante.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFabricante.setBounds(502, 178, 189, 19);
		getContentPane().add(lblFabricante);
		
		JLabel lblDataEntrada = new JLabel("Data entrada:");
		lblDataEntrada.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataEntrada.setBounds(508, 243, 156, 19);
		getContentPane().add(lblDataEntrada);
		
		JLabel lblDataValidade = new JLabel("Data validade:");
		lblDataValidade.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataValidade.setBounds(657, 243, 108, 19);
		getContentPane().add(lblDataValidade);
		
		JLabel lblGarantia = new JLabel("Garantia*");
		lblGarantia.setFont(new Font("Arial", Font.PLAIN, 16));
		lblGarantia.setBounds(22, 243, 156, 19);
		getContentPane().add(lblGarantia);
		
		txtGarantia = new JTextField();
		txtGarantia.setFont(new Font("Arial", Font.PLAIN, 16));
		txtGarantia.setColumns(10);
		txtGarantia.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtGarantia.setBackground(SystemColor.menu);
		txtGarantia.setBounds(22, 268, 261, 29);
		getContentPane().add(txtGarantia);
		
		JLabel lblEstoque_1_1_1 = new JLabel("Lucro:");
		lblEstoque_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque_1_1_1.setBounds(293, 243, 156, 19);
		getContentPane().add(lblEstoque_1_1_1);
		
		txtLucro = new JTextField();
		txtLucro.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLucro.setColumns(10);
		txtLucro.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLucro.setBackground(SystemColor.menu);
		txtLucro.setBounds(293, 268, 204, 29);
		getContentPane().add(txtLucro);
		
		JDateChooser dateEnt = new JDateChooser();
		dateEnt.setBounds(505, 268, 136, 29);
		getContentPane().add(dateEnt);
		
		JDateChooser dateVal = new JDateChooser();
		dateVal.setBounds(657, 268, 136, 29);
		getContentPane().add(dateVal);
		setBounds(100, 100, 1114, 552);

	}
	
	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)","png","jpg","jpeg"));
		jfc.showOpenDialog(this);
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}
		private void adicionar() {
		
		// System.out.println("Teste");
		// validação de campos obrigatórios

		
			
			
		if (txtBarras.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Código de Barras'. ");
			txtBarras.requestFocus();
		} else if (txtDesc.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Descrição'.");
			txtDesc.requestFocus();
		} else if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Nome do produto*'.");
			txtNome.requestFocus();
			
			
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque'.");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque Mínimo'.");
			txtEstoqueMin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Valor'.");
			txtValor.requestFocus();
		} else if (txtArmazem.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Local de Armazenagem'.");
			txtArmazem.requestFocus();
		} else if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'ID do Fornecedor'.");
			txtIDFor.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Lote'.");
			txtLote.requestFocus();
		} else if (txtGarantia.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Garantia'.");
			txtGarantia.requestFocus();
			
			
			btnAdicionar.setEnabled(true);
			btnEditar.setEnabled(true);
			btnExcluir.setEnabled(true);
		
	
		
			
		} else {
			// lógica principal
			// CRUD Create
			String create = "INSERT INTO produtos (barcode, descricao, foto, estoque, estoquemin, valor, medida, armazenagem, idfornecedor, nome, lote, fabricante, garantia, lucro) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
			// tratamento de exceções

			try {
				// abrir conexao
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(create);
				
				pst.setString(1, txtBarras.getText());
				pst.setString(2, txtDesc.getText());
				pst.setBlob(3, fis, tamanho);
				pst.setString(4, txtEstoque.getText());
				pst.setString(5, txtEstoqueMin.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, cboMedida.getSelectedItem().toString());
				pst.setString(8, txtArmazem.getText());
				pst.setString(9, txtIDFor.getText());
				pst.setString(10, txtNome.getText());
				pst.setString(11,txtLote.getText());
				pst.setString(12, txtFabricante.getText());
			
				pst.setString(13, txtGarantia.getText());
				pst.setString(14, txtLucro.getText());

				//pst.setString(13, txtDataEnt.getText());
				//pst.setString(14, txtDataVal.getText());
				// executa a query(instrução sql, CRUD)
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "Produto adicionado");
				// limpar campos
				limparCampos();
				
				btnAdicionar.setEnabled(false);
			

				// fechar conection
				con.close();

			//} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				//JOptionPane.showMessageDialog(null, "Cliente não adicionado.\nEste CNPJ ou EMAIL já está sendo utilizado.");
				//txtCNPJ.setText(null);
				//txtCNPJ.requestFocus();
				//txtEmail.setText(null);
				//txtEmail.requestFocus();
				
			
			} catch (Exception e2) {
				System.out.println(e2);
		}
		}
		
	}
		
	private void listarClientes() {
			DefaultListModel<String> modelo = new DefaultListModel<>();

			listProd.setModel(modelo);

			String readLista = "select * from produtos where idfornecedor like '" + txtIDFor.getText() + "%'" + "order by idfornecedor";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readLista);
				rs = pst.executeQuery();

				while (rs.next()) {
					modelo.addElement(rs.getString(10));
					scrollPaneProd.setVisible(true);

					if (txtIDFor.getText().isEmpty()) {
						scrollPaneProd.setVisible(false);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	
		private void buscarForLista() {

			// System.out.println("teste");

			// variavel que captuar o indice da linha da lista

			int linha = listProd.getSelectedIndex();

			if (linha >= 0) {

				// String readBuscaLista=

				// Query (instrução sql)

				// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista

				String readBuscaLista = "select *from produtos where idfornecedor like '" + txtIDFor.getText() + "%'"

						+ "order by idfornecedor limit " + (linha) + " ,1";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(readBuscaLista);

					rs = pst.executeQuery();

					if (rs.next()) {

						scrollPaneProd.setVisible(false);

						txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
						txtBarras.setText(rs.getString(2)); // 2º Campo da Tabela ID
						txtDesc.setText(rs.getString(3)); // 3º Campo da Tabela ID
						txtEstoque.setText(rs.getString(5)); // 4º Campo da Tabela ID
						txtEstoqueMin.setText(rs.getString(6)); // 4º Campo da Tabela ID
						txtValor.setText(rs.getString(7)); // 4º Campo da Tabela ID
						cboMedida.setSelectedItem(rs.getString(8)); // 4º Campo da Tabela ID
						txtArmazem.setText(rs.getString(9)); // 4º Campo da Tabela ID
						txtIDFor.setText(rs.getString(10)); // 4º Campo da Tabela ID
						txtNome.setText(rs.getString(11)); // 4º Campo da Tabela ID
						txtLote.setText(rs.getString(12));
						txtFabricante.setText(rs.getString(13));
						
						txtGarantia.setText(rs.getString(14));
						txtLucro.setText(rs.getString(15));
						//txtDataEnt.setText(rs.getString(14));
						//txtDataVal.setText(rs.getString(15));
						btnEditar.setEnabled(true);
						btnExcluir.setEnabled(true);
						
					
						
						
						Blob blob = (Blob) rs.getBlob(4);
						byte[] img = blob.getBytes(1, (int) blob.length());
						BufferedImage imagem = null;
						try {
								imagem = ImageIO.read(new ByteArrayInputStream(img));
								
						} catch (Exception e) {
							System.out.println(e);
						}
						ImageIcon icone = new ImageIcon(imagem);
						Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
						lblFoto.setIcon(foto);
						
					} else {
						JOptionPane.showMessageDialog(null, "Produto não encontrado");
						
						}

						con.close();

						} catch (Exception e) {
							System.out.println(e);

						 

						}

						 

						} else {

						scrollPaneProd.setVisible(false);

						 

						}
					}
		
		
		private void listarNome() {
			DefaultListModel<String> modelo = new DefaultListModel<>();

			listNome.setModel(modelo);

			String readLista = "select * from produtos where nome like '" + txtNome.getText() + "%'" + "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readLista);
				rs = pst.executeQuery();

				while (rs.next()) {
					modelo.addElement(rs.getString(11));
					scrollPaneNome.setVisible(true);

					if (txtNome.getText().isEmpty()) {
						scrollPaneNome.setVisible(false);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		private void buscarNome() {

			// System.out.println("teste");

			// variavel que captuar o indice da linha da lista

			int linha = listNome.getSelectedIndex();

			if (linha >= 0) {

				// String readBuscaLista=

				// Query (instrução sql)

				// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista

				String readBuscaLista = "select * from produtos where nome like '" + txtNome.getText() + "%'"

						+ "order by nome limit " + (linha) + " ,1";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(readBuscaLista);

					rs = pst.executeQuery();

					if (rs.next()) {

						scrollPaneNome.setVisible(false);

						txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
						txtBarras.setText(rs.getString(2)); // 2º Campo da Tabela ID
						txtDesc.setText(rs.getString(3)); // 3º Campo da Tabela ID
						txtEstoque.setText(rs.getString(5)); // 4º Campo da Tabela ID
						txtEstoqueMin.setText(rs.getString(6)); // 4º Campo da Tabela ID
						txtValor.setText(rs.getString(7)); // 4º Campo da Tabela ID
						cboMedida.setSelectedItem(rs.getString(8)); // 4º Campo da Tabela ID
						txtArmazem.setText(rs.getString(9)); // 4º Campo da Tabela ID
						txtIDFor.setText(rs.getString(10)); // 4º Campo da Tabela ID
						txtNome.setText(rs.getString(11)); // 4º Campo da Tabela ID
						txtLote.setText(rs.getString(12));
						txtFabricante.setText(rs.getString(13));
						
						txtGarantia.setText(rs.getString(14));
						txtLucro.setText(rs.getString(15));
						//txtDataEnt.setText(rs.getString(14));
						//txtDataVal.setText(rs.getString(15));
						btnEditar.setEnabled(true);
						btnExcluir.setEnabled(true);
						
					
						
						
						Blob blob = (Blob) rs.getBlob(4);
						byte[] img = blob.getBytes(1, (int) blob.length());
						BufferedImage imagem = null;
						try {
								imagem = ImageIO.read(new ByteArrayInputStream(img));
								
						} catch (Exception e) {
							System.out.println(e);
						}
						ImageIcon icone = new ImageIcon(imagem);
						Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
						lblFoto.setIcon(foto);
						
					} else {
						JOptionPane.showMessageDialog(null, "Produto não encontrado");
						
						}

						con.close();

						} catch (Exception e) {
							System.out.println(e);

						 

						}

						 

						} else {

						scrollPaneNome.setVisible(false);

						 

						}
					}
	
		
		private void editar() {
			
		
			
			
			if (txtBarras.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'Código de Barras'. ");
				txtBarras.requestFocus();
			} else if (txtDesc.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'Descrição'.");
				txtDesc.requestFocus();
				
				
			} else if (txtEstoque.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque'.");
				txtEstoque.requestFocus();
			} else if (txtEstoqueMin.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque Mínimo'.");
				txtEstoqueMin.requestFocus();
			} else if (txtValor.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'Valor'.");
				txtValor.requestFocus();
			} else if (txtArmazem.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'Local de Armazenagem'.");
				txtArmazem.requestFocus();
			} else if (txtIDFor.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Preencha o campo 'ID do Fornecedor'.");
				txtIDFor.requestFocus();
				
			//	btnAdicionar.setEnabled(true);
				//btnEditar.setEnabled(true);
				//btnExcluir.setEnabled(true);
				

			} else {
				// logica principal
				// CRUD - Update
				String update = "update produtos set barcode=?, descricao=?, foto=?, estoque=?, estoquemin=?, valor=?, medida=?, armazenagem=?, idfornecedor=?, nome=?, lote=?, fabricante=?, garantia=?, lucro=? where codigo=?";
				// trat de exceção
				try {
					// abrir conexão
					con = dao.conectar();
					
					pst = con.prepareStatement(update);
					
					pst.setString(1, txtBarras.getText());
					pst.setString(2, txtDesc.getText());
					pst.setBlob(3, fis, tamanho);
					pst.setString(4, txtEstoque.getText());
					pst.setString(5, txtEstoqueMin.getText());
					pst.setString(6, txtValor.getText());
					pst.setString(7, cboMedida.getSelectedItem().toString());
					pst.setString(8, txtArmazem.getText());
					pst.setString(9, txtIDFor.getText());
					pst.setString(10, txtNome.getText());
					pst.setString(11,txtLote.getText());
					pst.setString(12, txtFabricante.getText());
				
					pst.setString(13, txtGarantia.getText());
					pst.setString(14, txtLucro.getText());
					pst.setString(15, txtID.getText());
					//pst.setString(13, txtDataEnt.getText());
					//pst.setString(14, txtDataVal.getText());
					pst.executeUpdate();
					// confirmar para o user
					JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso!");
					// limpar campos
					limparCampos();
					// fechar conexao
					con.close();
					
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Coloque uma imagem para poder editar o produto!!");
			
					
					System.out.println(e);

				}

			}
			
		}
		
		private void excluir() {
			//System.out.println("teste");
			//validação de exclusao - a variável confirma captura a opção escolhida.
			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produtos?", "Atenção", JOptionPane.YES_NO_OPTION);
			if (confirma == JOptionPane.YES_OPTION) {
				// CRUD - Delete vai excluir o contato
				
		         
				String delete = "delete from produtos where codigo= ?";
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
					JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");
					// limpar campos
					limparCampos();
					// fechar conexao
					con.close();

			//	} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
					//JOptionPane.showMessageDialog(null, "Produto não excluido.\nEste produto e pendente.");
					//txtID.setText(null);
					//txtID.requestFocus();
					
				
				} catch (Exception e2) {
					System.out.println(e2);
			}
			}
			
		}
		
		private void buscarProduto() {
			// captura do numero a OS (sem usar caixa de txt)
			String produto = JOptionPane.showInputDialog("Digite o código do produto: ");

			// System.out.println("Teste do botão buscar");

			// Criar uma variável com a query (instrução do banco)

			// Tratamento de exceções
			String read = "select * from produtos where codigo=?";
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a execucão da query( instrução sql - CRUD Read)
				// o parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
				pst = con.prepareStatement(read);

				// SUBSTITUI A ? PELO NÚMERO DA OS
				pst.setString(1, produto);
				// executar a query e buscar o resultado
				rs = pst.executeQuery();
				// uso da estrutura if else para verificar se existe o contato
				// rs.next() -> se existir um contato no banco
				if (rs.next()) {
					// preencher as caixas de texto com as informações

					txtID.setText(rs.getString(1)); // 1º Campo da Tabela ID
					txtBarras.setText(rs.getString(2)); // 2º Campo da Tabela ID
					txtDesc.setText(rs.getString(3)); // 3º Campo da Tabela ID
					txtEstoque.setText(rs.getString(5)); // 4º Campo da Tabela ID
					txtEstoqueMin.setText(rs.getString(6)); // 4º Campo da Tabela ID
					txtValor.setText(rs.getString(7)); // 4º Campo da Tabela ID
					cboMedida.setSelectedItem(rs.getString(8)); // 4º Campo da Tabela ID
					txtArmazem.setText(rs.getString(9)); // 4º Campo da Tabela ID
					txtIDFor.setText(rs.getString(10)); // 4º Campo da Tabela ID
					txtNome.setText(rs.getString(11)); // 4º Campo da Tabela ID
					txtLote.setText(rs.getString(12));
					txtFabricante.setText(rs.getString(13));	
					txtGarantia.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					//txtDataEnt.setText(rs.getString(14));
					//tDataVal.setText(rs.getString(15));
					
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				
					
					Blob blob = (Blob) rs.getBlob(4);
					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;
					try {
							imagem = ImageIO.read(new ByteArrayInputStream(img));
							
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
					
					
					// btnEditar.setEnabled(true);
					// btnExcluir.setEnabled(true);
				} else {
					// System.out.println("Contaos não cadastrados");
					JOptionPane.showMessageDialog(null, "Produto inexistente");
					// btnAdicionar.setEnabled(true);
					// btnPesquisar.setEnabled(true);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}

		}
				
		
		
		 private void limparCampos() {
				
				txtBarras.setText(null);
				txtNome.setText(null);
				txtEstoque.setText(null);
				txtEstoqueMin.setText(null);
				txtDesc.setText(null);
				txtArmazem.setText(null);
				txtIDFor.setText(null);
				txtID.setText(null);
				txtValor.setText(null);
				lblFoto.setIcon(null);
				txtLote.setText(null);
				txtFabricante.setText(null);
				//txtDataEnt.setText(null);
				//txtDataVal.setText(null);
				txtGarantia.setText(null);
				txtLucro.setText(null);
				btnEditar.setEnabled(false);
				btnExcluir.setEnabled(false);
						
				
}
}