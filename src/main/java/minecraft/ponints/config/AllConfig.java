package minecraft.ponints.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class AllConfig {

    protected FileConfiguration config;

    private String path;

    public AllConfig(String path) {
        this.path = path;
        File file = this.readConfigFile(path);
        config = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 用来处理文件不存在的问题
     * @param FilePath
     * @return
     */
    private File readConfigFile(String FilePath){
        File confrgFile = new File(FilePath);
        try {
            if (!confrgFile.exists()){
                File fPath = new File(confrgFile.getParent());

                // 创建文件的文件夹（不包括创建文件）
                fPath.mkdirs();
                // 创建文件
                confrgFile.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return confrgFile;
    }

    /**
     * 刷新配置文件
     */
    public void FlushConfig() {
        config = YamlConfiguration.loadConfiguration(this.readConfigFile(path));
    }


    public void saveData(){
        try {
            config.save(path);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
