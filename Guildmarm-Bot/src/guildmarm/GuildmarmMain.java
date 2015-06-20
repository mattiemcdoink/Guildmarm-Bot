package guildmarm;

public class GuildmarmMain {
     
    public static void main(String[] args) throws Exception {
        String channel = "##monsterhunter";
//        String channel = "#bottest";
        Guildmarm bot = new Guildmarm(channel);
        
        bot.setVerbose(true);
        bot.connect("irc.freenode.net");
        bot.identify("");
        bot.joinChannel(channel);
    }
}