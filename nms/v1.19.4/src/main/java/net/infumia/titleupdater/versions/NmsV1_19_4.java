package net.infumia.titleupdater.versions;

import com.google.gson.JsonElement;
import net.infumia.titleupdater.Nms;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public final class NmsV1_19_4 implements Nms {

    public static final Nms INSTANCE = new NmsV1_19_4();

    @Override
    public void updateTitle(final Player player, final Object title) {
        final Component newTitle = this.toComponent(title);
        final var serverPlayer = ((CraftPlayer) player).getHandle();
        final var containerMenu = serverPlayer.containerMenu;
        final InventoryView view = containerMenu.getBukkitView();
        final Inventory topInventory = view.getTopInventory();
        final InventoryType type = topInventory.getType();
        if (
            type == InventoryType.CRAFTING ||
            type == InventoryType.PLAYER ||
            type == InventoryType.CREATIVE
        ) {
            return;
        }
        final var containerId = containerMenu.containerId;
        final ClientboundOpenScreenPacket packet = new ClientboundOpenScreenPacket(
            containerId,
            containerMenu.getType(),
            newTitle
        );
        serverPlayer.connection.send(packet);
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
            component = Component.Serializer.fromJson((JsonElement) text);
        }
        return component;
    }
}
