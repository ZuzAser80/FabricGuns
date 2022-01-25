package net.fabricmc.example.mixin;

import net.fabricmc.example.crap.MatrixAccess;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ModelPart.class)
public abstract class ModelPartAccessMixin implements MatrixAccess {

    @Shadow public abstract void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha);
    @Override
    public void methodAccess(MatrixStack matrices, VertexConsumer vertices, int light, int overlay)
    {
        this.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

}
