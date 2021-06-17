package minecraft.ponints.manager;

import minecraft.ponints.giftView.GiftInventory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GiftInventoryManager {

    private static final Map<Player, GiftInventory> gift_inventory_map = new HashMap<>();

    public static final GiftInventoryManager MANAGER = new GiftInventoryManager();

    private GiftInventoryManager(){
    }

    public void addInventory(Player player,GiftInventory inventory){
        gift_inventory_map.put(player,inventory);
    }

    public void removeInventory(Player player){
        gift_inventory_map.remove(player);
    }

    public GiftInventory getInventory(Player player){
        return gift_inventory_map.get(player);
    }
}
