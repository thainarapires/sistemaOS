package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
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
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;

public class Produtos extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtBarras;
	private JTextField txtID;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();

	private FileInputStream fis;

	private int tamanho;
	private JLabel lblFoto;
	private JTextField txtEstoqueMin;
	private JTextField txtEstoque;
	private JTextField txtArmazem;
	private JTextField txtValor;
	@SuppressWarnings("rawtypes")
	private JComboBox cboMedida;
	private JTextField txtIDFor;
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnBorracha;
	private JTextField txtNome;
	private JScrollPane scrollPaneNome;
	@SuppressWarnings("rawtypes")
	private JList listNome;
	private JTextField txtLote;
	private JTextField txtFabricante;
	private JTextField txtLucro;
	private JScrollPane scrollPane;
	private JTextArea txtDesc;
	private JDateChooser dateEnt;
	@SuppressWarnings("rawtypes")
	private JList listBarras;
	private JScrollPane scrollPaneBarras;
	private JTextField txtCodigoBarras;
	private JCheckBox checkAlterar;
	private JTextField txtFornecedor;
	private JScrollPane scrollPaneFor;
	@SuppressWarnings("rawtypes")
	private JList listFor;

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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/logo (1).png")));
		setTitle("Produtos");
		setType(Type.UTILITY);
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneNome.setVisible(false);
				
				scrollPaneBarras.setVisible(false);
				scrollPaneFor.setVisible(false);

			}
		});
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

			}
		});

		scrollPaneNome = new JScrollPane();
		scrollPaneNome.setVisible(false);

		scrollPaneBarras = new JScrollPane();
		scrollPaneBarras.setVisible(false);

		scrollPaneFor = new JScrollPane();
		scrollPaneFor.setVisible(false);
		scrollPaneFor.setBounds(682, 101, 118, 36);
		getContentPane().add(scrollPaneFor);

		listFor = new JList();
		scrollPaneFor.setViewportView(listFor);
		listFor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarForLista();
			}
		});
		scrollPaneBarras.setBounds(532, 101, 140, 36);
		getContentPane().add(scrollPaneBarras);

		listBarras = new JList();
		listBarras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarPlaca();
			}
		});
		scrollPaneBarras.setViewportView(listBarras);
		scrollPaneNome.setBounds(22, 101, 285, 23);
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

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(SystemColor.control);
		panel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneNome.setVisible(false);
			
			}
		});
		panel_1_1.setBounds(0, 0, 1138, 40);
		getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);

		JLabel lblTitulo = new JLabel("PRODUTOS");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setForeground(new Color(0, 128, 255));
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
		lblTitulo.setBounds(0, 0, 1105, 40);
		panel_1_1.add(lblTitulo);

		JPanel panel_1_1_1 = new JPanel();
		panel_1_1_1.setBackground(SystemColor.control);
		panel_1_1_1.setLayout(null);
		panel_1_1_1.setBounds(-11, 472, 1191, 49);
		getContentPane().add(panel_1_1_1);

		txtBarras = new JTextField();
		txtBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarCodigoPlaca();
			}
		});
		txtBarras.setFont(new Font("Arial", Font.PLAIN, 16));
		txtBarras.setColumns(10);
		txtBarras.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtBarras.setBackground(SystemColor.menu);
		txtBarras.setBounds(532, 76, 140, 29);
		txtBarras.setDocument(new Validador(100));
		getContentPane().add(txtBarras);

		JLabel lblBarCode = new JLabel("Código da placa:");
		lblBarCode.setFont(new Font("Arial", Font.PLAIN, 16));
		lblBarCode.setBounds(532, 51, 140, 19);
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
						
								txtIDFor = new JTextField();
								txtIDFor.setEditable(false);
								txtIDFor.setBounds(10, 90, 64, 29);
								panel.add(txtIDFor);
								
								txtIDFor.setFont(new Font("Arial", Font.PLAIN, 16));
								txtIDFor.setColumns(10);
								txtIDFor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
								txtIDFor.setBackground(SystemColor.control);
								
										JLabel lblIdFornecedor = new JLabel("ID Fornecedor");
										lblIdFornecedor.setBounds(10, 65, 140, 19);
										panel.add(lblIdFornecedor);
										lblIdFornecedor.setToolTipText("ID do Fornecedor");
										lblIdFornecedor.setFont(new Font("Arial", Font.PLAIN, 16));

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescricao.setBounds(22, 116, 156, 19);
		getContentPane().add(lblDescricao);

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
		txtEstoqueMin.setBounds(532, 152, 98, 30);
		txtEstoqueMin.setDocument(new Validador(60));

		getContentPane().add(txtEstoqueMin);

		JLabel lblEstoque = new JLabel("Estoque*");
		lblEstoque.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque.setBounds(413, 132, 98, 19);
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
		txtEstoque.setBounds(413, 151, 98, 31);
		txtEstoque.setDocument(new Validador(60));
		getContentPane().add(txtEstoque);

		JLabel lblDescricao_1_1 = new JLabel("Estoque Mínimo*");
		lblDescricao_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDescricao_1_1.setBounds(532, 132, 136, 19);
		getContentPane().add(lblDescricao_1_1);

		JLabel lblUnidades = new JLabel("Unidade:");
		lblUnidades.setFont(new Font("Arial", Font.PLAIN, 16));
		lblUnidades.setBounds(672, 132, 71, 19);
		getContentPane().add(lblUnidades);

		cboMedida = new JComboBox();
		cboMedida.setModel(new DefaultComboBoxModel(new String[] { "", "UN", "CX", "PC", "Kg", "M", "CM" }));
		cboMedida.setBounds(672, 151, 71, 30);
		getContentPane().add(cboMedida);

		JLabel lblLocalDeArmazenagem = new JLabel("Local de Armazenagem:");
		lblLocalDeArmazenagem.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLocalDeArmazenagem.setBounds(22, 214, 189, 19);
		getContentPane().add(lblLocalDeArmazenagem);

		txtArmazem = new JTextField();
		txtArmazem.setFont(new Font("Arial", Font.PLAIN, 16));
		txtArmazem.setColumns(10);
		txtArmazem.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtArmazem.setBackground(SystemColor.menu);
		txtArmazem.setBounds(22, 239, 261, 29);
		txtArmazem.setDocument(new Validador(50));
		getContentPane().add(txtArmazem);

		JLabel lblEstoque_1_1 = new JLabel("Valor");
		lblEstoque_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque_1_1.setBounds(22, 279, 156, 19);
		getContentPane().add(lblEstoque_1_1);

		txtValor = new JTextField();
		txtValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});

		txtValor.setFont(new Font("Arial", Font.PLAIN, 16));
		txtValor.setColumns(10);
		txtValor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtValor.setBackground(SystemColor.menu);
		txtValor.setBounds(22, 304, 261, 29);
		txtValor.setDocument(new Validador(60));
		getContentPane().add(txtValor);

		btnAdicionar = new JButton("");
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
		btnAdicionar.setBounds(475, 377, 68, 68);
		getContentPane().add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkAlterar.isSelected()) {
					editar();
				} else {

					editarExcetoImagem();
				}
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setContentAreaFilled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/editprod.png")));
		btnEditar.setBounds(553, 377, 64, 64);
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
		btnExcluir.setBounds(632, 377, 64, 64);
		getContentPane().add(btnExcluir);

		JLabel lblIDFor = new JLabel("Fornecedor:");
		lblIDFor.setFont(new Font("Arial", Font.PLAIN, 16));
		lblIDFor.setBounds(682, 51, 118, 19);
		getContentPane().add(lblIDFor);

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
		btnBorracha.setBounds(706, 379, 64, 64);
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
		txtNome.setBounds(22, 76, 285, 29);
		txtNome.setDocument(new Validador(100));
		getContentPane().add(txtNome);

		txtLote = new JTextField();
		txtLote.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLote.setColumns(10);
		txtLote.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLote.setBackground(SystemColor.menu);
		txtLote.setBounds(293, 239, 197, 29);
		txtLote.setDocument(new Validador(20));
		getContentPane().add(txtLote);

		JLabel lblLote = new JLabel("Lote");
		lblLote.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLote.setBounds(293, 214, 189, 19);
		getContentPane().add(lblLote);

		txtFabricante = new JTextField();
		txtFabricante.setFont(new Font("Arial", Font.PLAIN, 16));
		txtFabricante.setColumns(10);
		txtFabricante.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFabricante.setBackground(SystemColor.menu);
		txtFabricante.setBounds(502, 239, 291, 29);
		txtFabricante.setDocument(new Validador(20));
		getContentPane().add(txtFabricante);

		JLabel lblFabricante = new JLabel("Fabricante:");
		lblFabricante.setFont(new Font("Arial", Font.PLAIN, 16));
		lblFabricante.setBounds(502, 214, 189, 19);
		getContentPane().add(lblFabricante);

		JLabel lblDataEntrada = new JLabel("Data entrada:");
		lblDataEntrada.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDataEntrada.setBounds(505, 279, 156, 19);
		getContentPane().add(lblDataEntrada);

		JLabel lblEstoque_1_1_1 = new JLabel("Lucro:");
		lblEstoque_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque_1_1_1.setBounds(293, 279, 156, 19);
		getContentPane().add(lblEstoque_1_1_1);

		txtLucro = new JTextField();
		txtLucro.setText("0");
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}

		});
		txtLucro.setFont(new Font("Arial", Font.PLAIN, 16));
		txtLucro.setColumns(10);
		txtLucro.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtLucro.setBackground(SystemColor.menu);
		txtLucro.setBounds(293, 304, 149, 29);
		txtLucro.setDocument(new Validador(60));

		getContentPane().add(txtLucro);

		dateEnt = new JDateChooser();
		dateEnt.setEnabled(false);
		dateEnt.setBounds(502, 304, 136, 29);
		getContentPane().add(dateEnt);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 140, 368, 63);
		getContentPane().add(scrollPane);

		txtDesc = new JTextArea();
		txtDesc.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDesc.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtDesc.setBackground(SystemColor.control);
		txtDesc.setDocument(new Validador(100));
		scrollPane.setViewportView(txtDesc);

		JLabel lblNewLabel = new JLabel("%");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(447, 307, 46, 23);
		getContentPane().add(lblNewLabel);

		txtCodigoBarras = new JTextField();
		txtCodigoBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarBarcode();
				}
			}
		});
		txtCodigoBarras.setFont(new Font("Arial", Font.PLAIN, 16));
		txtCodigoBarras.setColumns(10);
		txtCodigoBarras.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtCodigoBarras.setBackground(SystemColor.menu);
		txtCodigoBarras.setBounds(355, 76, 156, 29);
		txtCodigoBarras.setDocument(new Validador(100));
		getContentPane().add(txtCodigoBarras);

		checkAlterar = new JCheckBox("Alterar a imagem");
		checkAlterar.setHorizontalAlignment(SwingConstants.CENTER);
		checkAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (checkAlterar.isSelected()) {

					carregarFoto();
				} else {

				}
			}
		});
		checkAlterar.setBounds(849, 404, 188, 23);
		getContentPane().add(checkAlterar);

		JButton btnLupa = new JButton("");
		btnLupa.setContentAreaFilled(false);
		btnLupa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lupa();

			}
		});
		btnLupa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLupa.setBorderPainted(false);
		btnLupa.setIcon(new ImageIcon(Produtos.class.getResource("/img/LUPA32px.png")));
		btnLupa.setBounds(313, 72, 32, 32);
		getContentPane().add(btnLupa);

		txtFornecedor = new JTextField();
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFor();
			}
		});
		txtFornecedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		txtFornecedor.setFont(new Font("Arial", Font.PLAIN, 16));
		txtFornecedor.setColumns(10);
		txtFornecedor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFornecedor.setBackground(SystemColor.menu);
		txtFornecedor.setBounds(682, 76, 118, 29);
		getContentPane().add(txtFornecedor);
		
		JLabel lblEstoque_1_1_2 = new JLabel("Modelo da TV:");
		lblEstoque_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblEstoque_1_1_2.setBounds(355, 51, 174, 19);
		getContentPane().add(lblEstoque_1_1_2);
		setBounds(100, 100, 1114, 539);

	}

	/**
	 * Metodo responsavel por: carreagar foto do produto.
	 */
	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();

		jfc.setDialogTitle("Selecionar arquivo de IMAGEM");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens(*.PNG,*.JPG,*.JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {

				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
				btnAdicionar.setEnabled(true);
				btnEditar.setEnabled(true);

			} catch (Exception e) {
				System.out.println(e);

			}
		} else if (resultado == JFileChooser.CANCEL_OPTION)
			checkAlterar.setSelected(false);

		{

		}

	}

	/**
	 * Metodo responsavel por: adicionar um produto.
	 */
	private void adicionar() {
		if (txtIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Id Fornecedor'.");
			txtIDFor.requestFocus();
		}

		else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque'.");
			txtEstoque.requestFocus();

		}

		else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Estoque minimo'.");
			txtEstoqueMin.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Valor'.");
			txtValor.requestFocus();
	

		
			btnAdicionar.setEnabled(true);
			btnEditar.setEnabled(true);
			btnExcluir.setEnabled(true);

		} else {

			String create = "INSERT INTO produtos (barcode, descricao, foto, estoque, estoquemin, valor, medida, armazenagem, idfornecedor, nome, lote, fabricante, lucro, dataent, codigobarras, razaosocial) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try {

				con = dao.conectar();

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
				pst.setString(11, txtLote.getText());
				pst.setString(12, txtFabricante.getText());
				pst.setString(13, txtLucro.getText());

				java.util.Date dataAtual = new java.util.Date();
				SimpleDateFormat formatadorEnt = new SimpleDateFormat("yyyyMMdd");
				String dataFormatadaEnt = formatadorEnt.format(dataAtual);
				pst.setString(14, dataFormatadaEnt);


				pst.setString(15, txtCodigoBarras.getText());
				pst.setString(16, txtFornecedor.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Produto adicionado");

				limparCampos();


				con.close();

			} catch (com.mysql.cj.jdbc.exceptions.MysqlDataTruncation se) {
				txtLucro.setText("0");
				adicionar();
			

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Cliente não adicionado.\nEste código de placa ou código de barras já está sendo utilizado. \n!!Verifique se o fornecedor existe.!!");
				txtCodigoBarras.setText(null);
				txtCodigoBarras.requestFocus();
				txtBarras.setText(null);
				txtBarras.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	

	/**
	 * Metodo responsavel por: listar produto pelo nome.
	 */
	@SuppressWarnings("unchecked")
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

	/**
	 * Metodo responsavel por: buscar produto pelo nome.
	 */
	private void buscarNome() {

		int linha = listNome.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select * from produtos where nome like '" + txtNome.getText() + "%'"

					+ "order by nome limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneNome.setVisible(false);

					txtID.setText(rs.getString(1));
					txtBarras.setText(rs.getString(2));
					txtDesc.setText(rs.getNString(3));
					txtEstoque.setText(rs.getString(5));
					txtEstoqueMin.setText(rs.getString(6));
					txtValor.setText(rs.getString(7));
					cboMedida.setSelectedItem(rs.getString(8));
					txtArmazem.setText(rs.getString(9));
					txtIDFor.setText(rs.getString(10));
					txtNome.setText(rs.getString(11));
					txtLote.setText(rs.getString(12));
					txtFabricante.setText(rs.getString(13));
					txtLucro.setText(rs.getString(14));

					String setarDataEnt = rs.getString(15);
					Date dataFormatadaEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
					dateEnt.setDate(dataFormatadaEnt);

					

					txtCodigoBarras.setText(rs.getString(16));
					txtFornecedor.setText(rs.getString(17));

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
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);

				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado");

				}

				con.close();

			} catch (java.lang.NullPointerException se) {

				JOptionPane.showInternalMessageDialog(null, "Não foi encontrada a imagem do produto!");

			} catch (Exception e) {
				System.out.println(e);

			}

		} else {

			scrollPaneNome.setVisible(false);

		}
	}

	/**
	 * Metodo responsavel por: buscar produto pela lupa.
	 */
	private void lupa() {

		String read = "select * from produtos where nome = ?";
		try {

			con = dao.conectar();

			pst = con.prepareStatement(read);
			pst.setString(1, txtNome.getText());

			rs = pst.executeQuery();

			if (rs.next()) {

				scrollPaneNome.setVisible(false);

				txtID.setText(rs.getString(1));
				txtBarras.setText(rs.getString(2));
				txtDesc.setText(rs.getNString(3));
				txtEstoque.setText(rs.getString(5));
				txtEstoqueMin.setText(rs.getString(6));
				txtValor.setText(rs.getString(7));
				cboMedida.setSelectedItem(rs.getString(8));
				txtArmazem.setText(rs.getString(9));
				txtIDFor.setText(rs.getString(10));
				txtNome.setText(rs.getString(11));
				txtLote.setText(rs.getString(12));
				txtFabricante.setText(rs.getString(13));
				txtLucro.setText(rs.getString(14));

				String setarDataEnt = rs.getString(15);
				Date dataFormatadaEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
				dateEnt.setDate(dataFormatadaEnt);

				

				txtCodigoBarras.setText(rs.getString(16));
				txtFornecedor.setText(rs.getString(17));

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
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);

				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);

			} else {
				btnAdicionar.setEnabled(true);
				JOptionPane.showMessageDialog(null, "Produto não encontrado");

			}

			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsavel por: editar produto.
	 */
	private void editar() {

		if (txtDesc.getText().isEmpty()) {
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
		} else if (txtLucro.getText().isEmpty()) {
			txtLucro.setText("0");
		} else if (txtArmazem.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo 'Local de Armazenagem'.");
			txtArmazem.requestFocus();

		} else {

			String update = "update produtos set barcode=?, descricao=?, foto=?, estoque=?, estoquemin=?, valor=?, medida=?, armazenagem=?, nome=?, lote=?, fabricante=?, lucro=?, dataent=?, codigobarras=? where codigo=?";

			try {

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

				pst.setString(9, txtNome.getText());
				pst.setString(10, txtLote.getText());
				pst.setString(11, txtFabricante.getText());
				pst.setString(12, txtLucro.getText());

				SimpleDateFormat formatadorEnt = new SimpleDateFormat("yyyyMMdd");
				String dataFormatadaEnt = formatadorEnt.format(dateEnt.getDate());
				pst.setString(13, dataFormatadaEnt);

				pst.setString(14, txtCodigoBarras.getText());
				pst.setString(15, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso!");

				limparCampos();

				con.close();
			} catch (com.mysql.cj.jdbc.exceptions.MysqlDataTruncation se) {
				txtLucro.setText("0");
				editar();
			} catch (Exception e) {

				System.out.println(e);

			}

		}

	}

	/**
	 * Metodo responsavel por: editar o produto exceto a imagem.
	 */
	private void editarExcetoImagem() {

		if (txtDesc.getText().isEmpty()) {
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

		} else {

			String update = "update produtos set barcode=?, descricao=?, estoque=?, estoquemin=?, valor=?, medida=?, armazenagem=?, nome=?, lote=?, fabricante=?, lucro=?, dataent=?, codigobarras=? where codigo=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);

				pst.setString(1, txtBarras.getText());
				pst.setString(2, txtDesc.getText());
				pst.setString(3, txtEstoque.getText());
				pst.setString(4, txtEstoqueMin.getText());
				pst.setString(5, txtValor.getText());
				pst.setString(6, cboMedida.getSelectedItem().toString());
				pst.setString(7, txtArmazem.getText());

				pst.setString(8, txtNome.getText());
				pst.setString(9, txtLote.getText());
				pst.setString(10, txtFabricante.getText());
				pst.setString(11, txtLucro.getText());

				SimpleDateFormat formatadorEnt = new SimpleDateFormat("yyyyMMdd");
				String dataFormatadaEnt = formatadorEnt.format(dateEnt.getDate());
				pst.setString(12, dataFormatadaEnt);

				pst.setString(13, txtCodigoBarras.getText());
				pst.setString(14, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do produto editados com sucesso!");

				limparCampos();

				con.close();

			} catch (Exception e) {

				System.out.println(e);

			}
		}
	}

	/**
	 * Metodo responsavel por: excluir produto.
	 */
	private void excluir() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste produtos?", "Atenção",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {

			String delete = "delete from produtos where codigo= ?";

			try {
				con = dao.conectar();

				pst = con.prepareStatement(delete);
				pst.setString(1, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");

				limparCampos();

				con.close();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	/**
	 * Metodo responsavel por: buscar produto pelo codigo.
	 */
	private void buscarProduto() {

		String produto = JOptionPane.showInputDialog("Digite o código do produto: ");

		if (produto != null) {

			String read = "select * from produtos inner join fornecedores on produtos.idfornecedor = fornecedores.idfornecedor where codigo=?";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(read);

				pst.setString(1, produto);

				rs = pst.executeQuery();

				if (rs.next()) {

					txtID.setText(rs.getString(1));
					txtBarras.setText(rs.getString(2));
					txtDesc.setText(rs.getNString(3));

					txtEstoque.setText(rs.getString(5));
					txtEstoqueMin.setText(rs.getString(6));
					txtValor.setText(rs.getString(7));
					cboMedida.setSelectedItem(rs.getString(8));
					txtArmazem.setText(rs.getString(9));
					txtIDFor.setText(rs.getString(10));
					txtNome.setText(rs.getString(11));
					txtLote.setText(rs.getString(12));
					txtFabricante.setText(rs.getString(13));
					txtLucro.setText(rs.getString(14));

					String setarDataEnt = rs.getString(15);
					Date dataFormatadaEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
					dateEnt.setDate(dataFormatadaEnt);


					txtCodigoBarras.setText(rs.getString(16));
					txtFornecedor.setText(rs.getString(17));

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
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);

				} else {
					btnAdicionar.setEnabled(true);

					JOptionPane.showMessageDialog(null, "Produto inexistente");

				}
				con.close();

			} catch (java.lang.NullPointerException se) {

				JOptionPane.showInternalMessageDialog(null, "Não foi encontrada a imagem do produto!");

			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}

	/**
	 * Metodo responsavel por: lista produto pelo codigo de placa .
	 */
	@SuppressWarnings("unchecked")
	private void listarCodigoPlaca() {
		DefaultListModel<String> modelo = new DefaultListModel<>();

		listBarras.setModel(modelo);

		String readLista = "select * from produtos where barcode like '" + txtBarras.getText() + "%'"
				+ "order by barcode";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				modelo.addElement(rs.getString(2));
				scrollPaneBarras.setVisible(true);

				if (txtBarras.getText().isEmpty()) {
					scrollPaneBarras.setVisible(false);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsavel por: buscar produto pelo codigo da placa.
	 */
	private void buscarPlaca() {

		int linha = listBarras.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select * from produtos where barcode like '" + txtBarras.getText() + "%'"

					+ "order by barcode limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneNome.setVisible(false);

					txtID.setText(rs.getString(1));
					txtBarras.setText(rs.getString(2));
					txtDesc.setText(rs.getNString(3));
					txtEstoque.setText(rs.getString(5));
					txtEstoqueMin.setText(rs.getString(6));
					txtValor.setText(rs.getString(7));
					cboMedida.setSelectedItem(rs.getString(8));
					txtArmazem.setText(rs.getString(9));
					txtIDFor.setText(rs.getString(10));
					txtNome.setText(rs.getString(11));
					txtLote.setText(rs.getString(12));
					txtFabricante.setText(rs.getString(13));
					txtLucro.setText(rs.getString(14));

					String setarDataEnt = rs.getString(15);
					Date dataFormatadaEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
					dateEnt.setDate(dataFormatadaEnt);
					
					txtCodigoBarras.setText(rs.getString(16));
					txtFornecedor.setText(rs.getString(17));

					
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
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);

					

				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado");

				}

				con.close();

			} catch (java.lang.NullPointerException se) {

				JOptionPane.showInternalMessageDialog(null, "Não foi encontrada a imagem do produto!");

			} catch (Exception e) {
				System.out.println(e);

			}

		} else {

			scrollPaneNome.setVisible(false);

		}
	}

	/**
	 * Metodo responsavel por: buscar produto pelo codigo de barras.
	 */

	private void buscarBarcode() {
		String readCodigodebarras = "select * from produtos where codigobarras =?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readCodigodebarras);
			pst.setString(1, txtCodigoBarras.getText());
			rs = pst.executeQuery();
			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtBarras.setText(rs.getString(2));
				txtDesc.setText(rs.getNString(3));
				txtEstoque.setText(rs.getString(5));
				txtEstoqueMin.setText(rs.getString(6));
				txtValor.setText(rs.getString(7));
				cboMedida.setSelectedItem(rs.getString(8));
				txtArmazem.setText(rs.getString(9));
				txtIDFor.setText(rs.getString(10));
				txtNome.setText(rs.getString(11));
				txtLote.setText(rs.getString(12));
				txtFabricante.setText(rs.getString(13));
				txtLucro.setText(rs.getString(14));

				String setarDataEnt = rs.getString(15);
				Date dataFormatadaEnt = new SimpleDateFormat("yyyy-MM-dd").parse(setarDataEnt);
				dateEnt.setDate(dataFormatadaEnt);
				txtCodigoBarras.setText(rs.getString(16));
				txtFornecedor.setText(rs.getString(17));



				Blob blob = (Blob) rs.getBlob(4);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));

				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);

		
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(null, "Produto não encontrado");
			}
			con.close();

		} catch (java.lang.NullPointerException se) {

			JOptionPane.showInternalMessageDialog(null, "Não foi encontrada a imagem do produto!");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Metodo responsavel por:listar fornecedor.
	 */
	@SuppressWarnings("unchecked")
	private void listarFor() {
		DefaultListModel<String> modelo = new DefaultListModel<>();

		listFor.setModel(modelo);

		String readLista = "select * from fornecedores where razaosocial like '" + txtFornecedor.getText() + "%'"
				+ "order by razaosocial";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				modelo.addElement(rs.getString(2));
				scrollPaneFor.setVisible(true);

				if (txtFornecedor.getText().isEmpty()) {
					scrollPaneFor.setVisible(false);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsavel por: buscar fornecedor.
	 */
	private void buscarForLista() {

		int linha = listFor.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select *from fornecedores where razaosocial like '" + txtFornecedor.getText()
					+ "%'"

					+ "order by razaosocial limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneFor.setVisible(false);

					txtIDFor.setText(rs.getString(1));
					txtFornecedor.setText(rs.getString(2));

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Produto não encontrado");

				}

				con.close();
			} catch (java.lang.NullPointerException se) {

				JOptionPane.showInternalMessageDialog(null, "Não foi encontrada a imagem do produto!");
			} catch (Exception e) {
				System.out.println(e);

			}

		} else {

			scrollPaneFor.setVisible(false);

		}
	}

	/**
	 * Metodo responsavel por: limpar os campos.
	 */
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
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/bluecamera.png")));
		txtLote.setText(null);
		txtFabricante.setText(null);
		txtLucro.setText(null);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		dateEnt.setDate(null);
		txtCodigoBarras.setText(null);
		checkAlterar.setSelected(false);
		
		scrollPaneNome.setVisible(false);
		scrollPaneBarras.setVisible(false);
		txtFornecedor.setText(null);
		cboMedida.setSelectedIndex(0);
	}
}
