package guildmarm;

import org.jibble.pircbot.*;

import java.util.Arrays;
import java.io.File;
import java.util.Date;

public class Guildmarm extends PircBot {
	long timeout = 3600000;
	String channel = "";
    String kiranico = "http://kiranico.com/en/mh4u/";
    String monster = "http://kiranico.com/en/mh4u/monster/";
    String bigstick = "http://kiranico.com/en/mh4u/weapon/greatsword/";
    String longstick = "http://kiranico.com/en/mh4u/weapon/longsword/";
    String sticknshield = "http://kiranico.com/en/mh4u/weapon/sword/";
    String doublestick = "http://kiranico.com/en/mh4u/weapon/dualblades/";
    String heavystick = "http://kiranico.com/en/mh4u/weapon/hammer/";
    String noisystick = "http://kiranico.com/en/mh4u/weapon/huntinghorn/";
    String pointystick = "http://kiranico.com/en/mh4u/weapon/lance/";
    String boomstick = "http://kiranico.com/en/mh4u/weapon/gunlance/";
    String switchstick = "http://kiranico.com/en/mh4u/weapon/switchaxe/";
    String chargestick = "http://kiranico.com/en/mh4u/weapon/chargeblade/";
    String bugstick = "http://kiranico.com/en/mh4u/weapon/insectglaive/";
    String sgunstick = "http://kiranico.com/en/mh4u/weapon/lightbowgun/";
    String lgunstick = "http://kiranico.com/en/mh4u/weapon/heavybowgun/";
    String stringystick = "http://kiranico.com/en/mh4u/weapon/bow/";
    String[] monsterList = {"seltas", "desert seltas", "seltas queen", "desert seltas queen", "nerscylla", "shrouded nerscylla",  "cephadrome", "great jaggi", "velocidrome", "gendrome", "iodrome", "yian kut-ku", "blue yian kut-ku", "gypceros", "purple gypceros", "yian garuga", "daimyo hermitaur", "plum d.hermitaur", "kecha wacha", "ash kecha wacha", "lagombi", "congalala", "emerald congalala", "rajang", "apex rajang", "furious rajang", "tetsucabra", "berserk tetsucabra", "zamtrios", "tigerstripe zamtrios", "najarala", "tidal najarala", "apex tidal najarala", "brachydios", "raging brachydios", "deviljho", "apex deviljho", "savage deviljho", "khezu", "red khezu", "basarios", "ruby basarios", "gravios", "apex gravios", "black gravios", "rathian", "pink rathian", "gold rathian", "rathalos", "azure rathalos", "silver rathalos", "tigrex", "apex tigrex", "brute tigrex", "molten tigrex",  "diablos", "apex diablos", "black diablos", "monoblos", "white monoblos", "seregios", "apex seregios", "gore magala", "chaotic gore magala", "akantor", "ukanlos", "zinogre", "apex zinogre", "stygian zinogre", "kirin", "oroshi kirin", "shagaru magala", "dah'ren mohran", "kushala daora", "rusted kushala daora", "teostra", "chameleos", "dalamadur", "shah dalamadur", "gogmazios", "fatalis", "crimson fatalis", "white fatalis", "felyne", "melynx", "altaroth", "konchu", "bnahabra", "delex", "cephalos", "kelbi", "aptonoth", "rhenoplos", "slagtoth", "popo", "apceros", "gargwa", "jaggi", "jaggia", "velociprey", "genprey", "ioprey", "hermitaur", "conga", "zamite", "remobra" };
    room[] roomList = new room[100];
    Integer numRooms = 0;

    private class room {
        String id = "";
        String key = "";
        String owner = "";
        String comment = "";
        String time = "";
        Date timeStamp = null;      
        
        private void update(String owner, String comment) {
            String[] tmp;
            this.comment = comment;
            this.timeStamp = new java.util.Date(); 
            tmp = timeStamp.toString().split(" ");
            this.time = tmp[0] + " " + tmp[3];
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

        public room(String id, String key, String owner, String comment) {
            this.id = id;
            this.key = key;
            this.owner = owner;
            this.comment = comment;
            this.update(owner, comment); 
        }
    }

    public Guildmarm(String channel) {
    	this.channel = channel;
        this.setName("Guildmarm");
        this.setLogin("Guildmarm");
    }
    
    public void onPrivateMessage(String sender, String login, String hostname, String message) {
        handleCommand(sender, message);
        checkRooms(new java.util.Date());
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        handleCommand(sender, message);
        checkRooms(new java.util.Date());
        //updateSeen(sender);
    }

    private void checkRooms(Date date) {
		long curTime = date.getTime();
		for (int i = 0; i < numRooms; i++) {
			if ( roomList[i].timeStamp.getTime() < curTime - this.timeout ) {
				// Remove any rooms that have a timestamp longer than 1 hour (curTime - timeout) ago
				closeRoom(i);
			}
		}
		
	}

    private void openRoom(boolean hasRoom, int tmp, String id, String key, String sender, String comment) {
		// TODO Auto-generated method stub
    	if (!hasRoom) {
            roomList[numRooms] = new room(id, key, sender, comment);
        } else {
            roomList[tmp] = new room(id, key, sender, comment);
        }
    	numRooms++;
	}
    
	private void closeRoom(int i) {
		for (int j = i; j < numRooms; j++) {
			if (j == this.numRooms-1) {
				roomList[j] = null;
			} else {
				roomList[j] = roomList[j+1];
			}
		}
		numRooms--;
	}

	public void handleCommand(String sender, String message) {
        String[] command = message.split(" ");
        int size = command.length;
        String arg = "";
        String[] link = { "!monster", "!link" };
        if (size > 1) {
            arg = command[1];
            for (int i = 2; i < size; i++) {
                arg = arg + " " + command[i];
            }
        }
        if (Arrays.asList(link).contains(command[0])) {
            commandLink(sender, command[0], arg);
        } else if (command[0].equalsIgnoreCase("!room")) {
            commandRoom(sender, arg);
        } else if (command[0].equalsIgnoreCase("!help")) {
            commandHelp(sender, arg);
        }
    }

    public void commandLink(String sender, String type, String name) {
        // Returns the Kiranico page for the specified item
        String linkName = name.toLowerCase().replaceAll(" ", "-");
        //this.sendMessage(channel, "Do I know about " + linkName + ", Doodle?");
        if (Arrays.asList(monsterList).contains(name.toLowerCase())) {
            this.sendMessage(sender, "Here you go Doodle! " + this.monster + linkName);  
        } else {
            this.sendMessage(sender, "Is that some new monster Doodle? It's not in my notebook.");
        }
    }

    public void commandRoom(String sender, String arg) {
        //maintains active room lists of the format
        //Owner ID Password Target Last Updated
        String[] command = arg.split(" ");
        String comment = "";
        String id = "";
        String key = "";
        Integer size = command.length;
        int tmp = 0;
        boolean hasRoom = false;
        boolean valid = false;
        if ( size > 0 ) {
            String option = command[0];
            if (option.equalsIgnoreCase("create")) {
                if (size >= 2) {
                    id = command[1];
                    if (size >= 3) {
                        key = command[2];
                        valid = validateInput(id, key, sender);
                        if (size >=4) {
                            comment = command[3];
                            for (int i = 4; i < size; i++) {
                                comment = comment + " " + command[i];
                            }
                        }
                    }
                }
                for (int i = 0; i < this.numRooms; i++) {
                    if ( this.roomList[i].getOwner().equalsIgnoreCase(sender) )  {
                        hasRoom = true;
                        tmp = i;
                        i = this.numRooms;
                    }
                }
                if (valid) {
                    this.sendMessage(sender, "Creating a new room for you Doodle!");
                    openRoom(hasRoom, tmp, id, key, sender, comment);
                } 
                

            } else if ( option.equalsIgnoreCase("update")) {
                if ( size >=2 ) {
                    comment = command[1];
                    for (int i = 2; i < size; i++) {
                        comment = comment + " " + command[i];
                    }
                }
                for (int i = 0; i < this.numRooms; i++) {
                    if ( this.roomList[i].getOwner().equalsIgnoreCase(sender) ) {
                        sendMessage(sender, "Found your room Doodle. I'll update it for you.");
                        roomList[i].update(sender, comment);
                    }
                }
            } else if ( option.equalsIgnoreCase("list")) {
                if ( numRooms.equals(0) ) {
                    sendMessage(sender, "Doesn't look like there are any rooms open right now Doodle. Sorry.");
                } else {
                    sendMessage(sender, "Here are those rooms you asked for Doodle.");
                }
                for (int i = 0; i < numRooms; i++) {
                    sendMessage(sender, getRoom(i));
                }
            } else if ( option.equalsIgnoreCase("close") ) { 
                for (int i = 0; i < numRooms; i++) {
                    if ( roomList[i].getOwner().equalsIgnoreCase(sender) )  {
                        hasRoom = true;
                        tmp = i;
                        i = numRooms;
                    }
                }
                if ( hasRoom ) {
                    sendMessage(sender, "All done hunting now Doodle? See you next time!");
                    closeRoom(tmp);
                }
            } else if ( option.equalsIgnoreCase("")) {
            	sendMessage(channel, "There are currently " + numRooms + " currently running Doodle.");
            }
        } else {
            sendMessage(sender, "There seems to have been a problem with your ID or Key Doodle, check them and try again would you please?");
        }
    }

	public void commandHelp(String sender, String arg) {
        this.sendMessage(sender, "Need some help Doodle?");
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
            return false;
        } else {
            for ( int i = 0; i < pIDLen; i++ ) {
                c = id.charAt(i);
                if ( i != 2 && i != 7 && i != 12 ) {
                    if ( !Character.isDigit(c) ) {
                        sendMessage(sender, "ID's can only be numbers you silly Doodle. " + c);
                        return false;
                    }
                } else {
                    if ( !Arrays.asList(separator).contains(c)) {
                        sendMessage(sender, "Sorry Doodle, you gotta use '-', ':', ';', '|', '|', or ',' to separate your ID.");
                        return false;
                    }
                }
            }
        }
        if ( !pKeyLen.equals(key.length()) ) {
            if ( key.equalsIgnoreCase("-")) {
                return true;
            }
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
        String room = "";
        room = roomList[index].getOwner();
        room = room + " " + roomList[index].getID();
        room = room + " " + roomList[index].getKey();
        room = room + " " + roomList[index].getTimeStr();
        room = room + " " + roomList[index].getComment();
        return room;
    }


        
}
