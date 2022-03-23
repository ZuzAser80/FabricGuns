package net.fabricmc.example.events;

import net.fabricmc.example.crap.GunItem;
import net.fabricmc.example.entity.basic.BasicRegistry;
import net.fabricmc.example.entity.basic.BulletEntity;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.explosion.Explosion;

public class GunUseCallback {
    public static GunItem USP;
    public static void registry()
    {
        USP = Registry.register(Registry.ITEM, new Identifier("fbg", "usp"), new GunItem(new Item.Settings().maxCount(1), 7, Items.STONE, 80, "usp"));
    }
}
