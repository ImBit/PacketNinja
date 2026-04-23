package xyz.bitsquidd.ninja.ui.widget

import com.noxcrew.sheeplib.util.opacity
import com.noxcrew.sheeplib.util.opaqueColor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.client.input.MouseButtonEvent
import net.minecraft.network.chat.Component
import xyz.bitsquidd.ninja.PacketInterceptorMod
import xyz.bitsquidd.ninja.PacketRegistry
import xyz.bitsquidd.ninja.handler.PacketHandler

class PacketListWidget(
    width: Int,
    height: Int,
    var searchQuery: String = "",
) : AbstractWidget(0, 0, width, height, Component.empty()) {

    companion object {
        const val ROW_HEIGHT = 16
    }

    private var scrollOffset = 0
    var hoveredHandler: PacketHandler<*>? = null
        private set

    private fun filteredHandlers(): List<PacketHandler<*>> {
        val query = searchQuery.trim().lowercase()
        return PacketRegistry.getAllHandlers().filter { handler ->
            query.isEmpty() || handler.friendlyName.lowercase().contains(query)
        }
    }

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        if (!isMouseOver(mouseX, mouseY)) return false
        val handlers = filteredHandlers()
        val maxScroll = maxOf(0, handlers.size * ROW_HEIGHT - height)
        scrollOffset = (scrollOffset - scrollY.toInt() * ROW_HEIGHT).coerceIn(0, maxScroll)
        return true
    }

    override fun mouseClicked(event: MouseButtonEvent, doubleClick: Boolean): Boolean {
        if (!isMouseOver(event.x, event.y)) return false
        val relY = event.y.toInt() - y + scrollOffset
        val index = relY / ROW_HEIGHT
        val handlers = filteredHandlers()
        if (index in handlers.indices) {
            PacketInterceptorMod.getInstance().packetFilter.togglePacketFilter(handlers[index])
            return true
        }
        return false
    }

    override fun extractWidgetRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
        val font = Minecraft.getInstance().font
        val handlers = filteredHandlers()

        graphics.enableScissor(x, y, x + width, y + height)

        val relMouseY = mouseY - y + scrollOffset
        val hoveredIndex = if (isMouseOver(mouseX.toDouble(), mouseY.toDouble())) relMouseY / ROW_HEIGHT else -1
        hoveredHandler = if (hoveredIndex in handlers.indices) handlers[hoveredIndex] else null

        handlers.forEachIndexed { index, handler ->
            val rowY = y + index * ROW_HEIGHT - scrollOffset
            if (rowY + ROW_HEIGHT < y || rowY > y + height) return@forEachIndexed

            val isEnabled = PacketInterceptorMod.getInstance().packetFilter.isPacketEnabled(handler.packetClass)
            val isHovered = index == hoveredIndex

            val baseBg = if (isEnabled) 0x666666 else 0x333333
            val bg = if (isHovered) baseBg + 0x0a0a0a else baseBg
            graphics.fill(x, rowY, x + width, rowY + ROW_HEIGHT, bg opacity 240)

            val dotColor = if (isEnabled) {
                handler.packetType.primaryColor.value()
            } else {
                0x505050
            }
            val dotX = x + 5
            val dotY = rowY + (ROW_HEIGHT - 6) / 2
            graphics.fill(dotX, dotY, dotX + 6, dotY + 6, dotColor.opaqueColor())

            // Handler name in packet-type color
            val nameText = Component.literal(handler.friendlyName)
            val nameColor = handler.packetType.primaryColor.value().opaqueColor()
            graphics.text(font, nameText, x + 16, rowY + (ROW_HEIGHT - 8) / 2, nameColor)
        }

        graphics.disableScissor()
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput): Unit = Unit
}
