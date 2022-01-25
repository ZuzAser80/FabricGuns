package net.fabricmc.example.crap;


import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public interface MatrixAccess {
    void methodAccess(MatrixStack matrices, VertexConsumer vertices, int light, int overlay);
    ModelPart getPlayerHand();
}
