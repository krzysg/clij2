package net.haesleinhuepf.clij2.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
import net.haesleinhuepf.clij.coremem.enums.NativeTypeEnum;
import net.haesleinhuepf.clij.macro.CLIJMacroPlugin;
import net.haesleinhuepf.clij.macro.CLIJOpenCLProcessor;
import net.haesleinhuepf.clij.macro.documentation.OffersDocumentation;
import net.haesleinhuepf.clij2.AbstractCLIJ2Plugin;
import net.haesleinhuepf.clij2.CLIJ2;
import net.haesleinhuepf.clij2.utilities.HasClassifiedInputOutput;
import net.haesleinhuepf.clij2.utilities.IsCategorized;
import org.scijava.plugin.Plugin;

import java.util.HashMap;

/**
 * Author: @haesleinhuepf
 *         October 2019
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_countTouchingNeighbors")
public class CountTouchingNeighbors extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput {
    @Override
    public String getInputType() {
        return "Matrix";
    }

    @Override
    public String getOutputType() {
        return "Vector";
    }

    @Override
    public String getCategories() {
        return "Measurements";
    }

    @Override
    public boolean executeCL() {
        Object[] args = openCLBufferArgs();
        boolean result = getCLIJ2().countTouchingNeighbors((ClearCLBuffer)( args[0]), (ClearCLBuffer)(args[1]));
        releaseBuffers(args);
        return result;
    }

    public static boolean countTouchingNeighbors(CLIJ2 clij2, ClearCLBuffer src_touch_matrix, ClearCLBuffer dst_count_list) {
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("src_touch_matrix", src_touch_matrix);
        parameters.put("dst_count_list", dst_count_list);

        long[] globalSizes = {src_touch_matrix.getWidth(), 1, 1};

        clij2.activateSizeIndependentKernelCompilation();
        clij2.execute(CountTouchingNeighbors.class, "count_touching_neighbors_x.cl", "count_touching_neighbors", globalSizes, globalSizes, parameters);
        return true;
    }

    @Override
    public String getParameterHelpText() {
        return "Image touch_matrix, ByRef Image touching_neighbors_count_destination";
    }

    @Override
    public ClearCLBuffer createOutputBufferFromSource(ClearCLBuffer input)
    {
        ClearCLBuffer output = clij.createCLBuffer(new long[]{input.getWidth(), 1, 1}, NativeTypeEnum.Float);
        return output;
    }

    @Override
    public String getDescription() {
        return "Takes a touch matrix as input and delivers a vector with number of touching neighbors per label as a vector.";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D -> 1D";
    }
}
