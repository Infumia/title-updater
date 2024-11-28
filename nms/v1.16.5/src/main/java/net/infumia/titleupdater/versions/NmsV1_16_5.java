package net.infumia.titleupdater.versions;

import com.google.gson.JsonElement;
import net.infumia.titleupdater.Nms;
import net.minecraft.server.v1_16_R3.ChatMessage;
import net.minecraft.server.v1_16_R3.Container;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public final class NmsV1_16_5 implements Nms {

    public static final Nms INSTANCE = new NmsV1_16_5();

    @Override
    public void updateTitle(final Player player, final Object title) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Container activeContainer = entityPlayer.activeContainer;
        final InventoryView view = activeContainer.getBukkitView();
        final Inventory topInventory = view.getTopInventory();
        final InventoryType type = topInventory.getType();
        if (
            type == InventoryType.CRAFTING ||
            type == InventoryType.PLAYER ||
            type == InventoryType.CREATIVE
        ) {
            return;
        }
        final int windowId = activeContainer.windowId;
        final PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(
            windowId,
            activeContainer.getType(),
            this.toComponent(title)
        );
        entityPlayer.playerConnection.sendPacket(packet);
        player.updateInventory();
    }

    private IChatBaseComponent toComponent(final Object text) {
        if (!(text instanceof String) && !(text instanceof JsonElement)) {
            throw new IllegalArgumentException("Text must be String or JsonElement!");
        }
        final IChatBaseComponent component;
        if (text instanceof String) {
            component = new ChatMessage((String) text);
        } else {
            component = IChatBaseComponent.ChatSerializer.a((JsonElement) text);
        }
        return component;
    }
}
