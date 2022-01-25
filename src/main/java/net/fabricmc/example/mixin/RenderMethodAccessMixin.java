package net.fabricmc.example.mixin;

import net.fabricmc.example.crap.MatrixAccess;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class RenderMethodAccessMixin implements MatrixAccess  {

    ModelPart playerHand;

    @Override
    public void methodAccess(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {

    }
    @Inject(at = @At("HEAD"), method = {"renderArm"})
    public void GetMethod(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci)
    {
        playerHand = arm;
    }
    @Override
    public ModelPart getPlayerHand()
    {
        return playerHand;
    }
}
