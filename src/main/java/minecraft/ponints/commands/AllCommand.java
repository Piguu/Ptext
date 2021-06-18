package minecraft.ponints.commands;

import minecraft.ponints.config.AllConfig;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public abstract class AllCommand implements CommandExecutor {

    protected String command_name;

    public AllCommand(String command,AllConfig config){
        this.command_name = command;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean flag = false;
        if (sender.isOp()){
            this.execute_op(sender, command, label, args);
        }else{
            this.execute_player(sender, command, label, args);
        }
        return flag;
    }

    private void execute_op(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player || sender instanceof ConsoleCommandSender || sender instanceof RemoteConsoleCommandSender){
            if (command.getName().equals(command_name)){

                if (args.length>0){
                    String operation = args[0];
                    if (operation.equals("gift")){
                        this.gift(sender,command.getName(),label,args);
                    }else if(operation.equals("giftlog")){
                        this.giftlog(sender,command.getName(),label,args);
                    }else if(operation.equals("add")){
                        this.add(sender,command.getName(),label,args);
                    }else if(operation.equals("show")){
                        this.show(sender,command.getName(),label,args);
                    }else if(operation.equals("update")){
                        this.update(sender,command.getName(),label,args);
                    }else if(operation.equals("reload")){
                        this.reload(sender,command.getName(),label,args);
                    }else if (operation.equals("open")){
                        this.open(sender,command.getName(),label,args);
                    }else{
                        this.sendErrorMessage(sender);
                    }
                }else{
                    this.sendErrorMessage(sender);
                }
            }
        }
    }

    private void execute_player(CommandSender sender, Command command, String label, String[] args){
        if (sender instanceof Player || sender instanceof ConsoleCommandSender){
            if (command.getName().equals(command_name)){
                if (args.length>0){
                    String operation = args[0];
                    if (operation.equals("gift")){
                        this.gift(sender,command.getName(),label,args);
                    }else if (operation.equals("open")){
                        this.open(sender,command.getName(),label,args);
                    }else{
                        sender.sendMessage("/"+command_name+" open [打开点券背包]");
                        sender.sendMessage("/"+command_name+" gift give 礼包id [赋予玩家指定id的礼包]");
                    }
                }else{
                    sender.sendMessage("/"+command_name+" open [打开点券背包]");
                    sender.sendMessage("/"+command_name+" gift give 礼包id [赋予玩家指定id的礼包]");
                }
            }
        }
    }

    protected abstract void gift(CommandSender sender, String command_name, String label, String[] args);
    protected abstract void giftlog(CommandSender sender, String command_name, String label, String[] args);
    protected abstract void add(CommandSender sender, String command_name, String label, String[] args);
    protected abstract void show(CommandSender sender, String command_name, String label, String[] args);
    protected abstract void update(CommandSender sender, String command_name, String label, String[] args);
    protected abstract void reload(CommandSender sender, String command_name, String label, String[] args);
    protected abstract void open(CommandSender sender, String command_name, String label, String[] args);

    protected abstract void sendErrorMessage(CommandSender sender);

    public abstract void reload(AllConfig config);
}
