package minecraft.ponints.config;

import java.io.File;
import java.util.Map;

/**
 * 点券累充配置文件
 */
public class PonintConfig extends AllConfig{

    private static final String path = "plugins/Ponints/ponint.yml";

    public PonintConfig() {
        super(path);
    }

    public int getPonint(String player){
        return config.getInt(player);
    }

    public void setPonint(String player,int ponint){
        config.set(player,ponint);
        super.saveData();
    }

    public Map<String,Object> getPlayer_Ponit_Map(){
        Map<String, Object> values = config.getValues(true);
        return values;
    }
}
