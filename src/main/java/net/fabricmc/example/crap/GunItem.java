package net.fabricmc.example.crap;

import net.fabricmc.example.entity.basic.BulletEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GunItem extends Item {
    public int magazineCap;
    Item bullet;
    public int damage;
    String name;

    public GunItem(Settings settings, int magCap, Item bulletItem, int damage, String name) {
        super(settings);
        magazineCap = magCap;
        bullet = bulletItem;
        this.damage = damage;
        this.name = name;
    }
    PlayerEntity player;
    public Item getBulletItem()
    {
        return bullet;
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected)
    {
        player = (PlayerEntity)entity;
        if(selected && MinecraftClient.getInstance().mouse.wasLeftButtonClicked())
        {
            if(!world.isClient && player.isHolding(this))
            {
                BulletEntity bulletEntity = new BulletEntity(world, this);
                bulletEntity.setPos(player.getX(), player.getY() + 0.5, player.getZ());
                bulletEntity.setVelocity(player, player.getPitch(), player.getYaw(), 0.0F, 3.0F, 1.0F);
                world.spawnEntity(bulletEntity);
            }
        }
    }
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context)
    {
        tooltip.add(new TranslatableText("item.fbg.usp.tooltip", damage / 2).formatted(Formatting.AQUA));
    }
}
