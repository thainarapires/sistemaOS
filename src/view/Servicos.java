package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import javax.swing.ImageIcon;

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
	private JTextField txtDeffeito;
	private JTextField txtCliente;
	private JScrollPane scrollPaneCli;
	private JList ListCli;

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
	public Servicos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneCli.setVisible(false);
			}
		});
		getContentPane().setBackground(new Color(249, 249, 249));
		setModal(true);
		setBounds(100, 100, 541, 299);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblOS = new JLabel("OS:");
		lblOS.setFont(new Font("Arial", Font.PLAIN, 14));
		lblOS.setBounds(20, 21, 46, 14);
		getContentPane().add(lblOS);

		txtOS = new JTextField();
		txtOS.setEditable(false);
		txtOS.setBounds(20, 42, 86, 20);
		getContentPane().add(txtOS);
		txtOS.setColumns(10);

		txtData = new JTextField();
		txtData.setEditable(false);
		txtData.setColumns(10);
		txtData.setBounds(203, 42, 130, 20);
		getContentPane().add(txtData);

		JLabel lblData = new JLabel("Data e hora:");
		lblData.setFont(new Font("Arial", Font.PLAIN, 14));
		lblData.setBounds(203, 21, 93, 14);
		getContentPane().add(lblData);

		txtEquipamento = new JTextField();
		txtEquipamento.setColumns(10);
		txtEquipamento.setBounds(20, 107, 490, 20);
		getContentPane().add(txtEquipamento);

		JLabel lblEquipamento = new JLabel("Equipamento:");
		lblEquipamento.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEquipamento.setBounds(20, 86, 105, 14);
		getContentPane().add(lblEquipamento);

		JLabel lblDefeito = new JLabel("Defeito:");
		lblDefeito.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDefeito.setBounds(20, 138, 105, 14);
		getContentPane().add(lblDefeito);

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setFont(new Font("Arial", Font.PLAIN, 14));
		lblValor.setBounds(20, 186, 105, 14);
		getContentPane().add(lblValor);

		txtValor = new JTextField();
		txtValor.setColumns(10);
		txtValor.setBounds(20, 211, 190, 20);
		getContentPane().add(txtValor);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnBuscar.setToolTipText("Buscar");
		btnBuscar.setBounds(116, 41, 77, 23);
		getContentPane().add(btnBuscar);

		JButton btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(Servicos.class.getResource("/img/adicionarrr.png")));
		btnAdd.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdd.setToolTipText("Adicionar");
		btnAdd.setBounds(227, 198, 48, 48);
		getContentPane().add(btnAdd);

		JButton btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Servicos.class.getResource("/img/editarrr.png")));
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();

			}
		});
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(285, 198, 48, 48);
		getContentPane().add(btnEditar);

		JButton btnExcluir = new JButton("");
		btnExcluir.setIcon(new ImageIcon(Servicos.class.getResource("/img/trashhh.png")));
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnExcluir.setBounds(346, 198, 48, 48);
		getContentPane().add(btnExcluir);

		txtDeffeito = new JTextField();
		txtDeffeito.setColumns(10);
		txtDeffeito.setBounds(20, 155, 490, 20);
		getContentPane().add(txtDeffeito);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(343, 11, 167, 80);
		getContentPane().add(panel);
		panel.setLayout(null);

		scrollPaneCli = new JScrollPane();
		scrollPaneCli.setVisible(false);
		scrollPaneCli.setBounds(10, 40, 139, 29);
		panel.add(scrollPaneCli);

		ListCli = new JList();
		ListCli.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClienteLista();
			}
		});
		scrollPaneCli.setViewportView(ListCli);

		txtCliente = new JTextField();
		txtCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}

		});
		txtCliente.setBounds(10, 22, 139, 20);
		panel.add(txtCliente);
		txtCliente.setColumns(10);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setBounds(31, 49, 60, 20);
		panel.add(txtID);
		txtID.setColumns(10);

		JLabel lblIdDoCliente = new JLabel("ID:");
		lblIdDoCliente.setBounds(10, 51, 25, 14);
		panel.add(lblIdDoCliente);
		lblIdDoCliente.setFont(new Font("Arial", Font.PLAIN, 14));

		JButton btnLimpar = new JButton("");
		btnLimpar.setIcon(new ImageIcon(Servicos.class.getResource("/img/borracha.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar todos o campos");
		btnLimpar.setBounds(404, 198, 48, 48);
		getContentPane().add(btnLimpar);

		JButton btnImprimir = new JButton("");
		btnImprimir.setToolTipText("Imprimir esta OS");
		btnImprimir.setIcon(new ImageIcon(Servicos.class.getResource("/img/printer.png")));
		btnImprimir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				imprimirOS();
			}
		});
		btnImprimir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnImprimir.setBounds(462, 198, 48, 48);
		getContentPane().add(btnImprimir);

	}

	private void buscarOS() {
		// captura do numero a OS (sem usar caixa de txt)
		String numOS = JOptionPane.showInputDialog("Número da OS ");

		// System.out.println("Teste do botão buscar");

		// Criar uma variável com a query (instrução do banco)

		// Tratamento de exceções
		String read = "select * from servicos where os = ?";
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execucão da query( instrução sql - CRUD Read)
			// o parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);

			// SUBSTITUI A ? PELO NÚMERO DA OS
			pst.setString(1, numOS);
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				// preencher as caixas de texto com as informações

				txtOS.setText(rs.getString(1)); // 1º Campo da Tabela ID
				txtData.setText(rs.getString(2)); // 2º Campo da Tabela ID
				txtEquipamento.setText(rs.getString(3)); // 3º Campo da Tabela ID
				txtDeffeito.setText(rs.getString(4)); // 4º Campo da Tabela ID
				txtValor.setText(rs.getString(5)); // 4º Campo da Tabela ID
				txtID.setText(rs.getString(6)); // 4º Campo da Tabela ID
				// btnEditar.setEnabled(true);
				// btnExcluir.setEnabled(true);
			} else {
				// System.out.println("Contaos não cadastrados");
				JOptionPane.showMessageDialog(null, "OS inexistente");
				// btnAdicionar.setEnabled(true);
				// btnPesquisar.setEnabled(true);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);

		}

	}

	private void adicionar() {// Criar váriavel/objeto para capturar a senha

		// System.out.println("Teste");
		// validação de campos obrigatórios

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o equipamento");
			txtEquipamento.requestFocus();
		} else if (txtDeffeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o deffeito");
			txtDeffeito.requestFocus();
		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor");
			txtValor.requestFocus();
		} else {
			// lógica principal
			// CRUD Create
			String create = "insert into servicos(equipamento,defeito,valor, idcli) values (?,?,?,?)";
			// tratamento de exceções

			try {
				// abrir conexao
				con = dao.conectar();
				// preparar a execução da query (instrução sql, CRUD CREATE)
				pst = con.prepareStatement(create);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDeffeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				// executa a query(instrução sql, CRUD)
				pst.executeUpdate();
				// confirmar
				JOptionPane.showMessageDialog(null, "OS adicionada");
				// limpar campos
				limparCampos();

				// fechar conection
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "OS não adicionada.\nEsta OS já está sendo utilizada.");
				txtOS.setText(null);
				txtOS.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void editarOS() {

		// System.out.println("teste do botão editar");
		// validar campos obrigatorios
		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo do equipamento");
			txtEquipamento.requestFocus();

		} else if (txtDeffeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo defeito");
			txtDeffeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo valor");
			txtValor.requestFocus();

		} else {
			// logica principal
			// CRUD - Update
			String update = "update servicos set equipamento=?, defeito=?, valor=?, idcli=? where os=?";
			// trat de exceção
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a query
				pst = con.prepareStatement(update);
				pst.setString(1, txtEquipamento.getText());
				pst.setString(2, txtDeffeito.getText());
				pst.setString(3, txtValor.getText());
				pst.setString(4, txtID.getText());
				pst.setString(5, txtOS.getText());

				// Executar query
				pst.executeUpdate();
				// confirmar para o user
				JOptionPane.showMessageDialog(null, "Dados do usuario editados com sucesso!");
				// limpar campos
				limparCampos();
				// fechar conexao
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

	private void excluirOS() {
		// System.out.println("teste");
		// validação de exclusao - a variável confirma captura a opção escolhida.

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo do equipamento");
			txtEquipamento.requestFocus();

		} else if (txtDeffeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo defeito");
			txtDeffeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo valor");
			txtValor.requestFocus();

		} else {

			int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão desta OS?", "Atenção",
					JOptionPane.YES_NO_OPTION);

			if (confirma == JOptionPane.YES_OPTION) {
				// CRUD - Delete vai excluir o contato
				String delete = "delete from servicos where os=?";
				// tratamento de exceção
				try {
					// abrir conexão
					con = dao.conectar();
					// preparar a query
					pst = con.prepareStatement(delete);
					pst.setString(1, txtOS.getText());
					// executar query
					pst.executeUpdate();
					// confirmar para o user
					JOptionPane.showMessageDialog(null, "OS excluida");
					// limpar campos
					limparCampos();
					// fechar conexao
					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void limparCampos() {
		// teste
		// System.out.println("Teste");
		txtOS.setText(null);
		txtData.setText(null);
		txtEquipamento.setText(null);
		txtDeffeito.setText(null);
		txtValor.setText(null);
		txtID.setText(null);
		txtCliente.setText(null);

	}

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

	private void buscarClienteLista() {

		// System.out.println("teste");

		// variavel que captuar o indice da linha da lista

		int linha = ListCli.getSelectedIndex();

		if (linha >= 0) {

			// String readBuscaLista=

			// Query (instrução sql)

			// limite " , 1" -> selecionar o indice 0 e 1 usuario da lista

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

					// System.out.println("Contatos não cadastrados");

					JOptionPane.showMessageDialog(null, "Cliente inexistente");

				}

				con.close();

			} catch (Exception e) {

			}

		} else {

			scrollPaneCli.setVisible(false);

		}
	}

	private void imprimirOS() {
		// instanciar objeto para usar os métodos da biblioteca
		Document document = new Document();
		// documento pdf

		if (txtEquipamento.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo do equipamento");
			txtEquipamento.requestFocus();

		} else if (txtDeffeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo defeito");
			txtDeffeito.requestFocus();

		} else if (txtValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o campo valor");
			txtValor.requestFocus();

		} else {

			try {
				// criar um documento em branco (pdf) de nome clientes.pdf
				PdfWriter.getInstance(document, new FileOutputStream("os.pdf"));
				// abrir o documento (formatar e inserir o conteúdo)
				document.open();

				String readOS = "select * from servicos where os = ?";

				// conexão com o banco
				try {
					// abrir a conexão
					con = dao.conectar();
					// preparar a execução da query (instrução sql)
					pst = con.prepareStatement(readOS);
					pst.setString(1, txtOS.getText());
					// executar a query
					rs = pst.executeQuery();
					// se existir a OS
					if (rs.next()) {
						// document.add(new Paragraph("OS: " + rs.getString(1)));
						
		
						Paragraph OS = new Paragraph ("SP ASSISTENCIA ELETRODOMÉSTICOS"); 
						OS.setAlignment(Element.ALIGN_CENTER);
						document.add(OS);
						
						Paragraph space = new Paragraph (" "); 
						space.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space);
						
						Paragraph os = new Paragraph("OS: " + rs.getString(1));
						os.setAlignment(Element.ALIGN_LEFT);
						document.add(os);
						
						Paragraph space1 = new Paragraph (" "); 
						space1.setAlignment(Element.ALIGN_MIDDLE);
						document.add(space1);
						
						

						Paragraph data = new Paragraph("Data: " + rs.getString(2));
						data.setAlignment(Element.ALIGN_LEFT);
						document.add(data);
						
						Paragraph equipamento = new Paragraph("Equipamento: " + rs.getString(3));
						equipamento.setAlignment(Element.ALIGN_LEFT);
						document.add(equipamento);
						

						Paragraph defeito = new Paragraph("Defeito: " + rs.getString(4));
						defeito.setAlignment(Element.ALIGN_LEFT);
						document.add(defeito);
						
						Paragraph valor = new Paragraph("Valor: " + rs.getString(5));
						defeito.setAlignment(Element.ALIGN_LEFT);
						document.add(valor);


						// imprimir imagens
						Image imagem = Image.getInstance(Servicos.class.getResource("/img/ossss.png"));

						// resolucao
						imagem.scaleToFit(128, 128);
						// imagem.setAbsolutePosition(20, 500);
						imagem.setAlignment(Element.ALIGN_CENTER);
						document.add(imagem);

					}
					// fechar a conexão com o banco
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			// fechar o documento (pronto para "impressão" (exibir o pdf))
			document.close();
			// Abrir o desktop do sistema operacional e usar o leitor padrão
			// de pdf para exibir o documento
			try {
				Desktop.getDesktop().open(new File("os.pdf"));
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}
