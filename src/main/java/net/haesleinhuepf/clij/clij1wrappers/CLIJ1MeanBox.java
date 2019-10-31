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
public class CLIJ1MeanBox{
   
    /**
     * Computes the local mean average of a pixels rectangular neighborhood. The rectangles size is specified by 
     * its half-width and half-height (radius).
     */
    public boolean meanBox(CLIJ clij, ClearCLImage arg1, ClearCLImage arg2, int arg3, int arg4, int arg5) {
        return Kernels.meanBox(clij, arg1, arg2, arg3, arg4, arg5);
    }

}
