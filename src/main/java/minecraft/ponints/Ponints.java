package minecraft.ponints;

import minecraft.ponints.commands.PointCommand;
import minecraft.ponints.giftView.GiftInventory;
import minecraft.ponints.listener.PlayerListener;
import minecraft.ponints.manager.ConfigManager;
import minecraft.ponints.manager.GiftInventoryManager;
import minecraft.ponints.untils.PointExponsion;
import minecraft.ponints.untils.Untiles;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ponints extends JavaPlugin {

    public static final String ponint = "ptext";

    @Override
    public void onEnable() {

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI")==null) {
            System.out.println("未找到PlaceholderAPI！");
            return;
        }

        new PointExponsion(this).register();

        ConfigManager.init();
        getCommand(ponint).setExecutor(new PointCommand(ponint));
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getLogger().info("点券累充礼包插件已启动！");
    }

    @Override
    public void onDisable() {

    }
}
