package foxiwhitee.FoxAE2Upgrade.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import foxiwhitee.FoxAE2Upgrade.client.render.CableBusTextures;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.lwjgl.opengl.GL11;

public class ClientEventHandler {
    @SubscribeEvent
    public void updateTextureSheet(TextureStitchEvent.Pre ev) {
        if (ev.map.getTextureType() == 0)
            for (CableBusTextures cb : CableBusTextures.values())
                cb.registerIcon(ev.map);
    }

}
