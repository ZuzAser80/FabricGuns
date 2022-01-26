package net.fabricmc.example.mixin;

import net.fabricmc.example.crap.duck;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerEntityRenderer.class)
public abstract class ModelPartGetterMixin implements duck {

    @Override
    public void methodAccess(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
    }
    @Shadow public abstract void renderRightArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player);

    @Override
    public void getHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player)
    {
        this.renderRightArm(matrices, vertexConsumers, light, player);
    }
}
