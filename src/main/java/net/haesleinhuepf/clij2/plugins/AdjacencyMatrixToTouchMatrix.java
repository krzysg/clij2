package net.haesleinhuepf.clij2.plugins;

import net.haesleinhuepf.clij.clearcl.ClearCLBuffer;
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
 *         April 2020
 */
@Plugin(type = CLIJMacroPlugin.class, name = "CLIJ2_adjacencyMatrixToTouchMatrix")
public class AdjacencyMatrixToTouchMatrix extends AbstractCLIJ2Plugin implements CLIJMacroPlugin, CLIJOpenCLProcessor, OffersDocumentation, IsCategorized, HasClassifiedInputOutput {
    @Override
    public String getInputType() {
        return "Matrix";
    }

    @Override
    public String getOutputType() {
        return "Matrix";
    }


    @Override
    public String getParameterHelpText() {
        return "Image adjacency_matrix, ByRef Image touch_matrix";
    }

    @Override
    public boolean executeCL() {
        ClearCLBuffer touch_matrix = (ClearCLBuffer) args[0];
        ClearCLBuffer adjacency_matrix = (ClearCLBuffer) args[1];

        return getCLIJ2().adjacencyMatrixToTouchMatrix(touch_matrix, adjacency_matrix);
    }

    public static boolean adjacencyMatrixToTouchMatrix(CLIJ2 clij2, ClearCLBuffer touch_matrix, ClearCLBuffer adjacency_matrix) {
        ClearCLBuffer temp = clij2.create(touch_matrix);
        clij2.transposeXY(touch_matrix, temp);

        clij2.binaryOr(touch_matrix, temp, adjacency_matrix);
        clij2.setWhereXequalsY(adjacency_matrix, 0);
        clij2.setWhereXgreaterThanY(adjacency_matrix, 0);

        temp.close();

        return true;
    }

    @Override
    public String getDescription() {
        return "Converts a adjacency matrix in a touch matrix";
    }

    @Override
    public String getAvailableForDimensions() {
        return "2D, 3D";
    }

    @Override
    public String getCategories() {
        return "Graph, Transform";
    }
}
