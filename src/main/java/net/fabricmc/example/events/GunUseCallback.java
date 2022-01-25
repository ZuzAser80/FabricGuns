package net.fabricmc.example.events;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class GunUseCallback {
    public static Item USP;
    public static void registry()
    {
        //План: перехватить renderrightArm в mixine и захуярить проверку на пушку, если пушка, то вывести в чат хуйню
        USP = Registry.register(Registry.ITEM, new Identifier("fbg", "usp"), new Item(new Item.Settings()));
        UseItemCallback.EVENT.register((player, world, hand) ->
        {

            /* Manual spectator check is necessary because AttackBlockCallbacks
               fire before the spectator check */
            if (!player.isSpectator() && player.getMainHandStack().isOf(USP))
            {
                    player.sendSystemMessage(new LiteralText("USP should shoot here"), Util.NIL_UUID);

            }
            return TypedActionResult.success(USP.getDefaultStack(), false);
        });
    }
}
