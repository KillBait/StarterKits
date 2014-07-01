package killbait.starterkits.common.client.gui;

import killbait.starterkits.common.utils.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiButtonImage extends GuiButton {

    public GuiButtonImage(int par1, int par2, int par3, int par4, int par5, String par6Str,int imagePosX,int imagePosY,int imageSizeX,int imageSizeY,ResourceLocation resourceLocation) {
        super(par1, par2, par3, par4, par5, par6Str);
        this.imageResourceLocation = resourceLocation;
        this.imageX = imagePosX;
        this.imageY = imagePosY;
        this.imageSizeX = imageSizeX;
        this.imageSizeY = imageSizeY;
    }

    private ResourceLocation imageResourceLocation;
    private int imageX;
    private int imageY;
    private int imageSizeX;
    private int imageSizeY;

    @Override
    public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
    {
        if (this.visible) {
            FontRenderer fontrenderer = p_146112_1_.fontRenderer;
            p_146112_1_.getTextureManager().bindTexture(this.imageResourceLocation);

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = p_146112_2_ >= this.xPosition && p_146112_3_ >= this.yPosition && p_146112_2_ < this.xPosition + this.width && p_146112_3_ < this.yPosition + this.height;
            // hoverState returns 1 when not over button or 2 when we are over button
            //int k = this.getHoverState(this.field_146123_n);
            int k = (this.enabled) ? this.getHoverState(this.field_146123_n) : 3;
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, this.imageX, this.imageY + (k - 1) * this.imageSizeX, this.imageSizeX, this.imageSizeY);
            this.mouseDragged(p_146112_1_, p_146112_2_, p_146112_3_);
            int l = 14737632;

            if (packedFGColour != 0) {
                l = packedFGColour;
            } else if (!this.enabled) {
                l = 10526880;
            } else if (this.field_146123_n) {
                l = 16777120;
            }

            if (this.displayString.length() != 0) {
                this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
            }
        }
    }

}
