package minecraft.ponints.untils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import minecraft.ponints.Menum.ConfigType;
import minecraft.ponints.config.PonintConfig;
import minecraft.ponints.manager.ConfigManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class PointExponsion extends PlaceholderExpansion {

    private Plugin plugin;

    public PointExponsion(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return plugin.getName();
    }

    @Override
    public String getAuthor() {
        return "pig_trotters";
    }

    @Override
    public  String getVersion() {
        return "1.0";
    }


    @Override
    public String onPlaceholderRequest(Player player, String params) {

        if (player == null){
            return "";
        }

        if (params.equals("point")){
            return String.valueOf(((PonintConfig) ConfigManager.getConfig(ConfigType.PONINTCONFIG)).getPonint(player.getName()));
        }

        return super.onPlaceholderRequest(player, params);
    }
}
