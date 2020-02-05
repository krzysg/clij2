package net.haesleinhuepf.clijx.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.ClearCLImage;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clijx.CLIJx;
import net.haesleinhuepf.clijx.utilities.AbstractCLIJxPlugin;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

/**
 * Author: @haesleinhuepf
 * December 2018
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJx_setWhereXgreaterThanY")
public class SetWhereXgreaterThanY extends AbstractCLIJxPlugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation {

    @Override
    public boolean executeCL() {
        boolean result = setWhereXgreaterThanY(getCLIJx(), (ClearCLBuffer)( args[0]),  asFloat(args[1]));
        return result;
    }


    public static boolean setWhereXgreaterThanY(CLIJx clijx, ClearCLImageInterface clImage, Float value) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("dst", clImage);
        parameters.put("value", value);

        clijx.execute(SetWhereXgreaterThanY.class, "setWhereXgreaterThanY_" + clImage.getDimension() + "d_x.cl", "set_where_x_greater_than_y_" + clImage.getDimension() + "d", clImage.getDimensions(), clImage.getDimensions(),  parameters);
        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image source, Number value";
    }

    @Override
    public String getDescription() {
        return "Sets all pixel values a of a given image A to a constant value v in case its coordinates x > y. " +
                "Otherwise the pixel is not overwritten.\nIf you want to initialize an identity transfrom matrix, set all pixels to 0 first.\n\n" +
                "<pre>f(a) = v</pre>";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }
}
