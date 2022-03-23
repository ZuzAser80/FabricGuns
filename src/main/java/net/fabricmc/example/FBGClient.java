package net.fabricmc.example;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.entity.basic.BasicRegistry;

@Environment(EnvType.CLIENT)
public class FBGClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("OnClientInitialize");
        BasicRegistry.clientRegistry();
    }
}
