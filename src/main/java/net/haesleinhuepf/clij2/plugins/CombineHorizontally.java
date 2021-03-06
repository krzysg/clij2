package net.haesleinhuepf.clij2.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.clearcl.interfaces.ClearCLImageInterface;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

/**
 * Author: @haesleinhuepf
 *         May 2020
 */

@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_combineHorizontally")
public class CombineHorizontally extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput {
    @Override
    public String getInputType() {
        return "Image";
    }

    @Override
    public String getOutputType() {
        return "Image";
    }

    @Override
    public String getCategories() {
        return "Transform";
    }

    @Override
    public boolean executeCL() {
        return getCLIJ2().combineHorizontally((ClearCLImageInterface)( args[0]), (ClearCLImageInterface)(args[1]), (ClearCLImageInterface)(args[2]));
    }

    public static boolean combineHorizontally(CLIJ2 clij2, ClearCLImageInterface stack1, ClearCLImageInterface stack2, ClearCLImageInterface dst) {
        if (stack1.getWidth() != stack2.getWidth() || stack1.getHeight() != stack2.getHeight())  {
            clij2.set(dst, 0);
        }

        clij2.paste(stack1, dst, 0, 0, 0);
        clij2.paste(stack2, dst, stack1.getWidth(), 0, 0);
        return true;
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input) {
        ClearCLBuffer stack1 = (ClearCLBuffer) args[0];
        ClearCLBuffer stack2 = (ClearCLBuffer) args[1];

        if (Math.max(stack1.getDimension(), stack2.getDimension()) == 2) {
            return getCLIJ2().create(new long[]{
                    stack1.getWidth() + stack2.getWidth(),
                    Math.max(stack1.getHeight(), stack2.getHeight())
            }, input.getNativeType());
        } else {
            return getCLIJ2().create(new long[]{
                    stack1.getWidth() + stack2.getWidth(),
                    Math.max(stack1.getHeight(), stack2.getHeight()),
                    Math.max(stack1.getDepth(), stack2.getDepth())
            }, input.getNativeType());
        }

    }

    @Override
    public String getParameterHelpText() {
        return "Image stack1, Image stack2, ByRef Image destination";
    }

    @Override
    public String getDescription() {
        return "Combines two images or stacks in X.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }
}
