package CommandController;

import CommandHandler.Settings_bot;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;


public class Filewriter_Controller {
    private File file;

    public Filewriter_Controller() {
        file = new File("server_variables.json");
    }

    // write the content in file
    public void write(JSONObject text) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.write(text.toString(2));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // reading the content of file
    public String read() {
        try {
            BufferedReader br = Files.newBufferedReader(file.toPath());
            String line = br.readLine();
            StringBuilder sb = new StringBuilder();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject getServerInfo(String guildID) {
        JSONObject File = new JSONObject(read());
        JSONArray servers = File.getJSONArray("servers");
        for (int i = 0; i < servers.length(); i++) {
            if (Settings_bot.getInstance().base.get_ServerID().equals(servers.getJSONObject(i).getString("server_ID"))) {
                return servers.getJSONObject(i);
            }
        }
        return null;
    }

    public JSONObject getGitinfo(String guildID, String Discord_name){
        JSONObject serverinfo = getServerInfo(guildID);
        JSONArray github = serverinfo.getJSONArray("server_github");
        for (int i = 0; i < github.length(); i++) {
            if(github.getJSONObject(i).get("discord").equals(Discord_name)){
                return github.getJSONObject(i);
            }
        }
        return null;
    }

    public JSONArray getServerSettings(String guildID){
        JSONObject serverinfo = getServerInfo(guildID);
        JSONArray settings = serverinfo.getJSONArray("server_Settings");
        return settings;
    }
    public JSONObject getSettingsInfo(String guildID){
        JSONObject serverinfo = getServerInfo(guildID);
        JSONObject settings_info = serverinfo.getJSONObject("Settings_Info");
        return settings_info;
    }



}
