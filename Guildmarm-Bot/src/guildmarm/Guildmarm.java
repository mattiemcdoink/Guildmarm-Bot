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
    String[] monsterList = {"seltas", "desert seltas", "seltas queen", "desert seltas queen", "nerscylla", 
    		"shrouded nerscylla",  "cephadrome", "great jaggi", "velocidrome", "gendrome", "iodrome", "yian kut-ku", 
    		"blue yian kut-ku", "gypceros", "purple gypceros", "yian garuga", "daimyo hermitaur", "plum d.hermitaur", 
    		"kecha wacha", "ash kecha wacha", "lagombi", "congalala", "emerald congalala", "rajang", "apex rajang", 
    		"furious rajang", "tetsucabra", "berserk tetsucabra", "zamtrios", "tigerstripe zamtrios", "najarala", 
    		"tidal najarala", "apex tidal najarala", "brachydios", "raging brachydios", "deviljho", "apex deviljho", 
    		"savage deviljho", "khezu", "red khezu", "basarios", "ruby basarios", "gravios", "apex gravios", "black gravios", 
    		"rathian", "pink rathian", "gold rathian", "rathalos", "azure rathalos", "silver rathalos", "tigrex", "apex tigrex", 
    		"brute tigrex", "molten tigrex",  "diablos", "apex diablos", "black diablos", "monoblos", "white monoblos", 
    		"seregios", "apex seregios", "gore magala", "chaotic gore magala", "akantor", "ukanlos", "zinogre", "apex zinogre", 
    		"stygian zinogre", "kirin", "oroshi kirin", "shagaru magala", "dah'ren mohran", "kushala daora", "rusted kushala daora", 
    		"teostra", "chameleos", "dalamadur", "shah dalamadur", "gogmazios", "fatalis", "crimson fatalis", "white fatalis", 
    		"felyne", "melynx", "altaroth", "konchu", "bnahabra", "delex", "cephalos", "kelbi", "aptonoth", "rhenoplos", 
    		"slagtoth", "popo", "apceros", "gargwa", "jaggi", "jaggia", "velociprey", "genprey", "ioprey", "hermitaur", "conga", "zamite", "remobra" };
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
		
		public void createRoom(String newID, String newKey, String newOwner, String newComment) {
			this.setID(newID);
			this.setKey(newKey);
			this.setComment(newComment);
			this.setOwner(newOwner);
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

    private void checkRooms(Date date) {
		long curTime = date.getTime();
		for (int i = 0; i < numRooms; i++) {
			if ( roomList[i].getTimeNum() < curTime - this.timeout ) {
				// Remove any rooms that have a timestamp longer than 1 hour (curTime - timeout) ago
				closeRoom(i);
			}
		}
		
	}

    private Room findRoom(String owner) {
    	Room target = null;
    	boolean foundOwnedRoom, foundFreeRoom;
    	foundOwnedRoom = foundFreeRoom = false;
    	for ( int i = 0; i < roomCap; i++ ) {
    		if ( roomList[i].isActive() && roomList[i].getOwner().equalsIgnoreCase(owner) && !foundOwnedRoom ) {
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
    	return target;
    }
    
    private void openRoom(int index, String id, String key, String sender, String comment) {
		// TODO Auto-generated method stub
    	roomList[index].createRoom(id, key, sender, comment);
    	joinChannel(roomList[index].getChannel());
    	setTopic( roomList[index].getChannel(), "Room " + (roomList[index].getRoomNumber()+1) + ": Owner: " + roomList[index].getOwner() + " ID: " 
    			+ roomList[index].getID() + " Key: " + roomList[index].getKey() + " " + roomList[index].getComment() );
    	sendInvite( roomList[index].getOwner(), roomList[index].getChannel() );
    	numRooms++;
	}
    
	private void closeRoom(int index) {
		roomList[index].removeRoom();
		sendMessage( "ChanServ", "CLEAR " + roomList[index].getChannel() + " users ROOM CLOSING" );
		setTopic( roomList[index].getChannel(), "Room " + (roomList[index].getRoomNumber()+1) );
		numRooms--;
	}

	public void handleCommand(String channel, String sender, String message) {
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
    	Room target = null;
        String[] command = arg.split(" ");
        String toUpdate = "";
        String field = "";
        String option = "";
        String id = "";
        String key = "";
        Integer size = command.length;
        int index = 0;
        boolean hasRoom = false;
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
                target = findRoom(sender);
                if ( target != null ) {
                	
                }
                if (valid) {
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
                        sendMessage(sender, "Found your room Doodle. I'll update it for you.");
                        roomList[i].update(sender, toUpdate);
                    }
                }
            } else if ( option.equalsIgnoreCase("list")) {
                if ( numRooms.equals(0) ) {
                    sendMessage(sender, "Doesn't look like there are any rooms open right now Doodle. Sorry.");
                } else {
                    sendMessage(sender, "Here are those rooms you asked for Doodle.");
                }
                for (int i = 0; i < roomCap; i++) {
                    if ( roomList[i].isActive() ) {
                		sendMessage(sender, getRoom(i));
                    }
                }
            } else if ( option.equalsIgnoreCase("close") ) { 
                for (int i = 0; i < numRooms; i++) {
                    if ( roomList[i].getOwner().equalsIgnoreCase(sender) )  {
                        hasRoom = true;
                        index = i;
                        i = numRooms;
                    }
                }
                if ( hasRoom ) {
                    sendMessage(sender, "All done hunting now Doodle? See you next time!");
                    closeRoom(index);
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
//    	String status = roomList[index].isActive()?"Active":"Closed";
//      String room = "Room " + (roomList[index].getRoomNumber()+1) + ": Status: " + status + " ";
    	String room = "Room " + (roomList[index].getRoomNumber()+1) + " ";
        room = room + roomList[index].getOwner() + " " + roomList[index].getTimeStr() + " " + roomList[index].getComment();
        return room;
    }


        
}
