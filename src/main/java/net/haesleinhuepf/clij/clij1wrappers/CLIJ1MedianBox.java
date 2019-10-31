package net.haesleinhuepf.clij.clij1wrappers;
import net.haesleinhuepf.clij.CLIJ;
import net.haesleinhuepf.clij.clearcl.ClearCLKernel;
import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.ClearCLImage;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.imglib2.realtransform.AffineTransform2D;
import net.imglib2.realtransform.AffineTransform3D;
import net.haesleinhuepf.clij.kernels.Kernels;
// this is generated code. See src/test/java/net/haesleinhuepf/clijx/codegenerator for details
public class CLIJ1MedianBox{
   
    /**
     * Computes the local median of a pixels rectangular neighborhood. The rectangle is specified by 
     * its half-width and half-height (radius).
     * 
     * For technical reasons, the area of the rectangle must have less than 1000 pixels.
     */
    public boolean medianBox(CLIJ clij, ClearCLBuffer source, ClearCLBuffer destination, double radiusX, double radiusY) {
        return Kernels.medianBox(clij, source, destination, new Double (radiusX).intValue(), new Double (radiusY).intValue());
    }

}
