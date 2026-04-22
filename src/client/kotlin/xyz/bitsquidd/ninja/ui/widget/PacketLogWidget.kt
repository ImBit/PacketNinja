package xyz.bitsquidd.ninja.ui.widget

import com.noxcrew.sheeplib.util.opacity
import com.noxcrew.sheeplib.util.opaqueColor
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component
import xyz.bitsquidd.ninja.handler.PacketType
import xyz.bitsquidd.ninja.ui.log.PacketLogEntry
import xyz.bitsquidd.ninja.ui.log.PacketLogStore

class PacketLogWidget(
    width: Int,
    height: Int,
    private val showIncoming: () -> Boolean,
    private val showOutgoing: () -> Boolean,
) : AbstractWidget(0, 0, width, height, Component.empty()) {

    companion object {
        private const val LINE_HEIGHT = 9
        private const val ENTRY_PADDING = 3
        private const val SCROLL_STEP = LINE_HEIGHT + ENTRY_PADDING
    }

    private var scrollOffset = 0

    override fun mouseScrolled(mouseX: Double, mouseY: Double, scrollX: Double, scrollY: Double): Boolean {
        if (!isMouseOver(mouseX, mouseY)) return false
        scrollOffset = maxOf(0, scrollOffset - scrollY.toInt() * SCROLL_STEP)
        return true
    }

    private fun filteredEntries(): List<PacketLogEntry> = PacketLogStore.getEntries().filter { entry ->
        when (entry.type) {
            PacketType.CLIENTBOUND -> showIncoming()
            PacketType.SERVERBOUND -> showOutgoing()
        }
    }

    override fun extractWidgetRenderState(graphics: GuiGraphicsExtractor, mouseX: Int, mouseY: Int, partialTick: Float) {
        val font = Minecraft.getInstance().font
        val entries = filteredEntries()

        if (entries.isEmpty()) {
            val hint = if (!showIncoming() && !showOutgoing()) {
                Component.literal("Select a direction filter above to see packets.")
            } else {
                Component.literal("No packets captured yet. Use /packets start.")
            }
            graphics.text(font, hint, x + 4, y + height / 2 - 4, 0x707070.opaqueColor())
            return
        }

        // Calculate entry heights and total content height
        data class EntryLayout(val entry: PacketLogEntry, val lineCount: Int, val height: Int)

        val layouts = entries.map { entry ->
            val lines = font.split(entry.component, width - 6)
            val lc = lines.size
            EntryLayout(entry, lc, lc * LINE_HEIGHT + ENTRY_PADDING)
        }

        val totalHeight = layouts.sumOf { it.height }
        val maxScroll = maxOf(0, totalHeight - height)
        scrollOffset = scrollOffset.coerceIn(0, maxScroll)

        graphics.enableScissor(x, y, x + width, y + height)

        // Render newest entries at the bottom, oldest at the top
        var drawY = y + height - totalHeight + scrollOffset

        layouts.forEachIndexed { index, layout ->
            val entryBottom = drawY + layout.height
            if (entryBottom >= y && drawY <= y + height) {
                val bg = when (layout.entry.type) {
                    PacketType.CLIENTBOUND -> if (index % 2 == 0) 0x1a3a28 opacity 220 else 0x1e4030 opacity 220
                    PacketType.SERVERBOUND -> if (index % 2 == 0) 0x3a1a28 opacity 220 else 0x3e1e2e opacity 220
                }
                graphics.fill(x, drawY, x + width, drawY + layout.height, bg)
                graphics.textWithWordWrap(font, layout.entry.component, x + 3, drawY + 1, width - 6, 0xDDDDDD.opaqueColor())
            }
            drawY += layout.height
        }

        graphics.disableScissor()
    }

    override fun updateWidgetNarration(narrationElementOutput: NarrationElementOutput): Unit = Unit
}
