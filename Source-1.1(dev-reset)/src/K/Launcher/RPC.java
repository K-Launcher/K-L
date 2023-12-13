package K.Launcher;

import club.minnced.discord.rpc.*;

public class RPC {

	public static void main(String args[]) {
		//if (args.length == 0) {
			//System.err.println("You must specify an application ID in the arguments!");
			//System.exit(-1);
		//}
		DiscordRPC lib = DiscordRPC.INSTANCE;
		DiscordRichPresence presence = new DiscordRichPresence();
		String applicationId = args.length < 1 ? "1072592217799151666" : args[0];
		String steamId       = args.length < 2 ? "" : args[1];
		
		DiscordEventHandlers handlers = new DiscordEventHandlers();
		handlers.ready = (user) -> System.out.println("Ready!");
		
		lib.Discord_Initialize(applicationId, handlers, true, steamId);
		
		presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
		//presence.endTimestamp   = presence.startTimestamp + 20;
		//presence.details   = "In KSP1 mode";
		presence.state     = "Test";
		lib.Discord_UpdatePresence(presence);
		
		Thread t = new Thread(() -> {
			while (!Thread.currentThread().isInterrupted()) {
				lib.Discord_RunCallbacks();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					lib.Discord_Shutdown();
					break;
				}
			}
		}, "RPC-Callback-Handler");
		t.start();
	   }
  }
