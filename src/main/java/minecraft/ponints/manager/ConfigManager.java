package minecraft.ponints.manager;

import minecraft.ponints.Menum.ConfigType;
import minecraft.ponints.config.AllConfig;
import minecraft.ponints.config.GiftConfig;
import minecraft.ponints.config.GiftLogConfig;
import minecraft.ponints.config.PonintConfig;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static final Map<ConfigType, AllConfig> config_map = new HashMap<>();

    private ConfigManager(){

    }

    public static void init(){
        if (config_map.size() == 0){

            GiftConfig giftConfig = new GiftConfig();
            GiftLogConfig giftLogConfig = new GiftLogConfig();
            PonintConfig ponintConfig = new PonintConfig();

            config_map.put(ConfigType.GIFTCONFIG,giftConfig);
            config_map.put(ConfigType.GIFTLOGCONFIG,giftLogConfig);
            config_map.put(ConfigType.PONINTCONFIG,ponintConfig);

        }
    }

    public static AllConfig getConfig(ConfigType type){
        return config_map.get(type);
    }
}
