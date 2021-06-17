package minecraft.ponints.entity;

import minecraft.ponints.Menum.ConfigType;
import minecraft.ponints.config.AllConfig;
import minecraft.ponints.config.GiftConfig;
import minecraft.ponints.config.GiftLogConfig;
import minecraft.ponints.manager.ConfigManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Gift {

    private String id;

    private int ponits;

    private String context;

    private List<String> cmds;

    public Gift(){

    }

    public Gift(String id,int ponits, String gift_name, String context, List<String> cmds) {
        this.id = id;
        this.ponits = ponits;
        this.context = context;
        this.cmds = cmds;
    }

    public int getPonits() {
        return ponits;
    }

    public String getContext() {
        return context;
    }

    public List<String> getCmds() {
        return cmds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPonits(int ponits) {
        this.ponits = ponits;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setCmds(List<String> cmds) {
        this.cmds = cmds;
    }

    /**
     * 替换%player%
     * @param text
     * @return
     */
    public String getPlayer_Text(String text,String player_name){
        return text.replaceAll("%player%",player_name);
    }

    /**
     * 玩家领取礼包后
     */
    public void Revice_gift(Player player,boolean is_log){
        String player_name = player.getName();
        for (String cmd : this.getCmds()) {
            player.getServer().dispatchCommand(player.getServer().getConsoleSender(), this.getPlayer_Text(cmd,player_name));
        }

        if (is_log){
            GiftLogConfig logConfig = (GiftLogConfig) ConfigManager.getConfig(ConfigType.GIFTLOGCONFIG);
            List<String> gift_log = logConfig.getPlayer_gift(player_name);
            if (gift_log==null){
                gift_log = new ArrayList<>();
            }
            gift_log.add(this.getId());
            logConfig.setPlayer_gift(player_name,gift_log);
        }

        System.out.println(player_name+"玩家领取了"+id+"礼包");
    }

    /**
     * 玩家是否领取了礼包
     * @param player
     * @return
     */
    public boolean hasRevice_gift(Player player,String id){
        boolean flag = false;
        GiftLogConfig config = (GiftLogConfig) ConfigManager.getConfig(ConfigType.GIFTLOGCONFIG);
        List<String> gift_log = config.getPlayer_gift(player.getName());
        if (gift_log!=null){
            for (String gift_id : gift_log) {
                if (gift_id.equals(id)){
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }
}
