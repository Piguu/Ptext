package minecraft.ponints.giftView;

import minecraft.ponints.Menum.ConfigType;
import minecraft.ponints.config.GiftConfig;
import minecraft.ponints.config.GiftLogConfig;
import minecraft.ponints.config.PonintConfig;
import minecraft.ponints.entity.Gift;
import minecraft.ponints.manager.ConfigManager;
import minecraft.ponints.untils.Untiles;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GiftInventory {

    private Map<Integer,ItemStack> gift_index_map;

    public static final String inventory_name = "礼包界面";

    private Inventory inventory;

    private Player player;

    private int size;

    public GiftInventory(Server server, Player player,int size) {
        gift_index_map = new HashMap<>();
        this.player = player;
        this.size = size;
        inventory = this.GenterFillInventory(server,inventory_name,player,size);

        this.init();
    }

    /**
     * 初始化格子
     */
    private void init() {


        List<Gift> gift_list = ((GiftConfig) ConfigManager.getConfig(ConfigType.GIFTCONFIG)).getGift_list();
        ItemStack air = new ItemStack(Material.AIR);
        int rowMessage = size / 9;
        for (int i = 1; i < rowMessage-1; i++) {
            int StartIndex = i * 9 + 1;
            int EndIndex = StartIndex + 7;
            for (int j = StartIndex; j < EndIndex; j++) {
                if (gift_list.size()!=0){
                    Gift gift = gift_list.get(0);
                    String gift_name = gift.getId();
                    int ponits = gift.getPonits();
                    String context = gift.getContext();

                    ItemStack gift_item = new ItemStack(Material.NETHER_STAR );
                    ItemMeta gift_meta = gift_item.getItemMeta();
                    gift_meta.setDisplayName(Untiles.getMessage(gift_name));

                    List<String> lores = new ArrayList<>();
                    lores.add(Untiles.getMessage("&f礼包内容："+context));
                    lores.add(Untiles.getMessage("&f需要累积充值 &4"+ponits+" &f点券才可领取"));
                    int player_ponints = ((PonintConfig)ConfigManager.getConfig(ConfigType.PONINTCONFIG)).getPonint(player.getName());
                    ponits = player_ponints>=ponits ? 0 : ponits-player_ponints;
                    lores.add(Untiles.getMessage("&f还需充值 &4"+ponits+"&f 点券"));

                    String stats = "未领取";
                    if (gift.hasRevice_gift(player,gift.getId())){
                        stats = "已领取";
                    }else if(player_ponints>=ponits){
                        stats = "可领取";
                    }


                    lores.add(Untiles.getMessage("&f领取状态：&7"+stats));


                    gift_meta.setLore(lores);
                    gift_item.setItemMeta(gift_meta);
                    inventory.setItem(j,gift_item);

                    gift_list.remove(gift);
                    gift_index_map.put(j,gift_item);
                }else{
                    inventory.setItem(j,air);
                }

            }
        }

        PonintConfig ponintConfig = (PonintConfig)ConfigManager.getConfig(ConfigType.PONINTCONFIG);
        ItemStack player_ponint_item = new ItemStack(Material.PAINTING);
        ItemMeta itemMeta = player_ponint_item.getItemMeta();
        // 设置个人的点券
        int ponints = ponintConfig.getPonint(player.getName());
        itemMeta.setDisplayName(Untiles.getMessage("&f当前已累充: &4"+ponints+"&f 点券"));
        List<String> lores = new ArrayList<>();

        // 设置排行榜
        List<Map.Entry<String, Object>> entries = new ArrayList<>(ponintConfig.getPlayer_Ponit_Map().entrySet());
        Collections.sort(entries, (o1, o2) -> {

            Integer ponint1 = Integer.valueOf(String.valueOf(o1.getValue()));
            Integer ponint2 = Integer.valueOf(String.valueOf(o2.getValue()));
            return -(ponint1.compareTo(ponint2));
        });
        if (entries.size()>10){
            entries = entries.subList(0,9);
        }

        lores.add(Untiles.getMessage("&6累计充值点券排行榜"));
        lores.add("");
        for (Map.Entry<String, Object> entry : entries) {
            lores.add(Untiles.getMessage("&eid: &a"+entry.getKey()+"   &e充值金额: &a"+entry.getValue()+" &e点券"));
        }



        itemMeta.setLore(lores);
        player_ponint_item.setItemMeta(itemMeta);

        inventory.setItem(4,player_ponint_item);
    }


    public void open(){
        player.openInventory(inventory);
    }

    /**
     * 生成一个装填好的背包
     *
     * @param player 拥有此背包的玩家
     * @param size   指定大小
     * @return 背包
     */
    private Inventory GenterFillInventory(Server server,String inventory_name,Player player, int size) {
        Inventory inventory = server.createInventory(player, size, inventory_name);
        ItemStack fillItem = new ItemStack(Material.STAINED_GLASS_PANE);
        List<ItemStack> allItem = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            allItem.add(fillItem);
        }
        inventory.setContents(allItem.toArray(new ItemStack[allItem.size()]));
        return inventory;
    }

    public void clickRun(Player player,int index){
        if (gift_index_map.containsKey(index)){
            PonintConfig ponintConfig = ((PonintConfig) ConfigManager.getConfig(ConfigType.PONINTCONFIG));
            GiftConfig giftConfig = ((GiftConfig) ConfigManager.getConfig(ConfigType.GIFTCONFIG));
            GiftLogConfig giftLogConfig = ((GiftLogConfig) ConfigManager.getConfig(ConfigType.GIFTLOGCONFIG));

            // 获取点击的礼包
            ItemStack gift_item = gift_index_map.get(index);
            String gift_name = gift_item.getItemMeta().getDisplayName();
            Gift gift = giftConfig.getGift(gift_name);

            // 判断是否已经领取过
            List<String> player_gift_log = giftLogConfig.getPlayer_gift(player.getName());
            if (player_gift_log!=null){
                for (String id : player_gift_log) {
                    // 如果相同就代表玩家领过该礼包
                    if (id.equals(gift.getId())){
                        return;
                    }
                }
            }

            int player_ponint = ponintConfig.getPonint(player.getName());
            if (player_ponint >= gift.getPonits()){
                gift.Revice_gift(player,true);
                player.closeInventory();
            }
        }
    }
}
