package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.DAO;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.SystemColor;

public class Principal extends JFrame {
	// Instanciar objetos JDBC
	private Connection con;
	DAO dao = new DAO();
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	//estes compenentes serao alterados pela tela de login :D
	public JLabel lblNomeUsuario;
	public JPanel panelRodape;
	public JButton btnUsuarios;
	public JButton btnRelatorios;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
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
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/icon.png")));
			addWindowListener(new WindowAdapter() {
				@Override
				public void windowActivated(WindowEvent e) {
					// ativação da janela
					status();
					setarData();
				
				}
			});
		
		setTitle("SP Assistencia Eletrodomésticos");
		setBounds(100, 100, 622, 620);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setToolTipText("Usuarios");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
				
			}
		});
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/3643746_add_friend_member_people_plus_icon.png")));
		btnUsuarios.setBounds(42, 35, 128, 128);
		contentPane.add(btnUsuarios);
		
		JButton btnSobre = new JButton("");
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorder(null);
		btnSobre.setBorderPainted(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about.png")));
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setBounds(553, -1, 48, 48);
		contentPane.add(btnSobre);
		
		panelRodape = new JPanel();
		panelRodape.setBackground(SystemColor.desktop);
		panelRodape.setBounds(0, 520, 635, 61);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("New label");
		lblData.setBounds(301, 20, 299, 20);
		panelRodape.add(lblData);
		lblData.setForeground(Color.WHITE);
		lblData.setToolTipText("DATA");
		lblData.setFont(new Font("Arial", Font.BOLD, 14));
		
		lblStatus = new JLabel("");
		lblStatus.setBounds(548, 6, 48, 48);
		panelRodape.add(lblStatus);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dataoff.png")));
		lblStatus.setToolTipText("Conexão");
		
		lblNomeUsuario = new JLabel("");
		lblNomeUsuario.setForeground(new Color(255, 255, 255));
		lblNomeUsuario.setBounds(76, 23, 194, 14);
		panelRodape.add(lblNomeUsuario);
		lblNomeUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(11, 23, 61, 14);
		panelRodape.add(lblUsuario);
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 14));
		
		JLabel lblNewLabel = new JLabel("SP ASSISTÊNCIA");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(243, 232, 156, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ELETRODOMÉSTICOS");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(257, 256, 142, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblMenu = new JLabel("Usuarios");
		lblMenu.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMenu.setBounds(73, 11, 72, 14);
		contentPane.add(lblMenu);
		
		JLabel lblLOGO = new JLabel("");
		lblLOGO.setIcon(new ImageIcon(Principal.class.getResource("/img/icon.png")));
		lblLOGO.setBounds(206, 231, 46, 48);
		contentPane.add(lblLOGO);
		
		JButton btnClientes = new JButton("");
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes cliente = new Clientes();
				cliente.setVisible(true);
				

			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/oi-removebg-preview.png")));
		btnClientes.setToolTipText("Clientes");
		btnClientes.setBounds(422, 35, 128, 128);
		contentPane.add(btnClientes);
		
		JLabel lblClientes = new JLabel("Clientes");
		lblClientes.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblClientes.setBounds(453, 11, 72, 14);
		contentPane.add(lblClientes);
		
		JButton btnServicos = new JButton("");
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(new ImageIcon(Principal.class.getResource("/img/4960335_cogwheel_gear_setting_wrench_icon.png")));
		btnServicos.setToolTipText("Serviços");
		btnServicos.setBounds(42, 194, 128, 128);
		contentPane.add(btnServicos);
		
		JLabel lblMenu_2 = new JLabel("Serviço");
		lblMenu_2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMenu_2.setBounds(73, 221, 72, 14);
		contentPane.add(lblMenu_2);
		
		btnRelatorios = new JButton("");
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/2931174_clipboard_copy_paste_analysis_report_icon.png")));
		btnRelatorios.setToolTipText("Relatórios");
		btnRelatorios.setBounds(422, 194, 129, 129);
		contentPane.add(btnRelatorios);
		
		JLabel lblRelatorios = new JLabel("Relatórios");
		lblRelatorios.setToolTipText("Relatórios");
		lblRelatorios.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblRelatorios.setBounds(453, 174, 72, 14);
		contentPane.add(lblRelatorios);
		
		JLabel lblServicos = new JLabel("Serviços");
		lblServicos.setToolTipText("Serviços");
		lblServicos.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblServicos.setBounds(73, 174, 72, 14);
		contentPane.add(lblServicos);
		
		JButton btnFornecedores = new JButton("");
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedor.png")));
		btnFornecedores.setToolTipText("Serviços");
		btnFornecedores.setBounds(42, 353, 128, 128);
		contentPane.add(btnFornecedores);
		
		JLabel lblFor = new JLabel("Fornecedores");
		lblFor.setToolTipText("Serviços");
		lblFor.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFor.setBounds(62, 334, 97, 14);
		contentPane.add(lblFor);
		
		JButton btnProd = new JButton("");
		btnProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Produtos produtos = new Produtos();
				produtos.setVisible(true);
				
			}
		});
		btnProd.setIcon(new ImageIcon(Principal.class.getResource("/img/box.png")));
		btnProd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProd.setToolTipText("Serviços");
		btnProd.setBounds(422, 354, 128, 128);
		contentPane.add(btnProd);
		
		JLabel lblProd = new JLabel("Produtos");
		lblProd.setToolTipText("Serviços");
		lblProd.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblProd.setBounds(453, 334, 72, 14);
		contentPane.add(lblProd);
	

	}
	/**
	 * Método responsável por exibir o status da conexão
	 * 
	 */

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

	}//Fim do método status

	/**
	 * Método responsável por setar a data no rodape
	 */
	private void setarData() {
		Date data = new Date();
		//CRIAR OBJETO PARA FORMATAR A DATA
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		//alterar o texto da label pela data atual formataada
		lblData.setText(formatador.format(data));
		setLocationRelativeTo(null);
	}
}
