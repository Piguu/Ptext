package minecraft.ponints.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import minecraft.ponints.Menum.ConfigType;
import minecraft.ponints.config.AllConfig;
import minecraft.ponints.config.GiftConfig;
import minecraft.ponints.config.PonintConfig;
import minecraft.ponints.entity.Gift;
import minecraft.ponints.giftView.GiftInventory;
import minecraft.ponints.manager.ConfigManager;
import minecraft.ponints.manager.GiftInventoryManager;
import minecraft.ponints.untils.Untiles;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointCommand extends AllCommand {

    public PointCommand(String command) {
        super(command,ConfigManager.getConfig(ConfigType.PONINTCONFIG));
    }

    @Override
    protected void gift(CommandSender sender, String command_name, String label, String[] args) {

        String s = PlaceholderAPI.setPlaceholders((Player) sender, "点券%ptext_point%");
        sender.sendMessage(s);

        if (!(sender instanceof Player)){
            this.sendMessage(sender,"该命令只能玩家调用！");
            return;
        }

        if (args.length == 3){
            String operation = args[1];
          if(operation.equals("give")){
                  Player player = (Player) sender;
                  String gift_id = args[2];
                  for (Gift gift : ((GiftConfig) ConfigManager.getConfig(ConfigType.GIFTCONFIG)).getGift_list()) {
                      // 如果此礼包id与gift_id 相同
                      if (gift_id.equals(gift.getId())){
                          if (gift.hasRevice_gift(player,gift_id)){
                              this.sendMessage(sender,"已经领取过该礼包了");
                              return;
                          }
                          int player_ponint = ((PonintConfig)ConfigManager.getConfig(ConfigType.PONINTCONFIG)).getPonint(player.getName());
                          if (player_ponint < gift.getPonits()){

                              this.sendMessage(sender,"当前累充点券没达到领取条件！");
                              return;
                          }

                          gift.Revice_gift(player,true);
                          return;
                      }
                  }
                  sender.sendMessage(Untiles.getMessage("&4未找到该礼包！"));
          }else{
              sender.sendMessage(Untiles.getMessage("%4未找到该玩家！"));
          }
        }else{
            this.sendMessage(sender,"/"+command_name+" gift give 礼包编码 [赋予玩家礼包]");
        }

    }

    @Override
    protected void giftlog(CommandSender sender, String command_name, String label, String[] args) {

    }

    @Override
    protected void show(CommandSender sender, String command_name, String label, String[] args) {
        if (args.length == 2){
            String player_name = args[1];
            int ponint = ((PonintConfig)ConfigManager.getConfig(ConfigType.PONINTCONFIG)).getPonint(player_name);
            if (ponint == 0){
                sender.sendMessage("查询值为 0 （可能未找到该玩家）!");
            }else{
                this.sendMessage(sender,"该玩家累充点券为：&e"+ponint);
            }
        }else{
            this.sendMessage(sender,"/"+command_name+" show 玩家");
        }
    }

    @Override
    protected void add(CommandSender sender, String command_name, String label, String[] args) {
        PonintConfig ponintConfig = ((PonintConfig) ConfigManager.getConfig(ConfigType.PONINTCONFIG));
        if (args.length == 3){
            String player_name = args[1];
            try {
                int ponint = Integer.valueOf(args[2]);
                int old_ponint = ponintConfig.getPonint(player_name);
                int new_ponint = old_ponint + ponint;
                ponintConfig.setPonint(player_name,new_ponint);
                this.sendMessage(sender,"添加成功，此次为 &e"+player_name+" &f添加了 &4"+ponint+" &f累充点券");
                this.ServersendToPlayerMessage(sender,player_name,"您当前点券变化,增添了 &4"+ponint+" &f累充点券");

            }catch (Exception e){
                this.sendErrorMessage(sender);
            }
        }else{
            this.sendMessage(sender,"/"+command_name+" add 玩家 点券数量");
        }
    }

    @Override
    protected void update(CommandSender sender, String command_name, String label, String[] args) {
        PonintConfig ponintConfig = ((PonintConfig) ConfigManager.getConfig(ConfigType.PONINTCONFIG));
        if (args.length == 3){
            String player_name = args[1];
            try {
                int  ponint = Integer.valueOf(args[2]);
                ponintConfig.setPonint(player_name,ponint);
                this.sendMessage(sender,"更新成功，此次修改 &e"+player_name+" &f的点券为 &4"+ponint);
                this.ServersendToPlayerMessage(sender,player_name,"点券变化,您当前累充点券为 &4"+ponint);
            }catch (Exception e){
                this.sendMessage(sender,"/"+command_name+" update 玩家 &4点券数量");
            }
        }else{
            this.sendMessage(sender,"/"+command_name+" update 玩家 点券数量");
        }
    }

    @Override
    protected void reload(CommandSender sender, String command_name, String label, String[] args) {
        this.reload(ConfigManager.getConfig(ConfigType.PONINTCONFIG));
        this.reload(ConfigManager.getConfig(ConfigType.GIFTCONFIG));
        this.reload(ConfigManager.getConfig(ConfigType.GIFTLOGCONFIG));
        this.sendMessage(sender,"刷新成功！");
    }

    @Override
    protected void open(CommandSender sender, String command_name, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (Player)sender;
            GiftInventory giftInventory = new GiftInventory(sender.getServer(), player, 54);
            GiftInventoryManager.MANAGER.addInventory(player,giftInventory);
            giftInventory.open();
        }else{
            this.sendMessage(sender,"该指令必须为玩家使用！");
        }
    }

    @Override
    protected void sendErrorMessage(CommandSender sender) {
        this.sendMessage(sender,"&7/"+command_name+" gift give 礼包id [赋予玩家指定id的礼包]");
        this.sendMessage(sender,"&7/"+command_name+" add 玩家 点券数量 [向指定玩家添加点券]");
        this.sendMessage(sender,"&7/"+command_name+" update 玩家 点券数量 [修改指定玩家的点券为指定数量]");
        this.sendMessage(sender,"&7/"+command_name+" show 玩家 [查看指定玩家的点券数量]");
        this.sendMessage(sender,"&7/"+command_name+" reload [刷新配置文件]");
        this.sendMessage(sender,"&7/"+command_name+" open [打开点券背包]");
    }

    private void ServersendToPlayerMessage(CommandSender sender,String player_name,String message){
        Player player = Bukkit.getServer().getPlayer(player_name);
        if (player!=null && !sender.getName().equals(player_name)){
            this.sendMessage(player,message);
        }
    }

    private void sendMessage(CommandSender sender,String message){
        sender.sendMessage(Untiles.getMessage(message));
    }

    @Override
    public void reload(AllConfig config) {
        config.FlushConfig();
    }

}
