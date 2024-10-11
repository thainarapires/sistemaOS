package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
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
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Servicos extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtOS;
	private JTextField txtData;
	private JTextField txtEquipamento;
	private JTextField txtValor;
	private JTextField txtID;

	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();
	private JTextField txtDefeito;
	private JTextField txtCliente;
	private JScrollPane scrollPaneCli;
	@SuppressWarnings("rawtypes")
	private JList ListCli;
	private JTextField txtMarca;
	private JTextField txtModelo;
	private JTextField txtSerie;
	private JTextField txtMaterial;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servicos dialog = new Servicos();
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
	public Servicos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servicos.class.getResource("/img/logo (1).png")));
		setTitle("Servicos");
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneCli.setVisible(false);
			}
		});
		getContentPane().setBackground(Color.WHITE);
		setModal(true);
		setBounds(100, 100, 1024, 700);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "DIGITE O NOME DO CLIENTE PARA CADASTRAR UMA OS", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(655, 36, 342, 170);
		getContentPane().add(panel);
		panel.setLayout(null);

		txtCliente = new JTextField();
		txtCliente.setFont(new Font("Arial", Font.PLAIN, 18));
		txtCliente.setBackground(SystemColor.control);
		txtCliente.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}

		});
		
				scrollPaneCli = new JScrollPane();
				scrollPaneCli.setBounds(10, 61, 322, 102);
				panel.add(scrollPaneCli);
				scrollPaneCli.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
				scrollPaneCli.setVisible(false);
				
						ListCli = new JList();
						ListCli.setBackground(Color.WHITE);
						ListCli.setFont(new Font("Arial", Font.PLAIN, 18));
						ListCli.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								buscarClienteLista();
							}
						});
						scrollPaneCli.setViewportView(ListCli);
		txtCliente.setBounds(10, 22, 322, 41);
		panel.add(txtCliente);
		txtCliente.setColumns(10);
		
				txtID = new JTextField();
				txtID.setBounds(136, 73, 61, 27);
				panel.add(txtID);
				txtID.setFont(new Font("Arial", Font.PLAIN, 18));
				txtID.setBackground(SystemColor.activeCaptionBorder);
				txtID.setEditable(false);
				txtID.setColumns(10);
				
						JLabel lblIdDoCliente = new JLabel("ID do Cliente:");
						lblIdDoCliente.setBounds(10, 73, 156, 31);
						panel.add(lblIdDoCliente);
						lblIdDoCliente.setFont(new Font("Arial", Font.PLAIN, 20));
						
						JButton btnBuscarCliente = new JButton("BUSCAR OS PELO NOME DO CLIENTE");
						btnBuscarCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
						btnBuscarCliente.setContentAreaFilled(false);
						btnBuscarCliente.setFont(new Font("Arial", Font.BOLD, 15));
						btnBuscarCliente.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								 buscarOSCliCli();
								
							}
						});
						btnBuscarCliente.setBounds(10, 111, 322, 48);
						panel.add(btnBuscarCliente);

		JLabel lblOS = new JLabel("Número da OS:");
		lblOS.setFont(new Font("Arial", Font.PLAIN, 28));
		lblOS.setBounds(16, 41, 202, 26);
		getContentPane().add(lblOS);

		txtOS = new JTextField();
		txtOS.setFont(new Font("Arial", Font.BOLD, 24));
		txtOS.setBackground(SystemColor.controlShadow);
		txtOS.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtOS.setEditable(false);
		txtOS.setBounds(16, 72, 202, 46);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		txtData = new JTextField();
		txtData.setFont(new Font("Arial", Font.BOLD, 21));
		txtData.setBackground(SystemColor.activeCaptionBorder);
		txtData.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtData.setEditable(false);
		txtData.setColumns(10);
		txtData.setBounds(386, 73, 212, 48);
		getContentPane().add(txtData);

		JLabel lblData = new JLabel("Data que foi feita:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 28));
		lblData.setBounds(386, 41, 248, 26);
		getContentPane().add(lblData);

		txtEquipamento = new JTextField();
		txtEquipamento.setFont(new Font("Arial", Font.PLAIN, 26));
		txtEquipamento.setBackground(Color.LIGHT_GRAY);
		txtEquipamento.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtEquipamento.setColumns(10);
		txtEquipamento.setBounds(16, 215, 301, 49);
		getContentPane().add(txtEquipamento);

		JLabel lblEquipamento = new JLabel("Aparelho:");
		lblEquipamento.setFont(new Font("Arial", Font.PLAIN, 28));
		lblEquipamento.setBounds(16, 176, 301, 41);
		getContentPane().add(lblEquipamento);

		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setFont(new Font("Arial", Font.PLAIN, 28));
		lblDefeito.setBounds(16, 354, 970, 32);
		getContentPane().add(lblDefeito);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Arial", Font.PLAIN, 28));
		lblValor.setBounds(17, 433, 105, 41);
		getContentPane().add(lblValor);

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
		txtValor.setFont(new Font("Arial", Font.PLAIN, 26));
		txtValor.setText("0");
		txtValor.setBackground(Color.LIGHT_GRAY);
		txtValor.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtValor.setColumns(10);
		txtValor.setBounds(16, 472, 297, 49);
		getContentPane().add(txtValor);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 20));
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setBounds(228, 75, 124, 35);
		getContentPane().add(btnBuscar);

		JButton btnAdd = new JButton("ADICIONAR NOVA OS");
		btnAdd.setForeground(new Color(34, 139, 34));
		btnAdd.setBackground(Color.LIGHT_GRAY);
		btnAdd.setFont(new Font("Arial", Font.BOLD, 20));
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdd.setToolTipText("Adicionar");
		btnAdd.setBounds(176, 539, 276, 46);
		getContentPane().add(btnAdd);

		JButton btnEditar = new JButton("EDITAR");
		btnEditar.setBackground(Color.LIGHT_GRAY);
		btnEditar.setFont(new Font("Arial", Font.BOLD, 20));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();

			}
		});
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(462, 539, 154, 46);
		getContentPane().add(btnEditar);

		JButton btnExcluir = new JButton("EXCLUIR ORDEM ");
		btnExcluir.setForeground(Color.RED);
		btnExcluir.setBackground(Color.LIGHT_GRAY);
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 20));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnExcluir.setBounds(576, 594, 236, 48);
		getContentPane().add(btnExcluir);

		txtDefeito = new JTextField();
		txtDefeito.setFont(new Font("Arial", Font.PLAIN, 26));
		txtDefeito.setBackground(Color.LIGHT_GRAY);
		txtDefeito.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtDefeito.setColumns(10);
		txtDefeito.setBounds(16, 387, 970, 49);
		getContentPane().add(txtDefeito);

		JButton btnLimpar = new JButton("LIMPAR TODOS OS CAMPOS");
		btnLimpar.setBackground(Color.LIGHT_GRAY);
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 20));
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/borracha.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar todos o campos");
		btnLimpar.setBounds(177, 594, 389, 48);
		getContentPane().add(btnLimpar);

		JButton btnImprimir = new JButton("IMPRIMIR");
		btnImprimir.setBackground(Color.LIGHT_GRAY);
		btnImprimir.setFont(new Font("Arial", Font.BOLD, 20));
		btnImprimir.setToolTipText("Imprimir esta OS");
		btnImprimir.setIcon(new ImageIcon(Servicos.class.getResource("/img/printer.png")));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImprimirOS();
			}
		});
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setBounds(626, 539, 186, 46);
		getContentPane().add(btnImprimir);

		txtMarca = new JTextField();
		txtMarca.setFont(new Font("Arial", Font.PLAIN, 26));
		txtMarca.setBackground(Color.LIGHT_GRAY);
		txtMarca.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtMarca.setColumns(10);
		txtMarca.setBounds(327, 215, 659, 49);
		getContentPane().add(txtMarca);

		JLabel lblMarca = new JLabel("Marca:");
		lblMarca.setFont(new Font("Arial", Font.PLAIN, 28));
		lblMarca.setBounds(327, 179, 318, 34);
		getContentPane().add(lblMarca);

		txtModelo = new JTextField();
		txtModelo.setFont(new Font("Arial", Font.PLAIN, 26));
		txtModelo.setBackground(Color.LIGHT_GRAY);
		txtModelo.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtModelo.setColumns(10);
		txtModelo.setBounds(16, 302, 301, 49);
		getContentPane().add(txtModelo);

		JLabel lblModelo = new JLabel("Modelo:");
		lblModelo.setFont(new Font("Arial", Font.PLAIN, 28));
		lblModelo.setBounds(16, 265, 105, 35);
		getContentPane().add(lblModelo);

		txtSerie = new JTextField();
		txtSerie.setFont(new Font("Arial", Font.PLAIN, 26));
		txtSerie.setBackground(Color.LIGHT_GRAY);
		txtSerie.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtSerie.setColumns(10);
		txtSerie.setBounds(327, 302, 659, 49);
		getContentPane().add(txtSerie);

		JLabel lblSerie = new JLabel("Número de série:");
		lblSerie.setFont(new Font("Arial", Font.PLAIN, 28));
		lblSerie.setBounds(327, 265, 659, 35);
		getContentPane().add(lblSerie);

		JButton btnNewButton = new JButton("Ver todas as OS");
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exibirTodasOS();
			}
		});
		btnNewButton.setBounds(16, 120, 202, 58);
		getContentPane().add(btnNewButton);

		JLabel lblMaterial = new JLabel("Material utilizado:");
		lblMaterial.setFont(new Font("Arial", Font.PLAIN, 28));
		lblMaterial.setBounds(323, 434, 659, 39);
		getContentPane().add(lblMaterial);

		txtMaterial = new JTextField();
		txtMaterial.setFont(new Font("Arial", Font.PLAIN, 26));
		txtMaterial.setColumns(10);
		txtMaterial.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtMaterial.setBackground(Color.LIGHT_GRAY);
		txtMaterial.setBounds(323, 472, 659, 49);
		getContentPane().add(txtMaterial);
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBackground(Color.LIGHT_GRAY);
		panel_1_1.setBounds(0, 0, 1024, 33);
		getContentPane().add(panel_1_1);
		panel_1_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CADASTRO DE ORDEM DE SERVIÇO");
		lblNewLabel.setForeground(SystemColor.textHighlight);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 18));
		lblNewLabel.setBounds(331, 4, 406, 29);
		panel_1_1.add(lblNewLabel);

	}
	

	private void buscarOSCliCli() {
	    String nomeCliente = JOptionPane.showInputDialog("Nome do cliente:");

	    if (nomeCliente != null) {
	        // Consulta SQL para buscar as informações relevantes
	        String read = "SELECT servicos.os, servicos.dataOS, servicos.equipamento, servicos.marca, "
	                    + "servicos.modelo, servicos.serie, servicos.defeito, servicos.valor, servicos.idcli, "
	                    + "clientes.nome "
	                    + "FROM servicos "
	                    + "INNER JOIN clientes ON servicos.idcli = clientes.idcli "
	                    + "WHERE clientes.nome LIKE ?";

	        try {
	            con = dao.conectar(); 

	            pst = con.prepareStatement(read);
	            pst.setString(1, "%" + nomeCliente + "%");

	            rs = pst.executeQuery();

	            // StringBuilder para armazenar todas as OS encontradas
	            StringBuilder resultado = new StringBuilder();

	            // Se encontrar resultados
	            if (rs.isBeforeFirst()) { 
	                while (rs.next()) {
	                    resultado.append("OS: ").append(rs.getString("os")).append("\n");
	                    resultado.append("Data: ").append(rs.getString("dataOS")).append("\n");
	                    resultado.append("Equipamento: ").append(rs.getString("equipamento")).append("\n");
	                    resultado.append("Marca: ").append(rs.getString("marca")).append("\n");
	                    resultado.append("Modelo: ").append(rs.getString("modelo")).append("\n");
	                    resultado.append("Série: ").append(rs.getString("serie")).append("\n");
	                    resultado.append("Defeito: ").append(rs.getString("defeito")).append("\n");
	                    resultado.append("Valor: ").append(rs.getString("valor")).append("\n");
	                    resultado.append("Cliente: ").append(rs.getString("nome")).append("\n\n");

	                    JOptionPane.showMessageDialog(null, resultado.toString(), "Detalhes da OS", JOptionPane.INFORMATION_MESSAGE);

	                    int resposta = JOptionPane.showConfirmDialog(null, "Mostrar próxima OS?", "OS Encontrada",
	                            JOptionPane.YES_NO_OPTION);
	                    
	                    if (resposta == JOptionPane.NO_OPTION) {
	                        break; 
	                    }
	                    resultado.setLength(0); 
	                }
	            } else {
	                JOptionPane.showMessageDialog(null, "Nenhuma OS encontrada para esse cliente.");
	            }

	            con.close();
	            

	        } catch (Exception e) {
	            System.out.println(e);
	            JOptionPane.showMessageDialog(null, "Ocorreu um erro no sistema.");
	        }
	    }
	}
	


	/**
	 * METODO APARECER TODAS AS ORDENS DE SERVICO EM ABERTO QUANDO CLICAR O BOTAO
	 */
	private void exibirTodasOS() {
		String query = "SELECT * FROM servicos INNER JOIN clientes ON servicos.idcli = clientes.idcli ORDER BY dataOS ASC";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(query);
			rs = pst.executeQuery();

			StringBuilder todasOS = new StringBuilder("Lista de Ordens de Serviço:\n");

			while (rs.next()) {
				todasOS.append("Número da OS: ").append(rs.getString(1)).append("     ");
				todasOS.append("Cliente: ").append(rs.getString(12)).append("     ");
				todasOS.append("ID do Cliente: ").append(rs.getString(9)).append("     ");

				todasOS.append("\n");
			}

			if (todasOS.length() > 0) {
				JOptionPane.showMessageDialog(null, todasOS.toString());
			} else {
				JOptionPane.showMessageDialog(null, "Nenhuma OS encontrada.");
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo responsavel por: buscar OS pelo numero OS.
	 */
	private void buscarOS() {

		String numOS = JOptionPane.showInputDialog("Número da OS ");

		if (numOS != null) {
			String read = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where os = ?";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(read);

				pst.setString(1, numOS);

				rs = pst.executeQuery();

				if (rs.next()) {

					txtOS.setText(rs.getString(1));
					txtData.setText(rs.getString(2));
					txtEquipamento.setText(rs.getString(3));
					txtMarca.setText(rs.getString(4));
					txtModelo.setText(rs.getString(5));
					txtSerie.setText(rs.getString(6));
					txtDefeito.setText(rs.getString(7));
					txtValor.setText(rs.getString(8));
					txtID.setText(rs.getString(9));
					txtMaterial.setText(rs.getString(10));
					txtCliente.setText(rs.getString(12));

				} else {

					JOptionPane.showMessageDialog(null, "OS inexistente");

				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}

	/**
	 * Metodo responsavel por: adicionar OS.
	 */
	private void adicionar() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o aparelho");
			txtEquipamento.requestFocus();
		} else if (txtCliente.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do cliente");
			txtCliente.requestFocus();

		} else {

			String create = "insert into servicos(equipamento,marca, modelo, serie, defeito,valor, idcli, material) values (?,?,?,?, ?, ?,?, ?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtMarca.getText());
				pst.setString(3, txtModelo.getText());
				pst.setString(4, txtSerie.getText());
				pst.setString(5, txtDefeito.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, txtID.getText());
				pst.setString(8, txtMaterial.getText());
				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "OS adicionada");
				limparCampos();

				con.close();

			} catch (com.mysql.cj.jdbc.exceptions.MysqlDataTruncation se) {
				// JOptionPane.showMessageDialog(null, "Cliente não encontrado");
				txtValor.setText("0");
				System.out.println(se);
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "OS não adicionada.\nEsta OS já está sendo utilizada.");
				txtOS.setText(null);
				txtOS.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	/**
	 * Metodo responsavel por: editar a OS .
	 */
	private void editarOS() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo do aparelho");
			txtEquipamento.requestFocus();

		} else {

			String update = "update servicos set equipamento=?, marca=?, modelo=?, serie=?, defeito=?, valor=?, idcli=?, material=? where os=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);

				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtMarca.getText());
				pst.setString(3, txtModelo.getText());
				pst.setString(4, txtSerie.getText());
				pst.setString(5, txtDefeito.getText());
				pst.setString(6, txtValor.getText());
				pst.setString(7, txtID.getText());
				pst.setString(8, txtMaterial.getText());
				pst.setString(9, txtOS.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");

				limparCampos();

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Cliente não editado.\nEsta OS já está sendo utilizada.");
				txtOS.setText(null);
				txtOS.requestFocus();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * Metodo responsavel por: excluir a OS.
	 */
	private void excluirOS() {

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo do aparelho");
			txtEquipamento.requestFocus();

		} else {

			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS?", "Atenção",
					JOptionPane.YES_NO_OPTION);

			if (confirma == JOptionPane.YES_OPTION) {

				String delete = "delete from servicos where os=?";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(delete);
					pst.setString(1, txtOS.getText());

					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "OS excluida");

					limparCampos();

					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	/**
	 * Metodo responsavel por: limpar os campos.
	 */
	private void limparCampos() {

		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtDefeito.setText(null);
		txtValor.setText(null);
		txtID.setText(null);
		txtCliente.setText(null);
		txtSerie.setText(null);
		txtMarca.setText(null);
		txtModelo.setText(null);
		txtMaterial.setText(null);

	}

	/**
	 * Metodo responsavel por: listar clientes pelo nome.
	 */
	@SuppressWarnings("unchecked")
	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();

		ListCli.setModel(modelo);

		String readLista = "select * from clientes where nome like '" + txtCliente.getText() + "%'" + "order by nome";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				modelo.addElement(rs.getString(2));
				scrollPaneCli.setVisible(true);

				if (txtCliente.getText().isEmpty()) {
					scrollPaneCli.setVisible(false);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsavel por: buscar cliente pelo nome.
	 */
	private void buscarClienteLista() {

		int linha = ListCli.getSelectedIndex();

		if (linha >= 0) {

			String readBuscaLista = "select *from clientes where nome like '" + txtCliente.getText() + "%'"

					+ "order by nome limit " + (linha) + " ,1";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(readBuscaLista);

				rs = pst.executeQuery();

				if (rs.next()) {

					scrollPaneCli.setVisible(false);

					txtID.setText(rs.getString(1));
					txtCliente.setText(rs.getString(2));

				} else {

					JOptionPane.showMessageDialog(null, "Cliente inexistente");

				}

				con.close();

			} catch (Exception e) {

			}

		} else {

			scrollPaneCli.setVisible(false);

		}
	}

	/**
	 * Metodo responsavel por: abrir uma das escolhas de qual OS imprimir.
	 */
	private void ImprimirOS() {
		int choice = showPrintOptionsDialog();
		
		if (choice == JOptionPane.YES_OPTION) {
			imprimirGarantia();
		} else if (choice == JOptionPane.NO_OPTION) {
			// Aqui era a segunda verificação incorreta.
			int secondChoice = showSecondPrintOptionsDialog();

			if (secondChoice == JOptionPane.YES_OPTION) {
				imprimirOSEmpresa();
			} else if (secondChoice == JOptionPane.NO_OPTION) {
				imprimirOSCliente();
			} else {

			}
		}
	}
	/**
	 * Metodo responsavel por: escrever as opcoes de qual impressao escolher.
	 */
	private static int showPrintOptionsDialog() {
		Object[] options = { "Imprimir Garantia", "Imprimir Ordens de Serviço" };
		return JOptionPane.showOptionDialog(null, "Escolha qual via imprimir:", "Opções de Impressão",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**
	 * Método para mostrar o segundo diálogo, caso o usuário escolha a opção
	 * "Imprimir Via Empresa".
	 */
	private static int showSecondPrintOptionsDialog() {
		Object[] options = { "Imprimir Via Empresa", "Imprimir Via Cliente" };
		return JOptionPane.showOptionDialog(null, "Escolha qual via imprimir:", "Opções de Impressão",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}

	/**
	 * Metodo responsavel por: imprimir a OS do cliente.
	 */
	private void imprimirOSCliente() {

		Document document = new Document();

		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual é a OS");
			txtOS.requestFocus();

		} else {

			try {

				PdfWriter.getInstance(document, new FileOutputStream("oscliente.pdf"));

				document.open();

				String readOS = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where os=?";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());

					rs = pst.executeQuery();

					if (rs.next()) {

						Image imagem = Image.getInstance(Servicos.class.getResource("/img/Logo.png"));

						imagem.scaleToFit(128, 128);

						imagem.setAlignment(Element.ALIGN_LEFT);
						document.add(imagem);

						Paragraph OS = new Paragraph("SP ASSISTÊNCIA TV");
						OS.setAlignment(Element.ALIGN_CENTER);
						document.add(OS);

						Paragraph via = new Paragraph(" (Via cliente) ");
						via.setAlignment(Element.ALIGN_CENTER);
						document.add(via);

						Paragraph cp = new Paragraph("COMPROVANTE DE RETIRADA",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						cp.setAlignment(Element.ALIGN_RIGHT);
						document.add(cp);

						Paragraph space = new Paragraph(" ");
						space.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space);
						Paragraph desc = new Paragraph(
								" Pessoa Jurídica \n CNPJ: 20992924000129  INSC. ESTADUAL   INS. MUNICIPAL \n Endereço: Rua Tabapuã, 648 - Loja 1 \n Laboratório Técnico: Rua Manuel da Costa, 391 \n (11) 947485487        Email: spassistenciavi@gmail.com");
						desc.setAlignment(Element.ALIGN_LEFT);
						document.add(desc);

						Paragraph desc2 = new Paragraph("\nSERVIÇO ESPECIALIZADO:",
								FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
						desc2.setAlignment(Element.ALIGN_LEFT);
						document.add(desc2);

						Paragraph TVS = new Paragraph(
								" TV LED - TV LCD - TV PLASMA - 4K - SAMSUNG \n LG - AOC - PHILIPS - TOSHIBA");
						TVS.setAlignment(Element.ALIGN_RIGHT);
						document.add(TVS);

						Paragraph os = new Paragraph("OS: " + rs.getString(1) + "\n",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						os.setAlignment(Element.ALIGN_RIGHT);

						document.add(os);

						Paragraph nome = new Paragraph("Cliente: " + rs.getString(12));
						nome.setAlignment(Element.ALIGN_LEFT);
						document.add(nome);

						Paragraph endereco = new Paragraph("Endereço: " + rs.getString(16) + ", " + rs.getString(17));
						endereco.setAlignment(Element.ALIGN_LEFT);
						document.add(endereco);

						Paragraph bairro = new Paragraph("Bairro: " + rs.getString(19));
						bairro.setAlignment(Element.ALIGN_LEFT);
						document.add(bairro);
						
						Paragraph compl = new Paragraph("Complemento: " + rs.getString(18) + "\n");
						compl.setAlignment(Element.ALIGN_LEFT);
						document.add(compl);

						Paragraph aparelho = new Paragraph("\n Dados do aparelho: \n   ",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						aparelho.setAlignment(Element.ALIGN_MIDDLE);
						document.add(aparelho);

						PdfPTable tabela = new PdfPTable(4);
						PdfPCell col1 = new PdfPCell(new Paragraph("Equipamento: "));

						PdfPCell col2 = new PdfPCell(new Paragraph("Marca: "));

						PdfPCell col3 = new PdfPCell(new Paragraph("Modelo: "));

						PdfPCell col4 = new PdfPCell(new Paragraph("Número de série: "));

						tabela.addCell(col1);
						tabela.addCell(col2);
						tabela.addCell(col3);
						tabela.addCell(col4);

						tabela.addCell(rs.getString(3));
						tabela.addCell(rs.getString(4));
						tabela.addCell(rs.getString(5));
						tabela.addCell(rs.getString(6));

						document.add(tabela);

						Paragraph def = new Paragraph("Defeito: (estado do aparelho ou acessório) \n" + rs.getString(7)
								+ "\n________________________________________________________________");
						def.setAlignment(Element.ALIGN_LEFT);
						document.add(def);

						String dataMySQL = rs.getString(2); // Suponha que a coluna 16 seja a coluna da data do MySQL

						// Converter a data do formato padrão do MySQL para "dd-mm-yyyy"
						LocalDate data = LocalDate.parse(dataMySQL);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String dataFormatada = data.format(formatter);

						Paragraph assin = new Paragraph(
								"\n \n \n  Aley Luciano Pires da Silva \n      Ass. Responsável                                                                     São Paulo, "
										+ dataFormatada);
						assin.setAlignment(Element.ALIGN_LEFT);
						document.add(assin);

						Paragraph under = new Paragraph(
								"\n________________________________________________________________________ \n");
						under.setAlignment(Element.ALIGN_LEFT);
						document.add(under);

						Paragraph ap = new Paragraph(
								"Eu estou ciente que o aparelho acima está sendo retirado pela SP ASSISTÊNCIA TV para reparo. \n");
						ap.setAlignment(Element.ALIGN_LEFT);
						document.add(ap);

						// Paragraph data = new Paragraph("Data OS: " + rs.getString(2));
						// data.setAlignment(Element.ALIGN_CENTER);
						// document.add(data);
					}

					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				document.close();

				try {

					Desktop.getDesktop().open(new File("oscliente.pdf"));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	/**
	 * Metodo responsavel por: imprimir a OS da empresa.
	 */
	private void imprimirOSEmpresa() {

		Document document = new Document();

		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual é a OS");
			txtOS.requestFocus();

		} else {

			try {

				PdfWriter.getInstance(document, new FileOutputStream("osempresa.pdf"));

				document.open();

				String readOS = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where os=?";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());

					rs = pst.executeQuery();

					if (rs.next()) {

						Image imagem = Image.getInstance(Servicos.class.getResource("/img/Logo.png"));

						imagem.scaleToFit(128, 128);

						imagem.setAlignment(Element.ALIGN_LEFT);
						document.add(imagem);

						Paragraph OS = new Paragraph("SP ASSISTÊNCIA TV");
						OS.setAlignment(Element.ALIGN_CENTER);
						document.add(OS);

						Paragraph via = new Paragraph(" (Via empresa) ");
						via.setAlignment(Element.ALIGN_CENTER);
						document.add(via);

						Paragraph cp = new Paragraph("COMPROVANTE DE RETIRADA",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						cp.setAlignment(Element.ALIGN_RIGHT);
						document.add(cp);

						Paragraph space = new Paragraph(" ");
						space.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space);

						Paragraph space2 = new Paragraph(" ");
						space2.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space2);

						Paragraph desc2 = new Paragraph("SERVIÇO ESPECIALIZADO:",
								FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD));
						desc2.setAlignment(Element.ALIGN_LEFT);
						document.add(desc2);

						Paragraph TVS = new Paragraph(
								" TV LED - TV LCD - TV PLASMA - 4K - SAMSUNG \n LG - AOC - PHILIPS - TOSHIBA");
						TVS.setAlignment(Element.ALIGN_RIGHT);
						document.add(TVS);

						Paragraph os = new Paragraph("OS: " + rs.getString(1) + "\n",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						os.setAlignment(Element.ALIGN_RIGHT);

						document.add(os);

						Paragraph nome = new Paragraph("Cliente: " + rs.getString(12));
						nome.setAlignment(Element.ALIGN_LEFT);
						document.add(nome);

						Paragraph endereco = new Paragraph("Endereço: " + rs.getString(16) + ", " + rs.getString(17));
						endereco.setAlignment(Element.ALIGN_LEFT);
						document.add(endereco);

						Paragraph bairro = new Paragraph("Bairro: " + rs.getString(19));
						bairro.setAlignment(Element.ALIGN_LEFT);
						document.add(bairro);
						
						Paragraph compl = new Paragraph("Complemento: " + rs.getString(18) + "\n");
						compl.setAlignment(Element.ALIGN_LEFT);
						document.add(compl);

						Paragraph aparelho = new Paragraph("\n Dados do aparelho: \n   ",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						aparelho.setAlignment(Element.ALIGN_MIDDLE);
						document.add(aparelho);

						PdfPTable tabela = new PdfPTable(4);
						PdfPCell col1 = new PdfPCell(new Paragraph("Equipamento: "));

						PdfPCell col2 = new PdfPCell(new Paragraph("Marca: "));

						PdfPCell col3 = new PdfPCell(new Paragraph("Modelo: "));

						PdfPCell col4 = new PdfPCell(new Paragraph("Número de série: "));

						tabela.addCell(col1);
						tabela.addCell(col2);
						tabela.addCell(col3);
						tabela.addCell(col4);

						tabela.addCell(rs.getString(3));
						tabela.addCell(rs.getString(4));
						tabela.addCell(rs.getString(5));
						tabela.addCell(rs.getString(6));

						document.add(tabela);

						Paragraph def = new Paragraph("Defeito (estado do aparelho ou acessório): \n" + rs.getString(7)
								+ "\n\n________________________________________________________________");
						def.setAlignment(Element.ALIGN_LEFT);
						document.add(def);

						String dataMySQL = rs.getString(2); // Suponha que a coluna 16 seja a coluna da data do MySQL

						// Converter a data do formato padrão do MySQL para "dd-mm-yyyy"
						LocalDate data = LocalDate.parse(dataMySQL);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String dataFormatada = data.format(formatter);

						Paragraph assin = new Paragraph(
								"\n \n Aley Luciano Pires da Silva \n      Ass. Responsável                                                                     São Paulo, "
										+ dataFormatada);
						assin.setAlignment(Element.ALIGN_LEFT);
						document.add(assin);

						Paragraph under = new Paragraph(
								"\n________________________________________________________________________ \n");
						under.setAlignment(Element.ALIGN_LEFT);
						document.add(under);

						Paragraph ap = new Paragraph(
								"Eu estou ciente que o aparelho acima está sendo retirado pela SP ASSISTÊNCIA TV para reparo. \n");
						ap.setAlignment(Element.ALIGN_LEFT);
						document.add(ap);

					
					}

					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				document.close();

				try {

					Desktop.getDesktop().open(new File("osempresa.pdf"));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}


	private void imprimirGarantia() {

		Document document = new Document();

		if (txtOS.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha qual é a OS");
			txtOS.requestFocus();

		} else {

			try {

				PdfWriter.getInstance(document, new FileOutputStream("osgarantia.pdf"));

				document.open();

				String readOS = "select * from servicos inner join clientes on servicos.idcli = clientes.idcli where os=?";

				try {

					con = dao.conectar();

					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());

					rs = pst.executeQuery();

					if (rs.next()) {

						Image imagem = Image.getInstance(Servicos.class.getResource("/img/Logo.png"));

						imagem.scaleToFit(128, 128);

						imagem.setAlignment(Element.ALIGN_LEFT);
						document.add(imagem);

						Paragraph OS = new Paragraph("SP ASSISTÊNCIA TV");
						OS.setAlignment(Element.ALIGN_CENTER);
						document.add(OS);

						Paragraph via = new Paragraph(" (Via do cliente) ",
								FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD));
						via.setAlignment(Element.ALIGN_RIGHT);
						document.add(via);

						Paragraph recibo = new Paragraph(" RECIBO/GARANTIA ",
								FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
						recibo.setAlignment(Element.ALIGN_RIGHT);
						document.add(recibo);

						Paragraph space = new Paragraph(" ");
						space.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space);

						Paragraph desc = new Paragraph(
								" Pessoa Jurídica \n CNPJ: 20992924000129  INSC. ESTADUAL   INS. MUNICIPAL \n Endereço: Rua Tabapuã, 648 - Loja 1 \n Laboratório Técnico: Rua Manuel da Costa, 391 \n (11) 947485487        Email: spassistenciavi@gmail.com");
						desc.setAlignment(Element.ALIGN_LEFT);
						document.add(desc);

						Paragraph desc2 = new Paragraph("\nSERVIÇO AUTORIZADO:",
								FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD));
						desc2.setAlignment(Element.ALIGN_LEFT);
						document.add(desc2);

						Paragraph TVS = new Paragraph(
								" TV LED - TV LCD - TV PLASMA - 4K - SAMSUNG \n LG - AOC - PHILIPS - TOSHIBA");
						TVS.setAlignment(Element.ALIGN_RIGHT);
						document.add(TVS);
						Paragraph nome = new Paragraph("Cliente: " + rs.getString(12));
						nome.setAlignment(Element.ALIGN_LEFT);
						document.add(nome);

						Paragraph endereco = new Paragraph("Endereço: " + rs.getString(16) + ", " + rs.getString(17));
						endereco.setAlignment(Element.ALIGN_LEFT);
						document.add(endereco);

						Paragraph bairro = new Paragraph("Bairro: " + rs.getString(19) + "\n");
						bairro.setAlignment(Element.ALIGN_LEFT);
						document.add(bairro);
						
						Paragraph compl = new Paragraph("Complemento: " + rs.getString(18) + "\n");
						compl.setAlignment(Element.ALIGN_LEFT);
						document.add(compl);

						Paragraph cpf = new Paragraph(
								"CPF:                                                           CNPJ:              \n\n");
						cpf.setAlignment(Element.ALIGN_LEFT);
						document.add(cpf);

						PdfPTable tabela = new PdfPTable(4);
						PdfPCell col1 = new PdfPCell(new Paragraph("Equipamento: "));

						PdfPCell col2 = new PdfPCell(new Paragraph("Marca: "));

						PdfPCell col3 = new PdfPCell(new Paragraph("Modelo: "));

						PdfPCell col4 = new PdfPCell(new Paragraph("Número de série: "));

						tabela.addCell(col1);
						tabela.addCell(col2);
						tabela.addCell(col3);
						tabela.addCell(col4);

						tabela.addCell(rs.getString(3));
						tabela.addCell(rs.getString(4));
						tabela.addCell(rs.getString(5));
						tabela.addCell(rs.getString(6));

						document.add(tabela);

						Paragraph mat = new Paragraph("\nMaterial utilizado: " + rs.getString(10));
						mat.setAlignment(Element.ALIGN_LEFT);
						document.add(mat);

						Paragraph def = new Paragraph("Código    Quantidade     Descrição",
								FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD));
						def.setAlignment(Element.ALIGN_LEFT);
						document.add(def);

						Paragraph assin = new Paragraph("\n");
						assin.setAlignment(Element.ALIGN_RIGHT);
						document.add(assin);

						Paragraph assin1 = new Paragraph(
								"                                                                               TOTAL: R$"
										+ rs.getString(8));
						assin1.setAlignment(Element.ALIGN_CENTER);
						document.add(assin1);

						Paragraph under = new Paragraph(
								"\nESTE SERVIÇO POSSUI GARANTIA DE 90 DIAS A PARTIR DA DATA DE ENTREGA! \n",
								FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD));
						under.setAlignment(Element.ALIGN_CENTER);
						document.add(under);

						Paragraph ap = new Paragraph(
								"Aley Luciano P.Silva (Administrador) \n- Afirmo que recebi o meu aparelho em perfeitas condições de funcionamento e que o mesmo foi testado na minha presença, não tendo nada a reclamar contra. A Violação do Selo de Segurança, implicará na anulação da garantia",
								FontFactory.getFont(FontFactory.HELVETICA, 12));
						ap.setAlignment(Element.ALIGN_CENTER);
						document.add(ap);
						
						String dataMySQL = rs.getString(2); // Suponha que a coluna 16 seja a coluna da data do MySQL

						// Converter a data do formato padrão do MySQL para "dd-mm-yyyy"
						LocalDate data = LocalDate.parse(dataMySQL);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						String dataFormatada = data.format(formatter);

						Paragraph assi = new Paragraph(
								"\nSão Paulo, " + dataFormatada);
						assi.setAlignment(Element.ALIGN_CENTER);
						document.add(assi);
						
					}

					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				document.close();

				try {

					Desktop.getDesktop().open(new File("osgarantia.pdf"));
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
}
