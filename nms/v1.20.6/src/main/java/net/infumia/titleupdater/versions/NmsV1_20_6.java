package net.infumia.titleupdater.versions;

import com.google.gson.JsonElement;
import java.util.stream.Stream;
import net.infumia.titleupdater.Nms;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.craftbukkit.CraftRegistry;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.inventory.CraftContainer;
import org.bukkit.entity.Player;

public final class NmsV1_20_6 implements Nms {

    public static final Nms INSTANCE = new NmsV1_20_6();

    @Override
    public void updateTitle(final Player player, final Object title) {
        final var serverPlayer = ((CraftPlayer) player).getHandle();
        final var containerId = serverPlayer.containerMenu.containerId;
        final MenuType<?> windowType = CraftContainer.getNotchInventoryType(
            player.getOpenInventory().getTopInventory()
        );
        serverPlayer.connection.send(
            new ClientboundOpenScreenPacket(containerId, windowType, this.toComponent(title))
        );
        player.updateInventory();
    }

    private Component toComponent(final Object text) {
        if (!(text instanceof String) && !(text instanceof JsonElement)) {
            throw new IllegalArgumentException("Text must be String or JsonElement!");
        }
        final Component component;
        if (text instanceof String) {
            component = Component.literal((String) text);
        } else {
            component = Component.Serializer.fromJson(
                (JsonElement) text,
                CraftRegistry.getMinecraftRegistry()
            );
        }
        return component;
    }
}
