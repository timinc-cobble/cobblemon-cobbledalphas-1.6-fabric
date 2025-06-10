package us.timinc.mc.cobblemon.cobbledalphas.config

import us.timinc.mc.cobblemon.cobbledalphas.api.fabric.AbstractConfig

class CobbledAlphasConfig : AbstractConfig() {
    val shuffleSpecialMoves: Boolean = false
    val defaultGuaranteedMaxIvs: Int = 3
    val defaultLevelBoost: Int = 10
    val defaultSpecialMoves: List<String> = emptyList()
    val defaultChance: Float = 100F
    val blockCaptureOnLowObedience: Boolean = false
}