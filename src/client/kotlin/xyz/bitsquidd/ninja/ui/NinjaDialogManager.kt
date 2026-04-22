package xyz.bitsquidd.ninja.ui

import com.noxcrew.sheeplib.DialogContainer
import net.minecraft.client.Minecraft
import xyz.bitsquidd.ninja.PacketInterceptorMod

object NinjaDialogManager {
    private var dialog: NinjaDialog? = null

    fun toggle() {
        if (dialog != null) {
            close()
        } else {
            open()
        }
    }

    fun open() {
        if (dialog != null) return
        val mc = Minecraft.getInstance()
        val x = mc.window.guiScaledWidth / 2 - NinjaDialog.DIALOG_WIDTH / 2
        val y = mc.window.guiScaledHeight / 2 - NinjaDialog.DIALOG_HEIGHT / 2
        val d = NinjaDialog(x, y)
        PacketInterceptorMod.logPackets = true
        DialogContainer += d
        dialog = d
    }

    fun close() {
        dialog?.close()
        dialog = null
    }

    fun onDialogClosed() {
        dialog = null
    }
}
