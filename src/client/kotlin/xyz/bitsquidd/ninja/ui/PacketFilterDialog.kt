package xyz.bitsquidd.ninja.ui

import com.noxcrew.sheeplib.dialog.Dialog
import com.noxcrew.sheeplib.dialog.title.TextTitleWidget
import com.noxcrew.sheeplib.layout.grid
import com.noxcrew.sheeplib.theme.DefaultTheme
import com.noxcrew.sheeplib.theme.Themed
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.narration.NarrationElementOutput
import net.minecraft.network.chat.Component
import xyz.bitsquidd.ninja.ui.widget.PacketListWidget

class PacketFilterDialog(x: Int, y: Int) : Dialog(x, y), Themed by DefaultTheme {

    companion object {
        const val FILTER_WIDTH = 240
        const val LIST_HEIGHT = 180
        const val DESCRIPTION_HEIGHT = 28
    }

    private val listWidget = PacketListWidget(FILTER_WIDTH, LIST_HEIGHT)

    private val searchBox = EditBox(
        Minecraft.getInstance().font,
        0, 0,
        FILTER_WIDTH,
        theme.dimensions.buttonHeight,
        Component.literal("Search packets..."),
    ).apply {
        setHint(Component.literal("Search packets..."))
        setResponder { query -> listWidget.searchQuery = query }
    }

    private val descriptionWidget = DescriptionWidget(FILTER_WIDTH, DESCRIPTION_HEIGHT) {
        listWidget.hoveredHandler?.description ?: ""
    }

    override val title = TextTitleWidget(this, Component.literal("Packet Filters"))

    override fun layout() = grid {
        searchBox.atBottom(0)
        listWidget.atBottom(0)
        descriptionWidget.atBottom(0)
    }

    private inner class DescriptionWidget(
        width: Int,
        height: Int,
        private val getText: () -> String,
    ) : AbstractWidget(0, 0, width, height, Component.empty()) {

        override fun extractWidgetRenderState(
            graphics: GuiGraphicsExtractor,
            mouseX: Int,
            mouseY: Int,
            partialTick: Float,
        ) {
            val text = getText()
            if (text.isEmpty()) return
            graphics.fill(x, y, x + width, y + height, 0x1a1a1a.or(0xFF000000.toInt()))
            graphics.textWithWordWrap(
                Minecraft.getInstance().font,
                Component.literal(text),
                x + 4, y + 4,
                width - 8,
                0xA0A0A0.or(0xFF000000.toInt()),
            )
        }

        override fun updateWidgetNarration(output: NarrationElementOutput): Unit = Unit
    }
}
