package view;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Component;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

@SuppressWarnings("unused")
public class Relatorios extends JDialog {
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	DAO dao = new DAO();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Relatorios dialog = new Relatorios();
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
	public Relatorios() {
		getContentPane().setForeground(new Color(0, 51, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Relatorios.class.getResource("/img/Logo.png")));
		getContentPane().setBackground(Color.WHITE);
		setModal(true);
		setTitle("Relatórios");
		setResizable(false);
		setBounds(100, 100, 425, 459);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setForeground(new Color(0, 51, 255));
		panel.setBackground(SystemColor.textHighlight);
		panel.setBounds(0, 0, 438, 461);
		getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnClientes = new JButton("CLIENTES");
		btnClientes.setBorderPainted(false);
		btnClientes.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnClientes.setForeground(Color.BLACK);
		btnClientes.setBounds(79, 226, 260, 68);
		panel.add(btnClientes);
		btnClientes.setFont(new Font("Arial", Font.BOLD, 16));
		btnClientes.setFocusPainted(false);
		btnClientes.setToolTipText("Clientes");
		btnClientes.setIcon(new ImageIcon(Relatorios.class.getResource("/img/309041_users_group_people_icon.png")));
		btnClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioClientes();
			}
		});
		btnClientes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnServicos = new JButton("SERVIÇOS");
		btnServicos.setBorderPainted(false);
		btnServicos.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnServicos.setForeground(Color.BLACK);
		btnServicos.setBounds(79, 132, 260, 68);
		panel.add(btnServicos);
		btnServicos.setFont(new Font("Arial", Font.BOLD, 16));
		btnServicos.setFocusPainted(false);
		btnServicos.setToolTipText("Serviços");
		btnServicos.setIcon(new ImageIcon(Relatorios.class.getResource("/img/serv.png")));
		btnServicos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioServicos();
			}
		});
		btnServicos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnServicos_1 = new JButton("ESTOQUE");
		btnServicos_1.setBorderPainted(false);
		btnServicos_1.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnServicos_1.setForeground(Color.BLACK);
		btnServicos_1.setBounds(79, 37, 260, 68);
		panel.add(btnServicos_1);
		btnServicos_1.setFont(new Font("Arial", Font.BOLD, 16));
		btnServicos_1.setFocusPainted(false);
		btnServicos_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServicos_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioEstoque();
			}
		});
		btnServicos_1.setIcon(new ImageIcon(Relatorios.class.getResource("/img/boxxq.png")));
		btnServicos_1.setToolTipText("Serviços");

		JButton btnUser = new JButton("USUÁRIOS");
		btnUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUser.setBorderPainted(false);
		btnUser.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		btnUser.setForeground(Color.BLACK);
		btnUser.setBounds(79, 320, 260, 68);
		panel.add(btnUser);
		btnUser.setIcon(
				new ImageIcon(Relatorios.class.getResource("/img/372902_user_name_round_username_linecon_icon.png")));
		btnUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				relatorioUsers();
			}
		});
		btnUser.setFont(new Font("Arial", Font.BOLD, 16));
		btnUser.setFocusPainted(false);
		btnUser.setToolTipText("Clientes");

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Relatorios.class.getResource("/img/sois.png")));
		lblNewLabel.setBounds(-20, -13, 446, 589);
		panel.add(lblNewLabel);

	}

	/**
	 * Metodo responsavel por: imprimir o relatorio de clientes.
	 */
	private void relatorioClientes() {

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream("clientes.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Clientes:"));
			document.add(new Paragraph(" "));

			String readClientes = "select nome, fone, email from clientes order by nome";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readClientes);

				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(3);

				PdfPCell col1 = new PdfPCell(new Paragraph("Cliente: "));
				PdfPCell col2 = new PdfPCell(new Paragraph("Fone: "));
				PdfPCell col3 = new PdfPCell(new Paragraph("Email: "));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {

					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("clientes.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void relatorioUsers() {

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream("usuarios.pdf"));

			document.open();

			Date dataRelatorio = new Date();
			DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
			document.add(new Paragraph(formatador.format(dataRelatorio)));

			document.add(new Paragraph("Usuários:"));
			document.add(new Paragraph(" "));

			String readUsuarios = "select nome, login, perfil from usuarios order by nome";
			try {

				con = dao.conectar();

				pst = con.prepareStatement(readUsuarios);

				rs = pst.executeQuery();

				PdfPTable tabela = new PdfPTable(3);

				PdfPCell col1 = new PdfPCell(new Paragraph("Nome: "));
				PdfPCell col2 = new PdfPCell(new Paragraph("Login: "));
				PdfPCell col3 = new PdfPCell(new Paragraph("Perfil: "));
				tabela.addCell(col1);
				tabela.addCell(col2);
				tabela.addCell(col3);
				while (rs.next()) {

					tabela.addCell(rs.getString(1));
					tabela.addCell(rs.getString(2));
					tabela.addCell(rs.getString(3));
				}

				document.add(tabela);

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		document.close();

		try {
			Desktop.getDesktop().open(new File("usuarios.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Metodo responsavel por: imprimir o relatorio de servicos.
	 */
	private void relatorioServicos() {

	    Document document = new Document(PageSize.A4.rotate(), 20, 20, 20, 20);

	    try {
	        PdfWriter.getInstance(document, new FileOutputStream("servicos.pdf"));
	        document.open();

	        com.itextpdf.text.Font fonteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
	        com.itextpdf.text.Font fonteSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	        com.itextpdf.text.Font fonteNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);
	        com.itextpdf.text.Font fonteCabecalho = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);

	        Date dataRelatorio = new Date();
	        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);

	        Paragraph titulo = new Paragraph("RELATÓRIO DE SERVIÇOS", fonteTitulo);
	        titulo.setAlignment(Element.ALIGN_CENTER);
	        titulo.setSpacingAfter(10);
	        document.add(titulo);

	        Paragraph data = new Paragraph("Emitido em: " + formatador.format(dataRelatorio), fonteNormal);
	        data.setSpacingAfter(15);
	        document.add(data);

	        String sql = """
	            select os, nome, dataOS, equipamento, defeito, valor
	            from servicos
	            inner join clientes on servicos.idcli = clientes.idcli
	            order by dataOS, os
	        """;

	        con = dao.conectar();
	        pst = con.prepareStatement(sql);
	        rs = pst.executeQuery();

	        String mesAtual = "";
	        double totalMes = 0.0;
	        double totalGeral = 0.0;
	        PdfPTable tabelaMes = null;

	        DateTimeFormatter entradaFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]");
	        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	        while (rs.next()) {
	            String dataBanco = rs.getString("dataOS");

	            LocalDate dataOs;
	            try {
	                if (dataBanco.length() > 10) {
	                    dataOs = LocalDateTime.parse(dataBanco.replace(" ", "T")).toLocalDate();
	                } else {
	                    dataOs = LocalDate.parse(dataBanco);
	                }
	            } catch (Exception e) {
	                // fallback caso o formato venha diferente
	                dataOs = rs.getDate("dataOS").toLocalDate();
	            }

	            String nomeMes = dataOs.getMonth()
	                    .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("pt", "BR"));
	            String mesAno = Character.toUpperCase(nomeMes.charAt(0)) + nomeMes.substring(1) + "/" + dataOs.getYear();

	            if (!mesAno.equals(mesAtual)) {

	                // fecha o mês anterior
	                if (!mesAtual.isEmpty()) {
	                    document.add(tabelaMes);

	                    Paragraph totalMesParagrafo = new Paragraph(
	                            "Total de " + mesAtual + ": R$ " + String.format("%.2f", totalMes),
	                            fonteSubtitulo
	                    );
	                    totalMesParagrafo.setAlignment(Element.ALIGN_RIGHT);
	                    totalMesParagrafo.setSpacingBefore(8);
	                    totalMesParagrafo.setSpacingAfter(15);
	                    document.add(totalMesParagrafo);
	                }

	                // inicia novo mês
	                mesAtual = mesAno;
	                totalMes = 0.0;

	                Paragraph mesTitulo = new Paragraph(mesAtual, fonteSubtitulo);
	                mesTitulo.setSpacingBefore(10);
	                mesTitulo.setSpacingAfter(8);
	                document.add(mesTitulo);

	                tabelaMes = new PdfPTable(6);
	                tabelaMes.setWidthPercentage(100);
	                tabelaMes.setSpacingAfter(5);
	                tabelaMes.setWidths(new float[]{1.2f, 2.5f, 1.6f, 2.0f, 3.2f, 1.4f});

	                PdfPCell col1 = new PdfPCell(new Paragraph("OS", fonteCabecalho));
	                PdfPCell col2 = new PdfPCell(new Paragraph("Cliente", fonteCabecalho));
	                PdfPCell col3 = new PdfPCell(new Paragraph("Data da OS", fonteCabecalho));
	                PdfPCell col4 = new PdfPCell(new Paragraph("Equipamento", fonteCabecalho));
	                PdfPCell col5 = new PdfPCell(new Paragraph("Defeito", fonteCabecalho));
	                PdfPCell col6 = new PdfPCell(new Paragraph("Valor", fonteCabecalho));

	                col1.setHorizontalAlignment(Element.ALIGN_CENTER);
	                col2.setHorizontalAlignment(Element.ALIGN_CENTER);
	                col3.setHorizontalAlignment(Element.ALIGN_CENTER);
	                col4.setHorizontalAlignment(Element.ALIGN_CENTER);
	                col5.setHorizontalAlignment(Element.ALIGN_CENTER);
	                col6.setHorizontalAlignment(Element.ALIGN_CENTER);

	                tabelaMes.addCell(col1);
	                tabelaMes.addCell(col2);
	                tabelaMes.addCell(col3);
	                tabelaMes.addCell(col4);
	                tabelaMes.addCell(col5);
	                tabelaMes.addCell(col6);
	            }

	            tabelaMes.addCell(new PdfPCell(new Paragraph(rs.getString("os"), fonteNormal)));
	            tabelaMes.addCell(new PdfPCell(new Paragraph(rs.getString("nome"), fonteNormal)));
	            tabelaMes.addCell(new PdfPCell(new Paragraph(dataOs.format(dataFormatter), fonteNormal)));
	            tabelaMes.addCell(new PdfPCell(new Paragraph(rs.getString("equipamento"), fonteNormal)));
	            tabelaMes.addCell(new PdfPCell(new Paragraph(rs.getString("defeito"), fonteNormal)));
	            tabelaMes.addCell(new PdfPCell(new Paragraph("R$ " + String.format("%.2f", rs.getDouble("valor")), fonteNormal)));

	            totalMes += rs.getDouble("valor");
	            totalGeral += rs.getDouble("valor");
	        }

	        // fecha último mês
	        if (!mesAtual.isEmpty() && tabelaMes != null) {
	            document.add(tabelaMes);

	            Paragraph totalMesParagrafo = new Paragraph(
	                    "Total de " + mesAtual + ": R$ " + String.format("%.2f", totalMes),
	                    fonteSubtitulo
	            );
	            totalMesParagrafo.setAlignment(Element.ALIGN_RIGHT);
	            totalMesParagrafo.setSpacingBefore(8);
	            totalMesParagrafo.setSpacingAfter(15);
	            document.add(totalMesParagrafo);
	        }

	        Paragraph totalGeralParagrafo = new Paragraph(
	                "TOTAL GERAL: R$ " + String.format("%.2f", totalGeral),
	                fonteTitulo
	        );
	        totalGeralParagrafo.setAlignment(Element.ALIGN_RIGHT);
	        totalGeralParagrafo.setSpacingBefore(10);
	        document.add(totalGeralParagrafo);

	    } catch (Exception e) {
	        System.out.println(e);
	        JOptionPane.showMessageDialog(null, "Erro ao gerar relatório: " + e.getMessage());
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (pst != null) pst.close();
	            if (con != null) con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        if (document.isOpen()) {
	            document.close();
	        }
	    }

	    try {
	        Desktop.getDesktop().open(new File("servicos.pdf"));
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}

	private void relatorioEstoque() {

	    Document document = new Document(PageSize.A3);

	    try {

	        PdfWriter.getInstance(document, new FileOutputStream("estoque.pdf"));

	        document.open();

	        Date dataRelatorio = new Date();
	        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);

	        // Adiciona a data ao documento
	        document.add(new Paragraph(formatador.format(dataRelatorio)));

	        document.add(new Paragraph("Produtos em Estoque:"));
	        document.add(new Paragraph(" "));

	        String readprod = "select nome, codigo, codigobarras as modelo_da_tv, barcode, valor, estoque, estoquemin as estóque_mínimo, armazenagem as local from produtos where estoque";

	        try {

	            con = dao.conectar();
	            pst = con.prepareStatement(readprod);
	            rs = pst.executeQuery();

	            PdfPTable tabela1 = new PdfPTable(8);
	            tabela1.setWidthPercentage(100);

	            PdfPCell col200 = new PdfPCell(new Paragraph("produto: "));
	            PdfPCell col100 = new PdfPCell(new Paragraph("código: "));
	            PdfPCell col300 = new PdfPCell(new Paragraph("modelo da tv"));
	            PdfPCell col700 = new PdfPCell(new Paragraph("código da placa: "));
	            PdfPCell col600 = new PdfPCell(new Paragraph("valor: "));
	            PdfPCell col400 = new PdfPCell(new Paragraph("estoque: "));
	            PdfPCell col500 = new PdfPCell(new Paragraph("estoque mínimo: "));
	            PdfPCell col430 = new PdfPCell(new Paragraph("local: "));

	            tabela1.addCell(col200);
	            tabela1.addCell(col100);
	            tabela1.addCell(col300);
	            tabela1.addCell(col700);
	            tabela1.addCell(col600);
	            tabela1.addCell(col400);
	            tabela1.addCell(col500);
	            tabela1.addCell(col430);

	            while (rs.next()) {
	                tabela1.addCell(rs.getString(1));
	                tabela1.addCell(rs.getString(2));
	                tabela1.addCell(rs.getString(3));
	                tabela1.addCell(rs.getString(4));
	                tabela1.addCell(rs.getString(5));
	                tabela1.addCell(rs.getString(6));
	                tabela1.addCell(rs.getString(7));
	                tabela1.addCell(rs.getString(8));
	            }
	            document.add(tabela1);

	            document.add(new Paragraph("Falta de estoque:"));
	            document.add(new Paragraph(" "));
	            String readClientes = "select codigo as código, nome as produto, estoque, estoquemin as estóque_mínimo from produtos where estoque < estoquemin";

	            pst = con.prepareStatement(readClientes);

	            rs = pst.executeQuery();

	            PdfPTable tabela = new PdfPTable(5);

	            PdfPCell col1 = new PdfPCell(new Paragraph("código: "));
	            PdfPCell col2 = new PdfPCell(new Paragraph("produto: "));
	            PdfPCell col3 = new PdfPCell(new Paragraph("validade: "));
	            PdfPCell col4 = new PdfPCell(new Paragraph("estoque: "));
	            PdfPCell col5 = new PdfPCell(new Paragraph("estoque mínimo: "));

	            tabela.addCell(col1);
	            tabela.addCell(col2);
	            tabela.addCell(col3);
	            tabela.addCell(col4);
	            tabela.addCell(col5);
	            while (rs.next()) {

	                tabela.addCell(rs.getString(1));
	                tabela.addCell(rs.getString(2));
	                tabela.addCell(rs.getString(3));
	                tabela.addCell(rs.getString(4));
	                tabela.addCell(rs.getString(5));
	            }

	            document.add(tabela);

	            document.add(new Paragraph(" "));

	            document.add(new Paragraph("Patrimônio (Custo):"));

	            document.add(new Paragraph(" "));

	            String read2 = "select sum(valor * estoque) as Total from produtos";

	            pst = con.prepareStatement(read2);
	            rs = pst.executeQuery();

	            PdfPTable tabela3 = new PdfPTable(1);
	            PdfPCell col12 = new PdfPCell(new Paragraph("Patrimônio custo: "));
	            tabela3.addCell(col12);

	            while (rs.next()) {
	                tabela3.addCell(rs.getString(1));
	            }

	            document.add(tabela3);

	            con.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	    } catch (Exception e) {
	        System.out.println(e);
	    }

	    document.close();

	    try {
	        Desktop.getDesktop().open(new File("estoque.pdf"));
	    } catch (Exception e) {
	        System.out.println(e);
	    }
	}
}