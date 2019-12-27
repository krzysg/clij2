package net.haesleinhuepf.clijx.advancedfilters;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.utilities.AbstractCLIJxPlugin;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

import static net.haesleinhuepf.clij.utilities.CLIJUtilities.assertDifferent;

/**
 * Author: @haesleinhuepf
 * 12 2018
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_minimumSliceBySliceSphere")
public class MinimumSliceBySliceSphere extends AbstractCLIJxPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        int radiusX = asInteger(args[2]);
        int radiusY = asInteger(args[3]);

        return minimum3DSliceBySliceSphere(getCLIJx(), (ClearCLBuffer)( args[0]), (ClearCLBuffer)(args[1]), radiusX, radiusY);
    }

    public static boolean minimum3DSliceBySliceSphere(CLIJx clijx, ClearCLImageInterface src, ClearCLImageInterface dst, Integer kernelSizeX, Integer kernelSizeY) {
        assertDifferent(src, dst);

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src", src);
        parameters.put("dst", dst);
        parameters.put("Nx", kernelSizeX);
        parameters.put("Ny", kernelSizeY);

        clijx.execute(MinimumSliceBySliceSphere.class, "minimum_slice_by_slice_sphere_3d_x.cl", "minimum_slice_by_slice_sphere_3d", dst.getDimensions(), dst.getDimensions(), parameters);
        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image source, Image destination, Number radiusX, Number radiusY";
    }

    @Override
    public String getDescription() {
        return "Computes the local minimum of a pixels ellipsoidal 2D neighborhood in an image stack \n" +
                "slice by slice. The ellipses size is specified by its half-width and half-height (radius).\n\n" +
                "This filter is applied slice by slice in 2D.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "3D";
    }
}
