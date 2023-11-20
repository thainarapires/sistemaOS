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
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import model.DAO;

public class Principal extends JFrame {

	private Connection con;
	DAO dao = new DAO();
	@SuppressWarnings("unused")
	private PreparedStatement pst;
	@SuppressWarnings("unused")
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblStatus;
	private JLabel lblData;
	public JPanel panelRodape;
	public JButton btnUsuarios;
	public JButton btnRelatorios;
	public JLabel lblNivelAcesso;
	public JLabel lblNomeUsuario;
	public JPanel painel;
	public JLabel lblWelcome;
	private JButton btnSair;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/Logo.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				status();
				setarData();

			}
		});

		setTitle("SP Assistencia Eletrodomésticos");
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		painel = new JPanel();
		painel.setBackground(new Color(0, 51, 255));
		painel.setBounds(0, -1, 205, 99);
		contentPane.add(painel);
		painel.setLayout(null);

		lblNomeUsuario = new JLabel("usr");
		lblNomeUsuario.setBounds(79, 25, 116, 14);
		painel.add(lblNomeUsuario);
		lblNomeUsuario.setForeground(new Color(255, 255, 255));
		lblNomeUsuario.setFont(new Font("Arial", Font.PLAIN, 14));

		lblNivelAcesso = new JLabel("usr");
		lblNivelAcesso.setForeground(Color.WHITE);
		lblNivelAcesso.setFont(new Font("Arial", Font.PLAIN, 14));
		lblNivelAcesso.setBounds(117, 53, 86, 14);
		painel.add(lblNivelAcesso);

		JLabel lblNivel = new JLabel("Nível Acesso:");
		lblNivel.setForeground(Color.WHITE);
		lblNivel.setFont(new Font("Arial", Font.BOLD, 12));
		lblNivel.setBounds(10, 53, 132, 14);
		painel.add(lblNivel);

		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(10, 25, 86, 14);
		painel.add(lblUsuario);
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 12));

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.text);
		panel.setBounds(-1, 80, 206, 492);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setFocusPainted(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/aboutwhite.png")));
		btnSobre.setBounds(31, 426, 48, 48);
		panel.add(btnSobre);
		btnSobre.setToolTipText("Sobre");
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorderPainted(false);
		btnSobre.setBorder(null);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(-3, 395, 211, 3);
		panel.add(panel_1_1);

		btnUsuarios = new JButton("USUÁRIOS                  ");
		btnUsuarios.setFocusPainted(false);
		btnUsuarios.setFont(new Font("Dialog", Font.BOLD, 12));
		btnUsuarios.setForeground(new Color(255, 255, 255));
		btnUsuarios.setHorizontalAlignment(SwingConstants.LEFT);
		btnUsuarios.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setBounds(11, 37, 187, 39);
		panel.add(btnUsuarios);
		btnUsuarios.setEnabled(false);
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);

			}
		});
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/userrrrwhite.png")));

		JButton btnServicos = new JButton("SERVIÇOS                   ");
		btnServicos.setFocusPainted(false);
		btnServicos.setFont(new Font("Dialog", Font.BOLD, 12));
		btnServicos.setForeground(new Color(255, 255, 255));
		btnServicos.setHorizontalAlignment(SwingConstants.LEFT);
		btnServicos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnServicos.setContentAreaFilled(false);
		btnServicos.setBounds(11, 285, 187, 39);
		panel.add(btnServicos);
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(new ImageIcon(Principal.class.getResource("/img/servwhite.png")));
		btnServicos.setToolTipText("Serviços");

		JButton btnClientes = new JButton("CLIENTES              ");
		btnClientes.setFocusPainted(false);
		btnClientes.setFont(new Font("Dialog", Font.BOLD, 12));
		btnClientes.setForeground(new Color(255, 255, 255));
		btnClientes.setHorizontalAlignment(SwingConstants.LEFT);
		btnClientes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnClientes.setContentAreaFilled(false);
		btnClientes.setBounds(11, 99, 187, 39);
		panel.add(btnClientes);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes cliente = new Clientes();
				cliente.setVisible(true);

			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(new ImageIcon(Principal.class.getResource("/img/usuariosssxwhite.png")));
		btnClientes.setToolTipText("Clientes");

		btnRelatorios = new JButton("RELATÓRIOS              ");
		btnRelatorios.setFocusPainted(false);
		btnRelatorios.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRelatorios.setForeground(new Color(255, 255, 255));
		btnRelatorios.setHorizontalAlignment(SwingConstants.LEFT);
		btnRelatorios.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setBounds(11, 347, 187, 39);
		panel.add(btnRelatorios);
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setIcon(new ImageIcon(Principal.class.getResource("/img/clienterelatwhite.png")));
		btnRelatorios.setToolTipText("Relatórios");

		JButton btnProd = new JButton("PRODUTOS              ");
		btnProd.setFocusPainted(false);
		btnProd.setFont(new Font("Dialog", Font.BOLD, 12));
		btnProd.setForeground(new Color(255, 255, 255));
		btnProd.setHorizontalAlignment(SwingConstants.LEFT);
		btnProd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnProd.setContentAreaFilled(false);
		btnProd.setBounds(11, 223, 187, 39);
		panel.add(btnProd);
		btnProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Produtos produtos = new Produtos();
				produtos.setVisible(true);

			}
		});
		btnProd.setIcon(new ImageIcon(Principal.class.getResource("/img/boxxq.png")));
		btnProd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProd.setToolTipText("Produtos");

		JButton btnFornecedores = new JButton("FORNECEDORES       ");
		btnFornecedores.setFocusPainted(false);
		btnFornecedores.setFont(new Font("Dialog", Font.BOLD, 12));
		btnFornecedores.setForeground(new Color(255, 255, 255));
		btnFornecedores.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnFornecedores.setContentAreaFilled(false);
		btnFornecedores.setHorizontalAlignment(SwingConstants.LEFT);
		btnFornecedores.setBounds(11, 161, 187, 39);
		panel.add(btnFornecedores);
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setIcon(new ImageIcon(Principal.class.getResource("/img/fornecedorrr.png")));
		btnFornecedores.setToolTipText("Fornecedores");

		btnSair = new JButton("SAIR");
		btnSair.setToolTipText("Voltar para o Login");
		btnSair.setFocusPainted(false);
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Login login = new Login();
				login.setVisible(true);
				
				dispose();
			}
		});
		btnSair.setBounds(93, 435, 116, 31);
		panel.add(btnSair);
		btnSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSair.setBorderPainted(false);
		btnSair.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnSair.setHorizontalAlignment(SwingConstants.LEFT);
		btnSair.setForeground(Color.WHITE);
		btnSair.setContentAreaFilled(false);
		btnSair.setIcon(new ImageIcon(Principal.class.getResource("/img/loggout.png")));
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalTextPosition(SwingConstants.LEADING);
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/sois.png")));
		lblNewLabel.setBounds(-58, -94, 538, 1033);
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 24, 211, 0);
		panel.add(panel_1);

		panelRodape = new JPanel();
		panelRodape.setBackground(new Color(0, 51, 255));
		panelRodape.setBounds(201, 526, 614, 59);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);

		lblData = new JLabel("Data e Hora");
		lblData.setHorizontalAlignment(SwingConstants.CENTER);
		lblData.setBounds(12, 0, 503, 48);
		panelRodape.add(lblData);
		lblData.setForeground(Color.WHITE);
		lblData.setToolTipText("DATA");
		lblData.setFont(new Font("Arial", Font.BOLD, 18));

		lblStatus = new JLabel("");
		lblStatus.setBounds(554, 0, 48, 48);
		panelRodape.add(lblStatus);
		lblStatus.setIcon(new ImageIcon(Principal.class.getResource("/img/dataoffline.png")));
		lblStatus.setToolTipText("Conexão");

		JLabel lblLogo = new JLabel("");
		lblLogo.setBounds(373, 51, 256, 160);
		contentPane.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/logo (1).png")));

		JLabel lblNewLabel_1 = new JLabel("SEJA BEM VINDO(A)");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 36));
		lblNewLabel_1.setBounds(305, 199, 400, 99);
		contentPane.add(lblNewLabel_1);

		lblWelcome = new JLabel("Administrador");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBackground(SystemColor.desktop);
		lblWelcome.setForeground(SystemColor.desktop);
		lblWelcome.setFont(new Font("Arial", Font.PLAIN, 36));
		lblWelcome.setBounds(303, 279, 390, 46);
		contentPane.add(lblWelcome);

	}

	/**
	 * Metodo responsavel por: exibir o status da conexao
	 * 
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

	/**
	 * Metodo responsavel por setar a data no rodape
	 */
	private void setarData() {
		Date data = new Date();

		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);

		lblData.setText(formatador.format(data));
		setLocationRelativeTo(null);
	}
}
