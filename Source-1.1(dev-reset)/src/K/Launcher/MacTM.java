package K.Launcher;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;

import javax.swing.SpringLayout;
import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.FlowLayout;

import javax.imageio.IIOException;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.ConnectIOException;
import java.awt.event.ActionEvent;

public class MacTM extends JFrame {

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
		presence.details   = "In KSP1 mode";
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
					MacTM frame = new MacTM();
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
	public MacTM() {
		setTitle("K-Launcher (OSX/macOS)");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MacTM.class.getResource("/javax/swing/plaf/basic/icons/K-L-icon-png.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 433);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		JButton btnSteamLaunch = new JButton("Steam Launch");
		btnSteamLaunch.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				        try {
				            Runtime runTime = Runtime.getRuntime();
				            
				            String executablePath;
				            executablePath = "~/Library/Application Support/Steam/steamapps/common/Kerbal Space Program/KSP.app";
				            
				            runTime.exec(executablePath);
				        } catch (IOException o) {
		                   CantFindStemInstall err = new CantFindStemInstall();
		                   err.show();
						}
					}
		});
		btnSteamLaunch.setActionCommand("Steam Launch");
		buttonPane.add(btnSteamLaunch);
		
		JButton btnGalaxyOfGames = new JButton("Galaxy Of Games Launch");
		btnGalaxyOfGames.setEnabled(false);
		btnGalaxyOfGames.setActionCommand("Cancel");
		buttonPane.add(btnGalaxyOfGames);
		
		JTextArea txtrE = new JTextArea();
		txtrE.setText("Note:THIS LAUNCHER IS UNOFFICAL AND MADE IN JAVA.\r\nDO NOT CLAIM THIS IS A OFFICAL LAUNCHER BY HARVESTER,SQAUD OR PRIVATE DIVISION\r\nreminder:REPORT BUGS TO THE GITHUB\r\nLauncher Change Log:\r\nv1.1 \"Jeb is a non-nuclear day at the KSC\"\r\nAdditons:\r\n-KSP 2 support.\r\n-Working RPC (Finally)\r\nChanges:\r\n-Restarted development from scratch\r\n-made icon a classpath one\r\n-Changed the changelog\r\nBug fixes:\r\nnone bc restarted development from scratch\r\nNotes:\r\n-reason for dev reset:I moved to a Alienware 15 R3 just for KSP2\r\n-a win fourms edition is in progress\r\nv1.0.2 \"Apples\" aka \"Big Cats and Places In California\" compiled: 7th Feb 2023\r\nChanges:\r\nMAC SUPPORT!!! HAVE FUN MAC USERS.\r\nAdditions:None\r\nBug Fixes:None\r\nv1.0.1 compiled: 4th Feb 2023\r\nChanges:\r\n-Downgraded to Java 5 update 13. Thus Windows 98,Neptune,2000,Whisler And Me are partly supported\r\n- Spelling Mistakes \r\nAdditions:None\r\nBug Fixes:None\r\nNote:A GITHUB NOW EXISTS! THE SOFTWARE IS NOW UNDER GPLv3 (I Hope I Don't regret that) btw the url:https://github.com/K-Launcher/K-L\r\nv1.0.0 compiled: 2nd Feb 2023\r\nfirst relase.");
		txtrE.setLineWrap(true);
		contentPane.add(txtrE, BorderLayout.CENTER);
	}

}
