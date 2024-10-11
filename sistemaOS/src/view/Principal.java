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
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.UIManager;

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
	private JLabel lblData;
	public JButton btnUsuarios;
	public JButton btnRelatorios;
	public JLabel lblNivelAcesso;
	public JLabel lblNomeUsuario;
	public JPanel painel;
	public JLabel lblWelcome;
	private JButton btnSair;
	private JPanel panelRodape;

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
		setBounds(100, 100, 1024, 700);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		painel = new JPanel();
		painel.setBackground(SystemColor.textHighlight);
		painel.setBounds(0, -1, 361, 99);
		contentPane.add(painel);
		painel.setLayout(null);

		lblNomeUsuario = new JLabel("usr");
		lblNomeUsuario.setBounds(94, 23, 267, 22);
		painel.add(lblNomeUsuario);
		lblNomeUsuario.setForeground(new Color(255, 255, 255));
		lblNomeUsuario.setFont(new Font("Arial", Font.BOLD, 20));

		lblNivelAcesso = new JLabel("usr");
		lblNivelAcesso.setForeground(Color.WHITE);
		lblNivelAcesso.setFont(new Font("Arial", Font.BOLD, 20));
		lblNivelAcesso.setBounds(143, 48, 218, 28);
		painel.add(lblNivelAcesso);

		JLabel lblNivel = new JLabel("Nível Acesso:");
		lblNivel.setForeground(Color.WHITE);
		lblNivel.setFont(new Font("Arial", Font.BOLD, 20));
		lblNivel.setBounds(10, 51, 195, 22);
		painel.add(lblNivel);

		JLabel lblUsuario = new JLabel("Usuário:");
		lblUsuario.setBounds(10, 23, 86, 22);
		painel.add(lblUsuario);
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setFont(new Font("Arial", Font.BOLD, 20));

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 64));
		panel.setBounds(-1, 80, 361, 660);
		contentPane.add(panel);
		panel.setLayout(null);

		btnUsuarios = new JButton(" Usuários");
		btnUsuarios.setDoubleBuffered(true);
		btnUsuarios.setFocusTraversalPolicyProvider(true);
		btnUsuarios.setOpaque(false);
		btnUsuarios.setAutoscrolls(true);
		btnUsuarios.setFocusCycleRoot(true);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.setHorizontalAlignment(SwingConstants.LEFT);
		btnUsuarios.setBackground(new Color(255, 255, 255));
		btnUsuarios.setFont(new Font("Arial", Font.BOLD, 48));
		btnUsuarios.setForeground(new Color(255, 255, 255));
		btnUsuarios.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnUsuarios.setBounds(10, 413, 341, 61);
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
		btnUsuarios.setIcon(null);

		JButton btnServicos = new JButton(" Serviços");
		btnServicos.setDoubleBuffered(true);
		btnServicos.setFocusTraversalPolicyProvider(true);
		btnServicos.setOpaque(false);
		btnServicos.setAutoscrolls(true);
		btnServicos.setFocusCycleRoot(true);
		btnServicos.setContentAreaFilled(false);
		btnServicos.setHorizontalAlignment(SwingConstants.LEFT);
		btnServicos.setBackground(new Color(255, 255, 255));
		btnServicos.setFont(new Font("Arial", Font.BOLD, 48));
		btnServicos.setForeground(new Color(255, 255, 255));
		btnServicos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnServicos.setBounds(10, 125, 341, 61);
		panel.add(btnServicos);
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servicos servicos = new Servicos();
				servicos.setVisible(true);
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos.setIcon(null);
		btnServicos.setToolTipText("Serviços");

		JButton btnClientes = new JButton(" Clientes");
		btnClientes.setDoubleBuffered(true);
		btnClientes.setFocusTraversalPolicyProvider(true);
		btnClientes.setOpaque(false);
		btnClientes.setAutoscrolls(true);
		btnClientes.setFocusCycleRoot(true);
		btnClientes.setContentAreaFilled(false);
		btnClientes.setHorizontalAlignment(SwingConstants.LEFT);
		btnClientes.setBackground(new Color(255, 255, 255));
		btnClientes.setFont(new Font("Arial", Font.BOLD, 48));
		btnClientes.setForeground(new Color(255, 255, 255));
		btnClientes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnClientes.setBounds(10, 53, 341, 61);
		panel.add(btnClientes);
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes cliente = new Clientes();
				cliente.setVisible(true);

			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClientes.setIcon(null);
		btnClientes.setToolTipText("Clientes");

		btnRelatorios = new JButton(" Relatórios");
		btnRelatorios.setDoubleBuffered(true);
		btnRelatorios.setFocusTraversalPolicyProvider(true);
		btnRelatorios.setOpaque(false);
		btnRelatorios.setAutoscrolls(true);
		btnRelatorios.setFocusCycleRoot(true);
		btnRelatorios.setContentAreaFilled(false);
		btnRelatorios.setHorizontalAlignment(SwingConstants.LEFT);
		btnRelatorios.setBackground(new Color(255, 255, 255));
		btnRelatorios.setFont(new Font("Arial", Font.BOLD, 48));
		btnRelatorios.setForeground(new Color(255, 255, 255));
		btnRelatorios.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnRelatorios.setBounds(10, 197, 341, 61);
		panel.add(btnRelatorios);
		btnRelatorios.setEnabled(false);
		btnRelatorios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorios.setIcon(null);
		btnRelatorios.setToolTipText("Relatórios");

		JButton btnProd = new JButton(" Produtos");
		btnProd.setDoubleBuffered(true);
		btnProd.setFocusTraversalPolicyProvider(true);
		btnProd.setOpaque(false);
		btnProd.setAutoscrolls(true);
		btnProd.setFocusCycleRoot(true);
		btnProd.setContentAreaFilled(false);
		btnProd.setHorizontalAlignment(SwingConstants.LEFT);
		btnProd.setBackground(new Color(255, 255, 255));
		btnProd.setFont(new Font("Arial", Font.BOLD, 48));
		btnProd.setForeground(new Color(255, 255, 255));
		btnProd.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnProd.setBounds(10, 341, 341, 61);
		panel.add(btnProd);
		btnProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Produtos produtos = new Produtos();
				produtos.setVisible(true);

			}
		});
		btnProd.setIcon(null);
		btnProd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnProd.setToolTipText(" Produtos");

		JButton btnFornecedores = new JButton(" Fornecedores");
		btnFornecedores.setDoubleBuffered(true);
		btnFornecedores.setFocusTraversalPolicyProvider(true);
		btnFornecedores.setOpaque(false);
		btnFornecedores.setAutoscrolls(true);
		btnFornecedores.setFocusCycleRoot(true);
		btnFornecedores.setContentAreaFilled(false);
		btnFornecedores.setHorizontalAlignment(SwingConstants.LEFT);
		btnFornecedores.setBackground(new Color(255, 255, 255));
		btnFornecedores.setFont(new Font("Arial", Font.BOLD, 48));
		btnFornecedores.setForeground(new Color(255, 255, 255));
		btnFornecedores.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		btnFornecedores.setBounds(10, 269, 341, 61);
		panel.add(btnFornecedores);
		btnFornecedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnFornecedores.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFornecedores.setIcon(null);
		btnFornecedores.setToolTipText("Fornecedores");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 24, 211, 0);
		panel.add(panel_1);
		
				btnSair = new JButton("SAIR");
				btnSair.setBounds(133, 536, 106, 31);
				panel.add(btnSair);
				btnSair.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
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
				btnSair.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btnSair.setBorderPainted(false);
				btnSair.setHorizontalTextPosition(SwingConstants.RIGHT);
				btnSair.setHorizontalAlignment(SwingConstants.LEFT);
				btnSair.setForeground(Color.WHITE);
				btnSair.setContentAreaFilled(false);
				btnSair.setIcon(new ImageIcon(Principal.class.getResource("/img/loggout.png")));
						
						JLabel lblNewLabel = new JLabel("New label");
						lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/img/ois.png")));
						lblNewLabel.setBounds(-104, 11, 662, 824);
						panel.add(lblNewLabel);

		panelRodape = new JPanel();
		panelRodape.setForeground(SystemColor.textHighlight);
		panelRodape.setBackground(new Color(0, 0, 128));
		panelRodape.setBounds(205, 591, 803, 70);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
				lblData = new JLabel("Data e Hora");
				lblData.setHorizontalAlignment(SwingConstants.CENTER);
				lblData.setBounds(156, 11, 647, 48);
				panelRodape.add(lblData);
				lblData.setForeground(Color.WHITE);
				lblData.setToolTipText("DATA");
				lblData.setFont(new Font("Arial", Font.BOLD, 26));

		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setBounds(362, 139, 646, 141);
		contentPane.add(lblLogo);
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/logo (1).png")));

		JLabel lblNewLabel_1 = new JLabel("SEJA BEM VINDO(A)");
		lblNewLabel_1.setForeground(SystemColor.activeCaptionText);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 44));
		lblNewLabel_1.setBounds(362, 285, 646, 70);
		contentPane.add(lblNewLabel_1);

		lblWelcome = new JLabel("Administrador");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBackground(SystemColor.desktop);
		lblWelcome.setForeground(SystemColor.desktop);
		lblWelcome.setFont(new Font("Arial", Font.PLAIN, 44));
		lblWelcome.setBounds(362, 341, 646, 66);
		contentPane.add(lblWelcome);
		
		JButton btnSobre = new JButton("");
		btnSobre.setBounds(960, -1, 48, 48);
		contentPane.add(btnSobre);
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setFocusPainted(false);
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/aboutwhite.png")));
		btnSobre.setToolTipText("Sobre");
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorderPainted(false);
		btnSobre.setBorder(null);

	}

	/**
	 * Metodo responsavel por: exibir o status da conexao
	 * 
	 */

	private void status() {
		try {

			con = dao.conectar();
			if (con == null) {

				lblData.setText("Você está offline");
				
				
			} else {

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
		if (con == null) {
			lblData.setText("SEU SERVIDOR ESTÁ OFFLINE");
			panelRodape.setBackground(Color.RED);
			
		} else {
		Date data = new Date();

		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);

		lblData.setText(formatador.format(data).toUpperCase());
		setLocationRelativeTo(null);
	}}
}
