package minecraft.ponints.config;

import minecraft.ponints.entity.Gift;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 礼包配置文件
 */
public class GiftConfig extends AllConfig{

    private static final String ponint = "point";

    private static final String context = "context";

    private static final String cmds = "cmds";

    private static final String path = "plugins/Ptext/gift.yml";

    public GiftConfig(){
        super(path);

        this.loadData();
    }

    private void loadData(){

        Map<String, Object> values = config.getValues(false);
        if (values!=null && values.size()!=0){
            return;
        }

        String gift1 = "礼包1.";
        config.set(gift1+ponint,6);
        config.set(gift1+context,"首冲礼包内容");
        List<String> cms_list = new ArrayList<>();
        cms_list.add("give %player% obsidian 1");
        cms_list.add("give %player% redstone 1");
        cms_list.add("say %player% 领取成功");
        config.set(gift1+cmds,cms_list);

        String gift2 = "礼包2.";
        config.set(gift2+ponint,18);
        config.set(gift2+context,"18的礼包内容");
        List<String> cms_list2 = new ArrayList<>();
        cms_list2.add("give %player% obsidian 1");
        cms_list2.add("give %player% redstone 1");
        cms_list2.add("say %player% 领取成功");
        config.set(gift2+cmds,cms_list2);

        super.saveData();
    }

    public List<Gift> getGift_list(){
        List<Gift> gift_list = new ArrayList<>();

        Map<String, Object> values_map = config.getValues(false);
        for (String key : values_map.keySet()) {
            if (config.isConfigurationSection(key)) {
                Gift gift = new Gift();
                gift.setId(key);
                ConfigurationSection section = config.getConfigurationSection(key);

                gift.setPonits(section.getInt(ponint));
                gift.setContext(section.getString(context));
                gift.setCmds((List<String>) section.getList(cmds));

                gift_list.add(gift);
            }
        }

        return gift_list;
    }

    public Gift getGift(String s_gift_id){
       Gift gift = new Gift();
        Map<String, Object> values_map = config.getValues(false);
        for (String key : values_map.keySet()) {
            if (config.isConfigurationSection(key)) {
                ConfigurationSection section = config.getConfigurationSection(key);
                if (key.equals(s_gift_id)) {
                    gift.setId(key);
                    gift.setPonits(section.getInt(ponint));
                    gift.setContext(section.getString(context));
                    gift.setCmds((List<String>) section.getList(cmds));
                    break;
                }
            }
        }
        return gift;
    }

}
