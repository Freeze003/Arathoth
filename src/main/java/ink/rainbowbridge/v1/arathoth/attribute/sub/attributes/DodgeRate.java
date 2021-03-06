package ink.rainbowbridge.v1.arathoth.attribute.sub.attributes;

import ink.rainbowbridge.v1.arathoth.ArathothI;
import ink.rainbowbridge.v1.arathoth.attribute.abstracts.ArathothAttribute;
import ink.rainbowbridge.v1.arathoth.attribute.data.ArathothStatusData;
import ink.rainbowbridge.v1.arathoth.attribute.enums.StatusType;
import ink.rainbowbridge.v1.arathoth.utils.NmsUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @Author 寒雨
 * @Since 2021/1/26 10:49
 */
public class DodgeRate extends ArathothAttribute {
    @Override
    public FileConfiguration setDefaultConfig(FileConfiguration config) {
        config.set(getName()+".Settings.subTitle_Victim","&3闪避");
        config.set(getName()+".Settings.subTitle_Attacker","&7遭闪避");
        return config;
    }

    @Override
    public void onRegistering() {

    }

    @Override
    public void onExecute(Event event, LivingEntity executor, ArathothStatusData data) {
        if(event instanceof EntityDamageByEntityEvent){
            //JAVA变量声明真的好几把麻烦
            EntityDamageByEntityEvent e = (EntityDamageByEntityEvent)event;
            Double chance = data.solveData();
            if (!(e.getDamager() instanceof LivingEntity)){
                if (ArathothI.getAPI().Chance(chance/100)){
                    try{
                        NmsUtils.sendTitle((Player)e.getEntity()," ",10,10,10,getConfig().getString(getName()+".Settings.subTitle_Victim"),10,30,10);
                    }catch (Exception e1){}
                    e.setCancelled(true);
                }
                return;
            }
            Double hit = ArathothI.getAPI().getAttrInstance("HitRate").ParseValue((LivingEntity)e.getDamager()).solveData();
            if(ArathothI.getAPI().Chance((chance-hit)/100)){
                try{
                    NmsUtils.sendTitle((Player)e.getDamager()," ",10,10,10,getConfig().getString(getName()+".Settings.subTitle_Attacker"),10,30,10);
                }catch (Exception e1){}
                try{
                    NmsUtils.sendTitle((Player)e.getEntity()," ",10,10,10,getConfig().getString(getName()+".Settings.subTitle_Victim"),10,30,10);
                }catch (Exception e1){}
                e.setCancelled(true);
            }
        }
    }

    @Override
    public StatusType getType() {
        return StatusType.DEFENSE;
    }

    @Override
    public String getDescription() {
        return "闪避概率，提升数值效果";
    }
}
