package net.fabricmc.example.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.events.GunUseCallback;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin <T extends LivingEntity> extends AnimalModel<T> implements ModelWithArms, ModelWithHead {

    @Shadow public ModelPart rightArm;
    @Shadow public ModelPart leftArm;
    @Shadow public ModelPart head;

    @Shadow public BipedEntityModel.ArmPose leftArmPose;
    public boolean isSprinting = false;
    @Shadow public BipedEntityModel.ArmPose rightArmPose;

    @Inject(method = "setAngles*", at = @At(value = "TAIL"))
    public void handRotation(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci)
    {
        if(!livingEntity.isSwimming() && livingEntity.getOffHandStack().isEmpty() && !livingEntity.hasVehicle())
        {
            isSprinting = livingEntity.isSprinting();
            if(livingEntity.getMainHandStack().isOf(GunUseCallback.USP))
            {
                this.leftArm.yaw = 0.956F;
                this.rightArm.yaw = -0.467F;
                this.rightArm.pitch = 300;
                this.leftArm.pitch = 300;
                if(isSprinting)
                {
                    this.leftArm.yaw += 0.2F;
                    this.rightArm.yaw -= 0.01F;
                    this.rightArm.pitch -= 100;
                    this.leftArm.pitch -= 100;
                }
                if(livingEntity.handSwinging && livingEntity.handSwingProgress == 0)
                {
                    System.out.println("leftClick detected");
                }
            }
        }
    }
}
