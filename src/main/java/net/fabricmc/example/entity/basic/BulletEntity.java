package net.fabricmc.example.entity.basic;

import net.fabricmc.example.crap.GunItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class BulletEntity extends PersistentProjectileEntity {

    protected Item bullet;
    protected GunItem gunItem;

    public BulletEntity(World world, GunItem gun) {
        super(BasicRegistry.PistolBullet, world);
        gunItem = gun;
        bullet = gun.getBulletItem();
    }
    protected void onCollision(HitResult hitResult) {
        if(hitResult.getType() == HitResult.Type.BLOCK)
        {
            this.discard();
        } else if(hitResult.getType() == HitResult.Type.ENTITY)
        {
            EntityHitResult entityHitResult = (EntityHitResult)hitResult;
            if(entityHitResult.getEntity() instanceof LivingEntity)
            {
                Entity entity = entityHitResult.getEntity();
                System.out.println(gunItem.damage);
                entity.damage(DamageSource.GENERIC, gunItem.damage);
                this.discard();
            }
        }
    }
    @Override
    protected ItemStack asItemStack() {
        return bullet.getDefaultStack();
    }
}
