package net.fabricmc.example.mixin;

import com.ibm.icu.text.Normalizer2;
import net.fabricmc.example.crap.duck;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class GunRenderMixin {

    //@Shadow PlayerEntityModel model;

    @Inject(at = @At("HEAD"), method = {"renderFirstPersonItem*"})
    public void RenderGun(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci)
    {
        if(item.isOf(Items.GOLDEN_AXE))
        {

            //this.model.rightArm;
            //DO A MIXIN ON PLAYERENTITYRENDERER AND GET THE VALUE OUT OF TOP SUPER IT SHOULD WORK
            //we can't cast ClientPlayerEntity to that
            //what do we do?
            //we do a little trolling :D
            ((duck)player).methodAccess(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
            //mixin to ModelPart.class, dig to render method and use it to render, cuz i have everything pretty much
        }
    }
}
