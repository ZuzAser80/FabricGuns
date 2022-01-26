package net.fabricmc.example.crap;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;

public interface duck {
    void methodAccess(MatrixStack matrices, VertexConsumer vertices, int light, int overlay);
    void getHand(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player);
}
