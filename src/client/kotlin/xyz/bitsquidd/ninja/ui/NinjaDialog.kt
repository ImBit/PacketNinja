package xyz.bitsquidd.ninja.ui

import com.noxcrew.sheeplib.dialog.Dialog
import com.noxcrew.sheeplib.dialog.title.TextTitleWidget
import com.noxcrew.sheeplib.layout.grid
import com.noxcrew.sheeplib.theme.DefaultTheme
import com.noxcrew.sheeplib.theme.Themed
import com.noxcrew.sheeplib.widget.ThemedButton
import net.minecraft.client.gui.layouts.LinearLayout
import net.minecraft.network.chat.Component
import xyz.bitsquidd.ninja.ui.widget.PacketLogWidget

class NinjaDialog(x: Int, y: Int) : Dialog(x, y), Themed by DefaultTheme {

    companion object {
        const val DIALOG_HEIGHT = 240
        const val DIALOG_WIDTH = 320
        const val LOG_HEIGHT = 200
    }

    private var showIncoming = false
    private var showOutgoing = false

    private val logWidget = PacketLogWidget(
        DIALOG_WIDTH - theme.dimensions.paddingOuter * 2,
        LOG_HEIGHT,
        { showIncoming },
        { showOutgoing },
    )

    override val title = TextTitleWidget(this, Component.literal("Packet Ninja"))

    override fun layout() = grid {
        val controlBar = LinearLayout.horizontal().apply {
            val pad = this@NinjaDialog.theme.dimensions.paddingInner
            defaultCellSetting().paddingRight(pad)
            addChild(makeDirectionButton("Incoming", showIncoming) { showIncoming = !showIncoming; init() })
            addChild(makeDirectionButton("Outgoing", showOutgoing) { showOutgoing = !showOutgoing; init() })
            addChild(
                ThemedButton(
                    message = Component.literal("Filters"),
                    theme = this@NinjaDialog,
                    clickHandler = { openFilterDialog() },
                )
            )
        }

        controlBar.atBottom(0)
        logWidget.atBottom(0)
    }

    private fun makeDirectionButton(label: String, active: Boolean, onClick: () -> Unit) = ThemedButton(
        message = Component.literal(label),
        theme = this,
        style = if (active) theme.buttonStyles.positive else theme.buttonStyles.standard,
        clickHandler = onClick,
    )

    private fun openFilterDialog() {
        popup(PacketFilterDialog(x + width + 4, y))
    }

    override fun onClose() {
        NinjaDialogManager.onDialogClosed()
        super.onClose()
    }
}
