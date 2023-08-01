package view;

import java.awt.EventQueue;

import javax.swing.JDialog;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Sobre extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
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
	public Sobre() {
		getContentPane().setBackground(Color.WHITE);
		setModal(true);
		setTitle("Sobre");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/about.png")));
		setBounds(100, 100, 430, 373);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblTitulo = new JLabel("SP ASSISTENCIA ELETRODOMÉSTICOS");
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTitulo.setBounds(55, 17, 349, 27);
		getContentPane().add(lblTitulo);
		
		JLabel lblAutora = new JLabel("AUTORA:");
		lblAutora.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblAutora.setBounds(127, 309, 177, 14);
		getContentPane().add(lblAutora);
		
		JLabel lblNewLabel_2 = new JLabel("Sob a licença MIT");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblNewLabel_2.setBounds(133, 93, 127, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(Sobre.class.getResource("/img/mit-icon.png")));
		lblNewLabel_3.setBounds(286, 231, 128, 103);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Sobre.class.getResource("/img/icon.png")));
		lblLogo.setBounds(10, 3, 40, 56);
		getContentPane().add(lblLogo);
		
		JLabel lblThainara = new JLabel("THAINARA PIRES DA SILVA");
		lblThainara.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblThainara.setBounds(127, 296, 177, 14);
		getContentPane().add(lblThainara);
		
		JLabel lblVersao = new JLabel("VERSÃO: 1.0 BETA");
		lblVersao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblVersao.setBounds(55, 43, 163, 14);
		getContentPane().add(lblVersao);
		
		JLabel txtMIT = new JLabel("Sistema para gestão de serviços e controle de estoque da:");
		txtMIT.setBounds(20, 118, 384, 14);
		getContentPane().add(txtMIT);
		
		JLabel lblNewLabel = new JLabel("SP ASSISTENCIA ELETRODOMÉSTICOS.");
		lblNewLabel.setBounds(88, 132, 326, 14);
		getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				link("https://github.com/thainarapires");
			}
		});
		btnNewButton.setBorderPainted(false);
		btnNewButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setIcon(new ImageIcon(Sobre.class.getResource("/img/github.png")));
		btnNewButton.setBounds(25, 259, 64, 64);
		getContentPane().add(btnNewButton);
		
	
	}
	 private void link(String site) { 
		 Desktop desktop = Desktop.getDesktop();
		 try {
			 JOptionPane.showMessageDialog(null, "Voce será encaminhado para o GitHub");
			 URI uri = new URI(site);
			 desktop.browse(uri);
			
		} catch (Exception e) {
			
			//System.out.println(e);
		}
		 }
	 }


