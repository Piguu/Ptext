package minecraft.ponints.listener;

import minecraft.ponints.Menum.ConfigType;
import minecraft.ponints.config.PonintConfig;
import minecraft.ponints.giftView.GiftInventory;
import minecraft.ponints.manager.ConfigManager;
import minecraft.ponints.manager.GiftInventoryManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class PlayerListener implements Listener {

    @EventHandler
    public void PlayerClickInventory(InventoryClickEvent event) {
        Inventory click_inventory = event.getClickedInventory();
        if (click_inventory!=null && click_inventory.getTitle().equals(GiftInventory.inventory_name)){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                ItemStack click_item = event.getCurrentItem();
                if (click_item.getItemMeta()!=null && click_item.getItemMeta().getDisplayName()!=null){
                    if (event.getWhoClicked() instanceof Player){
                        Player player = (Player) event.getWhoClicked();
                        GiftInventory gift_inventory = GiftInventoryManager.MANAGER.getInventory(player);
                        int index = event.getRawSlot();
                        gift_inventory.clickRun(player,index);
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        String player_name =event.getPlayer().getName();
        PonintConfig ponintConfig = ((PonintConfig) ConfigManager.getConfig(ConfigType.PONINTCONFIG));
        int ponint = ponintConfig.getPonint(player_name);
        if (ponint == 0){
            ponintConfig.setPonint(player_name,0);
        }
    }
}










