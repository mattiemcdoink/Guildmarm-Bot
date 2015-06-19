package guildmarm;

public class GuildmarmMain {
     
    public static void main(String[] args) throws Exception {
//        String channel = "##monsterhunter";
        String channel = "#bottest";
        Guildmarm bot = new Guildmarm(channel);
        
        bot.setVerbose(true);
        bot.connect("irc.freenode.net");
        bot.identify("J7HHJYf_D652a%DP");
        bot.joinChannel(channel);
    }
}