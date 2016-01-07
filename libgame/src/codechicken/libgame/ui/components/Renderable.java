package codechicken.libgame.ui.components;

import java.awt.image.WritableRaster;

public interface Renderable {
WritableRaster render(WritableRaster base,int[][] rects);
}
