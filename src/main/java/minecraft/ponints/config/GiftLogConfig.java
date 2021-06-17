package minecraft.ponints.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.List;

/**
 * 礼包日志配置文件
 */
public class GiftLogConfig extends AllConfig{

    private static final String path = "plugins/Ponints/giftlog.yml";

    public GiftLogConfig(){
        super(path);
    }

    public List<String> getPlayer_gift(String player_name){
        return (List<String>) config.getList(player_name);
    }

    public void setPlayer_gift(String player_name,List<String> list){
        config.set(player_name,list);
        super.saveData();
    }
}
