package K.Launcher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JTable;
import javax.imageio.IIOException;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class KSP2 extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DiscordRPC lib = DiscordRPC.INSTANCE;
		DiscordRichPresence presence = new DiscordRichPresence();
		String applicationId = args.length < 1 ? "1072592217799151666" : args[0];
		String steamId       = args.length < 2 ? "" : args[1];
		
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		handlers.ready = (user) -> System.out.println("Ready!");
		
		lib.Discord_Initialize(applicationId, handlers, true, steamId);
		
		presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
		//presence.endTimestamp   = presence.startTimestamp + 20;
		presence.details   = "In KSP2 mode";
		//presence.state     = "Test";
		lib.Discord_UpdatePresence(presence);
		
		Thread t = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				lib.Discord_RunCallbacks();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					lib.Discord_Shutdown(); //todo: find out why RPC ends after 10 seconds
					break;
				}
			}
		}, "RPC-Callback-Handler");
		t.start();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KSP2 frame = new KSP2();
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
	public KSP2() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(KSP2.class.getResource("/javax/swing/plaf/basic/icons/K-L-icon-png.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 777, 454);
		setUndecorated(true);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 64, 128));
		contentPane.setForeground(new Color(0, 64, 128));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Launch");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				  try {
			            Runtime runTime = Runtime.getRuntime();
			            
			            String executablePath;
			            executablePath = "C:\\Program Files (x86)\\Steam\\SteamApps\\common\\Kerbal Space Program 2\\KSP2_x64.exe";
			            
			            runTime.exec(executablePath);
			        } catch (IIOException o) {
	                   CantFindStemInstall2 errK2 = new CantFindStemInstall2();
	                   errK2.show();
			        } catch (java.io.IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		btnNewButton.setBounds(10, 399, 213, 55);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("X");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});
		btnNewButton_1.setBackground(new Color(255, 0, 0));
		btnNewButton_1.setBounds(686, 0, 91, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("KSP1 mode");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.out.println("Swapping to KSP1 mode");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            WindowsTM bin = new WindowsTM();
	            bin.show();
			}
		});
		btnNewButton_2.setBounds(343, 431, 178, 23);
		contentPane.add(btnNewButton_2);
	}
}
