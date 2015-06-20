package guildmarm;

import org.jibble.pircbot.*;

import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

public class Guildmarm extends PircBot {
	long timeout = 3600000;
	String channel = "";
	String kiranico = "http://kiranico.com/en/mh4u/";
	String guild = kiranico + "quest/guild/";
	String caravan = kiranico + "quest/caravan/";
	String event = kiranico + "quest/event/";
	String monster = kiranico + "monster/";
	String skill = kiranico + "armor/skill/";
	String head = kiranico + "armor/head/";
	String chest = kiranico + "armor/chest/";
	String arms = kiranico + "armor/arms/";
	String waist = kiranico + "armor/waist/";
	String legs = kiranico + "armor/legs/";
	String bigstick = kiranico + "weapon/greatsword/";
	String longstick = kiranico + "weapon/longsword/";
	String sticknshield = kiranico + "weapon/sword/";
	String doublestick = kiranico + "weapon/dualblades/";
	String heavystick = kiranico + "waepon/hammer/";
	String noisystick = kiranico + "weapon/huntinghorn/";
	String pointystick = kiranico + "weapon/lance/";
	String boomstick = kiranico + "weapon/gunlance/";
	String switchstick = kiranico + "weapon/switchaxe/";
	String chargestick = kiranico + "weapon/chargeblade/";
	String bugstick = kiranico + "weapon/insectglaive/";
	String sgunstick = kiranico + "weapon/lightbowgun/";
	String lgunstick = kiranico + "weapon/heavybowgun/";
	String stringystick = kiranico + "waepon/bow/";
	String[] weapons = { bigstick, longstick, sticknshield, doublestick, heavystick, noisystick, pointystick, boomstick, 
			switchstick, chargestick, bugstick, sgunstick, lgunstick, stringystick };
	String[] armor = { head, chest, arms, waist, legs, };
	String[] quest = { guild, caravan, event };
	//    String[] monsterList = {"seltas", "desert seltas", "seltas queen", "desert seltas queen", "nerscylla", 
	//    		"shrouded nerscylla",  "cephadrome", "great jaggi", "velocidrome", "gendrome", "iodrome", "yian kut-ku", 
	//    		"blue yian kut-ku", "gypceros", "purple gypceros", "yian garuga", "daimyo hermitaur", "plum d.hermitaur", 
	//    		"kecha wacha", "ash kecha wacha", "lagombi", "congalala", "emerald congalala", "rajang", "apex rajang", 
	//    		"furious rajang", "tetsucabra", "berserk tetsucabra", "zamtrios", "tigerstripe zamtrios", "najarala", 
	//    		"tidal najarala", "apex tidal najarala", "brachydios", "raging brachydios", "deviljho", "apex deviljho", 
	//    		"savage deviljho", "khezu", "red khezu", "basarios", "ruby basarios", "gravios", "apex gravios", "black gravios", 
	//    		"rathian", "pink rathian", "gold rathian", "rathalos", "azure rathalos", "silver rathalos", "tigrex", "apex tigrex", 
	//    		"brute tigrex", "molten tigrex",  "diablos", "apex diablos", "black diablos", "monoblos", "white monoblos", 
	//    		"seregios", "apex seregios", "gore magala", "chaotic gore magala", "akantor", "ukanlos", "zinogre", "apex zinogre", 
	//    		"stygian zinogre", "kirin", "oroshi kirin", "shagaru magala", "dah'ren mohran", "kushala daora", "rusted kushala daora", 
	//    		"teostra", "chameleos", "dalamadur", "shah dalamadur", "gogmazios", "fatalis", "crimson fatalis", "white fatalis", 
	//    		"felyne", "melynx", "altaroth", "konchu", "bnahabra", "delex", "cephalos", "kelbi", "aptonoth", "rhenoplos", 
	//    		"slagtoth", "popo", "apceros", "gargwa", "jaggi", "jaggia", "velociprey", "genprey", "ioprey", "hermitaur", "conga", "zamite", "remobra" };
	Integer roomCap = 20;
	Integer numRooms = 0;
	Room[] roomList = new Room[roomCap];

	private class Room {
		String id = "";
		String key = "";
		String owner = "";
		String comment = "";
		String time = "";
		String subChannel = "";
		Integer roomNumber = 0;
		Integer numPlayers = 0;
		boolean active = false;
		Date timeStamp = null;      

		private void update(String option, String toUpdate) {
			String[] tmp;
			String testKey = "0000";
			String testID = "00-0000-0000-0000";
			boolean valid;
			this.timeStamp = new java.util.Date(); 
			tmp = timeStamp.toString().split(" ");
			this.time = tmp[0] + " " + tmp[3];
			if ( option.equalsIgnoreCase("owner") ) {
				if ( Arrays.asList(getUsers(this.subChannel)).contains(toUpdate) ) {
					this.setOwner(toUpdate);
				}
			} else if ( option.equalsIgnoreCase("id") ) {
				valid = validateInput(toUpdate, testKey, this.getOwner());
				if ( valid ) {
					this.setID(toUpdate);
				}
			} else if ( option.equalsIgnoreCase("key") ) {
				valid = validateInput(testID, toUpdate, this.getOwner());
				if ( valid ) {
					this.setKey(toUpdate);
				}
			} else if ( option.equalsIgnoreCase("comment") ) {
				this.setComment(toUpdate);
			}
			if ( this.isActive() ) {
				setTopic( this.getChannel(), "Room " + (this.getRoomNumber()+1) + ": Owner: " + this.getOwner() + " ID: " 
						+ this.getID() + " Key: " + this.getKey() + " " + this.getComment() );
			} else {
				setTopic ( this.getChannel(), "Room " + (this.getRoomNumber()+1) );
			}
		}

		public boolean isActive() {
			return this.active;
		}
		public String getOwner() {
			return this.owner;
		}
		public void setOwner(String newOwner) {
			this.owner = newOwner;
		}
		public String getKey() {
			return this.key;
		}
		public void setKey(String newKey) {
			this.key = newKey;
		}
		public String getID() {
			return this.id;
		}
		public String getComment() {
			return this.comment;
		}
		public void setComment(String newComment) {
			this.comment = newComment;
		}
		public void setID(String newID) {
			this.id = newID;
		}
		public long getTimeNum() {
			long val = this.timeStamp.getTime();
			return val;
		}
		public String getTimeStr() {
			return this.time;
		}

		public void setChannel(String string) {
			this.subChannel = string;			
		}
		public String getChannel() {
			return this.subChannel;
		}
		public Integer getRoomNumber() {
			return this.roomNumber;
		}
		public void setRoomNumber(Integer num) {
			this.roomNumber = num;
		}
		private void setNumPlayers(int i) {
			this.numPlayers = i;
		}
		private Integer getNumPlayers() {
			return this.numPlayers;
		}

		public void createRoom(String newID, String newKey, String newOwner, String newComment) {
			this.setID(newID);
			this.setKey(newKey);
			this.setComment(newComment);
			this.setOwner(newOwner);
			this.setNumPlayers(1);
			this.active = true;
			this.update("", "");
		}

		public void removeRoom() {
			this.setID("");
			this.setKey("");
			this.setOwner("");
			this.setComment("");
			this.timeStamp = null;
			this.active = false;
			this.numPlayers = 0;
		}


		public Room() {
			this.setID("");
			this.setKey("");
			this.setOwner("");
			this.setComment("");
			this.timeStamp = null;
			this.active = false;
		}
	}

	public Guildmarm(String channel) {
		String chanNum = "";
		this.channel = channel;
		this.setName("Guildmarm");
		this.setLogin("GuildmarmBot");
		for ( int i = 0; i < roomCap; i++ ) {
			chanNum = i < 10?"0":"";
			roomList[i] = new Room();
			roomList[i].setRoomNumber(i);
			roomList[i].setChannel( "##mhroom" + chanNum + roomList[i].getRoomNumber().toString() );
			System.out.println( "Rooom " + (roomList[i].getRoomNumber()+1) + " initialized. Sub-Channel: " + roomList[i].getChannel() );
		}
	}

	public void onPrivateMessage(String sender, String login, String hostname, String message) {
		handleCommand("", sender, message);
		checkRooms(new java.util.Date());
	}

	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		handleCommand(channel, sender, message);
		checkRooms(new java.util.Date());
		//updateSeen(sender);
	}

	public void onOp(String channel, String sourceNick, String sourceLogin, String sourceHostname, String recipient) {
		if ( recipient.equalsIgnoreCase(this.getName()) ) {
			for ( int i = 0; i < roomCap; i++ ) {
				if ( roomList[i].getChannel().equalsIgnoreCase(channel) ) {
					if ( roomList[i].isActive() ) {
						setTopic( roomList[i].getChannel(), "Room " + (roomList[i].getRoomNumber()+1) + ": Owner: " + roomList[i].getOwner() + " ID: " 
								+ roomList[i].getID() + " Key: " + roomList[i].getKey() + " " + roomList[i].getComment() );
						sendInvite( roomList[i].getOwner(), roomList[i].getChannel() );
					} else {
						setTopic ( roomList[i].getChannel(), "Room " + (roomList[i].getRoomNumber()+1) );
					}
				} 
			}
		}
	}

	private void checkRooms(Date date) {
		long curTime = date.getTime();
		for (int i = 0; i < roomCap; i++) {
			if ( roomList[i].isActive() ) {
				if ( roomList[i].getTimeNum() < curTime - this.timeout ) {
					// Remove any rooms that have a timestamp longer than 1 hour (curTime - timeout) ago
					closeRoom(i);
				}
			}
		}

	}

	private Room findRoom(String owner, String channel) {
		Room target = null;
		boolean foundOwnedRoom, foundFreeRoom;
		foundOwnedRoom = foundFreeRoom = false;
		for ( int i = 0; i < roomCap; i++ ) {
			if ( ( roomList[i].getChannel().equalsIgnoreCase(channel) && roomList[i].isActive() ) || 
					( roomList[i].getOwner().equalsIgnoreCase(owner) && !foundOwnedRoom ) ) {
				target = roomList[i];
				foundOwnedRoom = true;
			} else if ( !roomList[i].isActive() && !foundFreeRoom && !foundOwnedRoom ) {
				target = roomList[i];
				foundFreeRoom = true;
			}
		}
		if ( foundOwnedRoom ) {
			sendMessage(owner, "Alright, I found your room Doodle.");
		} else if ( foundFreeRoom ) {
			sendMessage(owner, "Looks like you didn't have a room already Doodle. So I found you an open one");
		} else {
			sendMessage(owner, "Well Doodle, this is something that shouldn't have happened. Could you please Yell at Tonberry to fix findRoom() for me please?");
		}
		//    	if ( !foundOwnedRoom && !foundFreeRoom ) {
		//    		sendMessage(owner, "Well Doodle, this is something that shouldn't have happened. Could you please Yell at Tonberry to fix findRoom() for me please?");
		//    	}
		return target;
	}

	private void joinRoom(int index, String sender) {
		sendInvite(sender, roomList[index].getChannel());
		roomList[index].setNumPlayers(roomList[index].getNumPlayers()+1);
	}

	private void leaveRoom(String sender, String channel) {
		Room target = findRoom("", channel);
		if ( target != null ) {
			kick(channel, sender, "Thanks for hunting Doodle!");
		}
	}

	private void openRoom(int index, String id, String key, String sender, String comment) {
		// TODO Auto-generated method stub
		roomList[index].createRoom(id, key, sender, comment);
		joinChannel(roomList[index].getChannel());
		sendMessage(sender, "Making a room for you Doodle.");
		sendInvite(sender, roomList[index].getChannel());
		numRooms++;
	}

	private void closeRoom(int index) {
		roomList[index].removeRoom();
		sendMessage(roomList[index].getChannel(), "Closing room. Just a sec Doodle.");
		User[] users = getUsers(roomList[index].getChannel());
		for ( int i = 0; i < users.length; i++ ) {
			if ( !users[i].getNick().equalsIgnoreCase("Guildmarm") && !users[i].getNick().equalsIgnoreCase("ChanServ") ) {
				kick(roomList[index].getChannel(), users[i].getNick(), "ROOM CLOSING");
			}
		}
		setTopic( roomList[index].getChannel(), "Room " + (roomList[index].getRoomNumber()+1) );
		numRooms--;
	}

	public void handleCommand(String channel, String sender, String message) {
		String[] command = message.split(" ");
		int size = command.length;
		String arg = "";
		String[] link = { "!monster", "!link", "!weapon", "!armor", "!skill", "!quest" };
		if (size > 1) {
			arg = command[1];
			for (int i = 2; i < size; i++) {
				arg = arg + " " + command[i];
			}
		}
		if (Arrays.asList(link).contains(command[0])) {        
			try {
				commandLink(sender, command);
			} catch ( IOException e ) {
				System.out.println("Encountered Error");
			}
		} else if (command[0].equalsIgnoreCase("!room")) {
			commandRoom(sender, arg, channel);
		} else if (command[0].equalsIgnoreCase("!help")) {
			commandHelp(sender, arg);
		}
	}

	public void commandLink(String sender, String[] command) throws IOException {
		//        // Returns the Kiranico page for the specified item
		//        String linkName = name.toLowerCase().replaceAll(" ", "-");
		//        //this.sendMessage(channel, "Do I know about " + linkName + ", Doodle?");
		//        if (Arrays.asList(monsterList).contains(name.toLowerCase())) {
		//            this.sendMessage(sender, "Here you go Doodle! " + this.monster + linkName);  
		//        } else {
		//            this.sendMessage(sender, "Is that some new monster Doodle? It's not in my notebook.");
		//        }
		String[] listCommand = { "!weapon", "!quest", "!armor", "!monster", "!skill" };
		String[] listOption = { "weapon", "quest", "armor", "monster", "skill" };
		HttpURLConnection huc;
		URL u;
		String URLString = "";
		String option = "";
		String target = "";
		//    	HttpURLConnection.setFollowRedirects(false);
		if ( command.length > 1 ) {
			if ( Arrays.asList(listCommand).contains(command[0]) ) {
				target = command[1].toLowerCase().replace(":", "").replace("'", "");
				for ( int i = 2; i < command.length; i++ ) {
					target = target + "-" + command[i].toLowerCase().replace(":", "").replace("'", "");
				}
			} 
			if ( command.length > 2 ) {
				if ( command[0].equalsIgnoreCase("!link") && Arrays.asList(listOption).contains(command[1]) ) {
					option = command[1];
					target = command[2].toLowerCase().replace(":", "").replace("'", "");
					for ( int i = 3; i < command.length; i++ ) {
						target = target + "-" + command[i].toLowerCase().replace(":", "").replace("'", "");
					}
				}
			}
			if ( command[0].equalsIgnoreCase("!weapon") || ( command[0].equalsIgnoreCase("!link") && option.equalsIgnoreCase("weapon")) ) {
				for ( int i = 0; i < weapons.length; i++ ) {
					URLString = weapons[i] + target;
					u = new URL(URLString);
					huc = (HttpURLConnection)u.openConnection();
					huc.setRequestMethod("HEAD");
					huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
					huc.connect();
					System.out.println("Trying " + URLString + " Method " + huc.getRequestMethod() + " Got " + huc.getResponseCode() + " Needed " + HttpURLConnection.HTTP_OK);
					if ( huc.getResponseCode() == HttpURLConnection.HTTP_OK ) {
						sendNotice(sender, "Here you go Doodle. " + URLString);
					}
				}
			} else if ( command[0].equalsIgnoreCase("!monster") || ( command[0].equalsIgnoreCase("!link") && option.equalsIgnoreCase("monster") ) ) {
				URLString = monster + target;
				u = new URL(URLString);
				huc = (HttpURLConnection)u.openConnection();
				huc.setRequestMethod("HEAD");
				huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
				huc.connect();
				System.out.println("Trying " + URLString + " Method " + huc.getRequestMethod() + " Got " + huc.getResponseCode() + " Needed " + HttpURLConnection.HTTP_OK);
				if ( huc.getResponseCode() == HttpURLConnection.HTTP_OK ) {
					sendNotice(sender, "Here you go Doodle. " + URLString);
				}
			} else if ( command[0].equalsIgnoreCase("!quest") || ( command[0].equalsIgnoreCase("!link") && option.equalsIgnoreCase("quest") ) ) {
				for ( int i = 0; i < 3; i++ ) {
					for ( int j = 1; j <= 10; j++ ) {
						switch (i) {
						case(0):
							URLString = caravan + j + "/" + target;
						break;
						case(1):
							URLString = guild + j + "/" + target;
						break;
						case(2):
							URLString = event + j + "/" + target;
						break;
						}
						u = new URL(URLString);
						huc = (HttpURLConnection)u.openConnection();
						huc.setRequestMethod("HEAD");
						huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
						huc.connect();
						System.out.println("Trying " + URLString + " Method " + huc.getRequestMethod() + " Got " + huc.getResponseCode() + " Needed " + HttpURLConnection.HTTP_OK);
						if ( huc.getResponseCode() == HttpURLConnection.HTTP_OK ) {
							sendNotice(sender, "Here you go Doodle. " + URLString);
						}
					}
				}
			} else if ( command[0].equalsIgnoreCase("!armor") || ( command[0].equalsIgnoreCase("!link") && option.equalsIgnoreCase("armor") ) ) {
				for ( int i = 0; i < armor.length; i++ ) {
					URLString = armor[i] + target;
					u = new URL(URLString);
					huc = (HttpURLConnection)u.openConnection();
					huc.setRequestMethod("HEAD");
					huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
					huc.connect();
					System.out.println("Trying " + URLString + " Method " + huc.getRequestMethod() + " Got " + huc.getResponseCode() + " Needed " + HttpURLConnection.HTTP_OK);
					if ( huc.getResponseCode() == HttpURLConnection.HTTP_OK ) {
						sendNotice(sender, "Here you go Doodle. " + URLString);
					}
				}
			} else if ( command[0].equalsIgnoreCase("!skill") || ( command[0].equalsIgnoreCase("!link") && option.equalsIgnoreCase("skill") ) ) {
				URLString = skill + target;
				u = new URL(URLString);
				huc = (HttpURLConnection)u.openConnection();
				huc.setRequestMethod("HEAD");
				huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
				huc.connect();
				System.out.println("Trying " + URLString + " Method " + huc.getRequestMethod() + " Got " + huc.getResponseCode() + " Needed " + HttpURLConnection.HTTP_OK);
				if ( huc.getResponseCode() == HttpURLConnection.HTTP_OK ) {
					sendNotice(sender, "Here you go Doodle. " + URLString);
				}
			}
		}

	}

	public void commandRoom(String sender, String arg, String channel) {
		//maintains active room lists of the format
		//Owner ID Password Target Last Updated
		Room target = null;
		String[] command = arg.split(" ");
		String toUpdate = "";
		String field = "";
		String option = "";
		String id = "";
		String key = "";
		Integer size = command.length;
		int index = 0;
		boolean valid = false;
		if ( size > 0 ) {
			option = command[0];
			if (option.equalsIgnoreCase("create")) {
				if (size >= 2) {
					id = command[1];
					if (size >= 3) {
						key = command[2];
						valid = validateInput(id, key, sender);
						if (size >=4) {
							toUpdate = command[3];
							for (int i = 4; i < size; i++) {
								toUpdate = toUpdate + " " + command[i];
							}
						}
					}
				}
				target = findRoom(sender, "");
				if ( target != null && valid ) {
					index = target.getRoomNumber();
					openRoom(index, id, key, sender, toUpdate);
				}


			} else if ( option.equalsIgnoreCase("update")) {
				if ( size >=3 ) {
					field = command[1];
					toUpdate = command[2];
					for (int i = 3; i < size; i++) {
						toUpdate = toUpdate + " " + command[i];
					}
				}
				for (int i = 0; i < this.numRooms; i++) {
					if ( this.roomList[i].getOwner().equalsIgnoreCase(sender) ) {
						sendNotice(sender, "Found your room Doodle. I'll update it for you.");
						roomList[i].update(field, toUpdate);
					}
				}
			} else if ( option.equalsIgnoreCase("list")) {
				if ( numRooms.equals(0) ) {
					sendNotice(sender, "Doesn't look like there are any rooms open right now Doodle. Sorry.");
				} else {
					sendMessage(sender, "Here are those rooms you asked for Doodle.");
				}
				for (int i = 0; i < roomCap; i++) {
					if ( roomList[i].isActive() ) {
						sendMessage(sender, getRoom(i));
					}
				}
			} else if ( option.equalsIgnoreCase("close") ) {
				target = findRoom(sender, "");
				if ( target != null ) {
					closeRoom(target.getRoomNumber());
				}
			} else if ( option.equalsIgnoreCase("join") ) {
				if ( size >= 2 ) {
					toUpdate = command[1];
					for ( int i = 0; i < roomCap; i++ ) {
						if ( roomList[i].getOwner().equalsIgnoreCase(toUpdate) ) {
							joinRoom(i, sender);
						}
					}
				}
			} else if ( option.equalsIgnoreCase("leave") ) {
				leaveRoom(sender, channel);
			} else if ( option.equalsIgnoreCase("")) {
				String roomSuffix = (numRooms>1)?"s":"";
				sendMessage(this.channel, "There's " + numRooms + " room" + roomSuffix + " currently running, Doodle.");
			}
		} else {
			sendMessage(sender, "There seems to have been a problem with your ID or Key Doodle, check them and try again would you please?");
		}
	}

	public void commandHelp(String sender, String arg) {
		String[] command = arg.split(" ");
		Integer size = command.length; //use this later to add cases for arguments to commands
		if (command[0].equalsIgnoreCase("monster") || command[0].equalsIgnoreCase("!monster")) {
			sendMessage(sender, "Syntax: !monster name Example: !monster Apex Rajang. Just tell me the name of a monster and I'll find it here in my notebook.");
		} else if (command[0].equalsIgnoreCase("link") || command[0].equalsIgnoreCase("!link")) {
			sendMessage(sender, "I have these resources available to link. Just say the name of the resource after !link and I'll get it for you.");
			sendMessage(sender, "relicskins - Relic Skin Spreadsheet, bmrelics - Blademaster relic stats chart, charms - Charm stats and skill chart");
		} else if (command[0].equalsIgnoreCase("room") || command[0].equalsIgnoreCase("!room")) {
			sendMessage(sender, "!room list will list currently open rooms created with \"!room create\". Options are create, update, close. Rooms are associated with the nick of the person who created them.");
			sendMessage(sender, "create - Syntax: !room create id password comment. Creates a room with the provided information which can then be retrieved with !room. Comments can be anything like what you're fighting, whether the room is High Rank, G-Rank, turns, anything.");
			sendMessage(sender, "update - Syntax: !room update comment. Updates the last active time of the room. Rooms that have had no updates issued for over an hour will be automatically closed.");
			sendMessage(sender, "close - Syntax: !room close. Closes any rooms owned by you. Use this when you're done hosting your room.");
		} else {
			sendMessage(sender, "Available commands are. !monster, !room. Message !help followed by the command you wanna know about Doodle and I'll be happy to tell you.");
		}

	}

	public boolean validateInput(String id, String key, String sender) {
		//validating the input id. Proper id is of the format 00-0000-0000-0000
		//a 17 character string with - at the 3rd, 8th, and 13th positions with the 
		//rest being numbers
		Integer pIDLen = 17;
		Integer pKeyLen = 4;
		Character c = null;
		Character[] separator = { '-', ':', '.', ',', '|', '/', ';' };

		if ( !pIDLen.equals(id.length()) ) {
			sendMessage(sender, "Your ID needs to be 14 digits in the form 00-0000-0000-0000. Could you please fix that for me Doodle?");
			return false;
		} else {
			for ( int i = 0; i < pIDLen; i++ ) {
				c = id.charAt(i);
				if ( i != 2 && i != 7 && i != 12 ) {
					if ( !Character.isDigit(c) ) {
						sendMessage(sender, "Don't try to fool me, you silly Doodle. " + c + " isn't a number.");
						return false;
					}
				} else {
					if ( !Arrays.asList(separator).contains(c)) {
						sendMessage(sender, "Sorry Doodle, you gotta use '-' to separate the numbers in your ID.");
						return false;
					}
				}
			}
		}
		if ( !pKeyLen.equals(key.length()) ) {
			if ( key.equalsIgnoreCase("-")) {
				return true;
			}
			sendMessage(sender, "Keys can only be 4 digits or '-' Doodle.");
			return false;
		} else {
			for ( int i = 0; i < pKeyLen; i++ ) {
				c = key.charAt(i);
				if ( !Character.isDigit(c) ) {
					sendMessage(sender, "Keys can only be 4 digits or '-' Doodle.");
					return false;
				}
			}
		}
		return true;
	}
	public String getRoom(Integer index) {
		//    	String status = roomList[index].isActive()?"Active":"Closed";
		//      String room = "Room " + (roomList[index].getRoomNumber()+1) + ": Status: " + status + " ";
		String room = roomList[index].getOwner() + " " + roomList[index].getNumPlayers() + "/4 Players " 
				+ roomList[index].getTimeStr() + " " + roomList[index].getComment();
		return room;
	}



}
