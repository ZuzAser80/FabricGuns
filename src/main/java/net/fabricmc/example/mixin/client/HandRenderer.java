package net.fabricmc.example.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.example.FBGServer;
import net.fabricmc.example.crap.GunItem;
import net.fabricmc.example.entity.basic.BasicRegistry;
import net.fabricmc.example.entity.basic.BulletEntity;
import net.fabricmc.example.events.GunUseCallback;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.stat.Stats;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(HeldItemRenderer.class)
public abstract class HandRenderer {
    @Final @Shadow private EntityRenderDispatcher renderManager;

    @Shadow public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light){}

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"), cancellable = true)
    public void renderHands(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci)
    {
        //organise the code: do the switch thing, to the instanceof thing, make tags for different weapons,
        //try to work on animations and preventing some stuff that vanilla does like the hand wiggling when hitting.
        //Ya know, it better to do it later, but just... don't forget about it, alr?
        //Do the off-hand check to avoid having player with 4 hands, this is important
        if(item.isOf(GunUseCallback.USP))
        {
            renderPistolHands(matrices, vertexConsumers, light, player, item, (GunItem)item.getItem());
            ci.cancel();
        }
    }
    public boolean lastAimTime = false;
    public boolean lastClickTime = false;
    public boolean isAiming = false;
    public int totalAmmoCount = 0;
    public int magMaxCount = 1;
    public void renderPistolHands(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ItemStack item, GunItem gun)
    {
        pistolHandFeatures(matrices, player, gun);
        matrices.scale(2.5F, 2.5F, 2.5F);
        matrices.translate(0.25, -0.75, -1);
        this.renderItem(player, item, ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND, false, matrices, vertexConsumers, light);
        matrices.push();
        matrices.translate(0.85, -0.5, 0.45);
        matrices.multiply(new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(-95)));
        matrices.multiply(new Quaternion(Vec3f.POSITIVE_Z.getDegreesQuaternion(-5)));
        //compensate the 3rd person rotation from the BipedEntityModelMixin
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(0.467F));
        matrices.scale(2F, 2F, 2F);
        drawHand(matrices, vertexConsumers, light, Arm.RIGHT, player);
        matrices.push();
        matrices.translate(-0.850, 0.1, -0.56);
        matrices.multiply(new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(-40)));
        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(-1.4F));
        //matrices.multiply(new Quaternion(Vec3f.POSITIVE_Y.getDegreesQuaternion(40)));
        matrices.multiply(new Quaternion(Vec3f.POSITIVE_Z.getDegreesQuaternion(-50)));
        matrices.multiply(new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(-20)));
        //compensate the 3rd person rotation from the BipedEntityModelMixin
        drawHand(matrices, vertexConsumers, light, Arm.LEFT, player);
        lastAimTime = MinecraftClient.getInstance().mouse.wasRightButtonClicked();
        lastClickTime = MinecraftClient.getInstance().mouse.wasLeftButtonClicked();
    }
    public void pistolHandFeatures(MatrixStack matrices, AbstractClientPlayerEntity player, GunItem item)
    {
        if(MinecraftClient.getInstance().mouse.wasLeftButtonClicked() && !lastClickTime)
        {
            takeAwayAmmo(item.getBulletItem().getDefaultStack(), player, matrices);
        }
        if(MinecraftClient.getInstance().mouse.wasRightButtonClicked() && !lastAimTime)
        {
            toggleAiming(!isAiming, player);
            isAiming = !isAiming;
        }
        if(isAiming)
        {
            matrices.translate(-0.875, 0.7, 0.4);
        }
        if(player.isSprinting() && !isAiming) {
            matrices.multiply(new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(-10f)));
            matrices.translate(0, 0.25, 0);
            matrices.multiply(new Quaternion(Vec3f.POSITIVE_Y.getDegreesQuaternion(15)));
        }
        if(player.isSneaking() && !isAiming) {
            matrices.multiply(new Quaternion(Vec3f.POSITIVE_X.getDegreesQuaternion(15)));
            matrices.translate(0, 0, 0.5);
        }
    }
    public void toggleAiming(boolean isAiming, AbstractClientPlayerEntity player)
    {
        if(isAiming)
        {
            MinecraftClient.getInstance().options.fov /= 2;
            player.speed /= 2;
        } else
        {
            MinecraftClient.getInstance().options.fov *= 2;
            player.speed *= 2;
        }
    }
    public void takeAwayAmmo(ItemStack ammo, AbstractClientPlayerEntity player, MatrixStack matrices)
    {
        if(player.getInventory().contains(ammo) || player.getAbilities().creativeMode)
        {
            totalAmmoCount = player.getInventory().count(ammo.getItem());
            if (!player.getAbilities().creativeMode && totalAmmoCount > 0) {
                ItemStack ammoStack = player.getInventory().getStack(player.getInventory().getSlotWithStack(ammo));
                ammoStack.decrement(1);
                ammo.decrement(1);
                totalAmmoCount--;
            }
            //simulate a knockback from shooting a gun
            //TODO: RENDERING RANDOMLY ROTATED MUZZLE FLASH TEXTURE
            RenderSystem.setShaderTexture(0, new Identifier("minecraft:blocks/stone"));

            matrices.translate(0, 0, 0.25);
        } else
        {
            matrices.translate(0, -0.25, 0);
        }
    }
    public void drawHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Arm arm, AbstractClientPlayerEntity abstractClientPlayerEntity)
    {
        RenderSystem.setShaderTexture(0, abstractClientPlayerEntity.getSkinTexture());
        PlayerEntityRenderer playerEntityRenderer = (PlayerEntityRenderer)this.renderManager.getRenderer(abstractClientPlayerEntity);
        if(arm == Arm.RIGHT) {
            playerEntityRenderer.renderRightArm(matrices, vertexConsumers, light, abstractClientPlayerEntity);
        } else {
            playerEntityRenderer.renderLeftArm(matrices, vertexConsumers, light, abstractClientPlayerEntity);
        }
    }
}
