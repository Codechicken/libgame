package codechicken.libgame.ui.components;

import java.awt.image.WritableRaster;
import codechicken.libgame.Serial;

public final class Image2D implements Renderable, Serial<Image2D> {

	final int[][] pixels;
	final int xd, yd;

	Image2D(int[][] pixels, int xdim, int ydim) {
		this.pixels = pixels;
		xd = xdim;
		yd = ydim;
		if ((xd * yd * 3) != pixels.length)
			throw new ArrayStoreException("Invalid Array");

	}

	@Override
	public WritableRaster render(WritableRaster base, int[][] rects) {
		for (int[] i : rects) {
			int x, y, w, h, offset;
			x = i[0];
			y = i[1];
			w = i[2];
			h = i[3];
			offset = y * xd + x;
			for (int j = 0; j < h; j++) {
				for (int k = 0; k < w; k++) {
					base.setPixel(x + k, y + h, pixels[offset + k + h * xd]);
				}
			}
		}
		return base;
	}

	@Override
	public Image2D load(String in) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String put(Image2D ob) {
		// TODO Auto-generated method stub
		return null;
	}


}
