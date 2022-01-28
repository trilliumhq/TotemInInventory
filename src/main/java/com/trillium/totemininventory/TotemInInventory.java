package com.trillium.totemininventory;

import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class TotemInInventory extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(EntityResurrectEvent e) {
        if (!e.isCancelled() && e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player p && e.getFinalDamage() >= p.getHealth() && p.getInventory().contains(new ItemStack(Material.TOTEM_OF_UNDYING)) && !(e.getCause().equals(EntityDamageEvent.DamageCause.VOID) || e.getCause().equals(EntityDamageEvent.DamageCause.CUSTOM)) ) {
                e.setDamage(0.0D);
                p.playEffect(EntityEffect.TOTEM_RESURRECT);
                p.setFoodLevel(20);
                p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, 1, 20));
                p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1));
                p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 800, 0));
        }
    }

    @Override
    public void onDisable() {
        this.getServer().getPluginManager().disablePlugin(this);
    }
}
