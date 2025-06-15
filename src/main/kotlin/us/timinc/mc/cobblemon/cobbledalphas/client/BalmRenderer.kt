package us.timinc.mc.cobblemon.cobbledalphas.client

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemDisplayContext
import us.timinc.mc.cobblemon.cobbledalphas.entity.BalmEntity
import us.timinc.mc.cobblemon.cobbledalphas.registry.CobbledAlphasBalms

class BalmRenderer(ctx: EntityRendererProvider.Context) : EntityRenderer<BalmEntity>(ctx) {

    override fun render(
        balm: BalmEntity,
        entityYaw: Float,
        partialTicks: Float,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
    ) {
        poseStack.pushPose()
//        val consumer = ItemRenderer.getFoilBuffer(
//            buffer, RenderType.entityCutoutNoCull(
//                getTextureLocation(balm)
//            ), false, false
//        )
//        model.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY)
        Minecraft.getInstance().itemRenderer.renderStatic(
            balm.item,
            ItemDisplayContext.GROUND,
            packedLight,
            OverlayTexture.NO_OVERLAY,
            poseStack,
            buffer,
            balm.level(),
            0
        )

        poseStack.popPose()
        super.render(balm, entityYaw, partialTicks, poseStack, buffer, packedLight)
    }

    override fun getTextureLocation(balmEntity: BalmEntity): ResourceLocation {
        val balmResourceLocation = CobbledAlphasBalms.BALMS.getResourceKey(balmEntity.balm).get().location()
        return ResourceLocation.fromNamespaceAndPath(
            balmResourceLocation.namespace,
            "textures/entity/${balmResourceLocation.path}.png"
        )
    }
}
