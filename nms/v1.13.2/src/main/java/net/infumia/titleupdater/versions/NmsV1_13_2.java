package net.infumia.titleupdater.versions;

import com.google.gson.JsonElement;
import net.infumia.titleupdater.Nms;
import net.minecraft.server.v1_13_R2.ChatMessage;
import net.minecraft.server.v1_13_R2.Container;
import net.minecraft.server.v1_13_R2.EntityPlayer;
import net.minecraft.server.v1_13_R2.IChatBaseComponent;
import net.minecraft.server.v1_13_R2.PacketPlayOutOpenWindow;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

public final class NmsV1_13_2 implements Nms {

    public static final Nms INSTANCE = new NmsV1_13_2();

    @Override
    public void updateTitle(final Player player, final Object title) {
        final EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        final Container activeContainer = entityPlayer.activeContainer;
        final InventoryView view = activeContainer.getBukkitView();
        final Inventory topInventory = view.getTopInventory();
        final InventoryType type = topInventory.getType();
        if (
            type == InventoryType.WORKBENCH ||
            type == InventoryType.ANVIL ||
            type == InventoryType.CRAFTING ||
            type == InventoryType.PLAYER ||
            type == InventoryType.CREATIVE
        ) {
            return;
        }
        final int size = topInventory.getSize();
        final int windowId = activeContainer.windowId;
        final String containerName = this.toContainerName(type);
        final PacketPlayOutOpenWindow packet = new PacketPlayOutOpenWindow(
            windowId,
            containerName,
            this.toComponent(title),
            size
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

    private String toContainerName(final InventoryType type) {
        switch (type) {
            case CHEST:
            case ENDER_CHEST:
                return "minecraft:chest";
            case DISPENSER:
                return "minecraft:dispenser";
            case DROPPER:
                return "minecraft:dropper";
            case FURNACE:
                return "minecraft:furnace";
            case ENCHANTING:
                return "minecraft:enchanting_table";
            case BREWING:
                return "minecraft:brewing_stand";
            case MERCHANT:
                return "minecraft:villager";
            case BEACON:
                return "minecraft:beacon";
            case HOPPER:
                return "minecraft:hopper";
            case SHULKER_BOX:
                return "minecraft:blue_shulker_box";
            case WORKBENCH:
            case ANVIL:
            case CREATIVE:
            case PLAYER:
            case CRAFTING:
                break;
        }
        throw new IllegalArgumentException("Invalid inventory type!");
    }
}
